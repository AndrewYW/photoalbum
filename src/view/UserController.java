package view;

import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.SessionManager;
import model.User;

public class UserController {
	private Stage prevStage;
	private SessionManager sMan;
	private ObservableList<Album> obsList = FXCollections.observableArrayList();
	
	@FXML
	private ListView<Album> albumList;
	@FXML
	private Label albumName;
	@FXML
	private Label photoCount;
	@FXML
	private Label oldestPhoto;
	@FXML
	private Label dateRange;
	
	public void getSM(SessionManager sm){
		this.sMan = sm;
	}
	public void setPrevStage(Stage stage){
		this.prevStage = stage;
		
		for(Album album : sMan.myAlbums()){
			obsList.add(album);
		}
		albumList.setItems(obsList);
		if(!obsList.isEmpty())
			albumList.getSelectionModel().selectFirst();
		showAlbumDetails();
		albumList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)-> showAlbumDetails());
		
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
	@FXML
	private void createAlbum(){
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Create a new Album");
		dialog.setHeaderText("");
		dialog.setContentText("Please enter album title:");
		Optional<String> result = dialog.showAndWait();
		String test = result.toString();
		//TODO prevent existing album  name overlap
		for(Album b : sMan.myAlbums()){
			if(b.getTitle().equals(test)){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("Album already exists");
				alert.setContentText("Please enter a different title");
				alert.showAndWait();
				return;
			}else{
			}
		}
		result.ifPresent(name -> sMan.createAlbum(name));
		
		obsList.clear();
		for(Album album : sMan.myAlbums()){
			obsList.add(album);
		}
		if(!obsList.isEmpty()){
			albumList.setItems(obsList);
			albumList.getSelectionModel().selectFirst();
		}
	}
	@FXML
	private void deleteAlbum(){
		sMan.deleteAlbum(albumList.getSelectionModel().getSelectedItem());
		obsList.clear();
		for(Album album : sMan.myAlbums()){
			obsList.add(album);
		}
		if(!obsList.isEmpty()){
			albumList.setItems(obsList);
			albumList.getSelectionModel().selectFirst();
		}
	}
	@FXML
	private void renameAlbum(){
		//TODO must finish
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Rename Album");
		dialog.setHeaderText("");
		dialog.setContentText("Please enter new album title:");
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(name -> albumList.getSelectionModel().getSelectedItem().editTitle(name));
		showAlbumDetails();
	}
	@FXML
	private void openAlbum(){
		//TODO 
	}
	@FXML
	private void searchPhotos() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SearchHome.fxml"));
		AnchorPane searchLayout = (AnchorPane) loader.load();
		SearchController sControl = (SearchController) loader.getController();
		sControl.setPrevStage(prevStage);
		Scene scene = new Scene(searchLayout);
		prevStage.setTitle("Photo Album - Rumzi Tadros & Andrew Wang");
		prevStage.setScene(scene);
		prevStage.show();
	}
	
	private void showAlbumDetails(){
		if(albumList.getSelectionModel().getSelectedIndex() < 0){
			return;
		}
		
		Album album = albumList.getSelectionModel().getSelectedItem();
		albumName.setText(album.getTitle());
		photoCount.setText(album.countPhotos() + " Photos");
		if(album.olderDate() == null){
			oldestPhoto.setVisible(false);
			dateRange.setVisible(false);
		}else{
			oldestPhoto.setText("Oldest Photo: " + album.olderDate().toString());
			dateRange.setText(album.olderDate() + " - " + album.newestDate());
		}
	}
}
