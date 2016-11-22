package view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

public class LoginController {
	@FXML
	private ListView<User> userList;
	@FXML
	private TextField username;

	private ObservableList<User> obsList;
	Stage prevStage;
	
	public void start(Stage primaryStage) {
		obsList = FXCollections.observableArrayList();
		userList.setItems(obsList);
		
		
		primaryStage.setOnCloseRequest(event -> {
			//Serialization on close request
			
		});
		
		username.setText("");
	}
	
	@FXML
	private void handleLogin() throws IOException {
		if(userList.getSelectionModel() == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("No user selected");
			alert.setContentText("Please select a user");
			alert.showAndWait();
		}else if(userList.getSelectionModel().getSelectedItem().toString().equals("admin")){
			prevStage.setTitle("Admin panel");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminHome.fxml"));
			AnchorPane adminLayout = (AnchorPane) loader.load();
			AdminController aControl = (AdminController) loader.getController();
			//Pass in admin user necessary? Or skip to page (since no additional fields for admin)
			
			Scene scene = new Scene(adminLayout);
			prevStage.setScene(scene);
			prevStage.show();
		}else{
			prevStage.setTitle("User Home");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserHome.fxml"));
			AnchorPane adminLayout = (AnchorPane) loader.load();
			AlbumController aControl = (AlbumController) loader.getController();
			//Must pass in User 
			
			
		}
	}
	
	public void setPrevStage(Stage stage){
		this.prevStage = stage;
	}
	
	
}
