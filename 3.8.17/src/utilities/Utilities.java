package utilities;

import controller.IntErrorController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainApp.Main;

public class Utilities {

	public static boolean intCheck(String attempt){

		try{
			Integer.parseInt(attempt);
			return true;
		}catch(Exception e){
			
			return false;
		}
		 
	}
	
	public static void errorPopUp(String test){
		try{
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/view/intError.fxml"));
		Scene scene = new Scene(loader.load());
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
		IntErrorController controller = loader.getController();
		controller.getError().setText(test);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void alert(String text){
		   Stage window = new Stage();

	        //Block events to other windows
	        window.initModality(Modality.APPLICATION_MODAL);
	        window.setTitle("Error");
	        window.setMinWidth(350);

	        Label label = new Label();
	        label.setText(text);
	        Button closeButton = new Button("Close this window");
	        closeButton.setOnAction(e -> window.close());

	        VBox layout = new VBox(10);
	        layout.getChildren().addAll(label, closeButton);
	        layout.setAlignment(Pos.CENTER);

	        //Display window and wait for it to be closed before returning
	        Scene scene = new Scene(layout);
	        window.setScene(scene);
	        window.showAndWait();
	}
}
