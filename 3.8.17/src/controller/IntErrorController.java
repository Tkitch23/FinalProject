package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Should have been a utility but am stubborn
 * @author Tyler
 *@category Controller
 */
public class IntErrorController {
	@FXML private Label error;
	
	//I guess this should have been a utility, too late :(
	public Label getError() {
		return error;
	}

	public void setError(Label error) {
		this.error = error;
	}

	public IntErrorController(){
		
	}
	public void exit(){
		Stage stage = (Stage) error.getScene().getWindow();
		stage.close();
	}
}
