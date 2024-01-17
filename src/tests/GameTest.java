package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Game;
import model.Ship_One;

class GameTest {

	@Test
	void testCallMethods() {
		Game game = new Game();
		System.out.println(game.getComputerBoard());
		System.out.println(game.getPlayerBoard());
		game.startNewGame(null);
		game.setStrategyEasy();
		game.setStrategyHard();
	}
	
	@Test
	void testGame() {
		Game game = new Game();
		game.setStrategyEasy();
		game.getPlayerBoard().tryAddShip(new Ship_One(0,0));
		game.getPlayerBoard().tryAddShip(new Ship_One(6,6));
		assertTrue(game.isNotDoneYet());
		assertFalse(game.humanWon());
		assertFalse(game.computerWon());
		for(int i=0; i<7; i++) {
			for(int j=0; j<7; j++) {
				game.shoot(i, j);
			}
		}
		assertFalse(game.isNotDoneYet());
		//assertTrue(game.humanWon());
	}
	
	@Test
	void testGameComputerWins() {
		Game game = new Game();
		game.setStrategyEasy();
		game.getPlayerBoard().tryAddShip(new Ship_One(0,0));
		assertTrue(game.isNotDoneYet());
		assertFalse(game.humanWon());
		assertFalse(game.computerWon());
		for(int i=0; i<99; i++) {
			game.shoot(0,0);		// Hoping the RandomAI chooses (0,0) once in 99 tries.
		}
		assertFalse(game.isNotDoneYet());
		assertTrue(game.computerWon());
	}
	@Test
	void powerups() {
		// TODO
	}

}
