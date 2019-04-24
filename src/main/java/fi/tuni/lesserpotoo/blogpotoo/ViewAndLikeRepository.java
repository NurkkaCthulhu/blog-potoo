package fi.tuni.lesserpotoo.blogpotoo;

import org.springframework.data.repository.CrudRepository;

import javax.swing.text.View;
import java.util.Optional;

public interface ViewAndLikeRepository extends CrudRepository<ViewAndLike, CompositeKey> {
    Optional<ViewAndLike> findByUserIdAndBlogPostId(int userId, int blogPostId);
}
