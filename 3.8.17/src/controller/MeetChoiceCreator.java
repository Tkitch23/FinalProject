package controller;

import mainApp.Main;

/**
 * This is a controller for the basic picture Menu
 * @author Tyler
 *@category controller
 */
public class MeetChoiceCreator {
	private Main main;
	
	public MeetChoiceCreator(){
		
	}
	
	//Yet again a very simple method
	public void setMain(Main main){
		this.main=main;
	}
	public void handleEventAdd(){
		main.initEventAdd();
	}
	public void handleSwimmerAdd(){
		main.initSwimmerAdd();
	}
	public void handleHome(){
		main.initDataHome();
	}
}
