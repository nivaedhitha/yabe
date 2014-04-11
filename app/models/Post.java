package models;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import play.db.jpa.Model;
@Entity
public class Post extends Model
{
	
    public String title;
    public Date posteddate;	
  @Lob
  @Type(type="org.hibernate.type.TextType")
  public String content;
  @ManyToOne
  public User author;
  @OneToMany(mappedBy="post",cascade=CascadeType.ALL)
	public List<Comment> comments;
  public Post(User author,String title,String content)
  {
	  this.comments= new ArrayList<Comment>();
	  this.author=author;
	  this.title=title;
	  this.content=content;
	  this.posteddate=new Date();		
  }
  public Post addcomment(String author,String content)
  {
	Comment newcomment=new Comment(author,content,this).save();
	this.comments.add(newcomment);
	this.save();
	return this;
	  
	  
  }
}
