package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import model.Ship_One;
import model.Ship_Two;
import model.Ship_Three;
import model.Coord;
import model.Ship_Four;

class ShipsTest {

	@Test
	void test_One() {
		Ship_One ship = new Ship_One(2, 4);
		assertEquals("One", ship.getType());
		assertEquals(1, ship.getSize());
		assertFalse(ship.isSunk()); 
		ship.hit(2, 4);
		assertTrue(ship.isSunk());
		assertFalse(ship.exists(1, 0));
	}
	
	@Test
	void test_One_Coord() {
		Coord point = new Coord(4, 2);
		Ship_One ship = new Ship_One(point);
		assertEquals("One", ship.getType());
		assertEquals(1, ship.getSize());
		assertFalse(ship.isSunk());
		ship.hit(point);
		assertTrue(ship.isSunk());
		assertFalse(ship.exists(1, 0));
	}
	
	@Test
	void test_Two() {
		Ship_Two ship = new Ship_Two(3, 3, 3, 4);
		assertEquals("Two", ship.getType());
		assertEquals(3, ship.getLocation()[1][0]);
		assertEquals(4, ship.getLocation()[1][1]);
		assertTrue(ship.exists(3, 4));
		assertEquals(2, ship.getSize());
		assertFalse(ship.isSunk());
		ship.hit(3, 4);
		assertFalse(ship.isSunk());
		ship.hit(3, 3);
		assertTrue(ship.isSunk());
	}
	
	@Test
	void test_Three() {
		Ship_Three ship = new Ship_Three(2, 1, 4, 1);
		assertEquals("Three", ship.getType());
		assertTrue(ship.exists(3, 1));
		assertEquals(3, ship.getLocation()[1][0]);
		assertEquals(1, ship.getLocation()[1][1]);
		assertEquals(4, ship.getLocation()[2][0]);
		assertEquals(1, ship.getLocation()[2][1]);
		assertEquals(3, ship.getSize());
		assertFalse(ship.isSunk());
		assertFalse(ship.hit(3, 4));
		ship.hit(2, 1);
		ship.hit(3, 1);
		ship.hit(4, 1);
		assertTrue(ship.isSunk());
	}
	
	@Test
	void test_Three_Horizontal() {
		Ship_Three ship = new Ship_Three(3, 5, 3, 7);
		assertEquals("Three", ship.getType());
		assertTrue(ship.exists(3, 6));
		assertEquals(3, ship.getLocation()[1][0]);
		assertEquals(6, ship.getLocation()[1][1]);
		assertEquals(3, ship.getLocation()[2][0]);
		assertEquals(7, ship.getLocation()[2][1]);
		assertEquals(3, ship.getSize());
		assertFalse(ship.isSunk());
		assertFalse(ship.hit(3, 4));
		ship.hit(3, 5);
		ship.hit(3, 6);
		ship.hit(3, 7);
		assertTrue(ship.isSunk());
	}
	
	@Test
	void test_Four_Horizontal() {
		Ship_Four ship = new Ship_Four(5, 4, 5, 7);
		assertEquals("Four", ship.getType());
		assertFalse(ship.exists(3, 1));
		assertTrue(ship.exists(5, 5));
		assertTrue(ship.exists(5, 6));
		assertEquals(4, ship.getLocation().length);
		assertEquals(5, ship.getLocation()[1][0]);
		assertEquals(5, ship.getLocation()[1][1]);
		assertEquals(5, ship.getLocation()[2][0]);
		assertEquals(6, ship.getLocation()[2][1]);
		assertEquals(4, ship.getSize());
		assertFalse(ship.isSunk());
		ship.hit(5, 4);
		assertFalse(ship.hit(5, 4));
		ship.hit(5, 5);
		ship.hit(5, 6);
		ship.hit(5, 7);
		assertTrue(ship.isSunk());
	}
	
	@Test
	void test_Four_Vertical() {
		Ship_Four ship = new Ship_Four(0, 0, 3, 0);
		assertEquals("Four", ship.getType());
		assertFalse(ship.exists(2, 1));
		assertTrue(ship.exists(1, 0));
		assertTrue(ship.exists(2, 0));
		assertEquals(4, ship.getLocation().length);
		assertEquals(1, ship.getLocation()[1][0]);
		assertEquals(0, ship.getLocation()[1][1]);
		assertEquals(2, ship.getLocation()[2][0]);
		assertEquals(0, ship.getLocation()[2][1]);
		assertEquals(4, ship.getSize());
		assertFalse(ship.isSunk());
		ship.hit(0, 0);
		assertFalse(ship.hit(0, 0));
		ship.hit(1, 0);
		ship.hit(2, 0);
		ship.hit(3, 0);
		assertTrue(ship.isSunk());
	}
	
	
	
	
	
