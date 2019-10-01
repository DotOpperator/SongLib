//Manohar Chitoda
//Suraj Upadhyay
package view;

import java.io.IOException;
import app.SongLibFunc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AddController
{
	@FXML Button add, cancel;
	@FXML TextField song, artist, year, album;
	@FXML Label songlb, artistlb;

	// Whenever a button is clicked in the add window
	public void buttonClicked(ActionEvent e) throws IOException
	{
		// Pre-load the library view
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Display.fxml"));
		BorderPane root = (BorderPane)loader.load();
		DisplayController disp = loader.getController();

		if((Button)e.getSource() == add)
		{
			boolean checkInput = handle();
			if(checkInput)
			{
				disp.start(SongLibFunc.getIndex(song.getText(),artist.getText()));
				Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
				Scene newScene= new Scene(root);
				stage.setScene(newScene);
				stage.show();
			}

			else
			{
				alert((Stage)((Node)e.getSource()).getScene().getWindow(), checkInput);
			}
		}
		else if((Button)e.getSource() == cancel)
		{
			disp.start(0);
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			Scene newScene= new Scene(root);
			stage.setScene(newScene);
			stage.show();
		}
	}

	// Checkpoint
	public boolean handle()
	{
		if(song.getText().length() > 0 && artist.getText().length() > 0 )
			return (SongLibFunc.add(song.getText(),artist.getText(),year.getText(),album.getText()));
		else
			return false;
	}

	//This alert window is initiated when a delete button is clicked
	private void alert(Stage mainStage, boolean duplicate)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainStage);
		alert.setTitle("INPUT ERROR");
		alert.setHeaderText("Error In Input!");
		String content = ""; 

		if (!duplicate && song.getText().length() > 0 && artist.getText().length() > 0)
			content = "You Attempted to Add An Existing Song, Try again!";
		else if(song.getText().length() == 0 && artist.getText().length() == 0)
			content = "Song Name and Artist Name Fields Are Empty";
		else if(song.getText().length() > 0 && artist.getText().length() == 0)
			content = "Artist Name Field Is Empty";
		else if(song.getText().length() == 0 && artist.getText().length() > 0)
			content = "Song Name Field Is Empty";
		
		alert.setContentText(content);
		alert.showAndWait();
	}
}
