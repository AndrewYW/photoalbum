package model;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Album {
	private String title;
	private List<Photo> photos;
	private Calendar oldest;
	private Calendar newest;
	
	public Album(String t){
		title = t;
	}
	
	public Album(String t, List<Photo> p){
		title = t;
		photos = p;
	}
	
	public String getTitle(){
		return title;
	}
	
	public int countPhotos(){
		return photos.size();
	}
	
	public Calendar oldserDate(){
		return oldest;
	}
	
	public Calendar newestDate(){
		return newest;
	}

	public void rename(String t){
		title = t;
	}
	
	public void addPhoto(Photo p){
		photos.add(p);
	}
	
	public void deletePhoto(Photo p){
		photos.remove(p);
	}
	
	public List<Photo> openAlbum(){
		return photos;
	}
	
	public String getThumbnail(){
		Random rand = new Random();
		return photos.get(rand.nextInt(photos.size())).getPath();
	}
}