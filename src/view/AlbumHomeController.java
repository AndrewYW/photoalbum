package view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Photo;
import model.SessionManager;

public class AlbumHomeController {

	@FXML
	private TableView<Photo> photoTable;
	@FXML
	private TableColumn<Photo, ImageView> photoColumn;
	@FXML
	private TableColumn<Photo, String> captionColumn;
	
	private SessionManager sMan;
	private Stage prevStage;
	
	
	public void setPrevStage(Stage stage){
		//TODO MUST FINISH THIS TO SETUP
		prevStage = stage;
	}
	public void getSM(SessionManager sm){
		this.sMan = sm;
	}
	
	
	@FXML
	private void viewPhoto(){
		
	}
	@FXML
	private void addPhoto(){
		
	}
	@FXML
	private void deletePhoto(){
		
	}
	@FXML
	private void slideshow(){
		
	}
	@FXML
	private void searchPhotos() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SearchHome.fxml"));
		AnchorPane searchLayout = (AnchorPane) loader.load();
		SearchController sControl = (SearchController) loader.getController();
		sControl.setPrevStage(prevStage);
		Scene scene = new Scene(searchLayout);
		prevStage.setTitle("Search Window");
		prevStage.setScene(scene);
		prevStage.show();
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

}
