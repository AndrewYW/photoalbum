package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Album implements Serializable{
	private String title;
	private List<Photo> photos;
	private SimpleDate oldest;
	private SimpleDate newest;
	//FOR SERIALIZER
	public static final String storeDir = "dat";
	public static final String storeFile = "album.dat";
	private static final long serialVersionUID = 1L;
	
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
	
	public SimpleDate olderDate(){
		return oldest;
	}
	
	public SimpleDate newestDate(){
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
	
	//********************SERIALIZER********************
		public static void writeApp(Album a)	throws IOException {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
				oos.writeObject(a);
				oos.close();
		}
		
		public static Album readApp() throws IOException, ClassNotFoundException{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir+File.separator+storeFile));
			Album a = (Album)ois.readObject();
			ois.close();
			return a;
		}
}
