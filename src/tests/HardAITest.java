package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.HardAI;
import model.Ship_Four;
import model.Ship_One;
import model.Ship_Three;
import model.Ship_Two;
import model.Board;
import model.Coord;

class HardAITest {
	
	Board board = new Board(7, 7);
	HardAI player = new HardAI();

	@Test
	void testTwo() {
		board.addShip(new Ship_Two(1, 1, 1, 2));
		board.shot(1, 2);
		Coord newShot = player.getMove(board);
		assertEquals(1, newShot.getX());
		assertEquals(1, newShot.getY());
	}
	
	@Test
	void testTwoAgain() {
		board.addShip(new Ship_Two(6, 3, 5, 3));
		board.shot(5, 3);
		Coord newShot = player.getMove(board);
		assertEquals(6, newShot.getX());
		assertEquals(3, newShot.getY());
	}
	
	@Test
	void testThree() {
		board.addShip(new Ship_Three(2, 2, 2, 4));
		board.shot(2, 3);
		Coord newShot = player.getMove(board);
		//System.out.println(board.toString());
		assertTrue(newShot.getY() == 2);
		board.shot(2, 2);
		assertEquals(player.getMove(board).getY(), 4);
	}
	
	@Test
	void testThreeAgain() {
		board.addShip(new Ship_Three(0, 0, 2, 0));
		board.addShip(new Ship_Three(4, 2, 4, 4));
		board.shot(1, 0);
		Coord newShot = player.getMove(board);
		assertTrue(newShot.getX() == 0);
		assertTrue(newShot.getY() == 0);
		board.shot(0, 0);
		board.shot(2, 0); // first ship sunk
		board.shot(4, 3);
		assertEquals(player.getMove(board).getY(), 2);
	}
	
	@Test
	void testFour() {
		board.addShip(new Ship_Four(1, 1, 1, 4));
		board.addShip(new Ship_Four(6, 2, 6, 5));
		board.shot(1, 3);
		Coord newShot = player.getMove(board);
		assertTrue(newShot.getX() == 1);
		assertTrue(newShot.getY() == 1);
		board.shot(1, 1);
		board.shot(1, 2);
		board.shot(1, 4);// first ship sunk
		board.shot(6, 4);
		assertEquals(player.getMove(board).getX(), 6);
		assertEquals(player.getMove(board).getY(), 2);
	}
	
	@Test
	void testRandom() {
		board.addShip(new Ship_One(2, 2));
		board.addShip(new Ship_One(6, 3));
		Coord newShot = player.getMove(board);
		board.shot(newShot);
		Coord nextShot = player.getMove(board);
		assertNotEquals(newShot.getX(), nextShot.getX());
		assertNotEquals(newShot.getY(), nextShot.getY());
	}

}
