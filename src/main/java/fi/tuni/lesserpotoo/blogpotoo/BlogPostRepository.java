package fi.tuni.lesserpotoo.blogpotoo;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BlogPostRepository extends CrudRepository<BlogPost, Integer> {
    public List<BlogPost> findByAuthor(String author);
    public List<BlogPost> findByTimeOfCreationOrderByTimeOfCreationAsc(LocalDateTime timeOfCreation);
    public List<BlogPost> findByTimeOfCreationOrderByTimeOfCreationDesc(LocalDateTime timeOfCreation);
}
