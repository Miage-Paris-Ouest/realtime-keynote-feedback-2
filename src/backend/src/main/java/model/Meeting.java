package model;

public class Meeting {
	private String title;
	private String date;
	private int startTime;
	private int endTime;
	private int duration;
	private int numPers;
	private int attIndice;
	
	public Meeting (String title, String date, int startTime, int endTime,  int numPers) {
		this.title = title;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.numPers = numPers;
		this.DurationCalcul();
		this.AttentionCalcul();
	}
	
	public Meeting (String title,  int numPers) {
//		LocalDateTime currentTime = LocalDateTime.now();
		this.title = title;
//		this.date = currentTime.toLocalDate(); Gérer la date
//		this.startTime = LocalDateTime.now(); Gérer l'heure
//		this.endTime = (Integer) null;
		this.numPers = numPers;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getStartTime() {
		return startTime;
	}

	public int getNumPers() {
		return numPers;
	}

	public int getAttIndice() {
		return attIndice;
	}
	
	public void setAttIndice(int attIndice) {
		this.attIndice = attIndice;
	}

	public void DurationCalcul () {
		this.setDuration(this.endTime - this.startTime); 
	}
	
	public void AttentionCalcul () {
		this.setAttIndice(1/*Données de calcul*/);
	}
}
