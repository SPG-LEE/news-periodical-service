package sq.news.periodical.service;

import sq.base.ServiceResult;
import sq.news.periodical.entity.PeriodicalEdition;

import java.util.List;

public interface EditionService {
	PeriodicalEdition findById(long id);
	ServiceResult<List<PeriodicalEdition>> findAll(int pageSize, int pageNum, String title);
	void save(PeriodicalEdition entity);
	void update(PeriodicalEdition entity);
	void delete(long id);

    List<PeriodicalEdition> findByPeriodicalId(Long id);
}
