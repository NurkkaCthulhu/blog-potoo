package fi.tuni.lesserpotoo.blogpotoo;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fi.tuni.lesserpotoo.blogpotoo.entities.*;
import fi.tuni.lesserpotoo.blogpotoo.misc.UserType;
import fi.tuni.lesserpotoo.blogpotoo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0424
 * @since 1.0
 */
@RestController
public class PotooController {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class BlogPostNotFoundException extends RuntimeException {
        public BlogPostNotFoundException(int id) {
            super("BlogPost with id " + id + " not found.");
        }
    }

    /**
     * Repository of Users
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Repository of BlogPosts
     */
    @Autowired
    BlogPostRepository blogPostRepository;

    /**
     * Repository of Tags
     */
    @Autowired
    TagRepository tagRepository;

    /**
     * Repository of Comments
     */
    @Autowired
    CommentRepository commentRepository;

    /**
     * Repository of ViewAndLikes
     */
    @Autowired
    ViewAndLikeRepository viewAndLikeRepository;

    /**
     * Adds initial information to database for testing purposes.
     */
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
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------   POST MAPPINGS   -----------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates and saves a BlogPost based on given information.
     *
     * Creates a BlogPost if author is an existing User and then returns the id of created BlogPost. If User does not
     * exist, returns -1 as it cannot be an Id for BlogPost.
     *
     * @param blogPostInfo  needs to have properties authorId, title and content
     * @return id of created blogPost
     */
    @PostMapping(value = "/api/blogposts")
    public int saveBlogPost(@RequestBody ObjectNode blogPostInfo) {
        int authorId = blogPostInfo.get("authorId").asInt();
        String title = blogPostInfo.get("title").asText();
        String content = blogPostInfo.get("content").asText();
        Optional<User> authorOptional = userRepository.findById(authorId);

        if (authorOptional.isPresent()) {
            BlogPost blogPost = new BlogPost(authorOptional.get(), title, content);
            blogPostRepository.save(blogPost);
            return blogPost.getId();
        } else {
            return -1;
        }
    }

    /**
     * Creates and saves an User based on given information.
     *
     * Checks if user of that name already exists. If it doesn't, creates a new User, saves it and returns the User
     * object. If user of that name exists, returns null.
     *
     * @param user
     * @return User or null
     */
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

