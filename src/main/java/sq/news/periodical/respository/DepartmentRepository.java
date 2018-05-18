package sq.news.periodical.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import sq.news.periodical.entity.Department;
import sq.news.periodical.entity.HotZone;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long>,
		DepartmentCustomRepository {

	List<Department> findByDepartmentId(String id);
}
