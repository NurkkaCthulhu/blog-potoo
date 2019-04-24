package fi.tuni.lesserpotoo.blogpotoo.repositories;

import fi.tuni.lesserpotoo.blogpotoo.entities.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0423
 * @since 2.0
 */
public interface TagRepository extends CrudRepository<Tag, Integer> {
    Optional<Tag> findTagByTagNameIgnoreCase(String tagName);
}
