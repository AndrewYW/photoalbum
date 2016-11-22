package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Backend_Controller {
	private List<User> users;
	private int current_user_index;
	
	public Backend_Controller(){
		/* Only needs to be called once ever
		 * Serialize and reload this object
		 */
		List<User> users = new ArrayList<User>();
		users.add(new User("Admin"));
	}
	
	/*	Search Users.
	 * 	Returns users beginning with string
	 * 	Used for the log in page.
	 */
	public List<User> searchUsers(String s){
		if(current_user_index != -1){
			List<User> return_list = new ArrayList<User>();
			for(User u : users){
				if(u.toString().startsWith(s)){
					return_list.add(u);
				}
			}
			return return_list;
		}
		else{
			return null;
		}
	}
	
	/*	Login as specified user.
	 *  Used on login screen.
	 */
	public boolean login(User u){
		current_user_index = users.indexOf(u);
		if(current_user_index == -1){
			return false; //User isn't in list, shows that login failed
		}
		//TODO: Load users objects with serialization
		return true;
	}
	
	/*	Check that a user is logged in.
	 *  Use to verify before switching screens.
	 */
	public boolean isLoggedIn(){
		return (current_user_index != -1) ? true : false;
	}
	
	//**********EVERYTHING FROM HERE ON REQUIRES YOU TO BE LOGGED IN**********
	
	/* Serializes all objects used by the user
	 * closes the session by resetting the user index
	 */
	public void logout(){
		if(current_user_index != -1){
			/* TODO:
			 * Serialize
			 * EVERYTHING
			 */
			current_user_index = -1;
		}
	}
	
	/* Return a list of all tags in use by the user
	 * Just a helper method for performing searches
	 */
	public List<Tag> getAllTags(){
		if(current_user_index != -1){
			List<Tag> return_list = new ArrayList<Tag>();
			for(Photo p : users.get(current_user_index).getPhotos()){
				for(Tag tag : p.getTags()){
					if(!return_list.contains(tag)){
						return_list.add(tag);
					}
				}
			}
			return return_list;	
		}
		else{
			return null;
		}	
	}
	
	/* Returns a list of tags that have
	 * a type prefixed by t
	 * a value prefixed by v
	 * Helper method for performing searches
	 */
	public List<Tag> searchTags(String t, String v){
		if(current_user_index != -1){
			List<Tag> return_list = new ArrayList<Tag>();
			for(Tag tag_one : this.searchTag_Types(t)){
				for(Tag tag_two : this.searchTag_Values(v)){
					if(tag_one.equals(tag_two)){
						/* Interestingly I considered writing an equals method for tags
						 * However the default equals method compares memory addresses
						 * Since tags can only exist as one instance of type:value
						 * this this implementation of equals is actually what we want.
						 */
						return_list.add(tag_one);
					}
				}
			}
			return return_list;	
		}
		else{
			return null;
		}
	}
	
	/* Returns a list of tags that have
	 * types prefixed by s
	 * Helper method for searchTags
	 */
	public List<Tag> searchTag_Types(String s){
		if(current_user_index != -1){
			List<Tag> return_list = new ArrayList<Tag>();
			for(Tag tag : this.getAllTags()){
				if(tag.getType().startsWith(s)){
					return_list.add(tag);
				}
			}
			return return_list;
		}
		else{
			return null;
		}
	}
	
	/* Returns a list of tags that have
	 * values prefixed by s
	 * Helper method for searchTags
	 */
	public List<Tag> searchTag_Values(String s){
		if(current_user_index != -1){
			List<Tag> return_list = new ArrayList<Tag>();
			for(Tag tag : this.getAllTags()){
				if(tag.getValue().startsWith(s)){
					return_list.add(tag);
				}
			}
			return return_list;
		}
		else{
			return null;
		}
	}
	
	/* Returns a list of albums that
	 * have titles starting with s
	 * Used for selecting an album to move to
	 */
	public List<Album> searchAlbums(String s){
		if(current_user_index != -1){
			List<Album> return_list = new ArrayList<Album>();
			for(Album a : this.myAblums()){
				if(a.getTitle().startsWith(s)){
					return_list.add(a);
				}
			}
			return return_list;
		}
		else{
			return null;
		}
	}
	
	/* Returns list of albums
	 * Used for the initial screen after login
	 */
	public List<Album> myAblums(){
		if(current_user_index != -1){
			return users.get(this.current_user_index).getAlbum_List();
		}
		else{
			return null;
		}
	}
	
	/* TODO
	 * Create an empty album with title
	 */
	public void createAlbum(String title){
		if(current_user_index != -1){
			
		}
	}
	
	/* TODO
	 * Overloaded.
	 * Create an album with a list of photos already in it.
	 * Used for creating an album from a search.
	 */
	public void createAlbum(String title, List<Photo> p){
		if(current_user_index != -1){
			
		}
	}
	
	/*	TODO
	 * 	Removes album from the users list of albums
	 * 	doing this keeps all photos and tags in tact
	 */
	public void deleteAlbum(Album a){
		if(current_user_index != -1){
			
		}
	}
	
	/* TODO
	 * Copies photo 'p' to album 'to' then
	 * Removes photo 'p' from album 'from'
	 */
	public void movePhoto(Photo p, Album from, Album to){
		if(current_user_index != -1){
			
		}
	}
	
	/* TODO
	 * Copies photo 'p' to album 'to' then
	 */
	public void copyPhoto(Photo p, Album from, Album to){
		if(current_user_index != -1){
			
		}		
	}
	
	/* TODO
	 * Returns a list of photos
	 * that have a date newer than from
	 * and older than to
	 * Used in performing searches
	 */
	public List<Photo> getPhotos_Taken_Btwn(Calendar from, Calendar to){
		if(current_user_index != -1){
			
		}
		else{
			return null;
		}
		return null;
	}
	
	//**********THE FOLLOWING ARE ADMIN ONLY METHODS**********
	
	/* TODO
	 * Returns a list of all users.
	 * Used in the admin panel.
	 */
	public List<User> listUsers(){
		if(current_user_index != -1 && users.get(current_user_index).toString().compareTo("admin")==0){
			
		}
		return null;
	}
	
	/* TODO
	 * Adds a new user to this object
	 */
	public void createUser(String username){
		if(current_user_index != -1 && users.get(current_user_index).toString().compareTo("admin")==0){
			
		}	
	}
	
	/* TODO
	 * Deletes specified user from this object
	 */
	public void deleteUser(User u){
		if(current_user_index != -1 && users.get(current_user_index).toString().compareTo("admin")==0){
			
		}
	}
}