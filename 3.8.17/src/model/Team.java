package model;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Team {
	private StringProperty teamName;
	private ObservableList<Swimmers> roster = FXCollections.observableArrayList();
	public StringProperty getTeamName() {
		return teamName;
	}
	public void setTeamName(StringProperty teamName) {
		this.teamName = teamName;
	}
	public ObservableList<Swimmers> getRoster() {
		return roster;
	}
	public void setRoster(ObservableList<Swimmers> roster) {
		this.roster = roster;
	}
	
	public StringProperty teamNameProperty(){
		return teamName;
	}
	
	public void marshall(Team team){
		try{
			//creating a new object
			
			JAXBContext jc = JAXBContext.newInstance(Team.class);
			Marshaller ms = jc.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(team, System.out);
			ms.marshal(team, new File("src\\xmlFiles\\team.xml"));
			
		}catch(Exception e){
			System.out.println("" + e.getMessage());
		}
	}
	public void unMarshall(){
		try {
			JAXBContext jc = JAXBContext.newInstance(Team.class);
			Unmarshaller ums = jc.createUnmarshaller();
			Team swim = (Team)ums.unmarshal(new File("src\\xmlFiles\\team.xml"));
		
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
