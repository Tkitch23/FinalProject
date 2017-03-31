package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * @category Model
 * @author Tyler
 *This class sets up the swimmers in the program
 */
public class Swimmers {
	private StringProperty fName;
	private StringProperty lName;
	private IntegerProperty age;
	
	/**
	 * Default constructor
	 */
	public Swimmers(){
		fName = new SimpleStringProperty("");
		lName = new SimpleStringProperty("");
		age = new SimpleIntegerProperty(0);

	}
	
	/**
	 * Overloaded Constructor
	 * @param first The firstname of the swimmer
	 * @param last The last name of the swimmer
	 * @param old How old the swimmer is
	 */
	public Swimmers(String first, String last, int old){
		fName = new SimpleStringProperty(first);
		lName = new SimpleStringProperty(last);
		age = new SimpleIntegerProperty(old);
		
	}
	public StringProperty getfName() {
		return fName;
	}
	public void setfName(StringProperty fName) {
		this.fName = fName;
	}
	public StringProperty getlName() {
		return lName;
	}
	public void setlName(StringProperty lName) {
		this.lName = lName;
	}
	public IntegerProperty getAge() {
		return age;
	}
	public void setAge(IntegerProperty age) {
		this.age = age;
	}
	
	//special to get the property
	public IntegerProperty ageProperty(){
		return age;
	}
	public StringProperty fNameProperty(){
		return fName;
	}
	public StringProperty lNameProperty(){
		return lName;
	}
	
}
