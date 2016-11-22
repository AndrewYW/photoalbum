package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import view.LoginController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class PhotoAlbum extends Application {
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
		launch(args);
	}
}
