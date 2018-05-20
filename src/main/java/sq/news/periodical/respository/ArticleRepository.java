package sq.news.periodical.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sq.news.periodical.entity.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>,
		ArticleCustomRepository {

	Page<Article> findByTitleLike(String title, Pageable pageRequest);

    Article findByIdAndHasShowAndHasAudit(long id, boolean b, boolean b1);

	Article findByIdAndHasAudit(long id, boolean b);

    List<Article> findByEditionId(long id);
}
