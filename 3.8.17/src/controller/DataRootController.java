package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import mainApp.Main;

/**
 * Contains methods to launch various menu selections
 * @author Tyler
 *@category Controller
 */
public class DataRootController {
	private Main main;

	public DataRootController(){
		
	}
	public void setMain(Main main){
		this.main = main;
	}

	//Methods that launch various menu selections
	public void handleNewTeam(){
		
		main.setCenterTeamAdd();


	}
	
	public void handleNewMeet(){
		main.initMeetChoice();
	}
	
	public void  handleTeamEdit(){
		main.initEditChoice();
	}
	
	public void handleResults(){
		main.initResults();
	}
	@FXML 
	public void initialize(){
	}
	}
