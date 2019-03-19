package fi.tuni.lesserpotoo.blogpotoo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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
    }

    @PostMapping(value = "/api/blogposts")
    public void saveBlogPost(@RequestBody BlogPost blogPost) {
        System.out.println(blogPost);
        blogPostRepository.save(blogPost);
    }

    @GetMapping("/api/blogposts")
    public Iterable<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    @GetMapping("/api/blogposts/{blogPostId}")
    public Optional<BlogPost> getBlogPostById(@PathVariable int blogPostId) {
        return blogPostRepository.findById(blogPostId);
    }

    @GetMapping("/api/blogposts/author={authorName}")
    public Iterable<BlogPost> getBlogPostsByAuthor(@PathVariable String authorName) {
        return blogPostRepository.findByAuthor(authorName);
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "" + blogPostRepository.findAll();
    }
}