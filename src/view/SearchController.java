package view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Photo;
import model.SessionManager;
import model.Tag;

public class SearchController {
	@FXML
	private TextField oldDate;
	@FXML
	private TextField newDate;

	@FXML
	private ListView<Tag> tagTypes;
	@FXML
	private ListView<Tag> tagValues;
	
	
	private SessionManager sMan;
	private Stage prevStage;
	
	public void setPrevStage(Stage stage){
		prevStage = stage;
	}
	
	@FXML
	private void searchDateButton(){
		
	}
	@FXML
	private void searchTagButton(){
		
	}
	@FXML
	private void toAlbums() throws IOException{
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
	@FXML
	private void logout() throws IOException{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();
		LoginController lControl = (LoginController) loader.getController();
		lControl.setPrevStage(prevStage);
		Scene scene = new Scene(rootLayout);
		prevStage.setTitle("Photo Album - Rumzi Tadros & Andrew Wang");
		prevStage.setScene(scene);
		prevStage.show();
		sMan.logout();
	}

	public void getSM(SessionManager sm){
		this.sMan = sm;
	}
}
