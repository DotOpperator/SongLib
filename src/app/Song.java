// Manohar Chitoda
// Suraj Upadhyay
package app;

public class Song 
{
	private String song, artist, album, year;
	
	// Constructor to instantiate a song object
	public Song(String song, String artist, String year, String album) {
		this.song = song.trim();
		this.artist = artist.trim();
		this.album = album.trim();
		this.year = year.trim();
	}

	/**
	 * @return the song
	 */
	public String getSong() {
		return song;
	}

	/**
	 * @param song the song to set
	 */
	public void setSong(String song) {
		this.song = song;
	}

	/**
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * @param artist the artist to set
	 */
	public void setArtist(String artist){
		this.artist = artist;
	}

	/**
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * @param album the album to set
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	
	public String toString()
	{
		String s = this.getSong();
		if(s.length() >10)
			s += s.substring(0, 1);
		return (String.format("%45s %30s %50s",s,"< - >",this.getArtist()));
		
	}
}
