package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/***
 * 
 * 
 * Usage example: Game game = new Game(strategy); game.shoot(4,3); if
 * (game.hasPlayerWon()) // do something else game.nextTurn();
 * 
 * @author jgort
 *
 */
public class Game {
	private static final boolean PLAYER = true;
	private static final boolean AI = false;
	private Board playerBoard;
	private Board computerBoard;
	private Strategy strategy;
	private boolean turn;
	private boolean powerUps;
	private ArrayList<String> ships;
	private PowerUp powerupPlayer = null;
	private PowerUp powerupComputer = null;
	private Coord lastShotAI;
	private CurrencyBank computerBank;
	private CurrencyBank playerBank;
	private Random random = new Random();

	/**
	 * makes a game of standard size
	 */
	public Game() {
		playerBoard = new Board(7, 7);
		computerBoard = new Board(7, 7);

		this.strategy = null;
		turn = PLAYER;
		ships = new ArrayList<>();
		Collections.addAll(ships, "four", "three", "three", "two", "one");
		setShipsForAI();
	}

	/**
	 * resets the game so a game can be played again
	 * 
	 * @param strategy - hard or random ai
	 */
	public void startNewGame(Strategy strategy) {
		playerBoard = new Board(7, 7);
		computerBoard = new Board(7, 7);
		setShipsForAI();
		this.strategy = strategy;
		turn = PLAYER;
	}

	/***
	 * This will be run on each player's turn after they are done shooting, so if it
	 * is player's turn, it will return true if player won, and if it is ai's turn,
	 * it will return true if ai has won
	 * 
	 * @return
	 */
	public boolean hasPlayerWon() {
		if (turn == PLAYER) {
			return computerBoard.isAllSunk();
		} else {
			return playerBoard.isAllSunk();
		}
	}

	/**
	 * basic shoot function - assumes grid shaped boards, no power-ups allows user
	 * to shoot anywhere, including where previously shot
	 * 
	 * @param x
	 * @param y
	 */
	public boolean shoot(int x, int y) {
		if (turn == PLAYER) {
			computerBoard.shot(x, y);
		} else {
			playerBoard.shot(x, y);
		}
		if (hasPlayerWon()) {
			return true;
		}
		nextTurn();

		return computerShoot();

	}

	/**
	 * computer shoots and the turn is then changed back to player
	 * @return true if normal shot, false if powerup
	 */
	public boolean computerShoot() {
		boolean normal = false;
		if (hasPlayerWon()) {
			return normal;
		}
		int moveChoice = random.nextInt(2);
		int choice = random.nextInt(4);
		powerupComputer = new PowerUp(playerBoard, computerBank);
		lastShotAI = strategy.getMove(playerBoard);
		// this works better with if statements to hell and back 
		if (moveChoice == 0 && choice == 0 && powerUps && computerBank.getBalance() >= 3) {
			powerupComputer.smallSquareShot(lastShotAI.getX(), lastShotAI.getY());
		} else if (moveChoice == 0 && choice == 1 && powerUps && computerBank.getBalance() >= 6) {
			powerupComputer.squareShot(lastShotAI.getX(), lastShotAI.getY());
		} else if (moveChoice == 0 && choice == 2 && powerUps && computerBank.getBalance() >= 4) {
			powerupComputer.columnShot(lastShotAI.getX(), lastShotAI.getY());
		} else if (moveChoice == 0 && choice == 3 && powerUps && computerBank.getBalance() >= 4) {
			powerupComputer.rowShot(lastShotAI.getX(), lastShotAI.getY());
		} else {
			normal = true;
			playerBoard.shot(lastShotAI);
		}
		
		nextTurn();
		return normal;
	}

	/**
	 * changes whose turn it is, might be useful to notify gui eventually
	 */
	public void nextTurn() {
		
		turn = !turn;
		// if ai turn, make a shot? or separate method?
	}

	/**
	 * sets strategy to hard
	 */
	public void setStrategyHard() {
		strategy = new HardAI();

	}

	/**
	 * sets strategy to easy
	 */
	public void setStrategyEasy() {
		strategy = new RandomAI();

	}

