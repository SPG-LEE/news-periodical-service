package sq.news.periodical.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sq.base.AppResult;
import sq.base.ServiceResult;
import sq.bean.Admin;
import sq.constans.RestConstans;
import sq.news.periodical.entity.Article;
import sq.news.periodical.entity.ArticleComment;
import sq.news.periodical.entity.User;
import sq.news.periodical.respository.UserRepository;
import sq.news.periodical.service.AdminRedisService;
import sq.news.periodical.service.ArticleService;
import sq.util.AppResultBuilder;
import sq.util.FormatUtil;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private AdminRedisService adminRedisService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @ApiOperation(value = "获取文章列表")
    public AppResult<List<Article>> getArticles(@RequestHeader("x-access-token") final
                                                String token, @RequestParam(required = false, defaultValue = "0") int pageNumber
            , @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false) String title
    ) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "article:list");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        ServiceResult<List<Article>> findResult = articleService.findAll(pageSize, pageNumber, title);
        return AppResultBuilder.buildSuccessMessageResult(findResult.getData(), RestConstans.FIND_SUCCESS.getName(), findResult.getTotal());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取文章详情")
    public AppResult<Article> findById(@RequestHeader("x-access-token") final
                                       String token, @PathVariable long id) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "article:list");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        Article result = articleService.findById(id);
        return AppResultBuilder.buildSuccessMessageResult(result, RestConstans.FIND_SUCCESS.getName());
    }

    @GetMapping("/web/{id}")
    @ApiOperation(value = "获取文章详情")
    public AppResult<Article> findById(@PathVariable long id, @RequestParam String userId) {
        List<User> findUsers = userRepository.findByUserId(userId);
        if (findUsers.size() == 0) {
            return AppResultBuilder.buildFailedMessageResult("无权查看");
        }
        if (findUsers.get(0).getStatus() != 1) {
            Article result = articleService.findByIdAndHasShow(id);
            return AppResultBuilder.buildSuccessMessageResult(result, RestConstans.FIND_SUCCESS.getName());
        }
        Article result = articleService.findByIdAndHasAudit(id);
        if (result == null) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.FIND_FAILED.getName());
        }
        long newCount = result.getReadCount() + 1;
        result.setReadCount(newCount);
        articleService.save(result);
        result.setComments(articleService.findComments(id));
        return AppResultBuilder.buildSuccessMessageResult(result, RestConstans.FIND_SUCCESS.getName());
    }

    @GetMapping("/{id}/comment")
    @ApiOperation(value = "获取评论列表")
    public AppResult<List<ArticleComment>> findComments(@RequestHeader("x-access-token") final
                                                        String token, @PathVariable long id, @RequestParam(required = false, defaultValue = "0") int pageNumber
            , @RequestParam(required = false, defaultValue = "10") int pageSize) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "article:list");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        List<ArticleComment> comments = articleService.findComments(id, pageNumber, pageSize);
        long count = articleService.countComments(id);
        return AppResultBuilder.buildSuccessMessageResult(comments, RestConstans.FIND_SUCCESS.getName(), count);
    }

    @PostMapping("/comment/approve")
    @ApiOperation(value = "批量审核评论")
    public AppResult<Void> approveComments(@RequestHeader("x-access-token") final
                                           String token, @RequestBody List<Long> ids) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "article:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        List<ArticleComment> results = articleService.findCommentsByIds(ids);
        results.stream().forEach(result -> {
            result.setHasAudit(true);
        });
        articleService.saveAllComments(results);
        return AppResultBuilder.buildSuccessMessageResult(RestConstans.FIND_SUCCESS.getName());
    }

    @PostMapping("/comment/unApprove")
    @ApiOperation(value = "批量反审核评论")
    public AppResult<Void> unApproveComments(@RequestHeader("x-access-token") final
                                             String token, @RequestBody List<Long> ids) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "article:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        List<ArticleComment> results = articleService.findCommentsByIds(ids);
        results.stream().forEach(result -> {
            result.setHasAudit(false);
        });
        articleService.saveAllComments(results);
        return AppResultBuilder.buildSuccessMessageResult(RestConstans.FIND_SUCCESS.getName());
    }

    @PostMapping("/web/{id}/comment")
    @ApiOperation(value = "评论")
    public AppResult<String> saveComment(@PathVariable long id, @RequestBody ArticleComment comment) {
        comment.setArticleId(id);
        if (!FormatUtil.isNullOrEmpty(comment.getUserId())) {
            List<User> findUsers = userRepository.findByUserId(comment.getUserId());
            if (findUsers.size() > 0) {
                comment.setAvtar(findUsers.get(0).getAvatar());
                comment.setUserName(findUsers.get(0).getName());
                articleService.saveComment(comment);
                return AppResultBuilder.buildSuccessMessageResult(RestConstans.DISCUSS_SUCCESS.getName());
            }
        }
        return AppResultBuilder.buildFailedMessageResult(RestConstans.DISCUSS_FAILED.getName());
    }

    @PostMapping
    @ApiOperation(value = "保存文章")
    public AppResult<Article> save(@RequestHeader("x-access-token") final
                                   String token, @RequestBody Article periodical) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "article:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        periodical.setAuthorId(adminAppResult.getData().getId());
        periodical.setAuthor(adminAppResult.getData().getName());
        articleService.save(periodical);
        return AppResultBuilder.buildSuccessMessageResult(periodical, RestConstans.FIND_SUCCESS.getName());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改文章")
    public AppResult<Article> update(@RequestHeader("x-access-token") final
                                     String token, @PathVariable long id, @RequestBody Article periodical) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "article:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        periodical.setAuthorId(adminAppResult.getData().getId());
        periodical.setAuthor(adminAppResult.getData().getName());
        if (id != periodical.getId()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.SUBMIT_ERROR.getName());
        }
        articleService.update(periodical);
        return AppResultBuilder.buildSuccessMessageResult(periodical, RestConstans.FIND_SUCCESS.getName());
    }

    @PutMapping("/show")
    @ApiOperation(value = "显示文章")
    public AppResult<List<Article>> show(@RequestHeader("x-access-token") final
                                         String token, @RequestBody List<Long> ids) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "article:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        List<Article> needUpdate = new ArrayList<>();
        for (long id : ids) {
            Article article = articleService.findById(id);
            article.setHasShow(true);
            needUpdate.add(article);
        }
        articleService.updateAll(needUpdate);
        return AppResultBuilder.buildSuccessMessageResult(needUpdate, RestConstans.FIND_SUCCESS.getName());
    }

    @PutMapping("/unShow")
    @ApiOperation(value = "取消显示文章")
    public AppResult<List<Article>> unShow(@RequestHeader("x-access-token") final
                                           String token, @RequestBody List<Long> ids) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "article:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        List<Article> needUpdate = new ArrayList<>();
        for (long id : ids) {
            Article article = articleService.findById(id);
            article.setHasShow(false);
            needUpdate.add(article);
        }
        articleService.updateAll(needUpdate);
        return AppResultBuilder.buildSuccessMessageResult(needUpdate, RestConstans.FIND_SUCCESS.getName());
    }

    @PutMapping("/unApprove")
    @ApiOperation(value = "反审核文章")
    public AppResult<List<Article>> unApprove(@RequestHeader("x-access-token") final
                                              String token, @RequestBody List<Long> ids) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "article:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        List<Article> needUpdate = new ArrayList<>();
        for (long id : ids) {
            Article article = articleService.findById(id);
            article.setHasAudit(false);
            needUpdate.add(article);
        }
        articleService.updateAll(needUpdate);
        return AppResultBuilder.buildSuccessMessageResult(needUpdate, RestConstans.FIND_SUCCESS.getName());
    }

    @PutMapping("/approve")
    @ApiOperation(value = "审核文章")
    public AppResult<List<Article>> approve(@RequestHeader("x-access-token") final
                                            String token, @RequestBody List<Long> ids) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "article:edit");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        List<Article> needUpdate = new ArrayList<>();
        for (long id : ids) {
            Article article = articleService.findById(id);
            article.setHasAudit(true);
            needUpdate.add(article);
        }
        articleService.updateAll(needUpdate);
        return AppResultBuilder.buildSuccessMessageResult(needUpdate, RestConstans.FIND_SUCCESS.getName());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除文章")
    public AppResult<Void> update(@RequestHeader("x-access-token") final
                                  String token, @PathVariable long id) {
        AppResult<Admin> adminAppResult = adminRedisService.getAdmin(token);
        if (!adminAppResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_ADMIN.getName());
        }
        AppResult<Boolean> permissionResult = adminRedisService.hasPermission(token, "article:delete");
        if (!permissionResult.isSuccess() || !permissionResult.getData()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstans.NO_PERMISSION.getName());

        }
        articleService.delete(id);
        return AppResultBuilder.buildSuccessMessageResult(RestConstans.FIND_SUCCESS.getName());
    }
}
