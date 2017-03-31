package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainApp.Main;
import model.SingleEvent;
import model.Swimmers;
import utilities.Utilities;
/**
 * Controller for the AddBox
 * @category Controller
 * @author Tyler
 *
 */
public class EventAddController {

	/**
	 * SQL IS PLAIN ENGLISH NO COMMENTS NEEDED
	 */
	@FXML private TextField eNumber;
	@FXML private TextField eName;
	@FXML private TextField heats;
	@FXML private Button addButton;
	@FXML private Button exitButton;
	@FXML private Label number;
	@FXML private Label name;
	@FXML private Label heat;
	@FXML private Label txt1;
	@FXML private Label txt2;
	@FXML private Label txt3;
	@FXML private Label txt4;
	private Main main;


	/**
	 * Blank Constructor for initialize
	 */
	public EventAddController(){

	}

	/**
	 * gives the hook to the main app
	 * @param main Like looking into a puddle and seeing your self
	 */
	public void setMain(Main main){
		this.main = main;
	}

	/**
	 * add button triggers this method
	 */
	public void handleAdd(){
		//showing the last added event
		number.setVisible(true);
		name.setVisible(true);
		heat.setVisible(true);
		txt1.setVisible(true);
		txt2.setVisible(true);
		txt3.setVisible(true);
		txt4.setVisible(true);

		int eNum =0;
		int hNum=0;
		/**
		 * creating a new event and adding it to an array to store generic event
		 */
		if(Utilities.intCheck(eNumber.getText())==true){
			eNum = Integer.parseInt(eNumber.getText());
		}else{
			Utilities.errorPopUp(eNumber.getText());
		}

		if(Utilities.intCheck(heats.getText())==true){
			hNum = Integer.parseInt(heats.getText());
		}else{
			Utilities.errorPopUp(heats.getText());
		}

		if(eNum!=0&&hNum !=0){
			SingleEvent newOne = new SingleEvent(eNum, hNum-1, eName.getText());
			main.getEvents().add(newOne);
			

			//sets the last added text
			number.setText(eNumber.getText());
			name.setText(eName.getText());
			heat.setText(heats.getText());
			
			createEventTable();
			Connection c = null;
			Statement stmt = null;
			String sql = "";

			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:Timing.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				sql = "INSERT INTO EVENTS (EVENTNAME,EVENTNUMBER,HEATNUMBER) VALUES ('" +eName.getText()
				+ "'," + eNum+"," +  hNum +")";
				System.out.println("Added");
				stmt.execute(sql);
				stmt.close();
				c.commit();
				c.close();
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try{
				c = DriverManager.getConnection("jdbc:sqlite:timing.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				
				ResultSet rs = stmt.executeQuery( "SELECT * FROM EVENTS" );
				while ( rs.next() ==true) {
					String  eventName = rs.getString("EVENTNAME");
					int eventNumber = rs.getInt("EVENTNUMBER");
					int heatNumber = rs.getInt("HEATNUMBER");
					
					System.out.println(eventName + " " + eventNumber + " " + heatNumber + " ");
					
					
				}
				
				
			}catch(Exception e){
				System.out.println("No teams yet");
			}
			try{
				stmt.close();
				c.commit();
				c.close();
			}catch(Exception e){
				e.printStackTrace();
			}

		}

		



		//clears the textfields
		eNumber.clear();
		eName.clear();
		heats.clear();



	}
	public void handleExit(){

		
		main.initDataHome();

	}
	
	/**
	 * If this is the first time it is ran then this method makes the table in the database
	 */
	public void createEventTable(){
		Connection c = null;
		Statement stmt = null;
		String sql = "";
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Timing.db");
			//does not run all SQL at once, enables you to only have one SQL String variable 
			//but have to run an execute each time
			c.setAutoCommit(false);
			stmt = c.createStatement();
			System.out.println("Opened database successfully");
			sql = "CREATE TABLE EVENTS (EVENTNAME TEXT NOT NULL,"
					+ "	EVENTNUMBER INT NOT NULL, HEATNUMBER INT NOT NULL)";

			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();

		}catch ( Exception e ) {
			System.out.println("Already Created");
		}
	
	}



	public TextField geteNumber() {
		return eNumber;
	}

	public void seteNumber(TextField eNumber) {
		this.eNumber = eNumber;
	}

	public TextField geteName() {
		return eName;
	}

	public void seteName(TextField eName) {
		this.eName = eName;
	}

	public TextField getHeats() {
		return heats;
	}

	public void setHeats(TextField heats) {
		this.heats = heats;
	}

}
