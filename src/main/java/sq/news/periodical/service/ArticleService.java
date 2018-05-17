package sq.news.periodical.service;

import sq.base.ServiceResult;
import sq.news.periodical.entity.Article;

import java.util.List;

public interface ArticleService {
	Article findById(long id);
	ServiceResult<List<Article>> findAll(int pageSize, int pageNum, String title);
	void save(Article entity);
	void update(Article entity);
	void delete(long id);

}
