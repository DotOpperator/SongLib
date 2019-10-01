//Manohar Chitoda
//Suraj Upadhyay
package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.DisplayController;

public class SongLibFunc extends Application
{
	
	//Lauch the stage and the scene
	@Override
	public void start(Stage primaryStage) throws IOException

	{
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() 
		{
			@Override
			public void handle(WindowEvent e) 
			{
				try 
				{
					SongLibFunc.printList();
				} catch (FileNotFoundException e1) 
				{
					System.out.println("No File Foud!");
					// e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Display.fxml"));
		BorderPane root = (BorderPane)loader.load();
		DisplayController disp = loader.getController();
		disp.start(0);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	// Launch the application as well as load the stored files to open any previously stored songs
	public static void main(String[] args) throws IOException, ParseException
	{
		SongLibFunc.readNLoad();
		launch(args);
	}
	
	// Instantiate a static LinkedList to show across the classes
	public static LinkedList<Song> songList = new LinkedList<>();
	
	// Returns a particular song stored in the LinkedList
	public static Song getSong(String songName, String artist)
	{
		for (Song song : songList) 
		{
			//if song == s; if s exists in the song lib
			if(compare(song, new Song(songName, artist, "", "")) == 0)
				return song;
		}
		return null;
	}
	
	// Returns the index of the songName in the LinkedList
	public static int getIndex(String songName, String artist)
	{
		int i = -1;
		for (Song song : songList) 
		{
			i++;
			//if song == s; if s exists in the song lib
			if(compare(song, new Song(songName, artist, "", "")) == 0)
				return i;
		}

		return -1;
	}
	
	// Compare 2 song objects
	private static int compare(Song s1, Song s2)
	{
		String str1 = s1.getSong() + "  " + s1.getArtist();
		String str2 = s2.getSong() + "  " + s2.getArtist();

		return (str1.compareToIgnoreCase(str2));
	}

	//returns false if the same song and artist exits in the song lib.
	public static boolean add(String song, String artist, String year, String album)
	{
		Song s = new Song(song, artist, year, album);

		if(songList.isEmpty())
			songList.add(s);

		//if the song lib is not empty 
		else
		{	
			ListIterator<Song> itr = songList.listIterator();
			while(itr.hasNext())
			{
				Song curr = itr.next();

				int comparison = compare(curr, s);

				//curr(song+artist) > s(song+artist)
				if(comparison > 0)
				{
					itr.previous();
					itr.add(s);					
					return true;
				}

				//curr(song+artist) == s(song+artist)
				else if(comparison == 0)
					return false;
			}
			itr.add(s);
		}
		return true;
	}

	//This method will not check for the same data entered for the new song as the old song.
	public static boolean edit(String oldSong, String oldArtist,
			String newSong, String newArtist, String newYear, String newAlbum)
	{
		Song temp = getSong(oldSong, oldArtist);
		
		//temp != null --> song exists in the lib to remove
		if(temp != null && remove(oldSong, oldArtist))
		{
			//song was NOT successfully added..song name + artist already exist.
			if(!add(newSong, newArtist, newYear, newAlbum))
			{
				add(temp.getSong(), temp.getArtist(), temp.getYear(), temp.getAlbum());
				return false;
			}
			return true;
		}

		return false;
	}
	
	// Removes the song passed from the LinkedList
	public static boolean remove(String songName, String artist)
	{
		Song s = getSong(songName, artist);
		if(s == null)
			return false;
		else
		{
			songList.remove(s);
			return true;
		}
	}
	
	//Writes the Songs stored in the LinkedList to a local file
	@SuppressWarnings("unchecked")
	public static void printList() throws FileNotFoundException
	{
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();

		for (Song song : songList) 
		{
			JSONObject s = new JSONObject();
			s.put("song", song.getSong());
			s.put("artist", song.getArtist());
			s.put("year", song.getYear());
			s.put("album", song.getAlbum());
			arr.add(s);
		}

		obj.put("songs", arr);

		PrintWriter write = new PrintWriter("outFile.json");
		write.append(' ');
		write.append(obj.toString());
		write.close();

	}
	
	// Read the JSON file and load it into the LinkedList
	@SuppressWarnings("unchecked")
	public static void readNLoad() throws IOException, ParseException
	{
		File dataFile = new File("outFile.json");
		dataFile.createNewFile();

		FileReader read = new FileReader(dataFile);

		if(read.read() > 0)
		{
			JSONObject obj = (JSONObject) ((new JSONParser()).parse(read));

			JSONArray arr = (JSONArray) obj.get("songs");
			Iterator<JSONObject> i = arr.iterator();

			while (i.hasNext()) 
			{
				JSONObject s = i.next();
				add((String) s.get("song"), (String) s.get("artist"), (String) s.get("year"), (String) s.get("album"));
			}

		}
		read.close();
	}

}
