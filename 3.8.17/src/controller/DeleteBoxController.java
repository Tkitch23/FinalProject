package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import mainApp.Main;
/**
 * Class is the controller for the box that pops up when table delete is called
 * @author Tyler
 *	@category controller
 */
public class DeleteBoxController {

	private Main main;
	@FXML private Button no;
	@FXML private Button yes;

	public void setMain(Main main) {
		this.main = main;
	}
	
	public DeleteBoxController(){
		
	}
	
	/**
	 * Oh look you actually didnt want to delete this one
	 */
	public void nope(){
		Stage stage = (Stage) no.getScene().getWindow();
		stage.close();
		
	}
	
	/**
	 * Look at me I got smarter and made one window to do two things in both edit options
	 */
	public void yup(){
		try{
			main.getEditTeam().delete();
		}catch(Exception e){
			
		}
		try{
			main.getEditEvents().delete();
		}catch(Exception e){
			
		}

		Stage stage = (Stage) no.getScene().getWindow();
		stage.close();
		
	}
}
