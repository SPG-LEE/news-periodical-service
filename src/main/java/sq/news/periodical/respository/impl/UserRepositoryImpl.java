package sq.news.periodical.respository.impl;

import org.springframework.stereotype.Repository;
import sq.news.periodical.respository.DepartmentCustomRepository;
import sq.news.periodical.respository.UserCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepositoryImpl implements UserCustomRepository {
	@PersistenceContext
	private EntityManager entityManager;
}
