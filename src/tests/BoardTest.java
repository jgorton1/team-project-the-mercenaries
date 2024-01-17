package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import model.Board;
import model.Coord;
import model.Ship_Four;
import model.Ship_One;

class BoardTest {

	@SuppressWarnings("unused")
	@Test
	void testConstructors() {
		Board b1 = new Board("Me");
		Board b2 = new Board("Me", 7, 7);
		Board b3 = new Board(7, 7);
	}

	@Test
	void testGetters() {
		Board b1 = new Board("me", 7, 7);
		assertEquals(7, b1.getHeight());
		assertEquals(7, b1.getWidth());
		assertEquals("me", b1.getOwner());
	}
	
	@Test
	void testToStrings() {
		Board b1 = new Board("me", 7, 7);
		System.out.println(b1);
		b1.tryAddShip(new Ship_One(0,0));
		System.out.println(b1.toStringShotsOnly());
	}
	
	@Test
	void testMechanics() {
		Board b1 = new Board("me", 7, 7);
		assertTrue(b1.tryAddShip(new Ship_One(0,0)));
		assertFalse(b1.tryAddShip(new Ship_One(0,0)));
		assertFalse(b1.tryAddShip(new Ship_One(88,99)));
		assertFalse(b1.isAllSunk());
		b1.shot(0,0);
		assertTrue(b1.isAllSunk());
		assertFalse(b1.isCoordShot(new Coord(0,1)));
		b1.shot(new Coord(0,1));
		assertTrue(b1.isCoordShot(new Coord(0,1)));
		
	}
	@Test
	void testPowerups() {
		Board b1 = new Board("me", 7, 7);
		assertTrue(b1.tryAddShip(new Ship_Four(new Coord(0,0), true)));
		b1.powerUpShot(0,0);
		assertTrue(b1.isCoordShot(new Coord(0,0)));
		assertTrue(b1.isCoordHit(new Coord(0,0)));
		b1.powerUpShot(1,0);
		assertTrue(b1.isCoordShot(new Coord(1,0)));
		assertFalse(b1.isCoordHit(new Coord(1,0)));
		assertTrue(b1.isCoordMiss(new Coord(1,0)));
	}
}