	private void setShipsForAI() {
		Random random = new Random();
		int x, y;
		Coord coord;
		for (String ship : ships) {
			// feels like i should be able to make this into a function since
			// i repeat the same code so much but it may be more effort than it is worth
			if (ship.equals("four")) {
				do {
					x = random.nextInt(computerBoard.getWidth());
					y = random.nextInt(computerBoard.getHeight());
					coord = new Coord(x, y);
				} while (!computerBoard.tryAddShip(new Ship_Four(coord, random.nextBoolean())));
			} else if (ship.equals("three")) {
				do {
					x = random.nextInt(computerBoard.getWidth());
					y = random.nextInt(computerBoard.getHeight());
					coord = new Coord(x, y);
				} while (!computerBoard.tryAddShip(new Ship_Three(coord, random.nextBoolean())));
			} else if (ship.equals("two")) {
				do {
					x = random.nextInt(computerBoard.getWidth());
					y = random.nextInt(computerBoard.getHeight());
					coord = new Coord(x, y);
				} while (!computerBoard.tryAddShip(new Ship_Two(coord, random.nextBoolean())));
			} else if (ship.equals("one")) {
				do {
					x = random.nextInt(computerBoard.getWidth());
					y = random.nextInt(computerBoard.getHeight());
					coord = new Coord(x, y);
				} while (!computerBoard.tryAddShip(new Ship_One(coord, random.nextBoolean())));
			}

		}
	}
	public void setShipsForPlayer() {
		Random random = new Random();
		int x, y;
		Coord coord;
		for (String ship : ships) {
			// feels like i should be able to make this into a function since
			// i repeat the same code so much but it may be more effort than it is worth
			if (ship.equals("four")) {
				do {
					x = random.nextInt(playerBoard.getWidth());
					y = random.nextInt(playerBoard.getHeight());
					coord = new Coord(x, y);
				} while (!playerBoard.tryAddShip(new Ship_Four(coord, random.nextBoolean())));
			} else if (ship.equals("three")) {
				do {
					x = random.nextInt(playerBoard.getWidth());
					y = random.nextInt(playerBoard.getHeight());
					coord = new Coord(x, y);
				} while (!playerBoard.tryAddShip(new Ship_Three(coord, random.nextBoolean())));
			} else if (ship.equals("two")) {
				do {
					x = random.nextInt(playerBoard.getWidth());
					y = random.nextInt(playerBoard.getHeight());
					coord = new Coord(x, y);
				} while (!playerBoard.tryAddShip(new Ship_Two(coord, random.nextBoolean())));
			} else if (ship.equals("one")) {
				do {
					x = random.nextInt(playerBoard.getWidth());
					y = random.nextInt(playerBoard.getHeight());
					coord = new Coord(x, y);
				} while (!playerBoard.tryAddShip(new Ship_One(coord, random.nextBoolean())));
			}

		}
	}

	/**
	 * is game over?
	 * 
	 * @return if gameplay must continue, no end state reached
	 */
	public boolean isNotDoneYet() {
		// TODO Auto-generated method stub
		return !(humanWon() || computerWon());
	}

	/**
	 * 
	 * @return true if human won
	 */
	public boolean humanWon() {
		return computerBoard.isAllSunk();
	}

	/**
	 * 
	 * @return true if computer won
	 */
	public boolean computerWon() {
		return playerBoard.isAllSunk();
	}

	/**
	 * gets player board
	 * 
	 * @return board object belonging to player
	 */
	public Board getPlayerBoard() {
		// TODO Auto-generated method stub
		return playerBoard;
	}

	/**
	 * gets computer board
	 * 
	 * @return board object belonging to computer
	 */
	public Board getComputerBoard() {
		// TODO Auto-generated method stub
		return computerBoard;
	}

	/**
	 * gets an iterator of ships to add
	 * 
	 * @return iterator of ship sizes as strings that must be added to board
	 */
	public Iterator<String> getShipsToAdd() {
		return ships.iterator();
	}

	/**
	 * converts a string to a ship object
	 * 
	 * @param ship  - size of ship "one" ... "four"
	 * @param coord - top or left of ship
	 * @param up    - ship vertical if true
	 * @return ship with above paramerters
	 */
	public Ships getNewShipFromString(String ship, Coord coord, boolean up) {
		Ships shipToAdd = null;
		;
		ship = ship.toLowerCase();
		if (ship.equals("one")) {
			shipToAdd = new Ship_One(coord);
		} else if (ship.equals("two")) {
			shipToAdd = new Ship_Two(coord, up);
		} else if (ship.equals("three")) {
			shipToAdd = new Ship_Three(coord, up);
		} else if (ship.equals("four")) {
			shipToAdd = new Ship_Four(coord, up);
		}
		return shipToAdd;

	}

	/**
	 * get if powerups on or not
	 * 
	 * @return powerups on or not
	 */
	public boolean powerUpsOn() {
		return powerUps;
	}

	/**
	 * turns powerups on or off
	 * 
	 * @param on
	 */
	public void setPowerUps(boolean on) {
		playerBank = new CurrencyBank();
		computerBank = new CurrencyBank();
		playerBank.add(10);
		computerBank.add(10);
		powerupPlayer = new PowerUp(computerBoard, playerBank);
		powerupComputer = new PowerUp(playerBoard, computerBank);

		powerUps = on;
	}

	/**
	 * calls other shot method
	 * 
	 * @param coord
	 */
	public boolean shoot(Coord coord) {
		return shoot(coord.getX(), coord.getY());

	}

	/**
	 * 
	 * @return shot of ai so it can be displayed
	 */
	public Coord getAIShot() {
		return lastShotAI;
	}

	/**
	 * gets player currency
	 * 
	 * @return
	 */
	public int getPlayerCurrency() {
		return playerBank.getBalance();
	}

	/**
	 * gets compuer currency
	 * 
	 * @return
	 */
	public int getComputerCurrency() {
		return computerBank.getBalance();
	}

	/**
	 * gets powerup object
	 * 
	 * @return player powerup object
	 */
	public PowerUp getPlayerPowerUp() {
		return powerupPlayer;
	}

	/**
	 * gets powerup object
	 * 
	 * @return computer powerup object
	 */
	public PowerUp getComputerPowerUp() {
		return powerupComputer;
	}

}
