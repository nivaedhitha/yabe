import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest 
{
//	@Test
//	public void createAndRetrieveUser() {
//	    // Create a new user and save it
//	    new User("bob@gmail.com", "secret", "Bob").save();
//	    
//	    // Retrieve the user with e-mail address bob@gmail.com
//	    User bob = User.find("byEmail", "bob@gmail.com").first();
//	    
//	    // Test 
//	    assertNotNull(bob);
//	    assertEquals("Bob", bob.fullname);
//	}

	
	@Before
	public void setup()
	{
		Fixtures.deleteDatabase();
	}
	
    @Test
    public void createAndRetrieveUser()
    {
//      To create an User
    	new User("gannivae@gmail.com","nivae123","nivae").save();
//      To retrieve an User's full name by his email id
    	User u = User.find("byEmail", "gannivae@gmail.com").first();
//    Test
    	assertNotNull(u);
    	assertEquals("nivae",u.fullname);
    	
    	}
    @Test
    public void checkUserExistorNot()
    {
//      To create an User
    	new User("gannivae@gmail.com","nivae123","nivae").save();
    	assertNotNull(User.connect("gannivae@gmail.com","nivae123"));
    	assertNull(User.connect("gannivae@gmail.com", "nivae345"));
    	assertNull(User.connect("subu051189@gmail.com", "nivae123"));
    }
    @Test
    public void createPost()
    {
//    	To create an User
    	User u=new User("gannivae@gmail.com","nivae123","nivae").save();
    	new Post(u,"My First post","Hello friends").save();
//    	The first post is posted
    	assertEquals(1,Post.count());
    	//Retrieve all posts created by nivae
    	List<Post> nivaeposts= Post.find("byAuthor",u).fetch();
//    	Tests
    	assertEquals(1,nivaeposts.size());
    	Post newpost = nivaeposts.get(0);
    	assertNotNull("newpost");
    	assertEquals(u,newpost.author);
    	assertEquals("My First post",newpost.title);
    	assertEquals("Hello friends",newpost.content);
    	assertNotNull(newpost.posteddate);
    }
    @Test
    public void postComments()
    {
//    	To create an User
    	User u = new User("gannivae@gmail.com","nivae123","nivae").save();
//    	To create new post
    	Post p =new Post(u,"My First post","Hello friends").save();
//    	To create new comments
    	new Comment("nivae","Nice post",p).save();
    	new Comment("harini","Good One",p).save();
    	List<Comment> allComments=Comment.find("byPost", p).fetch();
    	assertEquals(2,allComments.size());
    	Comment newcomment= allComments.get(0);
    	assertNotNull(newcomment);
    	assertEquals("nivae",newcomment.author);
    	assertEquals("Nice post",newcomment.content);
    	assertNotNull(newcomment.posteddate);
    	Comment newcomment1=allComments.get(1);
    	assertNotNull(newcomment1);
    	assertEquals("harini",newcomment1.author);
    	assertEquals("Good One",newcomment1.content);
    	assertNotNull(newcomment1.posteddate);
    }
    @Test
    public void useTheCommentsRelation()
    {
//    	To create an User
    	User u = new User("gannivae@gmail.com","nivae123","nivae").save();
//    	To create an post
    	Post p = new Post(u,"My first post","Hello friends").save();
//    	To create comments
    	p.addcomment("nivae","Good One");
    	p.addcomment("harini","Simply Superb");
    	//Test
    	assertEquals(1,u.count());
    	assertEquals(1,p.count());
    	assertEquals(2,Comment.count());
//    	Retrieve the post created
    	p=Post.find("byAuthor", u).first();
    	assertNotNull(p);
//     Find the related comments to the post
    	assertEquals(2,p.comments.size());
    	assertEquals("nivae",p.comments.get(0).author);
//    Delete the post
    	p.delete();
    	//Final Test
    	assertEquals(1,u.count());
    	assertEquals(0,p.count());
    	assertEquals(0,Comment.count());
    }
   
    @Test
    public void fulltest()
    {
    	Fixtures.loadModels("data.yml");
    	//Count things
    	assertEquals(2,User.count());
    	assertEquals(3,Post.count());
    	assertEquals(3,Comment.count());
    	//Try to connect as users
    	assertNotNull(User.connect("gannivae@gmail.com","nivae123"));
    	assertNotNull(User.connect("subu051189@gmail.com","subu123"));
    	assertNull(User.connect("gannivae@gmail.com", "badpassword"));
    	assertNull(User.connect("subu051189@gmail.com", "badpassword"));
    	assertNull(User.connect("abc@gmail.com", "nivae123"));
    	assertNull(User.connect("abc@gmail.com", "subu123"));
    	//Find all nivae posts
    	List<Post> nivaeposts = Post.find("author.email","gannivae@gmail.com" ).fetch();
    	assertEquals(2,nivaeposts.size());
    	//Find all comments for nivae posts
    	List<Comment> comments = Comment.find("post.author.email","gannivae@gmail.com").fetch();
    	assertEquals(3,comments.size());
    	Post frontpost =Post.find("Order by posteddate desc").first();
    	assertNotNull(frontpost);
    	assertEquals("this is my first post",frontpost.title);
//    	The above post has two comments
    	assertEquals(2,frontpost.comments.size());
//    	Add a new comment to the frontpost
    	frontpost.addcomment("gowthami", "this is fake");
    	assertEquals(3,frontpost.comments.size());
    	assertEquals(4,Comment.count());
    	
    }
}
    	
    			
    


