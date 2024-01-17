package model;

import java.util.Random;

/**
 * This AI loops through all of the ships on your board and looks for any that
 * are hit but not completely sunk. If it find any hit ships, it hits them in an
 * unhit spot. If There are only sunk or complete intact ships, it chooses a
 * random spot using RandomAI.
 */
public class HardAI implements Strategy {

	Coord move;

	Random random = new Random();

	/**
	 * Returns the Coordinate object for the AI's move by looping through all of the
	 * ship objects in the board to look for any hit ships.
	 */
	@Override
	public Coord getMove(Board playerBoard) {
		for (Ships ship : playerBoard.getShips()) {
			// If the ship is not sunk, but is hit...
			if (!ship.isSunk() && ship.isHit()) {
				int[][] location = ship.getLocation();
				boolean[] hits = ship.getHits();
				// loop through other spaces and hit an unhit spot
				for (int i = 0; i < hits.length; i++) {
					if (hits[i] == false) {
						move = new Coord(location[i][0], location[i][1]);
						return move;
					}
				}
			}
		}
		Strategy randomShot = new RandomAI();
		return randomShot.getMove(playerBoard);
	}

}
