package sq.news.periodical.service;

import sq.base.ServiceResult;
import sq.news.periodical.entity.Article;
import sq.news.periodical.entity.ArticleComment;
import sq.news.periodical.entity.Department;

import java.util.List;

public interface DepartmentService {
	ServiceResult<List<Department>> findAll(int pageSize, int pageNum);

}
