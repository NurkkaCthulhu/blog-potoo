package fi.tuni.lesserpotoo.blogpotoo;

import org.springframework.data.repository.CrudRepository;

public interface ViewAndLikeRepository extends CrudRepository<ViewAndLike, CompositeKey> {
}
