package sq.news.periodical.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import sq.news.periodical.bean.PublishMessageBean;
import sq.news.periodical.bean.weixin.DepartmentResponse;
import sq.news.periodical.bean.weixin.UserResponse;
import sq.news.periodical.entity.Department;
import sq.news.periodical.entity.Periodical;
import sq.news.periodical.entity.PeriodicalEdition;
import sq.news.periodical.entity.User;
import sq.news.periodical.respository.DepartmentRepository;
import sq.news.periodical.respository.EditionRepository;
import sq.news.periodical.respository.PeriodicalRepository;
import sq.news.periodical.respository.UserRepository;
import sq.news.periodical.service.JobService;
import sq.news.periodical.util.QyWeixinUtil;
import sq.util.JsonUtil;

import java.util.List;
import java.util.Optional;

@Repository
public class JobServiceImpl implements JobService {

    private static String WEB_INDEX = "http%3a%2f%2fneikan.shengquan.com%2ftest%2fniekan519%2findex.html";
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PeriodicalRepository periodicalRepository;
    @Autowired
    private EditionRepository editionRepository;
    @Autowired
    private RedisTemplate redisTemplate;
    private final static String SYN_USER_CRON = "59 59 23 * * ?";

    @Override
    @Scheduled(cron = SYN_USER_CRON)
    public void synUser() {
        String departmentsJson = QyWeixinUtil.getDepartments(redisTemplate);
        if (departmentsJson != null) {
            DepartmentResponse departmentResponse = JsonUtil.toObject(departmentsJson, DepartmentResponse.class);
            if (departmentResponse.getDepartment() == null) {
                return;
            }
            departmentResponse.getDepartment().stream().forEach(department -> {
                Department needSaveDepartment;
                List<Department> findDepartment = departmentRepository.findByDepartmentId(department.getId() + "");
                if (findDepartment.size() > 0) {
                    needSaveDepartment = findDepartment.get(0);
                } else {
                    needSaveDepartment = new Department();
                }
                needSaveDepartment.copyFrom(department);
                departmentRepository.save(needSaveDepartment);
                String usersJson = QyWeixinUtil.getUsersByDepartmentId(redisTemplate, department.getId() + "");
                UserResponse userResponse = JsonUtil.toObject(usersJson, UserResponse.class);
                if (userResponse.getUserlist() == null) {
                    return;
                }
                userResponse.getUserlist().stream().forEach(user -> {
                    List<User> findUser = userRepository.findByUserId(user.getUserid());
                    User needSaveUser;
                    if (findUser.size() > 0) {
                        needSaveUser = findUser.get(0);
                    } else {
                        needSaveUser = new User();
                    }
                    needSaveUser.copyFrom(user);
                    userRepository.save(needSaveUser);
                });
            });
        }
    }

    @Override
    public void synUserTest() {
        String usersJson = QyWeixinUtil.getUsersByDepartmentId(redisTemplate, 213 + "");
        if (usersJson == null) {
            return;
        }
        System.err.println("usersJson:" + usersJson);
        UserResponse userResponse = JsonUtil.toObject(usersJson, UserResponse.class);
        if (userResponse.getUserlist() == null) {
            return;
        }
        userResponse.getUserlist().stream().forEach(user -> {
            List<User> findUser = userRepository.findByUserId(user.getUserid());
            User needSaveUser;
            if (findUser.size() > 0) {
                needSaveUser = findUser.get(0);
            } else {
                needSaveUser = new User();
            }
            needSaveUser.copyFrom(user);
            userRepository.save(needSaveUser);
        });
    }

    @Override
    public User getUserByAuthCode(String code) {
        String userIdResponse = QyWeixinUtil.getUserIdByAuthCode(redisTemplate, code);
        JSONObject userIdJson = JSONObject.parseObject(userIdResponse);
        String userId = userIdJson.getString("UserId");
        if (userId == null) {
            userId = userIdJson.getString("OpenId");
        }
        List<User> findUsers = userRepository.findByUserId(userId);
        if (findUsers.size()>0){
            return findUsers.get(0);
        }
        return null;
    }

    @Override
    public String sendMessage(PublishMessageBean publishMessageBean) {
        String departmentId = null;
        String userId = null;
        StringBuffer departmentIds = new StringBuffer();
        StringBuffer userIds = new StringBuffer();
        if (publishMessageBean.getDepartmentIds() != null) {
            for (String departmentIdStr : publishMessageBean.getDepartmentIds()) {
                departmentIds.append(departmentIdStr);
                departmentIds.append("|");
            }
            departmentIds.setLength(departmentIds.length() - 1);
            departmentId = departmentIds.toString();
        } else if(publishMessageBean.getUserIds()!=null) {
            for (String userIdStr : publishMessageBean.getUserIds()) {
                userIds.append(userIdStr);
                userIds.append("|");
            }
            userIds.setLength(userIds.length() - 1);
            userId = userIds.toString();
        }else{
                userId = "@all";
        }
        Optional<Periodical> periodicalOptional = periodicalRepository.findById(publishMessageBean.getPeriodicalId());
        if (periodicalOptional == null) {
            return "期刊不存在";
        }
        Periodical periodical = periodicalOptional.get();
        List<PeriodicalEdition> editions = editionRepository.findByPeriodicalId(publishMessageBean.getPeriodicalId());
        String picUrl = null;
        if (editions.size() > 0) {
            picUrl = editions.get(0).getImage();
        }
      return  QyWeixinUtil.sendMessage(redisTemplate, WEB_INDEX, picUrl, periodical.getTitle(), periodical.getDescription(), departmentId, userId);
    }
}
