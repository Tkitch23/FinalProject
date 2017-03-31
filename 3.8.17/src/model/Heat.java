package model;



import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * @category model
 * @author Tyler
 *
 */
public class Heat {
	
	private IntegerProperty heatNumber;
	private ObservableList<StringProperty> swimmers = FXCollections.observableArrayList();
	private StringProperty time;
	private ObservableList<Integer> place =  FXCollections.observableArrayList();
	
	/**
	 * Default constructor of the heat class
	 */
	public Heat(){
		heatNumber  = new SimpleIntegerProperty(0);

	}
	/**
	 * Overloaded Constructor
	 * @param number the number that the heat is
	 */
	public Heat(int number){
		heatNumber  = new SimpleIntegerProperty(number);
		

	}

	public IntegerProperty getHeatNumber() {
		return heatNumber;
	}

	public void setHeatNumber(IntegerProperty heatNumber) {
		this.heatNumber = heatNumber;
	}
	
	public IntegerProperty heatNumberProperty(){
		return heatNumber;
	}




	public ObservableList<StringProperty> getSwimmers() {
		return swimmers;
	}
	public void setSwimmers(ObservableList<StringProperty> swimmers) {
		this.swimmers = swimmers;
	}




	
	public StringProperty getTime() {
		return time;
	}
	public void setTime(StringProperty time) {
		this.time = time;
	}
	public ObservableList<Integer> getPlace() {
		return place;
	}

	public void setPlace(ObservableList<Integer> place) {
		this.place = place;
	}
}
