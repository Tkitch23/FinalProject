package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainApp.Main;
import model.Swimmers;
import utilities.Utilities;
/**
 * 
 * @author Tyler
 *@version 1.0
 */
public class AddBoxController {
	
	
	@FXML private TextField fName;
	@FXML private TextField lName;
	@FXML private TextField age;
	@FXML private Button addButton;
	private Main main;

	public void setMain(Main main){
		this.main = main;
	}

	public AddBoxController(){

	}


	

	public void handleAdd(){

		//Make sure fields are filled so nno null pointers come out
		if(fName.getText().equals("") || lName.getText().equals("") || age.getText().equals("")){
			Utilities.alert("Please Fill Out All Fields");

		}else{
			if(Utilities.intCheck(age.getText())==true){
				int old = Integer.parseInt(age.getText());
				Swimmers temp = new Swimmers(fName.getText(),lName.getText(),old);
				Stage stage = (Stage) addButton.getScene().getWindow();
				stage.close();
				main.getEditTeam().getNames().clear();
				main.getEditTeam().getNames().add(temp);
				System.out.println("Added");
				main.getEditTeam().populateTable();
				Connection c = null;
				Statement stmt = null;
				String sql = "";
				try{
					Class.forName("org.sqlite.JDBC");
					c = DriverManager.getConnection("jdbc:sqlite:timing.db");
					c.setAutoCommit(false);
					stmt = c.createStatement();
					//adds the swimmer information to a table for later use
					sql = "INSERT INTO '" + main.getEditTeam().getTeamName().getSelectionModel().getSelectedItem().toUpperCase().trim().replaceAll("\\s", "") +"' (FIRSTNAME,LASTNAME, AGE) VALUES (" +"'"+
							temp.getfName().get()+"','" + temp.getlName().get()+"', " +old +");" ;
					stmt.executeUpdate(sql);
					System.out.println("Swimmer Added");
					stmt.close();
					c.commit();
					c.close();
				}catch ( Exception e ) {
					System.out.println("You tried");
				}


			}else{
				Utilities.alert(age.getText() + " Is Not an Integer");
			}
		}
		main.getEditTeam().populateTable();
	}

	public void clearOne(){
		fName.clear();
	}

	public void clearTwo(){
		lName.clear();
	}

	public void clearThree(){
		age.clear();
	}
}