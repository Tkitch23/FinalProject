package controller;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import mainApp.Main;
/**
 * 
 * @author Tyler
 *
 */
public class TimerController {
	@FXML private Label eventNum;
	@FXML private Label heatNum;
	@FXML private Label minuteLbl;
	@FXML private Label secondLbl;
	@FXML private Label tensLabel;
	@FXML private ImageView readyImage;
	private boolean locked;
	private boolean readyUp;
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	private Main main;
	private boolean letsRun = false;

	private  BooleanProperty running = new SimpleBooleanProperty(false);

	public Label getSecondLbl() {
		return secondLbl;
	}

	public void setSecondLbl(Label secondLbl) {
		this.secondLbl = secondLbl;
	}

	public void setMain(Main main){
		this.main = main;
	}

	public Label getMinuteLbl() {
		return minuteLbl;
	}

	public void setMinuteLbl(Label minuteLbl) {
		this.minuteLbl = minuteLbl;
	}

	public TimerController(){

	}

	/**
	 * These are my animation points
	 */
	public AnimationTimer timer = new AnimationTimer() {

		private long startTime ;

		@Override
		public void handle(long now) {
			//this is the time in between the curent time with lots of conversions and math :)
			long duration = System.currentTimeMillis() - startTime;
			long minutes = duration / 60000 ;
			double seconds = duration / 1000.0 ;
			double secondsAdjusted = seconds%60;
			double secondsRounded = Math.round(secondsAdjusted * 100.0) / 100.0;

			
			minuteLbl.setText(""+minutes);
			secondLbl.setText(""+secondsRounded);

		}
		@Override
		public void start() {
			setLocked(true);
			running.set(true);
			startTime = System.currentTimeMillis();
			super.start();
		}
		@Override
		public void stop() {
			
			
			running.set(false);
			super.stop();
		}
	};

	public void ready(){
		setReadyUp(true);
		readyImage.setVisible(true);
	}

	public boolean isReadyUp() {
		return readyUp;
	}

	public void setReadyUp(boolean readyUp) {
		this.readyUp = readyUp;
	}

	public void startTimer(){
		timer.start();
	}
	
	/**
	 * saves the time in the list to view in results
	 */
	public void stopTimer(){
		timer.stop();
		setLocked(false);
		setReadyUp(false);
		readyImage.setVisible(false);
		String time = minuteLbl.getText() + ":" + secondLbl.getText();
		main.getEvents().get(Integer.parseInt(eventNum.getText())-1).getHeats().get(Integer.parseInt(heatNum.getText())-1).setTime(new SimpleStringProperty(time));;
		
		
	}
	
	@FXML
	public void initialize(){
		locked=false;
		readyUp=false;
		readyImage.setVisible(false);
	}



	public AnimationTimer getTimer() {
		return timer;
	}


	public void setTimer(AnimationTimer timer) {
		this.timer = timer;
	}

	
	public boolean isLetsRun() {
		return letsRun;
	}

	public void setLetsRun(boolean letsRun) {
		this.letsRun = letsRun;
	}
	
	public Label getEventNum() {
		return eventNum;
	}

	public void setEventNum(Label eventNum) {
		this.eventNum = eventNum;
	}
	
	public Label getHeatNum() {
		return heatNum;
	}

	public void setHeatNum(Label heatNum) {
		this.heatNum = heatNum;
	}


}