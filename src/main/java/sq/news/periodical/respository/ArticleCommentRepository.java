package sq.news.periodical.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sq.news.periodical.entity.Article;
import sq.news.periodical.entity.ArticleComment;

import java.util.List;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long>,
		ArticleCommentCustomRepository {

	List<ArticleComment> findByArticleIdAndAndHasShow(long articleId, boolean hasShow);
}
