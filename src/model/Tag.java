package model;

import java.util.ArrayList;
import java.util.List;

public class Tag {
	private String type;
	private String value;
	private List<Photo> photos;
	
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
		//TODO
		return false;
	}
}