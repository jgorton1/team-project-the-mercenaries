package model;

import java.util.ArrayList;

/**
 * This class holds the ships, allows shots to be fired, and gives the game
 * vital information about win states, etc.
 * 
 * @author jgort, andres
 *
 */
public class Board {
	private int width, height;
	private ArrayList<Ships> ships;
	private String owner; // whose board it is, maybe not needed
	private char[][] board;
	private Coord lastShot;

	public Board(String owner, int w, int h) {
		width = w;
		height = h;
		this.owner = owner;
		ships = new ArrayList<>();
		board = new char[width][height];
		populateBoard();
		lastShot = null;
	}

	/**
	 * Makes new board with given width and height
	 * 
	 * @param w - width
	 * @param h - height
	 */
	public Board(int w, int h) {
		width = w;
		height = h;
		ships = new ArrayList<>();
		board = new char[width][height];
		populateBoard();
		lastShot = null;
	}

	public Board(String owner) {
		width = 7;
		height = 7;
		this.owner = owner;
		ships = new ArrayList<>();
		board = new char[width][height];
		populateBoard();
		lastShot = null;
	}

	/**
	 * Are all ships sunk?
	 * 
	 * @return boolean of whether all ships are sunk
	 */
	public boolean isAllSunk() {
		for (Ships s : ships) {
			if (!s.isSunk())
				return false;
		}
		return true;
	}

	/**
	 * Calls other shot method
	 * 
	 * @param x
	 * @param y
	 */
	public void shot(int x, int y) {
		Coord coord = new Coord(x, y);
		shot(coord);
	}

	/**
	 * Checks all ships to see if one is hit, modifies ship and board accordingly
	 * 
	 * @param move
	 */
	public void shot(Coord move) {
		for (Ships ship : ships) {
			for (int[] coord : ship.getLocation()) {
				if (move.getX() == coord[0] && move.getY() == coord[1]) {
					board[move.getY()][move.getX()] = 'X';
					ship.hit(move);
					return;
				}
			}
		}
		board[move.getY()][move.getX()] = 'O';
	}

	/**
	 * Shoots a shot from a powerup, the difference is that this notifies observers
	 * 
	 * @param i - x coordinate
	 * @param j - y coordinate
	 */
	public void powerUpShot(int i, int j) {
		Coord move = new Coord(i, j);
		for (Ships ship : ships) {
			for (int[] coord : ship.getLocation()) {
				if (move.getX() == coord[0] && move.getY() == coord[1]) {
					board[move.getY()][move.getX()] = 'X';
					ship.hit(move);
					lastShot = move;
					return;
				}
			}
		}
		board[move.getY()][move.getX()] = 'O';
		lastShot = move;

	}

	/**
	 * width
	 * 
	 * @return width of board
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * height
	 * 
	 * @return height of board
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * owner
	 * 
	 * @return owner of board
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * original array of ships returned, editing will affect game state
	 * 
	 * @return original array of ships
	 */
	public ArrayList<Ships> getShips() {
		return ships;
	}

	/**
	 * add ship to the game, assumes ship location is valid
	 * 
	 * @param ship
	 */
	public void addShip(Ships ship) {
		ships.add(ship);

		for (int[] coord : ship.getLocation()) {
			board[coord[1]][coord[0]] = '$';
		}
	}

	/***
	 * adds a ship iff it does not intersect with another already existing ship
	 * 
	 * @param ship
	 * @return true if ship added, else false
	 */
	public boolean tryAddShip(Ships newShip) {
		for (int j = 0; j < newShip.getSize(); j++) {
			if (newShip.getLocation()[j][0] < 0 || newShip.getLocation()[j][0] >= width
					|| newShip.getLocation()[j][1] < 0 || newShip.getLocation()[j][1] >= height) {
				return false;
			}
		}
		for (Ships ship : ships) {
			for (int i = 0; i < ship.getSize(); i++) {
				for (int j = 0; j < newShip.getSize(); j++) {
					if (ship.getLocation()[i][0] == newShip.getLocation()[j][0]
							&& ship.getLocation()[i][1] == newShip.getLocation()[j][1]) {
						return false;
					}

				}
			}
		}

		addShip(newShip);
		return true;

	}

	private void populateBoard() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j] = '-';
			}
		}
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < height; i++) {
			str += "   ";
			for (int j = 0; j < width; j++) {
				str += board[i][j] + " ";
			}
			str += "\n";
		}
		return str;
	}

	/***
	 * We want to print only shots when we print the computer board for the human to
	 * see
	 * 
	 * @return string of shots only
	 */
	public String toStringShotsOnly() {
		String str = "";
		for (int i = 0; i < height; i++) {
			str += "   ";
			for (int j = 0; j < width; j++) {
				if (board[i][j] != '$') {
					str += board[i][j] + " ";
				} else {
					str += "- ";
				}

			}
			str += "\n";
		}
		return str;
	}

	/**
	 * is a given coord shot?
	 * 
	 * @param coord
	 * @return true if coord has been shot
	 */
	public boolean isCoordShot(Coord coord) {
		char space = board[coord.getY()][coord.getX()];

		if (space == '$' || space == '-')
			return false;
		return true;
	}

	/**
	 * is a given coord shot and has a ship on it?
	 * 
	 * @param coord
	 * @return true if a ship was present and has been shot
	 */
	public boolean isCoordHit(Coord coord) {
		char space = board[coord.getY()][coord.getX()];
		if (space == 'X')
			return true;
		return false;
	}

	/**
	 * is a coord shot and no ship present?
	 * 
	 * @param coord
	 * @return true if coord shot and no ship present
	 */
	public boolean isCoordMiss(Coord coord) {
		char space = board[coord.getY()][coord.getX()];
		if (space == 'O')
			return true;
		return false;
	}

	/**
	 * 
	 * @return most recent shot made to board
	 */
	public Coord getLastShot() {
		return lastShot;
	}

}
