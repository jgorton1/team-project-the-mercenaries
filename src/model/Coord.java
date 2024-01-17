package model;

/**
 * A simple Point class to store two integers that represent the row and column
 * of a Tic Tac Toe board
 * 
 * @author Rick Mercer Modified by Julius Gorton
 */
public class Coord {

	private int x, y;

	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The row
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return The column
	 */
	public int getY() {
		return y;
	}

	public boolean equals(Coord coord) {
		return x == coord.x && y == coord.y;

	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
