package view;

import java.io.IOException;

import application.PhotoAlbum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;

public class PhotoViewController {
	private ObservableList<Tag> obsList = FXCollections.observableArrayList();
	@FXML
	private ListView<Tag> tagList;
	@FXML
	private Label caption;
	private boolean captionEdit = false;
	@FXML
	private Label dateLabel;
	@FXML
	private TextField editField;
	private Stage prevStage;
	
	Album thisAlbum = PhotoAlbum.sMan.getCurrentAlbum();
	Photo thisPhoto = PhotoAlbum.sMan.getCurrentPhoto();
	
	public void setPrevStage(Stage stage){
		editField.setVisible(false);
		//TODO uncomment this after setting the current photo in album home
		/*
		caption.textProperty().setValue(thisPhoto.getCaption());
		obsList.clear();
		for(Tag tag : thisPhoto.getTags()){
			obsList.add(tag);
		}
		if(!obsList.isEmpty()){
			tagList.setItems(obsList);	
			tagList.getSelectionModel().selectFirst();
		}
		*/
		prevStage = stage;
	}
	
	public void loadTags(){
		obsList.clear();
		for(Tag tag : thisPhoto.getTags()){
			obsList.add(tag);
		}
		if(!obsList.isEmpty()){
			tagList.setItems(obsList);	
			tagList.getSelectionModel().selectFirst();
		}
	}
	
	@FXML
	private void addTag(){
		//TODO
	}
	@FXML
	private void deleteTag(){
		thisPhoto.deleteTag(tagList.getSelectionModel().getSelectedItem());
		loadTags();
	}
	@FXML
	private void copyPhoto(){
		//needs alert
	}
	@FXML
	private void movePhoto(){
		//needs alert
	}
	@FXML
	private void prevPhoto(){
		int index = thisAlbum.openAlbum().indexOf(thisPhoto);
		if((index-1) <0){
			index = thisAlbum.openAlbum().size() - 1;
		}
		else{
			index--;
		}
		PhotoAlbum.sMan.setCurrentPhoto(thisAlbum.openAlbum().get(index));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
		AnchorPane searchLayout = (AnchorPane) loader.load();
		PhotoViewController pControl = (PhotoViewController) loader.getController();
		pControl.setPrevStage(prevStage);
		Scene scene = new Scene(searchLayout);
		prevStage.setTitle("Search Window");
		prevStage.setScene(scene);
		prevStage.show();
	}
	@FXML
	private void nextPhoto() throws IOException{
		int index = thisAlbum.openAlbum().indexOf(thisPhoto);
		if((index+1) >= thisAlbum.openAlbum().size()){
			index = 0;
		}
		else{
			index++;
		}
		PhotoAlbum.sMan.setCurrentPhoto(thisAlbum.openAlbum().get(index));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
		AnchorPane searchLayout = (AnchorPane) loader.load();
		PhotoViewController pControl = (PhotoViewController) loader.getController();
		pControl.setPrevStage(prevStage);
		Scene scene = new Scene(searchLayout);
		prevStage.setTitle("Search Window");
		prevStage.setScene(scene);
		prevStage.show();
	}
	@FXML
	private void toPhotos() throws IOException{
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
	private void editCaption(){
		if(!captionEdit){
			caption.setVisible(false);
			editField.setVisible(true);
			editField.textProperty().setValue(caption.textProperty().get());
		}
		else{
			caption.setVisible(true);
			editField.setVisible(false);
			caption.textProperty().set(editField.textProperty().get());
			this.thisPhoto.setCaption(caption.textProperty().get());
		}
		captionEdit = !captionEdit;
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
