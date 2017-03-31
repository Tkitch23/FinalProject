package controller;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import mainApp.Main;

/**
 * @category Controller
 * @author Tyler
 *
 */
public class ValidationController {
	@FXML private  TextField usernameText;
	@FXML private  TextField passwordText;
	@FXML private Button enter;
	@FXML private CheckBox show;
	@FXML private  Label warningText;
	private Main main;

	public void setMain(Main main){
		this.main =main;

	}
	public ValidationController(){

	}
	public TextField getUsernameText() {
		return usernameText;
	}


	public void setUsernameText(TextField usernameText) {
		this.usernameText = usernameText;
	}


	public TextField getPasswordText() {
		return passwordText;
	}


	public void setPasswordText(TextField passwordText) {
		this.passwordText = passwordText;
	}


	public Button getEnter() {
		return enter;
	}


	public void setEnter(Button enter) {
		this.enter = enter;
	}


	public CheckBox getShow() {
		return show;
	}


	public void setShow(CheckBox show) {
		this.show = show;
	}


	public Label getWarningText() {
		return warningText;
	}


	public void setWarningText(Label warningText) {
		this.warningText = warningText;
	}

	/**
	 * validates which username was entered
	 */
	public void permission(){

		//potentional usernames to get to different windows and functions
		String starterName = "Starter";
		String timerName = "Timer";
		String dataView = "Data";
		String pass  = " ";
		String coach = "Coach";

		//method to get access to starter side of app
		if(getUsernameText().getText().equals(starterName)  && getPasswordText().getText().equals(pass)){
			main.initStarter();

		}

		//opens timer side of app
		else if(getUsernameText().getText().equals(timerName)  && getPasswordText().getText().equals(pass)){
			main.initTimer();

		}

		//brings you to the results
		else if(getUsernameText().getText().equals(dataView)  && getPasswordText().getText().equals(pass)){
			Stage primaryStage = new Stage();
			main.initData();

		}
		//brings you to the results
		else if(getUsernameText().getText().equals(coach)  && getPasswordText().getText().equals(pass)){
			Stage primaryStage = new Stage();

			try{ 
				Parent root = FXMLLoader.load(getClass().getResource("/view/TeamCreator.fxml"));
				primaryStage.setScene(new Scene(root));
				primaryStage.show();
			}
			catch(Exception e){
				e.printStackTrace();
			}

		}
		else{
			warningText.setVisible(true);
		}
		usernameText.clear();
		passwordText.clear();
	}

	/**
	 * Method allows the enter key to do the same thing as the button
	 * @param event enter being pressed
	 */
	public void enterKeyPressed(KeyEvent event){
		if(event.getCode()== KeyCode.ENTER){
			permission();
		}

	}
	@FXML
	public void initialize(){
		Connection c = null;
		Statement stmt = null;
		String sql = "";
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Timing.db");
			//does not run all SQL at once, enables you to only have one SQL String variable 
			//but have to run an execute each time
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			
		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
	}
		
		
}
}
