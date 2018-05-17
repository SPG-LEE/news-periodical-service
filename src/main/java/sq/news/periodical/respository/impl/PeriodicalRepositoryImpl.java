package sq.news.periodical.respository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sq.news.periodical.respository.PeriodicalCustomRepository;

@Repository
public class PeriodicalRepositoryImpl implements PeriodicalCustomRepository {
	@PersistenceContext
	private EntityManager entityManager;
}
