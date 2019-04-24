package fi.tuni.lesserpotoo.blogpotoo.repositories;

import fi.tuni.lesserpotoo.blogpotoo.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0418
 * @since 3.0
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsernameIgnoreCaseAndPasswordIn(String username, String password);
    Optional<User> findByUsernameIgnoreCase(String username);
}
