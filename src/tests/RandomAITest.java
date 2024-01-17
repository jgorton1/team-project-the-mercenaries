package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Board;
import model.Coord;
import model.RandomAI;
import model.Strategy;
class RandomAITest {

	@Test
	void test() {
		Strategy rand = new RandomAI();
		Board board = new Board(7,7);
		Coord testMove = new Coord(1,1);
		board.shot(testMove);
		// should fill board without hitting anything twice
		for (int i = 0; i < 48; i ++) {
			Coord move = rand.getMove(board);
			assertFalse(board.isCoordShot(move));
			board.shot(move);System.out.println(board.toString());
		}
		
		
	}

}
