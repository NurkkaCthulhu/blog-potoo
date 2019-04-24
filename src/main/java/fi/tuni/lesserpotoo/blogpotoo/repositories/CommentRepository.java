package fi.tuni.lesserpotoo.blogpotoo.repositories;

import fi.tuni.lesserpotoo.blogpotoo.entities.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
}
