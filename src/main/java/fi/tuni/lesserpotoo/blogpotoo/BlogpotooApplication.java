package fi.tuni.lesserpotoo.blogpotoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Anu Malm [anu.malm@tuni.fi]
 * @version 2019-0314
 * @since 1.0
 */
@SpringBootApplication
public class BlogpotooApplication {

	/**
	 * Launches the application.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("cURL commands for testing:\n"
			+ "\n"
			+ "POST User: 			curl -d \"{\"username\":\"testUser\",\"password\":\"password\",\"userType\":1}\" -H \"Content-Type:application/json\" -X POST http://localhost:8080/api/users\n"
			+ "POST BlogPost: 		curl -d \"{\"authorId\":1,\"title\":\"test title\",\"content\":\"test content\"}\" -H \"Content-Type:application/json\" -X -OST http://localhost:8080/api/blogposts\n"
			+ "POST Tags: 			curl -d \"[\"new tag1\", \"new tag2\"]\" -H \"Content-Type:application/json\" -X POST http://localhost:8080/api/blogposts/1/tag\n"
			+ "POST Comment: 		curl -d \"{\"userId\":1,\"content\":\"test comment\"}\" -H \"Content-Type:application/json\" -X POST http://localhost:8080/api/blogposts/1/comment\n"
			+ "POST ViewAndLike: 	curl -d \"{\"userId\":1,\"view\":true,\"like\":true}\" -H \"Content-Type:application/json\" -X POST http://localhost:8080/api/blogposts/4/viewAndLike\n"
			+ "\n"
			+ "FOLLOWING GETS DO NOT CONTAIN ALL GETMAPPING OF THE CONTROLLER, but you get the idea\n"
			+ "GET Users: 					curl -X GET http://localhost:8080/api/users \n"
			+ "GET BlogPosts by id: 		curl -X GET http://localhost:8080/api/blogposts/2 \n"
			+ "GET BlogPost comments by id: curl -X GET http://localhost:8080/api/blogposts/2/comments \n"
			+ "GET BlogPosts by author id: 	curl -X GET http://localhost:8080/api/blogposts/author/3 \n"
			+ "GET BlogPosts by title: 		curl -X GET http://localhost:8080/api/blogposts/title/potoo \n"
			+ "GET BlogPosts by tags: 		curl -X GET http://localhost:8080/api/blogposts/tag/last \n"
			+ "GET Search BlogPosts: 		curl -X GET http://localhost:8080/api/blogposts/search_all/day \n"
			+ "GET ViewAndLike: 			curl -X GET http://localhost:8080/api/blogposts/4/viewAndLike/1 \n"
			+ "\n"
			+ "DELETE BlogPosts: 	curl -X DELETE http://localhost:8080/api/blogposts/3 \n"
			+ "DELETE Users: 		curl -X DELETE http://localhost:8080/api/users/finaldelete/3 \n"
			+ "DELETE Comments: 	curl -X DELETE http://localhost:8080/api/blogposts/2/comments/2 \n"
			+ "\n"
			+ "PUT User login: 		curl -d \"{\"username\":\"testUSER\",\"password\":\"password\"}\" -H \"Content-Type:application/json\" -X PUT http://localhost:8080/api/users/login \n"
			+ "PUT Update BlogPost: curl -d \"{\"title\":\"new title\",\"content\":\"new content\", \"tags\":[\"newTag1\",\"newTag2\"]}\" -H \"Content-Type:application/json\" -X PUT http://localhost:8080/api/blogposts/1 \n"
			+ "PUT toggle viewed:   curl -X PUT http://localhost:8080/api/blogposts/4/toggleView/1 \n"
			+ "PUT toggle liked:   	curl -X PUT http://localhost:8080/api/blogposts/4/toggleLike/1 \n"
		);
		SpringApplication.run(BlogpotooApplication.class, args);
	}

}
