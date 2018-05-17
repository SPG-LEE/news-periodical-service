package sq.news.periodical.respository.impl;

import org.springframework.stereotype.Repository;
import sq.news.periodical.respository.ArticleCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ArticleRepositoryImpl implements ArticleCustomRepository {
	@PersistenceContext
	private EntityManager entityManager;
}
