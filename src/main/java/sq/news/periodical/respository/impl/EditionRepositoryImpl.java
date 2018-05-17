package sq.news.periodical.respository.impl;

import org.springframework.stereotype.Repository;
import sq.news.periodical.respository.EditionCustomRepository;
import sq.news.periodical.respository.PeriodicalCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class EditionRepositoryImpl implements EditionCustomRepository {
	@PersistenceContext
	private EntityManager entityManager;
}