    /**
     * Creates Tags, adds them to BlogPost and saves them.
     *
     * If BlogPost of given id exists, goes trough the list on tagNames. If a Tag of same name already exists, then
     * add the existing Tag to the BlogPost. If not, creates a new Tag of that name and adds it to BlogPost.
     *
     * @param blogPostId
     * @param tagNames
     */
    @PostMapping("/api/blogposts/{blogPostId}/tag")
    public void addTagsToBlogPost(@PathVariable int blogPostId, @RequestBody List<String> tagNames) throws BlogPostNotFoundException {
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
        } else {
            throw new BlogPostNotFoundException(blogPostId);
        }
    }

    /**
     * Adds a Comment to given BlogPost.
     *
     * If BlogPost and User exist make create a Comment and save it.
     *
     * @param blogPostId
     * @param commentInfo needs to have properties userId and content
     */
    @PostMapping("/api/blogposts/{blogPostId}/comments")
    public void addCommentToBlogPost(@PathVariable int blogPostId, @RequestBody ObjectNode commentInfo) throws BlogPostNotFoundException {
        int userId = commentInfo.get("userId").asInt();
        String content = commentInfo.get("content").asText();
        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(blogPostId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (!blogPostOptional.isPresent()) {
            throw new BlogPostNotFoundException(blogPostId);
        } else if (blogPostOptional.isPresent() && userOptional.isPresent()) {
            BlogPost blogPost = blogPostOptional.get();
            User user = userOptional.get();

            Comment newComment = new Comment(user, blogPost, content);
            commentRepository.save(newComment);
        }
    }

    /**
     * Adds ViewAndLike to BlogPost and User.
     *
     * If BlogPost and User exist, create and save ViewAndLike object.
     *
     * @param blogPostId
     * @param viewAndLikeInfo needs to have userId, view and like
     */
    @PostMapping("/api/blogposts/{blogPostId}/viewAndLike")
    public void addViewAndLikeToBlogPost(@PathVariable int blogPostId, @RequestBody ObjectNode viewAndLikeInfo) throws BlogPostNotFoundException {
        int userId = viewAndLikeInfo.get("userId").asInt();
        boolean view = viewAndLikeInfo.get("view").asBoolean();
        boolean like = viewAndLikeInfo.get("like").asBoolean();

        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(blogPostId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (!blogPostOptional.isPresent()) {
            throw new BlogPostNotFoundException(blogPostId);
        } else if (blogPostOptional.isPresent() && userOptional.isPresent()) {
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

    /**
     * Deletes BlogPost and Tags and ViewAndLikes associated with it.
     *
     * If BlogPoist exists, goes trough all the Tags. If Tag is only on the BlogPost, removes it. If not, removes it
     * only from BlogPost's tags. Also removes all ViewAndLikes associated with this BlogPost.
     *
     * @param blogPostId
     */
    @DeleteMapping("/api/blogposts/{blogPostId}")
    public void deleteBlogPost(@PathVariable int blogPostId) throws BlogPostNotFoundException {
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

            List<ViewAndLike> viewAndLikes = viewAndLikeRepository.findAllByBlogPostId(blogPostId);

            for (ViewAndLike val : viewAndLikes) {
                viewAndLikeRepository.delete(val);
            }

            blogPostRepository.deleteById(blogPostId);
        } else {
            throw new BlogPostNotFoundException(blogPostId);
        }
    }

    /**
     * Deletes Comment from a BlogPost.
     *
     * Checks if BlogPost and Comment exist. Checks if BlogPost has the said Comment. If it is, deletes the comment.
     *
     * @param blogPostId
     * @param commentId
     */
    @DeleteMapping("/api/blogposts/{blogPostId}/comments/{commentId}")
    public void deleteCommentFromABlogPost(@PathVariable int blogPostId, @PathVariable int commentId) throws BlogPostNotFoundException {
        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(blogPostId);
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (!blogPostOptional.isPresent()) {
            throw new BlogPostNotFoundException(blogPostId);
        } else if (blogPostOptional.isPresent() && commentOptional.isPresent()) {
            if (blogPostOptional.get().getComments().contains(commentOptional.get())) {
                blogPostOptional.get().getComments().remove(commentOptional.get());
                commentRepository.delete(commentOptional.get());
            }
        }
    }

    /**
     * Changes Users UserType to DELETED, does not delete user from the database.
     *
     * @param userId
     */
    @DeleteMapping("/api/users/{userId}")
    public void makeUserTypeDeletedById(@PathVariable int userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            userOptional.get().setUserType(UserType.DELETED);
            userRepository.save(userOptional.get());
        }
    }

    /**
     * Deletes User and all ViewAndLikes associated with it.
     *
     * If User exists, deletes it.
     *
     * @param userId
     */
    @DeleteMapping("/api/users/finaldelete/{userId}")
    public void removeUserById(@PathVariable int userId) {
        List<ViewAndLike> viewAndLikes = viewAndLikeRepository.findAllByUserId(userId);

        for (ViewAndLike val : viewAndLikes) {
            viewAndLikeRepository.delete(val);
        }

        userRepository.deleteById(userId);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------   GET MAPPINGS   ------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns all users.
     *
     * @return
     */
    @GetMapping("/api/users")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Returns user by id.
     *
     * @param userId
     * @return User (optional)
     */
    @GetMapping("/api/users/{userId}")
    public Optional<User> getAllUserById(@PathVariable int userId) {
        return userRepository.findById(userId);
    }

    /**
     * Returns all BlogPost in descending order by id.
     *
     * @return BlogPosts (iterable)
     */
    @GetMapping("/api/blogposts")
    public Iterable<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAllByOrderByIdDesc();
    }

    /**
     * Returns BlogPost by blogPostId.
     *
     * @param blogPostId
     * @return BlogPost (Optional)
     */
    @GetMapping("/api/blogposts/{blogPostId}")
    public BlogPost getBlogPostById(@PathVariable int blogPostId) throws BlogPostNotFoundException {
        Optional<BlogPost> blogPost = blogPostRepository.findById(blogPostId);

        if (blogPost.isPresent()) {
            return blogPost.get();
        } else {
            throw new BlogPostNotFoundException(blogPostId);
        }
    }

    /**
     * Returns all Comments of all BlogPosts.
     *
     * @param blogPostId
     * @return Comments (iterable)
     */
    @GetMapping("/api/blogposts/{blogPostId}/comments")
    public Iterable<Comment> getCommentsByPostId(@PathVariable int blogPostId) throws BlogPostNotFoundException {
        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(blogPostId);

        if (blogPostOptional.isPresent()) {
            return blogPostOptional.get().getComments();
        } else {
            throw new BlogPostNotFoundException(blogPostId);
        }
    }

    /**
     * Returns all BlogPosts written by given author.
     *
     * Checks if User of authorId exists. If so, returns all BlogPosts written by that User.
     *
     * @param authorId
     * @return BlogPosts (iterable)
     */
    @GetMapping("/api/blogposts/author/{authorId}")
    public Iterable<BlogPost> getBlogPostsById(@PathVariable int authorId) {
        Optional<User> userOptional = userRepository.findById(authorId);

        if (userOptional.isPresent()) {
            return userOptional.get().getBlogPosts();
        } else {
            return new HashSet<BlogPost>();
        }
    }

    /**
     * Returns all BlogPosts written by given author.
     *
     * Finds and returns all BlogPost by Author's username.
     *
     * @param authorName
     * @return BlogPosts (iterable)
     */
    @GetMapping("/api/blogposts/authorName/{authorName}")
    public Iterable<BlogPost> getBlogPostsByAuthor(@PathVariable String authorName) {
        return blogPostRepository.findByAuthorUsername(authorName);
    }

    /**
     * Returns all BlogPosts whose title contains given word.
     *
     * @param containingWord
     * @return BlogPosts (iterable)
     */
    @GetMapping("/api/blogposts/title/{containingWord}")
    public Iterable<BlogPost> getBlogPostsByTitleContaining(@PathVariable String containingWord) {
        return blogPostRepository.findByTitleContainingIgnoreCase(containingWord);
    }

    /**
     * Returns all BlogPost that have a Tag of certain name.
     *
     * Checks if tag by given name exists. Returns all BlogPosts that have it. If not, returns empty list.
     *
     * @param tagName
     * @return BlogPosts (iterable)
     */
    @GetMapping("/api/blogposts/tag/{tagName}")
    public Iterable<BlogPost> getBlogPostsByTag(@PathVariable String tagName) {
        Optional<Tag> tag = tagRepository.findTagByTagNameIgnoreCase(tagName);

        if (tag.isPresent()) {
            return tag.get().getBlogPosts();
        } else {
            return new LinkedList<BlogPost>();
        }
    }

    /**
     * Returns all BlogPosts written on a certain day.
     *
     * If date cannot be parsed, will return null.
     *
     * @param date
     * @return BlogPosts (iterable)
     */
    @GetMapping("/api/blogposts/date/{date:[0-9]{4}-[0-9]{2}-[0-9]{2}}")
    public Iterable<BlogPost> getBlogPostsByDateAsc(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return blogPostRepository.findByDateOfCreation(localDate);
    }

    /**
     * Returns all BlogPost which title, tags or author include the search word.
     *
     * Uses getBlogPostsByTag, getBlogPostsByTitleContaining and getBlogPostsByAuthor to find all BlogPosts. Adds them
     * to a same set. Returns that set.
     *
     * @param keyWord
     * @return BlogPosts (iterable)
     */
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

    /**
     * Returns ViewAndLike by userId and blogPostId.
     *
     * @param blogPostId
     * @param userId
     * @return ViewAndLike (Optional)
     */
    @GetMapping("/api/blogposts/{blogPostId}/viewAndLike/{userId}")
    public Optional<ViewAndLike> getViewAndLikeByBlogPostIdAndUserId(@PathVariable int blogPostId, @PathVariable int userId) throws BlogPostNotFoundException {
        if (!blogPostRepository.findById(blogPostId).isPresent()) {
            throw new BlogPostNotFoundException(blogPostId);
        }

        return viewAndLikeRepository.findByUserIdAndBlogPostId(userId, blogPostId);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------   PUT MAPPINGS   ------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns user by username and password.
     *
     * @param loginInformation needs to have username and password
     * @return User (optional)
     */
    @PutMapping("/api/users/login")
    public Optional<User> userExists(@RequestBody ObjectNode loginInformation) {
        String username = loginInformation.get("username").asText();
        String password = loginInformation.get("password").asText();

        return userRepository.findByUsernameIgnoreCaseAndPasswordIn(username, password);
    }

    /**
     * Updates the information in a blogPost.
     *
     * If BlogPost exists, gets new information from the updateJson. Uses updateBlogPostTitle, updateBlogPostContent and
     * updateBlogPostTags to update information.
     *
     * @param blogPostId
     * @param updateJson needs to have title, content and tags
     */
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


    /**
     * If BlogPost exists, updates the title and TimeOfEdit.
     *
     * @param blogPostId
     * @param title
     */
    @PutMapping("/api/blogposts/{blogPostId}/title")
    public void updateBlogPostTitle(@PathVariable int blogPostId, @RequestBody String title) {
        Optional<BlogPost> blogPostOpt = blogPostRepository.findById(blogPostId);

        if (blogPostOpt.isPresent()) {
            blogPostOpt.get().setTimeOfEdit(LocalDateTime.now());
            blogPostOpt.get().setTitle(title);
            blogPostRepository.save(blogPostOpt.get());
        }
    }

    /**
     * If BlogPost exists, updates the content and TimeOfEdit.
     *
     * @param blogPostId
     * @param content
     */
    @PutMapping("/api/blogposts/{blogPostId}/content")
    public void updateBlogPostContent(@PathVariable int blogPostId, @RequestBody String content) {
        Optional<BlogPost> blogPostOpt = blogPostRepository.findById(blogPostId);

        if (blogPostOpt.isPresent()) {
            blogPostOpt.get().setTimeOfEdit(LocalDateTime.now());
            blogPostOpt.get().setContent(content);
            blogPostRepository.save(blogPostOpt.get());
        }
    }

    /**
     * If BlogPost exists, adds tags.
     *
     * Checks if BlogPost exists. If so, checks if tags already exist. If tag exists, adds it to BlogPost. If not,
     * creates new Tag and adds it to BlogPost.
     *
     * @param blogPostId
     * @param tags
     */
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

    /**
     * Toggles view attribute of ViewAndLike of certain blogPostId and userId.
     *
     * @param blogPostId
     * @param userId
     */
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

    /**
     * Toggles like attribute of ViewAndLike of certain blogPostId and userId, and removes or adds likes to BlogPost.
     *
     * @param blogPostId
     * @param userId
     */
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
            BlogPost blogPost = blogPostRepository.findById(blogPostId).get();
            blogPost.setLikes(blogPost.getLikes() + 1);
            blogPostRepository.save(blogPost);
        }
    }
}