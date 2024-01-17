package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Board;
import model.Coord;
import model.CurrencyBank;
import model.PowerUp;
import model.Ship_Four;

class PowerUpsTest {

	@Test
	void testCol() {
		Board b1 = new Board(7,7);
		CurrencyBank bank = new CurrencyBank();
		
		PowerUp power = new PowerUp(b1, bank);
		
		b1.addShip(new Ship_Four(new Coord(0,0), true));
		assertFalse(power.columnShot(0, 0));
		bank.add(10);
		assertTrue(power.columnShot(0, 0));
		assertTrue(b1.isCoordHit(new Coord(0,0)));
		assertTrue(b1.isCoordMiss(new Coord(0,5)));
	}
	@Test
	void testRow() {
		Board b1 = new Board(7,7);
		CurrencyBank bank = new CurrencyBank();
		
		PowerUp power = new PowerUp(b1, bank);
		
		b1.addShip(new Ship_Four(new Coord(0,0), true));
		assertFalse(power.rowShot(0, 0));
		bank.add(10);
		assertTrue(power.rowShot(0, 0));
		assertTrue(b1.isCoordHit(new Coord(0,0)));
		assertFalse(b1.isCoordHit(new Coord(0,5)));
		assertTrue(b1.isCoordShot(new Coord(3,0)));
		assertTrue(b1.isCoordMiss(new Coord(5,0)));
	}

}
