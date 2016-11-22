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

public class Photo implements Serializable{
	private String image_path;
	private String caption;
	private SimpleDate date;
	private List<Tag> tags;
	//FOR SERIALIZER
	public static final String storeDir = "dat";
	public static final String storeFile = "date.dat";
	private static final long serialVersionUID = 1L;
	
	public Photo(String p, String cap, SimpleDate d){
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
	
	public SimpleDate getDate(){
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
	
	//********************SERIALIZER********************
		public static void writeApp(Photo p)	throws IOException {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
				oos.writeObject(p);
				oos.close();
		}
		
		public static Photo readApp() throws IOException, ClassNotFoundException{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir+File.separator+storeFile));
			Photo p = (Photo)ois.readObject();
			ois.close();
			return p;
		}
}
