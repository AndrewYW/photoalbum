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

public class Album implements Serializable{
	private String title;
	private List<Photo> photos;
	private SimpleDate oldest;
	private SimpleDate newest;
	private int numberOfPhotos;
	//FOR SERIALIZER
	public static final String storeDir = "dat";
	public static final String storeFile = "album.dat";
	private static final long serialVersionUID = 1L;
	
	public Album(String t){
		title = t;
		photos = new ArrayList<Photo>();
		//TODO remove test line
		photos.add(new Photo("test/test","ughhhhh",new SimpleDate(23423423L)));
		numberOfPhotos = 0;
	}
	
	public Album(String t, List<Photo> p){
		title = t;
		photos = p;
		if(p != null){
			numberOfPhotos = photos.size();
			for(Photo aPhoto : p){
				if(oldest == null || oldest.getCompareValue() > aPhoto.getDate().getCompareValue()){
					oldest = aPhoto.getDate();
				}
				if(newest == null || newest.getCompareValue() < aPhoto.getDate().getCompareValue()){
					newest = aPhoto.getDate();
				}
			}
		}
	}
	
	public String getTitle(){
		return title;
	}
	/**
	 * Oddly enough, this is required for the album name to be interpreted as a string
	 * 
	 * There are already methods that do both of these things.
	 * 	-Rumzi
	 * 
	public String toString(){
		return title;
	}
	public void editTitle(String s){
		title = s;
	}
	*/
	public int getNumberOfPhotos(){
		return numberOfPhotos;
	}
	
	public SimpleDate getOldest(){
		return oldest;
	}
	
	public SimpleDate getNewest(){
		return newest;
	}

	public void rename(String t){
		title = t;
	}
	
	public void addPhoto(Photo p){
		photos.add(p);
		numberOfPhotos++;
	}
	
	public void deletePhoto(Photo p){
		photos.remove(p);
		numberOfPhotos--;
	}
	
	public List<Photo> openAlbum(){
		return photos;
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
