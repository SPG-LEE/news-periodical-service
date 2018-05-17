package sq.news.periodical.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sq.news.periodical.entity.PeriodicalEdition;

import java.util.List;

public interface EditionRepository extends JpaRepository<PeriodicalEdition, Long>,
		PeriodicalCustomRepository {

	Page<PeriodicalEdition> findByTitleLike(String title, Pageable pageRequest);

    List<PeriodicalEdition> findByPeriodicalId(Long id);
}
