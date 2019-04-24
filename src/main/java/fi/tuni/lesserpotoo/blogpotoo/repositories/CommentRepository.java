package fi.tuni.lesserpotoo.blogpotoo.repositories;

import fi.tuni.lesserpotoo.blogpotoo.entities.Comment;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0424
 * @since 3.0
 */
public interface CommentRepository extends CrudRepository<Comment, Integer> {
}
