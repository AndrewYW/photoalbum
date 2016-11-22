package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class User {
	private String username;
	private List<Album> albums;
	private List<Photo> photos;
	
	public User(String u){
		username = u;
		albums = new ArrayList<Album>();
		photos = new ArrayList<Photo>();
	}
	
	public List<Album> getAlbum_List(){
		return albums;
	}
	
	public void addAlbum(Album a){
		albums.add(a);
	}
	
	public void deleteAlbum(Album a){
		/* Returns a boolean... 
		 * in case you wanted to verify that it was removed
		 */
		albums.remove(a);
	}
	
	public List<Photo> getPhotos(){
		return photos;
	}
	
	public List<Photo> getPhotos_Taken_Btwn(Calendar from, Calendar to){
		List<Photo> return_list = new ArrayList<Photo>();
		/* iterate through photos
		 * add to list if date is between from and to
		 */
		return return_list;
	}
}
