package fi.tuni.lesserpotoo.blogpotoo.repositories;

import fi.tuni.lesserpotoo.blogpotoo.entities.BlogPost;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface BlogPostRepository extends CrudRepository<BlogPost, Integer> {
    public List<BlogPost> findByAuthorUsername(String author);
    public List<BlogPost> findByTitleContainingIgnoreCase(String word);
    public List<BlogPost> findByDateOfCreation(LocalDate date);
    public List<BlogPost> findAllByOrderByIdDesc();
}
