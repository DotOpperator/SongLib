package app;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import view.DisplayController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class SongLib extends Application 
{
	private Stage primaryStage;
	private BorderPane mainLayout;
	@Override
	public void start(Stage primaryStage) throws IOException
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Song Library");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Display.fxml"));
		
		mainLayout = loader.load();
		
		DisplayController disp = loader.getController() ;
		disp.start();
		Scene scene = new Scene(mainLayout,600,294);
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
