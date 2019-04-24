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
		System.out.println("cURL command for testing:\n"
			+ "POST User: curl -d \"{\"username\":\"testUser\",\"password\":\"password\"}\",\"userType\":1}\" -H \"Content-Type:application/json\" -X -POST http://localhost:8080/api/users\n"
			+ "POST BlogPost: curl -d \"{\"userId\":1,\"title\":\"test title\"}\",\"content\":\"test content\"}\" -H \"Content-Type:application/json\" -X -POST http://localhost:8080/api/blogposts\n"
			+ "POST Tags: curl -d \"[\"new tag1\", \"new tag2\"]\" -H \"Content-Type:application/json\" -X -POST http://localhost:8080/api/blogposts/1/tag\n"
			+ "POST Comment: curl -d \"{\"userId\":1,\"content\":\"test comment\"}\"}\" -H \"Content-Type:application/json\" -X -POST http://localhost:8080/api/blogposts/1/comment\n"
			+ "\n"
			+ "DELETE BlogPosts: curl -X DELETE http://localhost:8080/api/blogposts/2 \n"
			+ "DELETE Users: curl -X GET http://localhost:8080/api/users/3 \n"
			+ "\n"
			+ "GET BlogPosts: curl -X GET http://localhost:8080/api/blogposts \n"
			+ "GET Users: curl -X GET http://localhost:8080/api/users \n"
		);
		SpringApplication.run(BlogpotooApplication.class, args);
	}

}
