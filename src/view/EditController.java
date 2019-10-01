//Manohar Chitoda
//Suraj Upadhyay
package view;

import java.io.IOException;

import app.Song;
import app.SongLibFunc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class EditController
{
	private Song songObj;
	DisplayController disp;
	
	@FXML Button saveEdit;
	@FXML Button cancel;
	@FXML TextField song, artist, year, album;
	
	// Setup a song object to get quickly grab the exiting song data 
	public void initData(Song song)
	{
		songObj = song;
		this.song.setText(songObj.getSong());
		this.artist.setText(songObj.getArtist());
		this.year.setText(songObj.getYear());
		this.album.setText(songObj.getAlbum());
	}
	
	//This alert window is initiated when a delete button is clicked
	public void buttonClicked(ActionEvent e) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Display.fxml"));
		BorderPane root = (BorderPane) loader.load();
		DisplayController disp = loader.getController();

		if (e.getSource() == saveEdit) 
		{
			boolean checkInput = handle();
			if(checkInput)
			{
				disp.start(SongLibFunc.getIndex(songObj.getSong(), songObj.getArtist()));
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				Scene newScene = new Scene(root);
				stage.setScene(newScene);
				stage.show();
			}
			else
				alert((Stage) ((Node) e.getSource()).getScene().getWindow(), checkInput);
		}

		else if(e.getSource() == cancel) 
		{

			disp.start(SongLibFunc.getIndex(songObj.getSong(), songObj.getArtist()));
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			Scene newScene = new Scene(root);
			stage.setScene(newScene);
			stage.show();
		}
	}

	// Checkpoint
	public boolean handle()
	{
		if(song.getText().length() > 0 && artist.getText().length() > 0 )
			return SongLibFunc.edit(songObj.getSong(), songObj.getArtist(), this.song.getText(), this.artist.getText(),
					this.year.getText(), this.album.getText());
		else
			return false;
	}

	//This alert window is initiated when a delete button is clicked
	private void alert(Stage mainStage, boolean duplicate)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainStage);
		alert.setTitle("ERROR INPUT");
		alert.setHeaderText("Error In Input!");

		String content = "";
		if (!duplicate && song.getText().length() > 0 && artist.getText().length() > 0)
			content = "You Renamed The Current Song \nTo An Existing Song, Try again!";
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
