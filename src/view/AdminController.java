package view;

import java.io.IOException;

import application.PhotoAlbum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

public class AdminController {
	@FXML
	private TextField newUserText;
	@FXML
	private ListView<User> userList;
	
	private Stage stage;
	//private Stage prevStage;
	private ObservableList<User> obsList = FXCollections.observableArrayList();
	
	public void setPrevStage(Stage prevStage){
		this.stage = prevStage;
		
		obsList.clear();
		for(User u : PhotoAlbum.sMan.listUsers()){
			obsList.add(u);
		}
		userList.setItems(obsList);
	}
	
	@FXML
	private void logout() throws IOException, ClassNotFoundException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();
		LoginController lControl = (LoginController) loader.getController();
		lControl.setPrevStage(stage);
		Scene scene = new Scene(rootLayout);
		stage.setTitle("Photo Album - Rumzi Tadros & Andrew Wang");
		stage.setScene(scene);
		stage.show();
		PhotoAlbum.sMan.logout();
	}
	
	@FXML
	private void addUser(){
		String newUser = newUserText.getText();
		switch(PhotoAlbum.sMan.createUser(newUser)){
			case 0:			//success\
				obsList.clear();
				for(User u : PhotoAlbum.sMan.listUsers()){
					obsList.add(u);
				}
				FXCollections.sort(obsList);
				newUserText.clear();
				break;
			case -1:		//Input error
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("Input formatting error");
				alert.setContentText("Please use only alphabetical characters, under 15 char in length");
				alert.showAndWait();
				newUserText.clear();
				break;
			case 1:			//User exists
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("WARNING");
				alert2.setHeaderText("User already exists");
				alert2.setContentText("Please enter another username");
				alert2.showAndWait();
				newUserText.clear();
				break;
			case -2:		//Not logged in as admin
				Alert alert3 = new Alert(AlertType.WARNING);
				alert3.setTitle("WARNING");
				alert3.setHeaderText("Not logged in as admin");
				alert3.setContentText("How did you get here?");
				alert3.showAndWait();
				newUserText.clear();
				break;
		}
		
	}
	@FXML
	private void deleteUser(){
			if(PhotoAlbum.sMan.deleteUser(userList.getSelectionModel().getSelectedItem())){
				obsList.clear();
				for(User u : PhotoAlbum.sMan.listUsers()){
					obsList.add(u);
				}
				FXCollections.sort(obsList);
			}
	}
}
