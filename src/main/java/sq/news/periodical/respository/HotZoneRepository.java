package sq.news.periodical.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sq.news.periodical.entity.HotZone;
import sq.news.periodical.entity.Periodical;

import java.util.List;

public interface HotZoneRepository extends JpaRepository<HotZone, Long>,
		PeriodicalCustomRepository {

	List<HotZone> findByEditionId(long id);
}
