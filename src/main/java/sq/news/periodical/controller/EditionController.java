package sq.news.periodical.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sq.base.AppResult;
import sq.base.ServiceResult;
import sq.bean.Admin;
import sq.constans.RestConstans;
import sq.news.periodical.entity.PeriodicalEdition;
import sq.news.periodical.service.AdminRedisService;
import sq.news.periodical.service.EditionService;
import sq.util.AppResultBuilder;

import java.util.List;


@RestController
@RequestMapping(path = "/editions")
public class EditionController {
    @Autowired
    private EditionService editionService;

    @Autowired
    private AdminRedisService adminRedisService;

    @GetMapping
    @ApiOperation(value = "获取版次列表")
    public AppResult<List<PeriodicalEdition>> getEditions(@RequestHeader("x-access-token") final
                                                      String token, @RequestParam(required = false, defaultValue = "0") int pageNumber
            , @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false) String title
    ) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "edition:list");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        ServiceResult<List<PeriodicalEdition>> findResult = editionService.findAll(pageSize, pageNumber, title);
        return AppResultBuilder.buildSuccessMessageResult(findResult.getData(), RestConstans.FIND_SUCCESS.getName(), findResult.getTotal());
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "获取版次详情")
    public AppResult<PeriodicalEdition> findById(@RequestHeader("x-access-token") final
                                                      String token, @PathVariable long id) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "edition:list");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        PeriodicalEdition result = editionService.findById(id);
        return AppResultBuilder.buildSuccessMessageResult(result, RestConstans.FIND_SUCCESS.getName());
    }
    @PostMapping
    @ApiOperation(value = "保存版次")
    public AppResult<PeriodicalEdition> save(@RequestHeader("x-access-token") final
                                          String token, @RequestBody PeriodicalEdition periodical) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "edition:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        editionService.save(periodical);
        return AppResultBuilder.buildSuccessMessageResult(periodical,RestConstans.FIND_SUCCESS.getName());
    }
    @PutMapping("/{id}")
    @ApiOperation(value = "修改版次")
    public AppResult<PeriodicalEdition> update(@RequestHeader("x-access-token") final
                                          String token, @PathVariable long id, @RequestBody PeriodicalEdition periodical) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "edition:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        if (id!=periodical.getId()){
            return AppResultBuilder.buildFailedMessageResult(RestConstans.SUBMIT_ERROR.getName());
        }
        editionService.update(periodical);
        return AppResultBuilder.buildSuccessMessageResult(periodical,RestConstans.FIND_SUCCESS.getName());
    }
   
    @DeleteMapping("/{id}")
    @ApiOperation(value = "修改版次")
    public AppResult<Void> update(@RequestHeader("x-access-token") final
                                        String token, @PathVariable long id) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "edition:delete");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        editionService.delete(id);
        return AppResultBuilder.buildSuccessMessageResult(RestConstans.FIND_SUCCESS.getName());
    }
}
