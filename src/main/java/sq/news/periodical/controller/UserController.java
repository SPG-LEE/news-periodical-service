package sq.news.periodical.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import sq.base.AppResult;
import sq.base.ServiceResult;
import sq.bean.Admin;
import sq.constans.RestConstans;
import sq.news.periodical.entity.Department;
import sq.news.periodical.entity.User;
import sq.news.periodical.service.AdminRedisService;
import sq.news.periodical.service.DepartmentService;
import sq.news.periodical.service.JobService;
import sq.news.periodical.util.QyWeixinUtil;
import sq.util.AppResultBuilder;

import java.util.List;


@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private AdminRedisService adminRedisService;

    @Autowired
    private JobService jobService;
    @Autowired
    private DepartmentService departmentService;
    @GetMapping
    @ApiOperation(value = "根据用户code获取用户")
//    @ApiIgnore
    public AppResult<User> getUser(@RequestParam String code) {
        User result= jobService.getUserByAuthCode(code);
        if (result == null){
            result = jobService.createVisitor();
        }
        return AppResultBuilder.buildSuccessMessageResult(result,RestConstans.FIND_SUCCESS.getName());
    }
    @GetMapping("/init")
    @ApiOperation(value = "获取所有成员")
    public AppResult<String> getMembers(@RequestHeader("x-access-token") final
                                         String token) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "job:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        jobService.synUser();
        return AppResultBuilder.buildSuccessMessageResult(RestConstans.FIND_SUCCESS.getName());
    }
    @GetMapping("/departments")
    @ApiOperation(value = "获取所有部门")
    public AppResult<List<Department>> getDepartments(@RequestHeader("x-access-token") final
                                        String token,@RequestParam(required = false,defaultValue = "0")int pageNum,@RequestParam(required = false,defaultValue = "20")int pageSize) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "job:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        ServiceResult<List<Department>> result = departmentService.findAll(pageSize, pageNum);
        return AppResultBuilder.buildSuccessMessageResult(result.getData(),result.getMessage(),result.getTotal());
    }
}
