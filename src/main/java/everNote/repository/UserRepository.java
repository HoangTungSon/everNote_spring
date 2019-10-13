package everNote.repository;

import everNote.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Iterable<User> findAllByUsername(String username);

    User findByUsername(String username);
}
