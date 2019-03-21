package fi.tuni.lesserpotoo.blogpotoo;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface BlogPostRepository extends CrudRepository<BlogPost, Integer> {
    public List<BlogPost> findByAuthor(String author);
    public List<BlogPost> findByTitleContainingIgnoreCase(String word);

    // public List<BlogPost> findByTimeOfCreation_YearAndTimeOfCreation_MonthAndTimeOfCreation_DayOfMonth(int year, int month, int date);
    // public List<BlogPost> findByTimeOfCreationOrderByTimeOfCreationDesc(LocalDateTime timeOfCreation);
}
