package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import mainApp.Main;
import model.Swimmers;


/**
 * Handles alot of data entry
 * @author Tyler
 *@category Controller
 */
public class SwimmerAddController {
	/**
	 * All these fields are insane, but useful
	 */
	@FXML private ChoiceBox<Integer> eventChoice;
	@FXML private ChoiceBox<Integer> heatChoice;
	@FXML private ComboBox<String> swimmerName;
	@FXML private Button addButton;
	@FXML private Button exitButton;
	@FXML private Label nameConfirm;
	@FXML private Label eConfirm;
	@FXML private Label hConfirm;
	@FXML private TextField laneText;
	@FXML private ChoiceBox<String> team1;
	@FXML private ChoiceBox<String> team2;
	@FXML private Label noEvents;
	@FXML private ChoiceBox<String> ageBox;
	private ArrayList<Swimmers> names = new ArrayList<>();
	private Main main;
	private boolean firstTime = true;
	private boolean test = true;
	private boolean numberOne = false;
	private boolean numberTwo = false;
	private boolean errorCatch = false;

	/**Some late binding right here
	 * |
	 * @param main
	 */
	public void setMain(Main main){
		System.out.println("Main called");
		this.main = main;

		//made methods so set Main was a littke cleaner
		populateTeamNames();
		populateEvents();
		noEvents.setVisible(false);	
		if(main.getEvents().size()==0){
			noEvents.setVisible(true);		}
		swimmerName.getEditor().textProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, 
					String oldValue, String newValue) {
				filterNames();

			}
			
			});

		team2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
				if(team1.getValue() !=null)
					filterBoxAdd();

			}
		});

	}	




	public SwimmerAddController(){
		
	}
	
	public void removeList(){
		
		
	}

	/**
	 * Basic method that clears stuff
	 */
	public void startFresh(){

		team1.getItems().clear();
		team2.getItems().clear();
		eventChoice.getItems().clear();
		heatChoice.getItems().clear();
		System.out.println("Cleared");

	}

	/**
	 * Gets the list already made so no SQL is needed
	 */
	public void populateTeamNames(){
		main.getTeamNames().clear();
		System.out.println("Fired");
		if(test==true){
			main.loadTeamNames();
			for(int i = 0; i<main.getTeamNames().size(); i++){


				team1.getItems().add(main.getTeamNames().get(i));
				team2.getItems().add(main.getTeamNames().get(i));

			}
		}
	}	
	
	/**
	 * autoupdates the heat tab
	 */
	public void populateEvents(){
		if(firstTime==true && main.getEvents().size() !=0){

			for(int i = 0; i<main.getEvents().size();i++){

				eventChoice.getItems().add(main.getEvents().get(i).getEventNumber().getValue());
			}
			firstTime=false;
			System.out.println("Flipped boolean");

		}

		/**
		 * Event Listener to autoupdate
		 */
		eventChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				//this has to be here to make the selection before it is called

				String newNum = newValue.toString();
				int intTest = Integer.parseInt(newNum);
				eventChoice.getSelectionModel().select(intTest);

				//if the event numbe gets changed this repopulates the heats
				heatLoop();

			}

		});




	}

	/**
	 * SETTING THE HEAT CHOICE BOX NUMBERS TO NUMBER OF HEATS IN EVENT
	 */
	public void heatLoop(){
		heatChoice.getItems().clear();
		int number = main.getEvents().get((eventChoice.getValue())-1).getHeats().size() +1;
		for(int i = 0;i< number;i++){
			heatChoice.getItems().add((i+1));
		}

	}
	/**
	 * offficial button call that adds the swimmer
	 */
	public void addSwimmer(){
		int index = Integer.parseInt(laneText.getText());
		String name = swimmerName.getEditor().getText();
		main.getEvents().get((eventChoice.getValue())-1).getHeats().get((heatChoice.getValue())-1).getSwimmers().add(new SimpleStringProperty(name)); 
		System.out.println(main.getEvents().get((eventChoice.getValue())-1).getHeats().get((heatChoice.getValue() - 1)).getSwimmers().get(index-1));
		
		swimmerName.getEditor().clear();
	}
	 /**
	  * No surprises here
	  */
	public void exit(){
		startFresh();
		main.initDataHome();

	}

	public void flipOne(){
		numberOne=true;

	}

	public void flipTwo(){
		numberTwo = true;
	}

	/**
	 * My most impressive method of all that adds  the people to the filter box
	 */
	public void filterBoxAdd(){

		Connection c = null;
		Statement stmt = null;

		try{
			c = DriverManager.getConnection("jdbc:sqlite:timing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String team = team1.getValue().toUpperCase().trim().replaceAll("\\s", "");
			ResultSet rs = stmt.executeQuery( "SELECT * FROM " + team+";" );
			while ( rs.next() ==true) {
				String  fName = rs.getString("FIRSTNAME");
				String lName = rs.getString("LASTNAME");
				int age = rs.getInt("AGE");
				Swimmers swimmer = new Swimmers(fName,lName,age);
				names.add(swimmer);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		try{
			c = DriverManager.getConnection("jdbc:sqlite:timing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String team = team2.getValue().toUpperCase().trim().replaceAll("\\s", "");
			ResultSet rs = stmt.executeQuery( "SELECT * FROM " + team+";" );
			while ( rs.next() ==true) {
				String  fName = rs.getString("FIRSTNAME");
				String lName = rs.getString("LASTNAME");
				int age = rs.getInt("AGE");
				Swimmers swimmer = new Swimmers(fName,lName,age);
				names.add(swimmer);
			}
		}catch(Exception e){
			System.out.println("No teams yet");
		}




	}
	
	public void flipper(){
			if(errorCatch==false){
					errorCatch=true;
			}else{
				errorCatch=false;
			}
	}

	/**
	 * I Take it back this is the most impressive that actually filters the names when you type
	 */
	public void filterNames(){
		try{
		swimmerName.getItems().clear();

		for(int x = 0;x<names.size();x++){
			String first = "";
			String last = "";
			first = names.get(x).getfName().get();
			last = names.get(x).getlName().get();
			String combo = first + " " + last;
			if(swimmerName.getEditor().getText() != ""){
				if(combo.indexOf(swimmerName.getEditor().getText()) != -1){
					swimmerName.getItems().add(combo);
				}
			}
		}
		System.out.println("Worked");
		}catch(Exception e){
			System.out.println("Caught the mofo error");
		}
	}


	@FXML 
	public void initialize(){


	}}