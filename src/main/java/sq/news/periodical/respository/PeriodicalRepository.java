package sq.news.periodical.respository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sq.enumeration.EntityStatus;
import sq.news.periodical.entity.Periodical;

public interface PeriodicalRepository extends JpaRepository<Periodical, Long>,
		PeriodicalCustomRepository {

	Page<Periodical> findByTitleLike(String title, Pageable pageRequest);

    List<Periodical> findByPublishDateBefore(Date publishDate, Pageable pageable);
}
