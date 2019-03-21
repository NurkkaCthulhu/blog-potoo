package fi.tuni.lesserpotoo.blogpotoo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@RestController
public class PotooController {

    @Autowired
    BlogPostRepository blogPostRepository;

    @PostConstruct
    public void init() {
        blogPostRepository.save(new BlogPost("General Potoo", "What is potoo?", "The great potoo (Nyctibius grandis) is a near passerine bird, both the largest potoo species and the largest member of the order Caprimulgiformes (nightjars and allies). They are also one of seven species in one genus, Nyctibius, located in tropical America.\n" +
                "Much like owls, this species is nocturnal. They prey on large insects and small vertebrates, which they capture in sallies from high perches.\n" +
                "Possibly its most well known characteristic is its unique moaning growl that the Great Potoo vocalizes throughout the night, creating an unsettling atmosphere in the Neotropics with its nocturnal sounds."));

        blogPostRepository.save(new BlogPost("General Potoo", "Potoo > Owl", "its just true"));

        blogPostRepository.save(new BlogPost("Baby Potoo", "Father feed me", "I'm hungry"));

        blogPostRepository.save(new BlogPost("Potoo mom", "Days of Potooing", "It is good to be a potoo. I recommend. I feel despair but it is completely ok."));
    }

    @PostMapping(value = "/api/blogposts")
    public void saveBlogPost(@RequestBody BlogPost blogPost) {
        blogPostRepository.save(blogPost);
    }

    @DeleteMapping("/api/blogposts/{blogPostId}")
    public void deleteBlogPost(@PathVariable int blogPostId) {
        blogPostRepository.deleteById(blogPostId);
    }

    @GetMapping("/api/blogposts")
    public Iterable<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    @GetMapping("/api/blogposts/{blogPostId}")
    public Optional<BlogPost> getBlogPostById(@PathVariable int blogPostId) {
        return blogPostRepository.findById(blogPostId);
    }

    @GetMapping("/api/blogposts/author/{authorName}")
    public Iterable<BlogPost> getBlogPostsByAuthor(@PathVariable String authorName) {
        return blogPostRepository.findByAuthor(authorName);
    }

    @GetMapping("/api/blogposts/title/{containingWord}")
    public Iterable<BlogPost> getBlogPostsByTitleContaining(@PathVariable String containingWord) {
        return blogPostRepository.findByTitleContainingIgnoreCase(containingWord);
    }

    /**
    @GetMapping("/api/blogposts/date/{date:[0-9]{4}-[0-9]{2}-[0-9]{2}}")
    public Iterable<BlogPost> getBlogPostsByDateAsc(@PathVariable String date) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.of(0,0));
        System.out.println(localDateTime);
        return null;
    }*/

    @GetMapping("/api/hello")
    public String hello() {
        return "" + blogPostRepository.findAll();
    }
}