package controller;

import mainApp.Main;

/**
 * This is a very basic menu with pictures :))))
 * @category Controller
 * @author Tyler
 *
 */
public class EditChoiceController {

	private Main main;

	/**
	 * Very basic methods of menu control, If you cant understand this go back to HTML, Java is not for you
	 * @param main reflection
	 */
	public void setMain(Main main) {
		this.main = main;
	}
	
	public void goHome(){
		main.initDataHome();
	}
	
	public void goTeam(){
		main.initTeamEdit();
	}
	
	public void goEvents(){
		main.initEventEdit();
	}
}
