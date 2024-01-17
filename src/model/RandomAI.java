package model;

import java.util.Random;

/**
 * This class uses a board to get moves for the computer player
 * 
 * @author jgort
 *
 */
public class RandomAI implements Strategy {
	Random random = new Random();

	@Override
	/**
	 * Given a player board, gets a random move. Never shoots the same square twice.
	 */
	public Coord getMove(Board playerBoard) {
		Coord move;
		do {
			move = new Coord(random.nextInt(playerBoard.getWidth()), random.nextInt(playerBoard.getHeight()));
		} while (playerBoard.isCoordShot(move));
		return move;

	}

}
