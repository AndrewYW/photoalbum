package view;

import java.io.IOException;
import java.util.Optional;

import application.PhotoAlbum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;

public class UserController {
	private Stage prevStage;
	private ObservableList<Album> obsList = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Album> albumList;
	@FXML
	private TableColumn<Album, String> albumNameCol;
	@FXML
	private TableColumn<Album, String> albumNumPhotoCol;
	@FXML
	private TableColumn<Album, String> albumOldCol;
	@FXML
	private TableColumn<Album, String> albumNewCol;
	@FXML
	private TextField albumFilter;
	
	public void setPrevStage(Stage stage){
		this.prevStage = stage;
		if(PhotoAlbum.sMan.myAlbums() != null){
			
			albumNameCol.setCellValueFactory(new PropertyValueFactory<>("title"));
			albumNumPhotoCol.setCellValueFactory(new PropertyValueFactory<>("numberOfPhotos"));
			albumOldCol.setCellValueFactory(new PropertyValueFactory<>("oldest"));
			albumNewCol.setCellValueFactory(new PropertyValueFactory<>("newest"));
			//albumList.getColumns().add(albumNameCol);
			//albumList.getColumns().add(albumNumPhotoCol);
			//albumList.getColumns().add(albumOldCol);
			//albumList.getColumns().add(albumNewCol);
			loadAlbums();
		}		
	}
	
	private void loadAlbums(){
		obsList.clear();
		for(Album album : PhotoAlbum.sMan.myAlbums()){
			obsList.add(album);
		}
		if(!obsList.isEmpty()){
			albumList.setItems(obsList);	
			albumList.getSelectionModel().selectFirst();
		}
	}
	
	@FXML
	private void logout() throws IOException, ClassNotFoundException{
		PhotoAlbum.sMan.logout();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();
		LoginController lControl = (LoginController) loader.getController();
		lControl.setPrevStage(prevStage);
		Scene scene = new Scene(rootLayout);
		prevStage.setTitle("Photo Album - Rumzi Tadros & Andrew Wang");
		prevStage.setScene(scene);
		prevStage.show();
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
		for(Album b : PhotoAlbum.sMan.myAlbums()){
			if(b.getTitle().compareTo(test) == 0){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("Album already exists");
				alert.setContentText("Please enter a different title");
				alert.showAndWait();
				return;
			}else{
			}
		}
		result.ifPresent(name -> PhotoAlbum.sMan.createAlbum(name));
		loadAlbums();
	}
	@FXML
	private void deleteAlbum(){
		PhotoAlbum.sMan.deleteAlbum(albumList.getSelectionModel().getSelectedItem());
		loadAlbums();
	}
	@FXML
	private void renameAlbum(){
		//TODO prevent existing album  name overlap
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Rename Album");
		dialog.setHeaderText("");
		dialog.setContentText("Please enter new album title:");
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(name -> albumList.getSelectionModel().getSelectedItem().rename(name));
		obsList.clear();
		loadAlbums();
	}
	@FXML
	private void openAlbum() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumHome.fxml"));
		AnchorPane PhotosLayout = (AnchorPane) loader.load();
		AlbumHomeController aHomeControl = (AlbumHomeController) loader.getController();
		aHomeControl.setPrevStage(prevStage);
		Scene scene = new Scene(PhotosLayout);
		prevStage.setTitle("Search Window");
		prevStage.setScene(scene);
		prevStage.show();
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
}
