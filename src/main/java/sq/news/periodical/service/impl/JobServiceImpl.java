package sq.news.periodical.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import sq.news.periodical.bean.weixin.DepartmentResponse;
import sq.news.periodical.bean.weixin.UserResponse;
import sq.news.periodical.entity.Department;
import sq.news.periodical.entity.User;
import sq.news.periodical.respository.DepartmentRepository;
import sq.news.periodical.respository.UserRepository;
import sq.news.periodical.service.JobService;
import sq.news.periodical.util.QyWeixinUtil;
import sq.util.JsonUtil;

import java.util.List;

@Repository
public class JobServiceImpl implements JobService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;
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
        System.err.println("usersJson:"+usersJson);
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
}
