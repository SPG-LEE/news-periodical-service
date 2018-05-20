package sq.news.periodical.service;

import java.util.Date;
import java.util.List;

import sq.base.ServiceResult;
import sq.news.periodical.entity.Periodical;

public interface PeriodicalService {
	Periodical findById(long id);
	ServiceResult<List<Periodical>> findAll(int pageSize, int pageNum, String title);
	void save(Periodical entity);
	void update(Periodical entity);
	void updateAll(List<Periodical> entitys);
	void delete(long id);
    Periodical findNewestPeriodical(Periodical indexPeriodical, boolean hasShow);

	List<Periodical> findOldBefore(Date publishDate, int pageNumber, int pageSize);

	Periodical findByIdHasAudit(long id);

	Periodical findByIdAndHasShow(Long id, boolean hasShow);

	List<Periodical> findOldBeforeAndHasShow(Date publishDate, boolean hasShow, int pageNumber, int pageSize);
}
