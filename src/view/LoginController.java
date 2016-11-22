package view;

import java.io.IOException;
import java.util.List;

import application.PhotoAlbum;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;


public class LoginController {
	@FXML
	private ListView<User> userList;
	@FXML
	private TextField username;

	private ObservableList<User> obsList;
	Stage prevStage;
	
	public void start(Stage primaryStage) throws ClassNotFoundException, IOException {
		
		username.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				List<User> users = PhotoAlbum.sMan.searchUsers(username.getText().toString());
				if(users != null && !username.getText().isEmpty()){
					obsList = FXCollections.observableArrayList(users);
					userList.setItems(obsList);
				}
				else{
					obsList.clear();
					userList.setItems(obsList);
				}
			}
		});
		
		cleanExit();
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
			if(PhotoAlbum.sMan.login(userList.getSelectionModel().getSelectedItem())){
				prevStage.setTitle("Admin panel");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminHome.fxml"));
				AnchorPane adminLayout = (AnchorPane) loader.load();
				AdminController aControl = (AdminController) loader.getController();
				aControl.setPrevStage(prevStage);
				if(PhotoAlbum.sMan.isLoggedIn()){
					Scene scene = new Scene(adminLayout);
					prevStage.setScene(scene);
					prevStage.show();
				}
			}
		}else{
			if(PhotoAlbum.sMan.login(userList.getSelectionModel().getSelectedItem())){
				prevStage.setTitle("User Home");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserHome.fxml"));
				AnchorPane albumLayout = (AnchorPane) loader.load();
				UserController uControl = (UserController) loader.getController();
				uControl.setPrevStage(prevStage);
				if(PhotoAlbum.sMan.isLoggedIn()){
					Scene scene = new Scene(albumLayout);
					prevStage.setScene(scene);
					prevStage.show();
				}
			}
			
		}
	}
	
	public void setPrevStage(Stage stage) throws ClassNotFoundException, IOException{
		this.prevStage = stage;
		start(stage);
	}
	
	private void cleanExit(){
		prevStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				try {
					PhotoAlbum.sMan.logout();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
	}
	
}
