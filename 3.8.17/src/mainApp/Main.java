package mainApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.animation.AnimationTimer;
import controller.AddBoxController;
import controller.AddEventController;
import controller.DataRootController;
import controller.DeleteBoxController;
import controller.EditChoiceController;
import controller.EditEventBoxController;
import controller.EditPersonController;
import controller.EditTeamController;
import controller.EventAddController;
import controller.EventEditController;
import controller.MeetChoiceCreator;
import controller.ResultsController;
import controller.StarterController;
import controller.SwimmerAddController;
import controller.TeamCreatorController;
import controller.TimerController;
import controller.ValidationController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.SingleEvent;
import model.Swimmers;

/**
 * @category Main
 * @author Tyler
 *
 */
public class Main extends Application{

	private Stage stage;
	private TimerController timerController;
	private DataRootController dataRootController;
	private EventAddController eventAddController;
	private BorderPane borderPane;
	private  ObservableList<SingleEvent> events = FXCollections.observableArrayList();
	private ObservableList<String> teamNames = FXCollections.observableArrayList();
	private boolean clear = true;
	private Swimmers tempAdd;
	private EditTeamController editTeam ;
	private EventEditController editEvents;
	


	//Standard Method that calls the start method
	public static void main(String[] args) {
		launch(args);
		

	}

