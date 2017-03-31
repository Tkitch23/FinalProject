package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mainApp.Main;
import model.SingleEvent;
import model.Swimmers;
/**
 * 
 * @author Tyler
 *
 */
/**
 * @author Tyler
 *
 */
public class EventEditController {

	private Main main;
	@FXML private TableView<SingleEvent> table;
	@FXML private TableColumn<SingleEvent,String> eName;
	@FXML private TableColumn<SingleEvent,Integer> eNumber;
	@FXML private TableColumn<SingleEvent,Integer> hNumber;
	private ObservableList<SingleEvent> events= FXCollections.observableArrayList();
	
	public EventEditController(){
		
	}
	
	public void setMain(Main main){
		this.main = main;
		populateTable();
		
		
		

	}
	

	/**
	 * The redundantcy is overwelming me, oh well Im lazy
	 */
	public void populateTable(){
		Connection c = null;
		Statement stmt = null;
		events.clear();
		
		try{

				c = DriverManager.getConnection("jdbc:sqlite:timing.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery( "SELECT * FROM EVENTS;" );
				while ( rs.next() ==true) {
					String  name = rs.getString("EVENTNAME");
					int eventNumber = rs.getInt("EVENTNUMBER");
					int heatNumber = rs.getInt("HEATNUMBER");
					SingleEvent temp = new SingleEvent(eventNumber,heatNumber, name);
					events.add(temp);
				
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
		eName.setCellValueFactory(name -> name.getValue().getEventName());
		eNumber.setCellValueFactory(number -> number.getValue().getEventNumber().asObject());
		hNumber.setCellValueFactory(h -> h.getValue().getHeatNumber().asObject());
		table.setItems(events);
		System.out.println("Items Set");
		
	}
	/**
	 * Goes to home menu
	 */
	public void goHome(){
		main.initDataHome();
	}
	/**
	 * opens delete box and calls the delete method if yes
	 */
	public void confirmDelete(){
		main.initDelete();
	}
	
	/**
	 * Complicated SQL calls, not really just a lazy comment
	 */
	public void delete(){
		int index =table.getSelectionModel().getSelectedIndex();
	
		Connection c = null;
		Statement stmt = null;
		try{
		
		
		c = DriverManager.getConnection("jdbc:sqlite:timing.db");
		System.out.println("b");
		
		stmt = c.createStatement();
		String eName  = table.getSelectionModel().getSelectedItem().getEventName().get();
		int eNum= table.getSelectionModel().getSelectedItem().getEventNumber().get();
		String sql = "DELETE FROM EVENTS  WHERE EVENTNAME = '" + eName + "' AND EVENTNUMBER = '" + eNum + "';";
		stmt.executeUpdate(sql);
		table.getItems().remove(index);

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
	
	/**
	 * Opens add box method
	 */
	public void add(){
		main.initEditEventAdd();
		System.out.println("BAMM");
	}
	
	public void edit(){
		main.initEventEditBox();
	}

	public TableView<SingleEvent> getTable() {
		return table;
	}

	public void setTable(TableView<SingleEvent> table) {
		this.table = table;
	}
	public ObservableList<SingleEvent> getEvents() {
		return events;
	}

	public void setEvents(ObservableList<SingleEvent> events) {
		this.events = events;
	}


}
