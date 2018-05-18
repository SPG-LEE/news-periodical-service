package sq.news.periodical.respository.impl;

import org.springframework.stereotype.Repository;
import sq.news.periodical.respository.DepartmentCustomRepository;
import sq.news.periodical.respository.HotZoneCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DepartmentRepositoryImpl implements DepartmentCustomRepository {
	@PersistenceContext
	private EntityManager entityManager;
}