	//loads the login screen
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		stage.setTitle("Login");
		initValidation();
		loadEvents();
	}



	/**
	 * method that is called in the start
	 */
	public void initValidation(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/Validaiton.fxml"));
			Scene scene = new Scene(loader.load());
			stage.setScene(scene);
			stage.show();
			stage.getIcons().add(new Image("file:src/Resources/Icon.png"));
			ValidationController validationController = loader.getController();
			validationController.setMain(this);


		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	
	/**
	 * if the right credentials are entered this method is called
	 */
	public void initStarter(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/Starter.fxml"));
			Scene scene = new Scene(loader.load());
			Stage window = new Stage();
			window.setScene(scene);
			window.show();

			StarterController starterController = loader.getController();
			starterController.setMain(this);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * if the right credentials are entered this method is called
	 */
	public void initTimer(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/Timer.fxml"));
			Scene scene = new Scene(loader.load());
			Stage window = new Stage();
			window.setScene(scene);
			window.show();

			timerController = loader.getController();
			timerController.setMain(this);

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * if the right credentials are entered this method is called
	 */
	public void initData(){

		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/DataRoot.fxml"));
			borderPane = (BorderPane) loader.load();
			Scene scene = new Scene(borderPane);

			initDataHome();
			Stage window = new Stage();
			window.setTitle("Welcome to Data Entry");
			window.getIcons().add(new Image("file:src/Resources/Icon.png"));
			window.setScene(scene);
			window.show();

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * home page of data entry
	 */
	public void initDataHome(){
		try{


			FXMLLoader defaultLoader = new FXMLLoader();
			defaultLoader.setLocation(Main.class.getResource("/view/DataHome.fxml"));
			AnchorPane anchorPane = (AnchorPane) defaultLoader.load();
			borderPane.setCenter(anchorPane);
			dataRootController = defaultLoader.getController();
			dataRootController.setMain(this);



		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * adds events
	 */
	public void initEventAdd(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/EventAdd.fxml"));
			AnchorPane meetCreate = (AnchorPane) loader.load();

			setEventAddController(loader.getController());
			getEventAddController().setMain(this);
			borderPane.setCenter(meetCreate);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * sets the center of the borderpane in the data rootLayout
	 */
	public void setCenterTeamAdd(){

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/TeamCreator.fxml"));
			AnchorPane teamAdd = (AnchorPane) loader.load();

			TeamCreatorController teamCreatorController = loader.getController();
			teamCreatorController.setMain(this);
			borderPane.setCenter(teamAdd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * decides if events or swimmers need to be added
	 */
	public void initMeetChoice(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/MeetCreateMenu.fxml"));
			AnchorPane meetChoice = (AnchorPane) loader.load();
			borderPane.setCenter(meetChoice);
			MeetChoiceCreator meet = loader.getController();
			meet.setMain(this);




		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * adds swimmers to events
	 */
	public void initSwimmerAdd(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/SwimmerAdd.fxml"));

			AnchorPane swimAdd = (AnchorPane) loader.load();
			SwimmerAddController swimmerAddController = loader.getController();
			swimmerAddController.setMain(this);
			borderPane.setCenter(swimAdd);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Opens the editor
	 */
	public void initTeamEdit(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/EditTeam.fxml"));

			AnchorPane edit = (AnchorPane) loader.load();
			editTeam = loader.getController();
			editTeam.setMain(this);
			borderPane.setCenter(edit);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Opens the Add box
	 */
	public void initAdd(){
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/AddPerson.fxml"));
			Scene scene = new Scene(loader.load(),500,300);
			stage.setScene(scene);
			AddBoxController editt = loader.getController();
			editt.setMain(this);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Opens the editor for teams
	 */
	public void initEdit(){
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/EditPerson.fxml"));
			Scene scene = new Scene(loader.load(),500,300);
			stage.setScene(scene);
			EditPersonController editt = loader.getController();
			editt.setMain(this);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens the delete box
	 */
	public void initDelete(){
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/DeleteBox.fxml"));
			Scene scene = new Scene(loader.load(),500,300);
			stage.setScene(scene);
			DeleteBoxController editt = loader.getController();
			editt.setMain(this);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Pulls entries from the SQL database and puts them as java objects
	 */
	public void loadTeamNames(){

		boolean add = true;
		Connection c = null;
		Statement stmt = null;

		try{
			c = DriverManager.getConnection("jdbc:sqlite:timing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM TEAMNAMES;" );
			System.out.println("3");
			while ( rs.next() ==true) {
				String  name = rs.getString("NAME");

				for(int i =0;i<teamNames.size();i++){
					if(teamNames.get(i)==name){
						add=false;
					}

				}
				if(add==true){
					teamNames.add(name);
					System.out.println(name + " ADDED TO  LIST");
				}

			}
		}catch(Exception e){
			System.out.println("No teams yet");
		}try{
			stmt.close();
			c.commit();
			c.close();}catch(Exception e){

			}

	}
	
	/**
	 * Opens the editor
	 */
	public void initEventEdit(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/EditEvents.fxml"));

			AnchorPane edit = (AnchorPane) loader.load();
			editEvents = loader.getController();
			editEvents.setMain(this);
			borderPane.setCenter(edit);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Makes a Database connection and loades the events already made into
	 * a java object
	 */
	public void loadEvents(){

		boolean add = true;
		Connection c = null;
		Statement stmt = null;
		events.clear();

		try{
			c = DriverManager.getConnection("jdbc:sqlite:timing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM EVENTS;" );
			while ( rs.next() ==true) {
				String  name = rs.getString("EVENTNAME");
				int eventNumber = rs.getInt("EVENTNUMBER");
				int heatNumber = rs.getInt("HEATNUMBER");
				SingleEvent temp = new SingleEvent(eventNumber,heatNumber, name);

				for(int i =0;i<events.size();i++){
					if(events.get(i).getEventName().get()==name){
						add=false;
					}

				}
				if(add==true){
					events.add(temp);
					System.out.println(name + " ADDED TO  LIST");
				}

			}
		}catch(Exception e){
			System.out.println("No Events Yet");
		}
		try{
			stmt.close();
			c.commit();
			c.close();
			System.out.println("Closed");
		}
		catch(Exception e){
			e.printStackTrace();
		}
			

	}
	
	/**
	 * Opens the Menu for editing
	 */
	public void initEditChoice(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/EditChoice.fxml"));

			AnchorPane edit = (AnchorPane) loader.load();
			EditChoiceController editor = loader.getController();
			editor.setMain(this);
			borderPane.setCenter(edit);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	/**
	 * Opens the Add box
	 */
	public void initEditEventAdd(){

		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/AddEvent.fxml"));
			Scene scene = new Scene(loader.load(),500,300);
			stage.setScene(scene);
			AddEventController ohhhh = loader.getController();
			ohhhh.setMain(this);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	/**
	 * Opens the editor
	 */
	public void initEventEditBox(){
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/EditEventBox.fxml"));
			Scene scene = new Scene(loader.load(),500,300);
			stage.setScene(scene);
			EditEventBoxController kk = loader.getController();
			kk.setMain(this);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens the Results
	 */
	public void initResults(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/Results.fxml"));
			AnchorPane result = (AnchorPane) loader.load();
			ResultsController results = loader.getController();
			results.setMain(this);
			
			borderPane.setCenter(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BorderPane getBorderPane() {
		return borderPane;
	}

	public void setBorderPane(BorderPane borderPane) {
		this.borderPane = borderPane;
	}

	public DataRootController getDataRootController() {
		return dataRootController;
	}

	public void setDataRootController(DataRootController dataRootController) {
		this.dataRootController = dataRootController;
	}

	public TimerController getTimerController() {
		return timerController;
	}

	public void setTimerController(TimerController timerController) {
		this.timerController = timerController;
	}


	public EventAddController getEventAddController() {
		return eventAddController;
	}

	public void setEventAddController(EventAddController eventAddController) {
		this.eventAddController = eventAddController;
	}
	public ObservableList<SingleEvent> getEvents() {
		return events;
	}

	public void setEvents(ObservableList<SingleEvent> events) {
		this.events = events;
	}

	public ObservableList<String> getTeamNames() {
		return teamNames;
	}

	public void setTeamNames(ObservableList<String> teamNames) {
		this.teamNames = teamNames;
	}
	

	public EditTeamController getEditTeam() {
		return editTeam;
	}

	public void setEditTeam(EditTeamController editTeam) {
		this.editTeam = editTeam;
	}
	
	public EventEditController getEditEvents() {
		return editEvents;
	}

	public void setEditEvents(EventEditController editEvents) {
		this.editEvents = editEvents;
	}

	public Swimmers getTempAdd() {
		return tempAdd;
	}

	public void setTempAdd(Swimmers tempAdd) {
		this.tempAdd = tempAdd;
	}

	public boolean isClear() {
		return clear;
	}

	public void setClear(boolean clear) {
		this.clear = clear;
	}



}
