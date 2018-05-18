package sq.news.periodical.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import sq.news.periodical.entity.Department;
import sq.news.periodical.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>,
		DepartmentCustomRepository {

	List<User> findByUserId(String id);
}