	@Test
	void test_One_CoordNewConstructor() {
		Coord point = new Coord(4, 2);
		Ship_One ship = new Ship_One(point, false);
		assertEquals("One", ship.getType());
		assertEquals(1, ship.getSize());
		assertFalse(ship.isSunk());
		ship.hit(point);
		assertTrue(ship.isSunk());
		assertFalse(ship.exists(1, 0));
	}
	
	@Test
	void test_TwoNewConstructor() {
		Ship_Two ship = new Ship_Two(new Coord(3, 3), true);
		assertEquals("Two", ship.getType());
		assertEquals(3, ship.getLocation()[1][0]);
		assertEquals(4, ship.getLocation()[1][1]);
		assertTrue(ship.exists(3, 4));
		assertEquals(2, ship.getSize());
		assertFalse(ship.isSunk());
		ship.hit(3, 4);
		assertFalse(ship.isSunk());
		ship.hit(3, 3);
		assertTrue(ship.isSunk());
	}
	
	@Test
	void test_ThreeNewConstructor() {
		Ship_Three ship = new Ship_Three(new Coord(2, 1), false);
		assertEquals("Three", ship.getType());
		assertTrue(ship.exists(3, 1));
		assertEquals(3, ship.getLocation()[1][0]);
		assertEquals(1, ship.getLocation()[1][1]);
		assertEquals(4, ship.getLocation()[2][0]);
		assertEquals(1, ship.getLocation()[2][1]);
		assertEquals(3, ship.getSize());
		assertFalse(ship.isSunk());
		assertFalse(ship.hit(3, 4));
		ship.hit(2, 1);
		ship.hit(3, 1);
		ship.hit(4, 1);
		assertTrue(ship.isSunk());
	}
	
	@Test
	void test_Three_HorizontalNewConstructor() {
		Ship_Three ship = new Ship_Three(new Coord(3, 5), true);
		assertEquals("Three", ship.getType());
		assertTrue(ship.exists(3, 6));
		assertEquals(3, ship.getLocation()[1][0]);
		assertEquals(6, ship.getLocation()[1][1]);
		assertEquals(3, ship.getLocation()[2][0]);
		assertEquals(7, ship.getLocation()[2][1]);
		assertEquals(3, ship.getSize());
		assertFalse(ship.isSunk());
		assertFalse(ship.hit(3, 4));
		ship.hit(3, 5);
		ship.hit(3, 6);
		ship.hit(3, 7);
		assertTrue(ship.isSunk());
	}
	
	@Test
	void test_Four_HorizontalNewConstructor() {
		Ship_Four ship = new Ship_Four(new Coord(5, 4), true);
		assertEquals("Four", ship.getType());
		assertFalse(ship.exists(3, 1));
		assertTrue(ship.exists(5, 5));
		assertTrue(ship.exists(5, 6));
		assertEquals(4, ship.getLocation().length);
		assertEquals(5, ship.getLocation()[1][0]);
		assertEquals(5, ship.getLocation()[1][1]);
		assertEquals(5, ship.getLocation()[2][0]);
		assertEquals(6, ship.getLocation()[2][1]);
		assertEquals(4, ship.getSize());
		assertFalse(ship.isSunk());
		ship.hit(5, 4);
		assertFalse(ship.hit(5, 4));
		ship.hit(5, 5);
		ship.hit(5, 6);
		ship.hit(5, 7);
		assertTrue(ship.isSunk());
	}
	
	@Test
	void test_Four_VerticalNewConstructor() {
		Ship_Four ship = new Ship_Four(new Coord(0, 0), false);
		assertEquals("Four", ship.getType());
		assertFalse(ship.exists(2, 1));
		assertTrue(ship.exists(1, 0));
		assertTrue(ship.exists(2, 0));
		assertEquals(4, ship.getLocation().length);
		assertEquals(1, ship.getLocation()[1][0]);
		assertEquals(0, ship.getLocation()[1][1]);
		assertEquals(2, ship.getLocation()[2][0]);
		assertEquals(0, ship.getLocation()[2][1]);
		assertEquals(4, ship.getSize());
		assertFalse(ship.isSunk());
		ship.hit(0, 0);
		assertFalse(ship.hit(0, 0));
		ship.hit(1, 0);
		ship.hit(2, 0);
		ship.hit(3, 0);
		assertTrue(ship.isSunk());
	}

}
