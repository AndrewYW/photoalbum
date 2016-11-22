package view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.SessionManager;
import model.Tag;

public class PhotoViewController {
	@FXML
	private ListView<Tag> tagList;
	@FXML
	private Label caption;
	@FXML
	private Label dateLabel;
	private Stage prevStage;
	private SessionManager sMan;
	
	
	@FXML
	private void addTag(){
		
	}
	@FXML
	private void deleteTag(){
		
	}
	@FXML
	private void copyPhoto(){
		
	}
	@FXML
	private void movePhoto(){
		
	}
	@FXML
	private void prevPhoto(){
		
	}
	@FXML
	private void nextPhoto(){
		
	}
	@FXML
	private void toPhotos(){
		
	}
	@FXML 
	private void editCaption(){
		
	}
	@FXML
	private void searchPhotos(){
		
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
