package tron.modele;

public class Wall extends GameObject {

	public Wall(int row, int col) {
		super(row, col);
	}
	
	public String toString(){
		return "#";
	}
}
