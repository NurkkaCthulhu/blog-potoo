package fi.tuni.lesserpotoo.blogpotoo;

import org.springframework.data.repository.CrudRepository;

public interface BlogPostRepository extends CrudRepository<BlogPost, Integer> {
}
