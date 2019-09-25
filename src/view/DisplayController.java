package view;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DisplayController {
	
	
	@FXML ListView<String> vlist;
	@FXML TextField songtx;
	@FXML TextField artisttx;
	@FXML TextField yeartx;
	@FXML TextField albumtx;
	@FXML Button add;
	@FXML Button delete;
	@FXML Button edit;
	private ObservableList<String> obs;
	 public void start()
	{
		obs = FXCollections.observableArrayList("chocolate", "salmon", "gold", "coral", "darkorchid",
	            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
	            "blueviolet", "brown");
		vlist.setItems(obs);
		vlist.getSelectionModel().select(0);
		//vlist.getSelectionModel().selectedIndexProperty().addListener(vlist.getSelectionModel().selectedIndexProperty().getValue());
        
	}
	public void sort(ObservableList<String> s) {
		// Sort using FXCollections
        FXCollections.sort(s);
        vlist.getSelectionModel().select(0);
	}
	public void buttonClicked(ActionEvent e)
	{
		Button b = (Button)e.getSource();
		if(b == add) {
			sort(obs);
			vlist.setItems(obs);
		}
		else if(b == delete) {
			
		}else if(b == edit) {
			
		}
	}
}
