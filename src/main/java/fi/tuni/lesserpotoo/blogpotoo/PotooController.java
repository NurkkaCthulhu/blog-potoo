package fi.tuni.lesserpotoo.blogpotoo;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
public class PotooController {

    @Autowired
    BlogPostRepository blogPostRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void init() {

        Tag potooTag = new Tag("potoo");
        Tag firstTag = new Tag("first");
        Tag lastTag = new Tag("last");

        tagRepository.save(potooTag);
        tagRepository.save(firstTag);
        tagRepository.save(lastTag);

        User user1 = new User("General Potoo", "password", UserType.ADMIN);
        User user2 = new User("Some owl", "guest", UserType.DELETED);
        User user3 = new User("Mom Potoo", "pain", UserType.VISITOR);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        BlogPost post1= new BlogPost("General Potoo", "What is potoo?", "The great potoo (Nyctibius grandis) is a near passerine bird, both the largest potoo species and the largest member of the order Caprimulgiformes (nightjars and allies). They are also one of seven species in one genus, Nyctibius, located in tropical America.\n" +
                "Much like owls, this species is nocturnal. They prey on large insects and small vertebrates, which they capture in sallies from high perches.\n" +
                "Possibly its most well known characteristic is its unique moaning growl that the Great Potoo vocalizes throughout the night, creating an unsettling atmosphere in the Neotropics with its nocturnal sounds.");
        BlogPost post2 = new BlogPost("General Potoo", "Potoo > Owl", "its just true");
        BlogPost post3 = new BlogPost("Baby Potoo", "Father feed me", "I'm hungry");
        BlogPost post4 = new BlogPost("Potoo mom", "Days of Potooing", "It is good to be a potoo. I recommend. I feel despair but it is completely ok.");

        post1.getTags().add(potooTag);
        post1.getTags().add(firstTag);
        post2.getTags().add(potooTag);
        post3.getTags().add(potooTag);
        post4.getTags().add(potooTag);
        post4.getTags().add(lastTag);

        blogPostRepository.save(post1);
        blogPostRepository.save(post2);
        blogPostRepository.save(post3);
        blogPostRepository.save(post4);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------   POST MAPPINGS   -----------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @PostMapping(value = "/api/blogposts")
    public int saveBlogPost(@RequestBody BlogPost blogPost) {
        blogPost.getTags().clear();
        blogPostRepository.save(blogPost);
        return blogPost.getId();
    }

    @PostMapping("/api/blogposts/{blogPostId}/tag")
    public void addTagsToBlogPost(@PathVariable int blogPostId, @RequestBody List<String> tagNames) {
        Optional<BlogPost> blogPostO = blogPostRepository.findById(blogPostId);

        if (blogPostO.isPresent()) {
            BlogPost blogPost = blogPostO.get();

            for (String tagName : tagNames) {
                Optional<Tag> tag = tagRepository.findTagByTagNameIgnoreCase(tagName);

                if (tag.isPresent()) {
                    blogPost.getTags().add(tag.get());
                } else {
                    Tag newTag = new Tag(tagName.toLowerCase());
                    tagRepository.save(newTag);
                    blogPost.getTags().add(newTag);
                }
            }

            blogPostRepository.save(blogPost);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // ----------------------------------------------   DELETE MAPPINGS   ----------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @DeleteMapping("/api/blogposts/{blogPostId}")
    public void deleteBlogPost(@PathVariable int blogPostId) {
        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(blogPostId);
        if (blogPostOptional.isPresent()) {
            BlogPost blogPost = blogPostOptional.get();
            LinkedList<Tag> tagsToBeRemoved = new LinkedList<>();

            for (Tag t : blogPost.getTags()) {
                if (t.getBlogPosts().size() > 1) {
                    t.getBlogPosts().remove(blogPost);
                } else {
                    tagsToBeRemoved.add(t);
                }
            }

            if (tagsToBeRemoved.size() > 0) {
                for (Tag t : tagsToBeRemoved) {
                    blogPost.getTags().remove(t);
                }

                tagRepository.deleteAll(tagsToBeRemoved);
            }

            blogPostRepository.deleteById(blogPostId);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------   GET MAPPINGS   ------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private Iterable<Integer> getIdsOfThesePosts(Iterable<BlogPost> blogPosts) {
        LinkedList<Integer> allIds = new LinkedList<>();
        blogPosts.forEach(x -> allIds.add(x.getId()));
        return allIds;
    }

    @GetMapping("/api/blogposts")
    public Iterable<Integer> getAllBlogPosts() {
        return getIdsOfThesePosts(blogPostRepository.findAll());
    }

    @GetMapping("/api/blogposts/{blogPostId}")
    public Optional<BlogPost> getBlogPostById(@PathVariable int blogPostId) {
        return blogPostRepository.findById(blogPostId);
    }

    @GetMapping("/api/blogposts/author/{authorName}")
    public Iterable<Integer> getBlogPostsByAuthor(@PathVariable String authorName) {
        return getIdsOfThesePosts(blogPostRepository.findByAuthor(authorName));
    }

    @GetMapping("/api/blogposts/title/{containingWord}")
    public Iterable<Integer> getBlogPostsByTitleContaining(@PathVariable String containingWord) {
        return getIdsOfThesePosts(blogPostRepository.findByTitleContainingIgnoreCase(containingWord));
    }

    @GetMapping("/api/blogposts/tag/{tagName}")
    public Iterable<Integer> getBlogPostsByTag(@PathVariable String tagName) {
        Optional<Tag> tag = tagRepository.findTagByTagNameIgnoreCase(tagName);

        if (tag.isPresent()) {
            return getIdsOfThesePosts(tag.get().getBlogPosts());
        } else {
            return new LinkedList<Integer>();
        }
    }

    @GetMapping("/api/blogposts/date/{date:[0-9]{4}-[0-9]{2}-[0-9]{2}}")
    public Iterable<Integer> getBlogPostsByDateAsc(@PathVariable String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            return getIdsOfThesePosts(blogPostRepository.findByDateOfCreation(localDate));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/api/blogposts/search_all/{keyWord}")
    public Iterable<Integer> getBlogPostsByKeyWord(@PathVariable String keyWord) {
        HashSet<Integer> blogPostIds = new HashSet<>();

        for (Integer blogPostId : getBlogPostsByAuthor(keyWord)) {
            blogPostIds.add(blogPostId);
        }

        for (Integer blogPostId : getBlogPostsByTag(keyWord)) {
            blogPostIds.add(blogPostId);
        }

        for (Integer blogPostId : getBlogPostsByTitleContaining(keyWord)) {
            blogPostIds.add(blogPostId);
        }

        return blogPostIds;
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "" + blogPostRepository.findAll();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------   PUT MAPPINGS   ------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @PutMapping("/api/blogposts/{blogPostId}")
    public void updateBlogPost(@PathVariable int blogPostId, @RequestBody ObjectNode updateJson) {
        updateBlogPostTitle(blogPostId, updateJson.get("title").asText());
        updateBlogPostContent(blogPostId, updateJson.get("content").asText());

        ArrayNode tagArray = (ArrayNode) updateJson.get("tags");
        LinkedList<String> tags = new LinkedList<>();

        for (int i = 0; i < tagArray.size(); i++) {
            tags.add(tagArray.get(i).asText());
        }

        updateBlogPostTags(blogPostId, tags);
    }


    @PutMapping("/api/blogposts/{blogPostId}/title")
    public void updateBlogPostTitle(@PathVariable int blogPostId, @RequestBody String title) {
        Optional<BlogPost> blogPostOpt = blogPostRepository.findById(blogPostId);

        if (blogPostOpt.isPresent()) {
            blogPostOpt.get().setTimeOfEdit(LocalDateTime.now());
            blogPostOpt.get().setTitle(title);
            blogPostRepository.save(blogPostOpt.get());
        }
    }

    @PutMapping("/api/blogposts/{blogPostId}/content")
    public void updateBlogPostContent(@PathVariable int blogPostId, @RequestBody String content) {
        Optional<BlogPost> blogPostOpt = blogPostRepository.findById(blogPostId);

        if (blogPostOpt.isPresent()) {
            blogPostOpt.get().setTimeOfEdit(LocalDateTime.now());
            blogPostOpt.get().setContent(content);
            blogPostRepository.save(blogPostOpt.get());
        }
    }

    @PutMapping("/api/blogposts/{blogPostId}/tags")
    public void updateBlogPostTags(@PathVariable int blogPostId, @RequestBody List<String> tags) {
        Optional<BlogPost> blogPostOpt = blogPostRepository.findById(blogPostId);

        if (blogPostOpt.isPresent()) {
            BlogPost blogPost = blogPostOpt.get();
            LinkedList<Tag> removeTags = new LinkedList<>();

            for (Tag tag : blogPost.getTags()) {
                if (tag.getBlogPosts().size() == 1) {
                    removeTags.add(tag);
                }
            }

            blogPost.getTags().clear();
            tagRepository.deleteAll(removeTags);

            blogPostOpt.get().setTimeOfEdit(LocalDateTime.now());
            blogPostRepository.save(blogPostOpt.get());

            addTagsToBlogPost(blogPostId, tags);
        }
    }
}