package application;

public class Student {
	private int ID;
	private String Name;
	private int Score;
	
	
	public Student(int iD, String name, int score) {
		this.ID = iD;
		this.Name = name;
		this.Score = score;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		this.ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public int getScore() {
		return Score;
	}
	public void setScore(int score) {
		this.Score = score;
	}
	
	public String ToString() {
		return "[ID="+ID+",name="+Name+",score="+Score+"]";
	}
}
