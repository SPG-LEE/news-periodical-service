package sq.news.periodical.respository.impl;

import org.springframework.stereotype.Repository;
import sq.news.periodical.respository.HotZoneCustomRepository;
import sq.news.periodical.respository.PeriodicalCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class HotZoneRepositoryImpl implements HotZoneCustomRepository {
	@PersistenceContext
	private EntityManager entityManager;
}
