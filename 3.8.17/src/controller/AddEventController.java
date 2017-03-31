package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainApp.Main;
import model.SingleEvent;
import model.Swimmers;
import utilities.Utilities;

/**
 * @category controller
 * @author Tyler
 *
 */
public class AddEventController {
	@FXML private TextField eName;
	@FXML private TextField eNum;
	@FXML private TextField heatNum;
	private Main main;

	public AddEventController(){

	}
	
	/**
	 * Some quality Reflection
	 * @param main sets the reflection
	 */
	public void setMain(Main main){
		this.main = main;
	}
	
	/**
	 * Oh look the same method as the  class before this because I am an idiot and lazy and didnt
	 * want to remake the class so and have it have an option on what is what editing :)
	 */
	public void handleAdd(){
		if(heatNum.getText().equals("") || eNum.getText().equals("") || eName.getText().equals("")){
			Utilities.alert("Please Fill Out All Fields");
		
		}else{
			if(Utilities.intCheck(eNum.getText())==true && Utilities.intCheck(heatNum.getText())==true){
			int eNumber = Integer.parseInt(eNum.getText());
			int hNumber = Integer.parseInt(heatNum.getText());
			Stage stage = (Stage) eName.getScene().getWindow();
			stage.close();
			
			SingleEvent newOne = new SingleEvent(eNumber, hNumber-1, eName.getText());
			main.getEvents().add(newOne);
			
			System.out.println("Added");
			
			Connection c = null;
			Statement stmt = null;
			String sql = "";
			try{
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:timing.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				//adds the swimmer information to a table for later use
				
				sql = "INSERT INTO EVENTS (EVENTNAME,EVENTNUMBER,HEATNUMBER) VALUES ('" +eName.getText()
				+ "'," + eNumber+"," +  hNumber +")";
				stmt.executeUpdate(sql);
				System.out.println("Event Added");
				stmt.close();
				c.commit();
				c.close();
			}catch ( Exception e ) {
				e.printStackTrace();
			}
			
		
		}else{
			Utilities.alert(eNum.getText() + " Is Not an Integer");
		}
		}
		main.getEditEvents().populateTable();
	}


}
