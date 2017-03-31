package controller;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.SingleEvent;
import mainApp.Main;

/**
 * @author Tyler
 *@category Contorller
 */
public class ResultsController {
	@FXML
	private ChoiceBox<Integer> eventChoice;
	@FXML
	private ChoiceBox<Integer> heatChoice;
	@FXML
	private TableView<SingleEvent> table;
	@FXML
	private TableColumn<SingleEvent, String> timeCol;
	private boolean firstTime = true;
	private Main main;

	public void setMain(Main main){
		this.main = main;
		populateEvents();
		
	
		//Sets the times
		timeCol.setCellValueFactory(time -> time.getValue().getHeats().get(0).getTime());
		
		
	}

	/**
	 * blank constructor to enable initialize
	 */
	public ResultsController(){

	}
	
	public void populateEvents(){
		if(firstTime==true && main.getEvents().size() !=0){

			for(int i = 0; i<main.getEvents().size();i++){

				eventChoice.getItems().add(main.getEvents().get(i).getEventNumber().getValue());
			}
			firstTime=false;
			System.out.println("Flipped boolean");

		}

		eventChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				//this has to be here to make the selection before it is called

				String newNum = newValue.toString();
				int intTest = Integer.parseInt(newNum);
				eventChoice.getSelectionModel().select(intTest);

				//if the event numbe gets changed this repopulates the heats
				heatLoop();

			}

		});
	}



	/**
	 * This fills the heat choicebox when the event changes
	 */
	public void heatLoop(){
		heatChoice.getItems().clear();
		int number = main.getEvents().get((eventChoice.getValue()-1)).getHeats().size() +1;
		for(int i = 0;i< number;i++){
			heatChoice.getItems().add((i+1));
		}

	}
	
	/**
	 * Updates the table when the new is selected
	 */
	public void fillTable(){
		//creates a temp list to set items because you cant just set an object cause java
		ObservableList<SingleEvent> list = FXCollections.observableArrayList();
		list.clear();
		int number = main.getEvents().get((eventChoice.getValue())-1).getHeats().size() +1;
		SingleEvent temp = main.getEvents().get(eventChoice.getValue()-1);
		list.add(temp);
		table.setItems(list);

	}
//Goes home to homescreen
	public void goHome(){
		main.initDataHome();
	}
	//runs when loaded
	@FXML
	private void initialize(){

	}


}
