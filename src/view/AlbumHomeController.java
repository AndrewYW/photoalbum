package view;

import java.io.IOException;

import application.PhotoAlbum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.SimpleDate;
/**
 * Controller for Album home page. Holds a tableview of photos and some buttons.
 * @author Rumzi Tadros
 *
 */
public class AlbumHomeController {
	private ObservableList<Photo> obsList = FXCollections.observableArrayList();
	private Album thisAlbum = PhotoAlbum.sMan.getCurrentAlbum();
	@FXML
	private TableView<Photo> photoTable;
	@FXML
	private TableColumn<Photo, Image> photoColumn;
	@FXML
	private TableColumn<Photo, String> captionColumn;
	private Stage prevStage;
	
	
	public void setPrevStage(Stage stage){
		//TODO MUST FINISH THIS TO SETUP
		if(thisAlbum.openAlbum() != null){
			photoColumn.setCellFactory(new Callback<TableColumn<Photo, Image>,
						TableCell<Photo, Image>>() {
				@Override
				public TableCell<Photo, Image> call(TableColumn<Photo, Image> param) {
					
					final ImageView imageView = new ImageView();
					imageView.setFitHeight(50);
					imageView.setFitWidth(50);
					TableCell<Photo, Image> cell = new TableCell<Photo, Image>() {
						public void updateItem(Photo photo, boolean empty) {
							if(photo != null) {
								//imageView.setImage(photo.getPath());
							}
						}
					};
					cell.setGraphic(imageView);
					return cell;
				}
			});
			captionColumn.setCellValueFactory(new PropertyValueFactory<>("caption"));
			loadPhotos();
		}
		prevStage = stage;
		
	}
	
	private void loadPhotos(){
		obsList.clear();
		for(Photo photo : thisAlbum.openAlbum()){
			obsList.add(photo);
		}
		if(!obsList.isEmpty()){
			photoTable.setItems(obsList);	
			photoTable.getSelectionModel().selectFirst();
		}
	}
	
	@FXML
	private void viewPhoto() throws IOException{
		PhotoAlbum.sMan.setCurrentPhoto(photoTable.getSelectionModel().getSelectedItem());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
		AnchorPane searchLayout = (AnchorPane) loader.load();
		PhotoViewController pControl = (PhotoViewController) loader.getController();
		pControl.setPrevStage(prevStage);
		Scene scene = new Scene(searchLayout);
		prevStage.setTitle("Photo View");
		prevStage.setScene(scene);
		prevStage.show();
	}
	@FXML
	private void addPhoto(){
		String path = "test/test/test";
		String caption = "I wish this worked";
		SimpleDate date = new SimpleDate(8574837547L);
		Photo p = new Photo(path, caption, date);
		PhotoAlbum.sMan.addPhoto(p, thisAlbum);
		loadPhotos();
	}
	@FXML
	private void deletePhoto(){
		thisAlbum.deletePhoto(photoTable.getSelectionModel().getSelectedItem());
		loadPhotos();
	}
	@FXML
	private void slideshow() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
		AnchorPane photoLayout = (AnchorPane) loader.load();
		PhotoViewController pControl = (PhotoViewController) loader.getController();
		pControl.setPrevStage(prevStage);		//needs to select first photo -- new method?
		Scene scene = new Scene(photoLayout);
		prevStage.setTitle("Slideshow");
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
	@FXML
	private void toAlbums() throws IOException{
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
	@FXML
	private void logout() throws IOException, ClassNotFoundException{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();
		LoginController lControl = (LoginController) loader.getController();
		lControl.setPrevStage(prevStage);
		Scene scene = new Scene(rootLayout);
		prevStage.setTitle("Photo Album - Rumzi Tadros & Andrew Wang");
		prevStage.setScene(scene);
		prevStage.show();
		PhotoAlbum.sMan.logout();
	}

}
