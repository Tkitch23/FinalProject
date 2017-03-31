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
 * @category Controller
 * @author Tyler
 *
 */
public class EditPersonController {
	@FXML private TextField fName;
	@FXML private TextField lName;
	@FXML private TextField age;
	@FXML private Button addButton;
	private Swimmers current;
	private Main main;

	
	/**
	 * Using this instead of initialize because it allows for late binding and efficiency improvements so that your potatoe of a school PC can run this ;)
	 * @param main Like looking in a mirror (****Ba Dum Pshhhhhh*****)
	 */
	public void setMain(Main main){
		this.main = main;

		current = main.getEditTeam().getTable().getSelectionModel().getSelectedItem();
		fName.setText(main.getEditTeam().getTable().getSelectionModel().getSelectedItem().getfName().get());
		lName.setText(main.getEditTeam().getTable().getSelectionModel().getSelectedItem().getlName().get());
		Integer oldy = main.getEditTeam().getTable().getSelectionModel().getSelectedItem().getAge().get();
		age.setText(oldy.toString());
	}

	public EditPersonController(){

	}

	
	/**
	 * Would you look at that the same method came up again, how Strange, never would have guessed that 
	 */
	public void handleEdit(){



		if(Utilities.intCheck(age.getText())==true){
			int old = Integer.parseInt(age.getText());
			Swimmers temp = new Swimmers(fName.getText(),lName.getText(),old);
			Stage stage = (Stage) addButton.getScene().getWindow();
			stage.close();
			main.getEditTeam().getNames().clear();


			Connection c = null;
			Statement stmt = null;
			String sql = "";

			//OH YEAH look at those update calls and using another language other than java
			// because marshallers and unmarshallers are extra and not needed
			try{
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:timing.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				//adds the swimmer information to a table for later use
				sql = "UPDATE " + main.getEditTeam().getTeamName().getSelectionModel().getSelectedItem().toUpperCase().trim().replaceAll("\\s", "") +" SET FIRSTNAME = " +"'"+
						temp.getfName().get()+"', LASTNAME = '" + temp.getlName().get()+"', AGE = " +old +" WHERE FIRSTNAME = '" + current.getfName().get() + "' AND LASTNAME = '" + current.getlName().get()+"';"  ;
				stmt.executeUpdate(sql);
				System.out.println("Swimmer Added");
				stmt.close();
				c.commit();
				c.close();
			}catch ( Exception e ) {
				e.printStackTrace();
			}


		}else{
			Utilities.alert(age.getText() + " Is Not an Integer");
		}
		System.out.println("Added");
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