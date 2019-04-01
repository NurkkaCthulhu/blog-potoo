package fi.tuni.lesserpotoo.blogpotoo;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag, Integer> {
    Optional<Tag> findTagByTagNameIgnoreCase(String tagName);
}
