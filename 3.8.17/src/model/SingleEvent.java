package model;



import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Tyler
 * @category Model
 *
 */
public class SingleEvent {
	private IntegerProperty eventNumber;
	private StringProperty eventName;
	private ArrayList<Heat> heats = new ArrayList<>();
	private IntegerProperty heatNumber;

	/**
	 * This is the default constructor
	 */
	public SingleEvent(){
		eventNumber = new SimpleIntegerProperty(0);
		eventName = new SimpleStringProperty("1 Fly");

	}


	/**
	 * The over loaded constructor for this class
	 * @param eventNumber The number that is displayed on the timer
	 * @param heat the number that is dispplayed on the timer
	 * @param eventName The Name of the event that it will be called by
	 */
	public SingleEvent(int eventNumber, int heat, String eventName) {
		
		this.eventNumber = new SimpleIntegerProperty(eventNumber);
		this.eventName =new SimpleStringProperty(eventName);
		for(int i =0;i<heat;i++){
			heats.add(new Heat(i+1));
		}

		this.setHeatNumber(new SimpleIntegerProperty(heat));


	}



	public IntegerProperty getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(IntegerProperty eventNumber) {
		this.eventNumber = eventNumber;
	}


	public StringProperty getEventName() {
		return eventName;
	}

	public void setEventName(StringProperty eventName) {
		this.eventName = eventName;
	}




	public ArrayList<Heat> getHeats() {
		return heats;
	}



	public void setHeats(ArrayList<Heat> heats) {
		this.heats = heats;
	}



	//getting the property value
	public IntegerProperty eventNumberProperty(){
		return eventNumber;
	}

	public StringProperty eventNameProperty(){
		return eventName;
	}



	public IntegerProperty getHeatNumber() {
		return heatNumber;
	}



	public void setHeatNumber(IntegerProperty heatNumber) {
		this.heatNumber = heatNumber;
	}




}
