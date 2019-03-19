package fi.tuni.lesserpotoo.blogpotoo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Optional;

@RestController
public class HelloController {

    @Autowired
    BlogPostRepository blogPostRepository;

    @PostConstruct
    public void init() {
        blogPostRepository.save(new BlogPost("General Potoo", "What is potoo?", "The great potoo (Nyctibius grandis) is a near passerine bird, both the largest potoo species and the largest member of the order Caprimulgiformes (nightjars and allies). They are also one of seven species in one genus, Nyctibius, located in tropical America.\n" +
                "Much like owls, this species is nocturnal. They prey on large insects and small vertebrates, which they capture in sallies from high perches.\n" +
                "Possibly its most well known characteristic is its unique moaning growl that the Great Potoo vocalizes throughout the night, creating an unsettling atmosphere in the Neotropics with its nocturnal sounds."));

        blogPostRepository.save(new BlogPost("General Potoo", "Potoo > Owl", "its just true"));
    }

    @PostMapping(value="/blogposts")
    public void saveCustomer(@RequestBody BlogPost blogPost) {
        System.out.println(blogPost);
        blogPostRepository.save(blogPost);
    }

    @GetMapping(value = "/blogposts")
    public Iterable<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    @GetMapping(value = "/blogposts/{blogPostId}")
    public Optional<BlogPost> getBlogPostById(@PathVariable int blogPostId) {
        return blogPostRepository.findById(blogPostId);
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "" + blogPostRepository.findAll();
    }
}