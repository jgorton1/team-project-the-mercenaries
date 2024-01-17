package model;

import java.util.ArrayList;

/**
 * Ships are the objects in battleship used to determine if ship has been shot,
 * whether the entire ship has been shot, and eventually whether the game has
 * been won. They contain coordinates with their position, positions of shot
 * locations, etc.
 * 
 * @author everyone
 *
 */
public abstract class Ships {
	protected int size;
	protected boolean isSunk;
	protected String type;
	protected int[][] location;
	protected boolean[] hits;
	protected boolean up;

	public Ships() {

	}

	/**
	 * if up, make ship point up. if not up, make ship point right tbh it might be
	 * better to use a string "up" or "right" but i am using a random number
	 * generator so it is easier for me to just cast to boolean
	 * 
	 * @param coord
	 * @param up
	 * @param coord
	 * @param size
	 * @param up
	 */
	public Ships(Coord coord, int size, boolean up) {
		this.size = size;
		this.location = new int[size][2];
		this.isSunk = false;
		this.hits = new boolean[size];
		this.up = up;

		// each class just needs to add type
		if (up) {
			for (int i = 0; i < size; i++) {
				this.location[i][0] = coord.getX();
				this.location[i][1] = coord.getY() + i;
			}
		} else {
			for (int i = 0; i < size; i++) {
				this.location[i][0] = coord.getX() + i;
				this.location[i][1] = coord.getY();
			}
		}
	}

	/**
	 * returns ship types
	 * 
	 * @return ship type as string
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * returns ship size
	 * 
	 * @return ship size (int)
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * returns if ships is pointing up
	 * 
	 * @return if ships is pointing up
	 */
	public boolean isUp() {
		return up;
	}

	/**
	 * returns if ship is sunk
	 * 
	 * @return if ship is sunk
	 */
	public boolean isSunk() {
		return this.isSunk;
	}

	/**
	 * returns 2d int array of ship location on the board
	 * 
	 * @return 2d int array of ship location on the board
	 */
	public int[][] getLocation() {
		return location;
	}

	/**
	 * returns array of what parts of the ship have been hit
	 * 
	 * @return array of what parts of the ship have been hit
	 */
	public boolean[] getHits() {
		return this.hits;
	}

	/**
	 * returns if a ship has one or more hit
	 * 
	 * @return if a ship has one or more hit
	 */
	public boolean isHit() {
		if (this.isSunk)
			return true;
		boolean hit = false;
		for (boolean spaces : hits) {
			if (spaces == true)
				hit = true;
		}
		return hit;
	}

	/**
	 * Takes x and y coordinates of the board and marks them as hits in the 'hits'
	 * array. Also checks if all spaces have been hit to mark the ship as sunk.
	 * 
	 * @param x_coord - x coordinate of board
	 * @param y_coord
	 * @return boolean of whether the ship was hit
	 */
	public boolean hit(int x_coord, int y_coord) {
		for (int i = 0; i < size; i++) {
			if (location[i][0] == x_coord && location[i][1] == y_coord && hits[i] == false) {
				hits[i] = true;
				boolean sunk = true;
				for (boolean spaces : hits) {
					if (spaces == false)
						sunk = false;
				}
				if (sunk)
					isSunk = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * Calls other hit method
	 * 
	 * @param move
	 * @return boolean of whether ship was hit
	 */
	public boolean hit(Coord move) {
		return hit(move.getX(), move.getY());
	}

	/**
	 * Finds out if part of a ship is at a given location
	 * 
	 * @param x_coord
	 * @param y_coord
	 * @return boolean of whether ship is at a given location
	 */
	public boolean exists(int x_coord, int y_coord) {
		for (int i = 0; i < size; i++) {
			if (location[i][0] == x_coord && location[i][1] == y_coord) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return unique arraylist of coordinates that ship resides in
	 */
	public ArrayList<Coord> getCoords() {
		ArrayList<Coord> coords = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			coords.add(new Coord(location[i][0], location[i][1]));
		}
		return coords;
	}
}
