package sq.news.periodical.service;

import sq.base.ServiceResult;
import sq.news.periodical.entity.Article;
import sq.news.periodical.entity.ArticleComment;

import java.util.List;

public interface ArticleService {
	Article findById(long id);
	Article findByIdAndHasAudit(long id);
	ServiceResult<List<Article>> findAll(int pageSize, int pageNum, String title);
	void save(Article entity);
	void update(Article entity);
	List<ArticleComment> findComments(long articleId);
	void delete(long id);
	void saveComment(ArticleComment comment);

    void updateAll(List<Article> needUpdate);

	Article findByIdAndHasShow(long id);

	List<Article> findByEditionId(long id);
}
