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

public class Tag implements Serializable{
	private String type;
	private String value;
	private List<Photo> photos;
	//FOR SERIALIZER
	public static final String storeDir = "dat";
	public static final String storeFile = "tag.dat";
	private static final long serialVersionUID = 1L;
	
	public Tag(String t, String v){
		type = t;
		value = v;
		photos = new ArrayList<Photo>();
	}
	
	public Tag(String t, String v, List<Photo> p){
		type = t;
		value =v;
		photos = p;
	}
	
	public String getType(){
		return type;
	}
	
	public String getValue(){
		return value;
	}
	
	public void addTo(Photo p){
		p.addTag(this);
		photos.add(p);
	}
	
	public void removeFrom(Photo p){
		p.deleteTag(this);
		photos.remove(p);
	}
	
	@Override
	public boolean equals(Object o){
	    //not equal if object points to nothing
	    if (o == null)
	        return false;
		//Equal if they are literally the same object in memory
	    if (this == o)
	        return true;
	    //Not equal if they aren't of the same class
	    if (getClass() != o.getClass())
	        return false;
	    //Cast object to Tag
	    Tag other_tag = (Tag)o;
	    if(this.getType().compareTo(other_tag.getType()) == 0 && this.getValue().compareTo(other_tag.getValue()) == 0)
	    	return true;
	    else
	    	return false;
	}
	
	//********************SERIALIZER********************
		public static void writeApp(Tag tag)	throws IOException {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
				oos.writeObject(tag);
				oos.close();
		}
		
		public static Tag readApp() throws IOException, ClassNotFoundException{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir+File.separator+storeFile));
			Tag tag = (Tag)ois.readObject();
			ois.close();
			return tag;
		}
}
