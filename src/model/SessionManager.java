package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SessionManager implements Serializable{
	private List<User> users;
	private int current_user_index;
	//FOR SERIALIZER
	public static final String storeDir = "dat";
	public static final String storeFile = "sessions.dat";
	private static final long serialVersionUID = 1L;
	
	public SessionManager(){
		/* Only needs to be called once ever
		 * Serialize and reload this object
		 */
		users = new ArrayList<User>();
		users.add(new User("admin"));
		current_user_index = -1;
	}
	
	/*	Search Users.
	 * 	Returns users beginning with string
	 * 	Used for the log in page.
	 */
	public List<User> searchUsers(String s){
		if(current_user_index == -1){
			List<User> return_list = new ArrayList<User>();
			for(User u : users){
				if(u.toString().startsWith(s)){
					return_list.add(u);
				}
			}
			return return_list;
		}
		return null;
	}
	
	/*	Login as specified user.
	 *  Used on login screen.
	 */
	public boolean login(User u){
		current_user_index = users.indexOf(u);
		if(current_user_index == -1){
			return false; //User isn't in list, shows that login failed
		}
		//Load user's objects with serialization... should be implicit?
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
	public void logout() throws IOException{
		if(current_user_index != -1){
			SessionManager.writeApp(this);
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
		return null;	
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
		return null;
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
		return null;
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
		return null;
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
		return null;
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
	
	/* Create an empty album with title */
	public void createAlbum(String title){
		if(current_user_index != -1){
			users.get(this.current_user_index).addAlbum(new Album(title));
		}
	}
	
	/* Overloaded.
	 * Create an album with a list of photos already in it.
	 * Used for creating an album from a search.
	 */
	public void createAlbum(String title, List<Photo> p){
		if(current_user_index != -1){
			users.get(this.current_user_index).addAlbum(new Album(title, p));
		}
	}
	
	/*	Removes album from the users list of albums
	 * 	doing this keeps all photos and tags in tact
	 */
	public void deleteAlbum(Album a){
		if(current_user_index != -1){
			users.get(this.current_user_index).deleteAlbum(a);
		}
	}
	
	/* Copies photo 'p' to album 'to' then
	 * Removes photo 'p' from album 'from'
	 */
	public void movePhoto(Photo p, Album from, Album to){
		if(current_user_index != -1){
			this.copyPhoto(p, to);
			from.deletePhoto(p);
		}
	}
	
	/* Copies photo 'p' to album 'to' then */
	public void copyPhoto(Photo p, Album to){
		if(current_user_index != -1){
			to.addPhoto(p);
		}		
	}
	
	/* Adds photo to the session.
	 * -adds to the user
	 * -adds it to an album
	 */
	public void addPhoto(Photo p, Album a){
		a.addPhoto(p);
		users.get(this.current_user_index).addPhoto(p);
	}
	
	/* Returns a list of photos
	 * that have a date newer than from
	 * and older than to
	 * Used in performing searches
	 */
	public List<Photo> getPhotos_Taken_Btwn(SimpleDate from, SimpleDate to){
		if(current_user_index != -1){
			List<Photo> return_list = new ArrayList<Photo>();
			for(Photo p : users.get(this.current_user_index).getPhotos()){
				if(p.getDate().isInRange(from, to)){
					return_list.add(p);
				}
			}
			return return_list;
		}
		return null;
	}
	
	//**********THE FOLLOWING ARE ADMIN ONLY METHODS**********
	
	/* Returns a list of all users.
	 * Used in the admin panel.
	 */
	public List<User> listUsers(){
		if(current_user_index != -1 && users.get(current_user_index).toString().compareTo("admin")==0){
			return users;
		}
		return null;
	}
	
	/* Adds a new user to this object 
	 * Return values:
	 *  0 -> Success
	 * -1 -> Bad input
	 *  1 -> User name exists
	 * -2 -> not logged in as admin
	 */
	public int createUser(String username){
		if(current_user_index != -1 && users.get(current_user_index).toString().compareTo("admin")==0 && !username.matches("[a-zA-Z]+")){
			if(username.isEmpty() || username.length() > 15){
				return -1;
			}
			for(User u : users){
				if(u.toString().compareTo(username) == 0){
					return 1;
				}
			}
			users.add(new User(username));
			return 0;
		}
		return -2;
	}
	
	/* Deletes specified user from this object
	 * returns false if the user doesn't exist
	 * or if the method is called when admin isn't logged in
	 */
	public boolean deleteUser(User u){
		if(current_user_index != -1 && users.get(current_user_index).toString().compareTo("admin")==0){
			return users.remove(u);
		}
		return false;
	}
	
	//********************SERIALIZER********************
	public static void writeApp(SessionManager man)	throws IOException {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
			oos.writeObject(man);
			oos.close();
	}
	
	public static SessionManager readApp() throws IOException, ClassNotFoundException{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir+File.separator+storeFile));
		SessionManager man = (SessionManager)ois.readObject();
		ois.close();
		return man;
	}
	
}
