package controllers;

import play.*;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() 
    {
        Post frontPost = Post.find("Order by posteddate desc").first();
        List<Post> olderPosts = Post.find("Order by posteddate desc").from(1).fetch(10);
        
        render(frontPost,olderPosts);
    }
    @Before
    static void addDefaults()
    {
    	renderArgs.put("blogTitle",Play.configuration.getProperty("blog.title"));
    	renderArgs.put("blogBaseline",Play.configuration.getProperty("blog.baseline"));
    }
    public static void show(Long id)
    {
    	Post post=Post.findById(id);
    	render(post);
    }
	
//    public Post previous(Date posteddate)
//    {
//    	return Post.find("posteddate < ? order by posteddate desc",posteddate).first();
//    	
//    }
//    public Post next(Date posteddate)
//    {
//    	return Post.find("posteddate > ? order by posteddate asc",posteddate).first();	
//    }
    public static void postcomment(Long postid,@Required String author,@Required String content)
    {
    	Post post=Post.findById(postid);
    	if(validation.hasErrors())
    	{
    		render("Application/show.html",post);
    	}
    	post.addcomment(author, content);
    	flash.success("Thanks for posting %s",author);
    	show(postid);
    }
}