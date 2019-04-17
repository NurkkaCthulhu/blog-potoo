package fi.tuni.lesserpotoo.blogpotoo;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUserNameAndPasswordIn(String userName, String password);
}
