package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

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
import model.SessionManager;
import model.User;


public class LoginController {
	@FXML
	private ListView<User> userList;
	@FXML
	private TextField username;

	private ObservableList<User> obsList;
	Stage prevStage;
	
	protected static SessionManager sMan = null;
	
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

		cleanExit();
		
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
			if(sMan.login(userList.getSelectionModel().getSelectedItem())){
				prevStage.setTitle("Admin panel");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminHome.fxml"));
				AnchorPane adminLayout = (AnchorPane) loader.load();
				AdminController aControl = (AdminController) loader.getController();
				aControl.getSM(sMan);
				aControl.setPrevStage(prevStage);
				if(sMan.isLoggedIn()){
					Scene scene = new Scene(adminLayout);
					prevStage.setScene(scene);
					prevStage.show();
				}
			}
		}else{
			if(sMan.login(userList.getSelectionModel().getSelectedItem())){
				prevStage.setTitle("User Home");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserHome.fxml"));
				AnchorPane albumLayout = (AnchorPane) loader.load();
				UserController uControl = (UserController) loader.getController();
				uControl.getSM(sMan);
				uControl.setPrevStage(prevStage);
				if(sMan.isLoggedIn()){
					Scene scene = new Scene(albumLayout);
					prevStage.setScene(scene);
					prevStage.show();
				}
			}
			
		}
	}
	
	public void setPrevStage(Stage stage){
		this.prevStage = stage;
	}
	
	private void cleanExit(){
		prevStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				try {
					sMan.logout();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Safely closed");
				System.exit(0);
			}
		});
	}
	
}
