//Manohar Chitoda
//Suraj Upadhyay
package view;

import java.io.IOException;
import java.util.Optional;

import app.Song;
import app.SongLibFunc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DisplayController
{
	@FXML ListView<Song> vlist;
	@FXML Label songlb, artistlb, yearlb, albumlb;
	@FXML Button add, delete, edit;
	static ObservableList<Song> obs;
	
	// Initiate the main stage display and print the list
	public void start(int i) throws IOException
	{
		obs = FXCollections.observableArrayList(SongLibFunc.songList);
		vlist.setItems(obs);
		vlist.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal) -> selected(i));
		vlist.getSelectionModel().select(i);
	}
	
	
	@FXML // When a button is clicked in main window, add, delete, and edit 
	public void buttonClicked(ActionEvent e) throws IOException
	{
		if(e.getSource() == add)
		{
			
			BorderPane n = FXMLLoader.load(getClass().getResource("/view/AddSong.fxml"));
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			Scene newScene= new Scene(n);
			stage.setScene(newScene);
			stage.show();
		}else if(e.getSource() == delete)
		{
			if(!obs.isEmpty() && alert((Stage)((Node)e.getSource()).getScene().getWindow()))
			{
				int index = vlist.getSelectionModel().getSelectedIndex();
				
				vlist.getSelectionModel().selectedIndexProperty().removeListener((obs,oldVal,newVal) -> selected(index));
				SongLibFunc.remove(obs.get(index).getSong(), obs.get(index).getArtist());
				
				if(index == SongLibFunc.songList.size())
				{
					start(index-1);
				}else if(index > 0 && index < SongLibFunc.songList.size())
				{
					start(index);
				}else {
					start(0);
				}
			}
		}else if(e.getSource() == edit)
		{
			if(obs.isEmpty() != true)
			{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/EditSong.fxml"));
				BorderPane n = loader.load();
				Scene newScene= new Scene(n);
				
				EditController econt = loader.getController();
				econt.initData(vlist.getSelectionModel().getSelectedItem());
				Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
				
				stage.setScene(newScene);
				stage.show();
			}
		}
	}
	
	//This alert window is initiated when a delete button is clicked
	private boolean alert(Stage mainStage)
	{
		boolean choice = true;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(mainStage);
		alert.setTitle("Delete Song");
		alert.setHeaderText("Confirm Delete!");
		String content = "Are you sure you want delete this song?";
		alert.setContentText(content);
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK)
			choice = true;
		else
			choice = false;
		return choice;
	}
	
	// This is used to listen when an item is selected 
	// and display the details of selected items
	public void selected(int i)
	{
		try
		{
			if(SongLibFunc.songList.isEmpty() != true)
			{
				//vlist.getSelectionModel().select(i);
				songlb.setText(vlist.getSelectionModel().getSelectedItem().getSong());
				artistlb.setText(vlist.getSelectionModel().getSelectedItem().getArtist());
				yearlb.setText(vlist.getSelectionModel().getSelectedItem().getYear());
				albumlb.setText(vlist.getSelectionModel().getSelectedItem().getAlbum());
			}
		}
		catch(Exception e)
		{
			System.out.println();
		}
	}
}
