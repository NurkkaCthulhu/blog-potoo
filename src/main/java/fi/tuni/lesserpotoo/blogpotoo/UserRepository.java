package fi.tuni.lesserpotoo.blogpotoo;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
