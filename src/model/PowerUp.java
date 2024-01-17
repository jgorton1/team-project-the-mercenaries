package model;

import java.util.ArrayList;

/**
 * This class defines the Power-Up shots that the user can spend in-game
 * currency on that will allow them to hit more spaces on the board.
 * 
 * @author luis_
 * 
 * 
 */
public class PowerUp{

	private Board theBoard;
	private CurrencyBank bank;
	private ArrayList<Coord> hits;

	public PowerUp(Board board, CurrencyBank playerBank) {
		theBoard = board;
		bank = playerBank;
		hits = new ArrayList<>();
	}

	/**
	 * shoots an entire row
	 * 
	 * @param row
	 * @param col
	 * @return true if there was enough money to buy it
	 */
	public boolean rowShot(int row, int col) {
		hits.clear();
		if (bank.spend(4)) {
			for (int i = 0; i < theBoard.getHeight(); i++) {
				theBoard.powerUpShot(i, col);
				hits.add(new Coord(i, col));
			}
			return true;
		} else
			return false;
	}

	/**
	 * shoots an entire column
	 * 
	 * @param row
	 * @param col
	 * @return true of there was enought money to buy it
	 */
	public boolean columnShot(int row, int col) {
		hits.clear();
		if (bank.spend(4)) {
			for (int i = 0; i < theBoard.getWidth(); i++) {
				theBoard.powerUpShot(row, i);
				hits.add(new Coord(row, i));
			}
			return true;
		} else
			return false;
	}

	/**
	 * shoots a square (3x3)
	 * 
	 * @param row
	 * @param col
	 * @return true if there was enough money to buy it
	 */
	public boolean squareShot(int row, int col) {
		hits.clear();
		if (bank.spend(6)) {
			int y_start = row - 1;
			int x_start = col - 1;
			boolean[][] spaces = getSquares(row, col);
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (spaces[i][j]) {
						theBoard.powerUpShot(y_start + j, x_start + i);
						hits.add(new Coord(y_start + j, x_start + i));
					}
				}
			}
			return true;
		} else
			return false;
	}

	/**
	 * shoots a 2x2 square
	 * 
	 * @param row
	 * @param col
	 * @return true if there was enough money to buy it
	 */
	public boolean smallSquareShot(int row, int col) {
		hits.clear();
		if (bank.spend(3)) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					if (isInRange(row + i, col + j)) {
						theBoard.powerUpShot(row + i, col + j);
					    hits.add(new Coord(row + i, col + j));
					}
				}
			}
			return true;
		} else
			return false;
	}

	private boolean[][] getSquares(int row, int col) {
		hits.clear();
		int y_start = row - 1;
		int x_start = col - 1;
		boolean[][] spaces = new boolean[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!isInRange(x_start + i, y_start + j)) {
					spaces[i][j] = false;
				} else
					spaces[i][j] = true;
			}
		}
		return spaces;
	}

	private boolean isInRange(int row, int col) {
		if (row < 0 || row > theBoard.getHeight() - 1 || col < 0 || col > theBoard.getWidth() - 1)
			return false;
		else
			return true;
	}
	public ArrayList<Coord> getNewHits() {
		return hits;
	}

}
