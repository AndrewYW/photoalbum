package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import model.SessionManager;
import model.User;


public class LoginController {
	@FXML
	private ListView<User> userList;
	@FXML
	private TextField username;

	private ObservableList<User> obsList;
	Stage prevStage;
	
	SessionManager sMan = null;
	public void start(Stage primaryStage) throws ClassNotFoundException, IOException {
		File data = new File("dat"+File.separator+"sessions.dat");
		if(data.exists() && !data.isDirectory()){
			try{
				sMan = SessionManager.readApp();
			}catch(Exception e){
				sMan = new SessionManager();
			}
		}
		else
			sMan = new SessionManager();

		
		username.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				//FXCollections.sort(obsList);
				List<User> test = sMan.searchUsers(username.getText().toString());
				System.out.println(username.getText().toString());
				if(test != null){
					obsList = FXCollections.observableArrayList(sMan.searchUsers(username.getText().toString()));
					userList.setItems(obsList);
				}
				//userList.setItems(value);
				//
			}
		});
		//obsList = FXCollections.observableArrayList();
		//userList.setItems(obsList);
		
		
		
		primaryStage.setOnCloseRequest(event -> {
			//Serialization on close request
			
		});
		
	}
	
	@FXML
	private void handleLogin() throws IOException {
		if(userList.getSelectionModel().getSelectedItem() == null) {
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
