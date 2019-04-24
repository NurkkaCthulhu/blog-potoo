package fi.tuni.lesserpotoo.blogpotoo.repositories;

import fi.tuni.lesserpotoo.blogpotoo.misc.CompositeKey;
import fi.tuni.lesserpotoo.blogpotoo.entities.ViewAndLike;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0424
 * @since 3.0
 */
public interface ViewAndLikeRepository extends CrudRepository<ViewAndLike, CompositeKey> {
    Optional<ViewAndLike> findByUserIdAndBlogPostId(int userId, int blogPostId);
}
