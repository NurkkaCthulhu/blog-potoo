package fi.tuni.lesserpotoo.blogpotoo;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.text.View;
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

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ViewAndLikeRepository viewAndLikeRepository;

    @PostConstruct
    public void init() {

        Tag potooTag = new Tag("potoo");
        Tag firstTag = new Tag("first");
        Tag lastTag = new Tag("last");

        tagRepository.save(potooTag);
        tagRepository.save(firstTag);
        tagRepository.save(lastTag);

        User user1 = new User("General Potoo", "password", UserType.ADMIN);
        User user2 = new User("Some owl", "pain", UserType.DELETED);
        User user3 = new User("Potoo Mom", "pain", UserType.VISITOR);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        BlogPost post1= new BlogPost(user1, "What is potoo?", "The great potoo (Nyctibius grandis) is a near passerine bird, both the largest potoo species and the largest member of the order Caprimulgiformes (nightjars and allies). They are also one of seven species in one genus, Nyctibius, located in tropical America.\n" +
                "Much like owls, this species is nocturnal. They prey on large insects and small vertebrates, which they capture in sallies from high perches.\n" +
                "Possibly its most well known characteristic is its unique moaning growl that the Great Potoo vocalizes throughout the night, creating an unsettling atmosphere in the Neotropics with its nocturnal sounds.");
        BlogPost post2 = new BlogPost(user1, "Potoo > Owl", "its just true");
        BlogPost post3 = new BlogPost(user3, "Father feed me", "I'm hungry");
        BlogPost post4 = new BlogPost(user3, "Days of Potooing", "It is good to be a potoo. I recommend. I feel despair but it is completely ok.");

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

        Comment comment1 = new Comment(user1, post2, "This is a great post, POTOO");
        Comment comment2 = new Comment(user2, post2, "certainly so!");
        Comment comment3 = new Comment(user3, post4, "my life");

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        ViewAndLike viewAndLike1 = new ViewAndLike(user1.getId(), post2.getId(), true, true);
        ViewAndLike viewAndLike2 = new ViewAndLike(user2.getId(), post2.getId(), true, false);

        viewAndLikeRepository.save(viewAndLike1);
        viewAndLikeRepository.save(viewAndLike2);
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

    @PostMapping(value = "/api/users")
    public Optional<User> saveUser(@RequestBody User user) {
        Optional<User> userOptional = userRepository.findByUsernameIgnoreCase(user.getUsername());

        if (userOptional.isPresent()) {
            return Optional.empty();
        } else {
            userRepository.save(user);
            return Optional.of(user);
        }
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

    @PostMapping("/api/blogposts/{blogPostId}/comments")
    public void addCommentToBlogPost(@PathVariable int blogPostId, @RequestBody ObjectNode commentInfo) {
        int userId = commentInfo.get("userId").asInt();
        String content = commentInfo.get("content").asText();
        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(blogPostId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (blogPostOptional.isPresent() && userOptional.isPresent()) {
            BlogPost blogPost = blogPostOptional.get();
            User user = userOptional.get();

            Comment newComment = new Comment(user, blogPost, content);
            commentRepository.save(newComment);
        }
    }

    @PostMapping("/api/blogposts/{blogPostId}/viewAndLike")
    public void addViewAndLikeToBlogPost(@PathVariable int blogPostId, @RequestBody ObjectNode viewAndLikeInfo) {
        int userId = viewAndLikeInfo.get("userId").asInt();
        boolean view = viewAndLikeInfo.get("view").asBoolean();
        boolean like = viewAndLikeInfo.get("like").asBoolean();

        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(blogPostId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (blogPostOptional.isPresent() && userOptional.isPresent()) {
            if (like) {
                BlogPost blogPost = blogPostOptional.get();
                blogPost.setLikes(blogPost.getLikes() + 1);
                blogPostRepository.save(blogPost);
            }
            viewAndLikeRepository.save(new ViewAndLike(userId, blogPostId, view, like));
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

    @DeleteMapping("/api/blogposts/{blogPostId}/comments/{commentId}")
    public void deleteCommentFromABlogPost(@PathVariable int blogPostId, @PathVariable int commentId) {
        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(blogPostId);
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (blogPostOptional.isPresent() && commentOptional.isPresent()) {
            if (blogPostOptional.get().getComments().contains(commentOptional.get())) {
                blogPostOptional.get().getComments().remove(commentOptional.get());
                commentRepository.delete(commentOptional.get());
            }
        }
    }

    @DeleteMapping("/api/users/{userId}")
    public void makeUserTypeDeletedById(@PathVariable int userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            userOptional.get().setUserType(UserType.DELETED);
            userRepository.save(userOptional.get());
        }
    }

    @DeleteMapping("/api/users/finaldelete/{userId}")
    public void removeUserById(@PathVariable int userId) {
        userRepository.deleteById(userId);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------   GET MAPPINGS   ------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @GetMapping("/api/users")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{userId}")
    public Optional<User> getAllUserById(@PathVariable int userId) {
        return userRepository.findById(userId);
    }

    @GetMapping("/api/blogposts")
    public Iterable<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    @GetMapping("/api/blogposts/{blogPostId}")
    public Optional<BlogPost> getBlogPostById(@PathVariable int blogPostId) {
        return blogPostRepository.findById(blogPostId);
    }

    @GetMapping("/api/blogposts/{blogPostId}/comments")
    public Iterable<Comment> getCommentsByPostId(@PathVariable int blogPostId) {
        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(blogPostId);

        if (blogPostOptional.isPresent()) {
            return blogPostOptional.get().getComments();
        } else {
            return new HashSet<Comment>();
        }
    }

    @GetMapping("/api/blogposts/author/{authorId}")
    public Iterable<BlogPost> getBlogPostsById(@PathVariable int authorId) {
        Optional<User> userOptional = userRepository.findById(authorId);

        if (userOptional.isPresent()) {
            return userOptional.get().getBlogPosts();
        } else {
            return new HashSet<BlogPost>();
        }
    }

    @GetMapping("/api/blogposts/authorName/{authorName}")
    public Iterable<BlogPost> getBlogPostsByAuthor(@PathVariable String authorName) {
        return blogPostRepository.findByAuthorUsername(authorName);
    }

    @GetMapping("/api/blogposts/title/{containingWord}")
    public Iterable<BlogPost> getBlogPostsByTitleContaining(@PathVariable String containingWord) {
        return blogPostRepository.findByTitleContainingIgnoreCase(containingWord);
    }

    @GetMapping("/api/blogposts/tag/{tagName}")
    public Iterable<BlogPost> getBlogPostsByTag(@PathVariable String tagName) {
        Optional<Tag> tag = tagRepository.findTagByTagNameIgnoreCase(tagName);

        if (tag.isPresent()) {
            return tag.get().getBlogPosts();
        } else {
            return new LinkedList<BlogPost>();
        }
    }

    @GetMapping("/api/blogposts/date/{date:[0-9]{4}-[0-9]{2}-[0-9]{2}}")
    public Iterable<BlogPost> getBlogPostsByDateAsc(@PathVariable String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            return blogPostRepository.findByDateOfCreation(localDate);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/api/blogposts/search_all/{keyWord}")
    public Iterable<BlogPost> getBlogPostsByKeyWord(@PathVariable String keyWord) {
        HashSet<BlogPost> blogPosts = new HashSet<>();

        for (BlogPost blogPost : getBlogPostsByAuthor(keyWord)) {
            blogPosts.add(blogPost);
        }

        for (BlogPost blogPost: getBlogPostsByTag(keyWord)) {
            blogPosts.add(blogPost);
        }

        for (BlogPost blogPost : getBlogPostsByTitleContaining(keyWord)) {
            blogPosts.add(blogPost);
        }

        return blogPosts;
    }

    @GetMapping("/api/blogposts/{blogPostId}/viewAndLike/{userId}")
    public Optional<ViewAndLike> getViewAndLikeByBlogPostIdAndUserId(@PathVariable int blogPostId, @PathVariable int userId) {
        return viewAndLikeRepository.findByUserIdAndBlogPostId(userId, blogPostId);
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "" + blogPostRepository.findAll();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------   PUT MAPPINGS   ------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @PutMapping("/api/users/login")
    public Optional<User> userExists(@RequestBody ObjectNode loginInformation) {
        String username = loginInformation.get("username").asText();
        String password = loginInformation.get("password").asText();

        return userRepository.findByUsernameIgnoreCaseAndPasswordIn(username, password);
    }

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

    @PutMapping("/api/blogposts/{blogPostId}/toggleView/{userId}")
    public void toggleBlogPostView(@PathVariable int blogPostId, @PathVariable int userId) {
        Optional<ViewAndLike> viewAndLikeOptional = viewAndLikeRepository.findByUserIdAndBlogPostId(userId, blogPostId);

        if (viewAndLikeOptional.isPresent()) {
            ViewAndLike viewAndLike = viewAndLikeOptional.get();
            viewAndLike.setViewed(!viewAndLike.isViewed());
            viewAndLikeRepository.save(viewAndLike);
        } else if (blogPostRepository.findById(blogPostId).isPresent() && userRepository.findById(userId).isPresent()) {
            viewAndLikeRepository.save(new ViewAndLike(userId, blogPostId, true, false));
        }
    }

    @PutMapping("/api/blogposts/{blogPostId}/toggleLike/{userId}")
    public void toggleBlogPostLike(@PathVariable int blogPostId, @PathVariable int userId) {
        Optional<ViewAndLike> viewAndLikeOptional = viewAndLikeRepository.findByUserIdAndBlogPostId(userId, blogPostId);

        if (viewAndLikeOptional.isPresent()) {
            ViewAndLike viewAndLike = viewAndLikeOptional.get();
            BlogPost blogPost = blogPostRepository.findById(blogPostId).get();

            if (viewAndLike.isLiked()) {
                blogPost.setLikes(blogPost.getLikes() - 1);
            } else {
                blogPost.setLikes(blogPost.getLikes() + 1);
            }

            viewAndLike.setLiked(!viewAndLike.isLiked());
            viewAndLikeRepository.save(viewAndLike);
            blogPostRepository.save(blogPost);
        } else if (blogPostRepository.findById(blogPostId).isPresent() && userRepository.findById(userId).isPresent()) {
            viewAndLikeRepository.save(new ViewAndLike(userId, blogPostId, false, true));
        }
    }
}