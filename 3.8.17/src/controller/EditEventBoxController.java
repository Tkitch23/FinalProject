package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainApp.Main;
import model.SingleEvent;
import model.Swimmers;
import utilities.Utilities;

/**
 * This class is the controller for the team edit
 * @category Controller
 * @author Tyler
 *
 */
public class EditEventBoxController {
	@FXML private TextField eName;
	@FXML private TextField eNum;
	@FXML private TextField heatNum;
	@FXML private Button addButton;
	private SingleEvent current;
	private Main main;

	/**
	 * Using the initialize method would result in an error because it builds it first
	 * looks sloppy but late binding works!
	 */
	public void setMain(Main main){
		this.main = main;
		Integer eNumber = main.getEditEvents().getTable().getSelectionModel().getSelectedItem().getEventNumber().get();
		Integer hNumber = main.getEditEvents().getTable().getSelectionModel().getSelectedItem().getHeatNumber().get();
		current = main.getEditEvents().getTable().getSelectionModel().getSelectedItem();
		eName.setText(main.getEditEvents().getTable().getSelectionModel().getSelectedItem().getEventName().get());
		eNum.setText(eNumber.toString());
		heatNum.setText(hNumber.toString());
	}

	public EditEventBoxController(){

	}

	/**
	 * You might see this method later because I am yet again lazy and copy pasted code :)
	 */
	public void handleEdit(){
		if(Utilities.intCheck(eNum.getText())==true && Utilities.intCheck(heatNum.getText())==true){
			int eNumber = Integer.parseInt(eNum.getText());
			int hNumber = Integer.parseInt(heatNum.getText());
			SingleEvent temp = new SingleEvent(eNumber,hNumber,eName.getText());
			Stage stage = (Stage) addButton.getScene().getWindow();
			stage.close();
			main.getEditEvents().getEvents().clear();


			Connection c = null;
			Statement stmt = null;
			String sql = "";

			try{
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:timing.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				//adds the swimmer information to a table for later use
				sql = "UPDATE EVENTS SET EVENTNAME = " +"'"+
						temp.getEventName().get()+"', EVENTNUMBER = '" + temp.getEventNumber().get()+"', HEATNUMBER = " +hNumber +" WHERE EVENTNAME = '" + current.getEventName().get() + "' AND EVENTNUMBER = '" + current.getEventNumber().get()+"';"  ;
				stmt.executeUpdate(sql);
				System.out.println("Event Edited");
				stmt.close();
				c.commit();
				c.close();
			}catch ( Exception e ) {
				e.printStackTrace();
			}


		}else{
			/**
			 * Look at me using a utility class, So impressive
			 */
			Utilities.alert(eNum.getText() + " Is Not an Integer");
		}
		System.out.println("Added");
		main.getEditEvents().populateTable();
	}



}
