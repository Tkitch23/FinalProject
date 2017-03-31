package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import mainApp.Main;

import model.Swimmers;
/**
 * The controller for the Team editor table menu
 * @category Controller
 * @author Tyler
 *
 */
public class EditTeamController {
	@FXML private TableView<Swimmers> table;
	@FXML private TableColumn<Swimmers, String> fName;
	@FXML private TableColumn<Swimmers, String> lName;
	@FXML private TableColumn<Swimmers, Integer> age;
	@FXML private ChoiceBox<String> teamName;
	private Main main;
	private ObservableList<Swimmers> names= FXCollections.observableArrayList();

	public void setMain(Main main){
		this.main = main;
		populateTeamNames();

	}

	/**
	 * This method pulls the list of teamnames from main from the database	
	 */
	public void populateTeamNames(){
		main.getTeamNames().clear();
		System.out.println("Fired");

		main.loadTeamNames();
		for(int i = 0; i<main.getTeamNames().size(); i++){
			teamName.getItems().add(main.getTeamNames().get(i));

		}		}
	/**
	 * method that gets called when the teams have been selected
	 */
	public void populateTable(){
		Connection c = null;
		Statement stmt = null;
		names.clear();

		try{
			c = DriverManager.getConnection("jdbc:sqlite:timing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String team = teamName.getValue().toUpperCase().trim().replaceAll("\\s", "");
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
		}try{
			stmt.close();
			c.commit();
			c.close();
			System.out.println("Closed One");
		}catch(Exception e){

		}
		fName.setCellValueFactory(firstName -> firstName.getValue().fNameProperty());
		lName.setCellValueFactory(lastName -> lastName.getValue().lNameProperty());
		age.setCellValueFactory(old -> old.getValue().ageProperty().asObject());
		table.setItems(names);
		System.out.println("Items Set");

	}

	/**
	 * Havent Seen this method before ***cough***** cough****
	 */
	public void delete(){
		int index =table.getSelectionModel().getSelectedIndex();

		Connection c = null;
		Statement stmt = null;
		try{

			System.out.println("A");
			c = DriverManager.getConnection("jdbc:sqlite:timing.db");
			System.out.println("b");

			stmt = c.createStatement();
			String fName  = table.getSelectionModel().getSelectedItem().getfName().get();
			String lName = table.getSelectionModel().getSelectedItem().getlName().get();

			String sql = "DELETE FROM "+ teamName.getSelectionModel().getSelectedItem().toUpperCase().trim().replaceAll("\\s", "")
					+ " WHERE FIRSTNAME = '" + fName + "' AND LASTNAME = '" + lName + "';";
			stmt.executeUpdate(sql);
			System.out.println("C");
			table.getItems().remove(index);
			System.out.println("Closed two");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("You goofed");
		}
		try{
			stmt.close();

			c.close();
			System.out.println("Closed One");
		}catch(Exception e){
			System.out.println("You Thought");
		}
	}


	public TableView<Swimmers> getTable() {
		return table;
	}


	public void setTable(TableView<Swimmers> table) {
		this.table = table;
	}


	public ChoiceBox<String> getTeamName() {
		return teamName;
	}


	public void setTeamName(ChoiceBox<String> teamName) {
		this.teamName = teamName;
	}


	public void edit(){
		main.initEdit();
	}

	public void add(){
		main.initAdd();
	}

	public void closing(){
		main.initDataHome();
	}
	public void confirmDelete(){
		main.initDelete();
	}


	public ObservableList<Swimmers> getNames() {
		return names;
	}


	public void setNames(ObservableList<Swimmers> names) {
		this.names = names;
	}
}	


