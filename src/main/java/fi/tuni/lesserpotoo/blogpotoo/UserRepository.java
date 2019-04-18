package fi.tuni.lesserpotoo.blogpotoo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsernameIgnoreCaseAndPasswordIn(String username, String password);
    Optional<User> findByUsernameIgnoreCase(String username);
}
