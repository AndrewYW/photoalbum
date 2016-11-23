package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.SessionManager;
import view.LoginController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Main class to launch application.
 * @author Andrew Wang
 * @author Rumzi Tadros
 *
 */
public class PhotoAlbum extends Application {
	public static SessionManager sMan = null;
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
			loader.setLocation(getClass().getResource("/view/LoginPage.fxml"));
			AnchorPane rootLayout = (AnchorPane) loader.load();
			
			LoginController lControl = (LoginController)loader.getController();
			lControl.setPrevStage(primaryStage);
			lControl.start(primaryStage);
			Scene scene = new Scene(rootLayout);
			primaryStage.setTitle("Photo Album - Rumzi Tadros & Andrew Wang");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		File data = new File("dat"+File.separator+"sessions.dat");
		if(data.exists() && !data.isDirectory()){
			try{
				PhotoAlbum.sMan = SessionManager.readApp();
			}catch(Exception e){
				PhotoAlbum.sMan = new SessionManager();
			}
		}
		else
			PhotoAlbum.sMan = new SessionManager();
		launch(args);
	}
}
