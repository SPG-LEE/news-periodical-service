package sq.news.periodical.service;

import java.util.List;

import sq.base.ServiceResult;
import sq.news.periodical.entity.Periodical;

public interface PeriodicalService {
	Periodical findById(long id);
	ServiceResult<List<Periodical>> findAll(int pageSize, int pageNum, String title);
	void save(Periodical entity);
	void update(Periodical entity);
	void delete(long id);

}