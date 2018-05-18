package sq.news.periodical.controller;

import sq.base.AppResult;
import sq.base.ServiceResult;
import sq.bean.Admin;
import sq.constans.RestConstans;
import sq.news.periodical.entity.Periodical;
import sq.news.periodical.entity.PeriodicalEdition;
import sq.news.periodical.service.AdminRedisService;
import sq.news.periodical.service.EditionService;
import sq.news.periodical.service.PeriodicalService;
import sq.util.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(path = "/periodicals")
public class PeriodicalController {
    @Autowired
    private PeriodicalService periodicalService;
    @Autowired
    private EditionService editionService;

    @Autowired
    private AdminRedisService adminRedisService;

    @GetMapping
    @ApiOperation(value = "获取期刊列表")
    public AppResult<List<Periodical>> getPeriodicals(@RequestHeader("x-access-token") final
                                                      String token, @RequestParam(required = false, defaultValue = "0") int pageNumber
            , @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false) String title
    ) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "periodical:list");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        ServiceResult<List<Periodical>> findResult = periodicalService.findAll(pageSize, pageNumber, title);
        return AppResultBuilder.buildSuccessMessageResult(findResult.getData(), RestConstans.FIND_SUCCESS.getName(), findResult.getTotal());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取期刊详情")
    public AppResult<Periodical> findById(@RequestHeader("x-access-token") final
                                          String token, @PathVariable long id) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "periodical:list");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        Periodical result = periodicalService.findById(id);
        return AppResultBuilder.buildSuccessMessageResult(result, RestConstans.FIND_SUCCESS.getName());
    }

    @PostMapping
    @ApiOperation(value = "保存期刊")
    public AppResult<Periodical> save(@RequestHeader("x-access-token") final
                                      String token, @RequestBody Periodical periodical) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "periodical:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        periodicalService.save(periodical);
        return AppResultBuilder.buildSuccessMessageResult(periodical, RestConstans.FIND_SUCCESS.getName());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改期刊")
    public AppResult<Periodical> update(@RequestHeader("x-access-token") final
                                        String token, @PathVariable long id, @RequestBody Periodical periodical) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "periodical:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        if (id != periodical.getId()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.SUBMIT_ERROR.getName());
        }
        periodicalService.update(periodical);
        return AppResultBuilder.buildSuccessMessageResult(periodical, RestConstans.FIND_SUCCESS.getName());
    }

    @PutMapping("/{id}/approve")
    @ApiOperation(value = "审核期刊")
    public AppResult<Periodical> approve(@RequestHeader("x-access-token") final
                                         String token, @PathVariable long id) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "periodical:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        Periodical periodical = periodicalService.findById(id);
        periodical.setHasAudit(true);
        periodicalService.update(periodical);
        return AppResultBuilder.buildSuccessMessageResult(periodical, RestConstans.FIND_SUCCESS.getName());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "修改期刊")
    public AppResult<Void> update(@RequestHeader("x-access-token") final
                                  String token, @PathVariable long id) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "periodical:delete");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        periodicalService.delete(id);
        return AppResultBuilder.buildSuccessMessageResult(RestConstans.FIND_SUCCESS.getName());
    }

    @GetMapping("/web/newest")
    @ApiOperation(value = "web端首页最新期刊数据")
    public AppResult<Periodical> findNewPeriodical(@RequestParam(required = false) Long id) {
        Periodical indexPeriodical = null;
        if (id != null) {
            indexPeriodical = periodicalService.findById(id);
        }
        Periodical result = periodicalService.findNewestPeriodical(indexPeriodical);
        joinEdition(result);
        return AppResultBuilder.buildSuccessMessageResult(result, RestConstans.FIND_SUCCESS.getName());
    }

    @GetMapping("/web/oldList")
    @ApiOperation(value = "web端首页往期期刊数据")
    public AppResult<List<Periodical>> findOldList(@RequestParam(required = false, defaultValue = "0") int pageNumber
            , @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false) Long id) {
        Date publishDate = new Date();
        if (id != null) {
            Periodical indexPeriodical = periodicalService.findById(id);
            publishDate = indexPeriodical.getPublishDate();
        }
        List<Periodical> result = periodicalService.findOldBefore(publishDate,pageNumber,pageSize);
        return AppResultBuilder.buildSuccessMessageResult(result, RestConstans.FIND_SUCCESS.getName());
    }

    private void joinEdition(Periodical result) {
        List<PeriodicalEdition> editions = editionService.findByPeriodicalId(result.getId());
        result.setEditions(editions);
    }
}
