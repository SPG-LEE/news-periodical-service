package sq.news.periodical.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import sq.base.AppResult;
import sq.base.ServiceResult;
import sq.bean.Admin;
import sq.constans.RestConstans;
import sq.news.periodical.bean.PublishMessageBean;
import sq.news.periodical.entity.Article;
import sq.news.periodical.service.AdminRedisService;
import sq.news.periodical.service.ArticleService;
import sq.news.periodical.service.JobService;
import sq.news.periodical.util.QyWeixinUtil;
import sq.util.AppResultBuilder;

import java.util.List;


@RestController
@RequestMapping(path = "/jobs")
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private AdminRedisService adminRedisService;

    @GetMapping("/test")
    @ApiOperation(value = "获取测试部门成员")
    public AppResult<String> getTestMembers(@RequestHeader("x-access-token") final
                                         String token) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "job:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        jobService.synUserTest();
        return AppResultBuilder.buildSuccessMessageResult(RestConstans.FIND_SUCCESS.getName());
    }
    @GetMapping("/init")
    @ApiOperation(value = "获取所有成员")
    @ApiIgnore
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
    @PostMapping("/sendMessage")
    @ApiOperation(value = "推送期刊")
    public AppResult<String> sendMessage(@RequestHeader("x-access-token") final
                                        String token,@RequestBody PublishMessageBean publishMessageBean) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "job:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        List<String> departmentIdList = publishMessageBean.getDepartmentIds();
        String message = jobService.sendMessage(publishMessageBean);
        return AppResultBuilder.buildSuccessMessageResult(message,RestConstans.FIND_SUCCESS.getName());
    }
    @PostMapping("/sendMessage/test")
    @ApiOperation(value = "推送期刊")
    public AppResult<String> sendMessageTest(@RequestHeader("x-access-token") final
                                         String token,@RequestBody PublishMessageBean publishMessageBean) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "job:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        String message = jobService.sendMessage(publishMessageBean);
        return AppResultBuilder.buildSuccessMessageResult(message,RestConstans.FIND_SUCCESS.getName());
    }
}
