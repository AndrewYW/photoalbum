package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Photo {
	private String image_path;
	private String caption;
	private Calendar date;
	private List<Tag> tags;
	
	public Photo(String p, String cap, Calendar d){
		image_path = p;
		caption = cap;
		date = d;
		tags = new ArrayList<Tag>();
	}
	
	public String getPath(){
		return image_path;
	}
	
	public void setCaption(String cap){
		caption = cap;
	}
	
	public String getCaption(){
		return caption;
	}
	
	public Calendar getDate(){
		return date;
	}
	
	public List<Tag> getTags(){
		return tags;
	}
	
	public void addTag(Tag t){
		tags.add(t);
	}
	
	public void deleteTag(Tag t){
		tags.remove(t);
	}
}
