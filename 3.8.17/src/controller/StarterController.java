package controller;



import java.net.URL;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import mainApp.Main;
import model.SingleEvent;
/**
 * @category Controller
 * @author Tyler
 *
 */
public class StarterController {
	@FXML private Button goButton;
	@FXML private Label eventNum;
	@FXML private Label heatNum;
	private Main main;
	private int eventCounter =0;
	private boolean firstTime = true;
	private int heatCounter =0;
	private ObservableList<SingleEvent> events;

	public StarterController(){

	}

	/**
	 * reflects through main to get the label of another window
	 */
	public void startTimer(){
		if(main.getTimerController().isLocked()==false && main.getTimerController().isReadyUp()==true){
			main.getTimerController().startTimer();
			go();
		}

	}


	/**
	 * cycles through and goes one forward
	 */
	public void nextEvent(){
		events = main.getEvents();
		System.out.println(events.get(0).getEventName().get());

		if(getEventCounter()<= events.size()){

			int event = events.get(getEventCounter()).getEventNumber().get();
			String eventString = Integer.toString(event);
			main.getTimerController().getEventNum().setText(eventString);
			eventNum.setText(eventString);
			setEventCounter(eventCounter+1);
			System.out.println(getEventCounter());

		}
	}

	/**
	 * Take you back an event from the previous one
	 */
	public void backEvent(){



		if(getEventCounter()<= events.size()&& getEventCounter()>0){

			setEventCounter(eventCounter-1);
			System.out.println(getEventCounter());
			int event = getEventCounter();
			String eventStringThing = Integer.toString(event);
			eventNum.setText(eventStringThing);
			main.getTimerController().getEventNum().setText(eventStringThing);


		}
	}

	/**
	 * The signature gives it away no comments needed ;)
	 */
	public  void nextHeat(){
		System.out.println("Next");
		events = main.getEvents();

		if( heatCounter<= events.get(eventCounter-1).getHeats().size()){
			int heat = events.get(eventCounter-1).getHeats().get(heatCounter).getHeatNumber().get();
			String heatString = Integer.toString(heat);
			main.getTimerController().getHeatNum().setText(heatString);
			heatNum.setText(heatString);
			heatCounter++;
		}
		if(heatCounter== events.get(eventCounter-1).getEventNumber().get()){
			heatCounter=0;

		}
	}
	/**
	 * Wow another self explanatory method
	 */
	public  void backHeat(){
		System.out.println("Back");


		heatCounter--;

		if( heatCounter<= events.get(eventCounter-1).getHeats().size()){

			int heat = events.get(eventCounter-1).getHeats().get(heatCounter-1).getHeatNumber().get();
			String heatString = Integer.toString(heat);
			heatNum.setText(heatString);
			main.getTimerController().getHeatNum().setText(heatString);
		}

	}
	/**
	 * Says what the name is 
	 */
	public void takeMark(){
		try{
			Media media = new Media(getClass().getResource("/Sounds/taker.mp3").toURI().toString());

			MediaPlayer mp = new MediaPlayer(media);
			mp.play();

			System.out.println("Playing...");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * BAMMMMMMMMMM the race started
	 */
	public void go(){
		try{
			Media media = new Media(getClass().getResource("/Sounds/GO.mp3").toURI().toString());

			MediaPlayer mp = new MediaPlayer(media);
			mp.play();
			main.getTimerController().getMinuteLbl().setTextFill(Color.BLACK);
			System.out.println("Playing...");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * YES AUDIO YES BONUS POINTS FOR DAYS
	 */
	public void falseStart(){
		try{
			Media media = new Media(getClass().getResource("/Sounds/False.mp3").toURI().toString());

			MediaPlayer mp = new MediaPlayer(media);
			mp.play();

			main.getTimerController().stopTimer();
			main.getTimerController().getMinuteLbl().setTextFill(Color.RED);
			main.getTimerController().getMinuteLbl().setText("*******");
			main.getTimerController().getSecondLbl().setText("");
			System.out.println("Playing...");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void setMain(Main main){
		this.main = main;
	}
	public int getEventCounter() {
		return eventCounter;
	}
	public void setEventCounter(int eventCounter) {
		this.eventCounter = eventCounter;
	}
	public int getHeatCounter() {
		return heatCounter;
	}
	public void setHeatCounter(int heatCounter) {
		this.heatCounter = heatCounter;
	}
	public boolean isFirstTime() {
		return firstTime;
	}
	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}




	@FXML
	public void initialize(){



	}
}