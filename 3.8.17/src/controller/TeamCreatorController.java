package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mainApp.Main;
import model.Swimmers;
import model.Team;

/**
 * @author Tyler
 *@Category Controller
 */
public class TeamCreatorController {

	/**
	 * Class Fields to get Form submitions
	 */
	@FXML private Label tNameLbl;
	@FXML private Label sFNameLbl;
	@FXML private Label sLNameLbl;
	@FXML private Label sAgeLbl;
	@FXML private Label lbl1;
	@FXML private Label lbl2;
	@FXML private Label lastF;
	@FXML private Label lbl3;
	@FXML private Label lastL;
	@FXML private Label lbl4;
	@FXML private Label tNameHeader;
	@FXML private Label lastA;
	@FXML private TextField tNameTxt;
	@FXML private TextField sFNameTxt;
	@FXML private TextField sLNameTxt;
	@FXML private TextField sAgeTxt;
	@FXML private Button tNameBtn;
	@FXML private Button addButton;
	private String tableName;
	private ObservableList<String> teamNames = FXCollections.observableArrayList();
	private Main main;
	private ObservableList<Team> teams = FXCollections.observableArrayList();
	private Team team = new Team();

	/**
	 * Blank Constructor
	 */
	public TeamCreatorController(){

	}

	/**
	 * sets the hook for reflection  to main
	 * @param main
	 */
	public void setMain(Main main){
		this.main = main;
	}

	/**
	 * first method that flips visibility so I didnt have to make  two views Also Creates a table of with the team name to store data for later use
	 */
	public void handleTeamAdd(){

		team.setTeamName(new SimpleStringProperty(tNameTxt.getText()));
		tNameHeader.setText(tNameTxt.getText());
		sFNameLbl.setVisible(true);
		sLNameLbl.setVisible(true);
		sAgeLbl.setVisible(true);
		tNameHeader.setVisible(true);
		sFNameTxt.setVisible(true);
		sLNameTxt.setVisible(true);
		sAgeTxt.setVisible(true);
		addButton.setVisible(true);
		tNameLbl.setVisible(false);
		tNameTxt.setVisible(false);
		tNameBtn.setVisible(false);
		teamNames.add(tNameTxt.getText().toUpperCase());
		Connection c = null;
		Statement stmt = null;
		String sql = "";
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:timing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();

			//SQL cant have space in naming so i reformat the name
			setTableName(tNameTxt.getText().toUpperCase().trim().replaceAll("\\s", ""));
			sql = "CREATE TABLE " + getTableName() +
					"(" +
					" FIRSTNAME           TEXT    NOT NULL, " + 
					" LASTNAME            TEXT     NOT NULL,"
					+ "AGE	INT NOT NULL) " ;
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		//method to store the name of the teams for later use
		createNameTable();
	}

	/**
	 * after the name is decided this is method to add swimmers to a list
	 */
	public void handleSwimmerAdd(){
		//surround this with error if they do not enter a proper integer
		int oldy = Integer.parseInt(sAgeTxt.getText());
		Swimmers swimmer = new Swimmers();
		swimmer.setfName(new SimpleStringProperty(sFNameTxt.getText()));
		swimmer.setlName(new SimpleStringProperty(sLNameTxt.getText()));
		swimmer.setAge(new SimpleIntegerProperty(oldy));
		team.getRoster().add(swimmer);

		lastF.setText(sFNameTxt.getText());
		lastL.setText(sLNameTxt.getText());
		lastA.setText(sAgeTxt.getText());


		Connection c = null;
		Statement stmt = null;
		String sql = "";
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:timing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			//adds the swimmer information to a table for later use
			sql = "INSERT INTO '" + getTableName() +"' (FIRSTNAME,LASTNAME, AGE) VALUES (" +"'"+
					sFNameTxt.getText()+"','" + sLNameTxt.getText()+"', " +oldy +");" ;
			stmt.executeUpdate(sql);
			System.out.println("Swimmer Added");
			stmt.close();
			c.commit();
			c.close();
		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}

		sFNameTxt.clear();
		sLNameTxt.clear();
		sAgeTxt.clear();


		lbl1.setVisible(true);
		lbl2.setVisible(true);
		lbl3.setVisible(true);
		lbl4.setVisible(true);
		lastF.setVisible(true);
		lastL.setVisible(true);
		lastA.setVisible(true);
	}

	/**
	 * takes you back to the data entry side and closes window
	 */
	public void home(){
		
		main.initDataHome();
	}
	
	/**
	 * Either creates a table to store the list of names to query later or adds the team to the table
	 */
	public void createNameTable(){
		Connection c = null;
		Statement stmt = null;
		String sql = "";
		try{
			c = DriverManager.getConnection("jdbc:sqlite:timing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();

			sql = "CREATE TABLE TEAMNAMES (NAME  TEXT NOT NULL)";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO TEAMNAMES (NAME) VALUES ('" + tNameTxt.getText()  + "')";
			System.out.println("YUP");
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
			System.out.println("First Try");
		}catch(Exception e){

			try {
				c = DriverManager.getConnection("jdbc:sqlite:timing.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				sql = "INSERT INTO TEAMNAMES (NAME) VALUES ('" + tNameTxt.getText()  + "')";
				System.out.println("Its in there");
				stmt.executeUpdate(sql);
				stmt.close();
				c.commit();
				c.close();
				System.out.println(tNameTxt.getText());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(main.getTeamNames().size() !=0){
		main.loadTeamNames();
		}
	}
	@FXML 
	public void initialize(){
		//setting all the other shit to invisible 
		sFNameLbl.setVisible(false);
		sLNameLbl.setVisible(false);
		sAgeLbl.setVisible(false);
		lbl1.setVisible(false);
		lbl2.setVisible(false);
		lbl3.setVisible(false);
		lbl4.setVisible(false);
		lastF.setVisible(false);
		lastL.setVisible(false);
		tNameHeader.setVisible(false);
		lastA.setVisible(false);
		sFNameTxt.setVisible(false);
		sLNameTxt.setVisible(false);
		sAgeTxt.setVisible(false);
		addButton.setVisible(false);
	}
	
	//Getters and setters
	public ObservableList<String> getTeamNames() {
		return teamNames;
	}

	public void setTeamNames(ObservableList<String> teamNames) {
		this.teamNames = teamNames;
	}
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public ObservableList<Team> getTeams() {
		return teams;
	}

	public void setTeams(ObservableList<Team> teams) {
		this.teams = teams;
	}
}
