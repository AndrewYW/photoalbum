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

public class User implements Serializable{
	private String username;
	private List<Album> albums;
	private List<Photo> photos;
	//FOR SERIALIZER
	public static final String storeDir = "dat";
	public static final String storeFile = "user.dat"; //prefixes username
	private static final long serialVersionUID = 1L;
	
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
	
	public void addPhoto(Photo p){
		photos.add(p);
	}
	
	/* Helper method for searching by date range
	 * Iterates through photos and creates a list
	 * adds to the list if a date is in the range
	 */
	public List<Photo> getPhotos_Taken_Btwn(SimpleDate from, SimpleDate to){
		List<Photo> return_list = new ArrayList<Photo>();
		for(Photo p : this.getPhotos()){
			if(p.getDate().isInRange(from, to)){
				return_list.add(p);
			}
		}
		return return_list;
	}
	
	@Override
	public String toString(){
		return username;
	}
	
	//********************SERIALIZER********************
		public static void writeApp(User user)	throws IOException {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + user.toString() +"_"+ storeFile));
				oos.writeObject(user);
				oos.close();
		}
		
		public static User readApp(String username) throws IOException, ClassNotFoundException{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir+File.separator + username +"_"+storeFile));
			User user = (User)ois.readObject();
			ois.close();
			return user;
		}
}
