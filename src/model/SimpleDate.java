package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.*;

/* Decided to make my own date class
 * Didn't need all the extra stuff from other ones
 */
public class SimpleDate implements Serializable {
	private int day;
	private int month;
	private int year;
	//FOR SERIALIZER
	public static final String storeDir = "dat";
	public static final String storeFile = "date.dat";
	private static final long serialVersionUID = 1L;
	
	public SimpleDate(long value){
		LocalDate d = LocalDate.ofEpochDay(value);
		day = d.getDayOfMonth();
		month = d.getMonthValue();
		year = d.getYear();
	}
	
	public boolean isInRange(SimpleDate from, SimpleDate to){
		if(this.getCompareValue() >= from.getCompareValue() && this.getCompareValue() <= to.getCompareValue()){
			return true;
		}
		return false;
	}
	
	public int getCompareValue(){
		//YYYYMMDD
		return (this.year*10000)+(this.month*100)+this.day;
	}
	
	@Override
	public String toString(){
		return month +"/"+ day +"/"+ year;
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
	    //Cast object to SimpleDate
	    SimpleDate other_date = (SimpleDate)o;
	    if(this.getCompareValue() == other_date.getCompareValue())
	    	return true;
	    else
	    	return false;
	}
	
	//********************SERIALIZER********************
		public static void writeApp(SimpleDate date)	throws IOException {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
				oos.writeObject(date);
				oos.close();
		}
		
		public static SimpleDate readApp() throws IOException, ClassNotFoundException{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir+File.separator+storeFile));
			SimpleDate date = (SimpleDate)ois.readObject();
			ois.close();
			return date;
		}
}
