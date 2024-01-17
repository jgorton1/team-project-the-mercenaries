package view_controller;

import java.util.Scanner;

import model.Game;
import model.Ship_Four;
import model.Ship_One;
import model.Ship_Three;
import model.Ship_Two;
import model.Ships;

/**
 * Console view for battleship, no powerups Stopped development to work on
 * javafx battleship
 * 
 * @author jgort
 *
 */
public class BattleShipConsoleView {
	private static Game game;

	public static void main(String[] args) {
		game = new Game();
		System.out.println("Play a game of battleship!");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter e for easy, h for hard:");
		String difficulty = scanner.nextLine();
		if (difficulty.equals("h")) {
			game.setStrategyHard();
		} else {
			game.setStrategyEasy();
		}
		// place ships TODO - probably should have a list of ships, iterate through list
		// and add them
		for (int i = 0; i < 1; i++) {
			System.out.println("enter an x and y for a 1x1 ship (eg \"2 4\"): ");
			String[] str = scanner.nextLine().split(" ");
			int x = Integer.parseInt(str[0]);
			int y = Integer.parseInt(str[1]);
			if (taken(x, y, 0, 0, true)) {
				game.getPlayerBoard().addShip(new Ship_One(x, y));
			} else
				i--;
		}
		System.out.println(game.getPlayerBoard() + "\n");
		for (int i = 0; i < 1; i++) {
			System.out.println("enter an x and y for a 2x1 ship (eg \"2 4,3 4\"): ");
			String str = scanner.nextLine();
			String[] str2 = str.split(",");
			String[] str2_1 = str2[0].split(" ");
			String[] str2_2 = str2[1].split(" ");
			int x1 = Integer.parseInt(str2_1[0]);
			int y1 = Integer.parseInt(str2_1[1]);
			int x2 = Integer.parseInt(str2_2[0]);
			int y2 = Integer.parseInt(str2_2[1]);
			boolean legal = true;
			if (x1 == x2) {
				if (y1 - y2 != 1 && y2 - y1 != 1) {
					System.out.println("This is not a 2x1 ship, please enter a legal ship");
					i--;
					legal = false;
				}
			} else if (y1 == y2) {
				if (x1 - x2 != 1 && x2 - x1 != 1) {
					System.out.println("This is not a 2x1 ship, please enter a legal ship");
					i--;
					legal = false;
				}
			} else {
				System.out.println("This is not a 2x1 ship, please enter a legal ship");
				i--;
				legal = false;
			}
			if (legal) {
				if (taken(x1, y1, x2, y2, false))
					game.getPlayerBoard().addShip(new Ship_Two(x1, y1, x2, y2));
				else
					i--;
			}
		}
		System.out.println(game.getPlayerBoard() + "\n");
		for (int i = 0; i < 2; i++) {
			System.out.println("enter an x and y for a 3x1 ship (eg \"2 4,4 4\"): ");
			String str = scanner.nextLine();
			String[] str2 = str.split(",");
			String[] str2_1 = str2[0].split(" ");
			String[] str2_2 = str2[1].split(" ");
			int x1 = Integer.parseInt(str2_1[0]);
			int y1 = Integer.parseInt(str2_1[1]);
			int x2 = Integer.parseInt(str2_2[0]);
			int y2 = Integer.parseInt(str2_2[1]);
			boolean legal = true;
			if (x1 == x2) {
				if (y1 - y2 != 2 && y2 - y1 != 2) {
					System.out.println("This is not a 3x1 ship, please enter a legal ship");
					i--;
					legal = false;
				}
			} else if (y1 == y2) {
				if (x1 - x2 != 2 && x2 - x1 != 2) {
					System.out.println("This is not a 3x1 ship, please enter a legal ship");
					i--;
					legal = false;
				}
			} else {
				System.out.println("This is not a 3x1 ship, please enter a legal ship");
				i--;
				legal = false;
			}
			if (legal) {
				if (taken(x1, y1, x2, y2, false))
					game.getPlayerBoard().addShip(new Ship_Three(x1, y1, x2, y2));
				else
					i--;
			}
			System.out.println(game.getPlayerBoard() + "\n");
		}
		System.out.println(game.getPlayerBoard() + "\n");
		for (int i = 0; i < 1; i++) {
			System.out.println("enter an x and y for a 4x1 ship (eg \"2 4,5 4\"): ");
			String str = scanner.nextLine();
			String[] str2 = str.split(",");
			String[] str2_1 = str2[0].split(" ");
			String[] str2_2 = str2[1].split(" ");
			int x1 = Integer.parseInt(str2_1[0]);
			int y1 = Integer.parseInt(str2_1[1]);
			int x2 = Integer.parseInt(str2_2[0]);
			int y2 = Integer.parseInt(str2_2[1]);
			boolean legal = true;
			if (x1 == x2) {
				if (y1 - y2 != 3 && y2 - y1 != 3) {
					System.out.println("This is not a 4x1 ship, please enter a legal ship");
					i--;
					legal = false;
				}
			} else if (y1 == y2) {
				if (x1 - x2 != 3 && x2 - x1 != 3) {
					System.out.println("This is not a 4x1 ship, please enter a legal ship");
					i--;
					legal = false;
				}
			} else {
				System.out.println("This is not a 4x1 ship, please enter a legal ship");
				i--;
				legal = false;
			}
			if (legal) {
				if (taken(x1, y1, x2, y2, false))
					game.getPlayerBoard().addShip(new Ship_Four(x1, y1, x2, y2));
				else
					i--;
			}
		}

		System.out.println(game.getPlayerBoard() + "\n");
		// need ships to be of same class
		// game.getPlayerBoard().addShip(new Ship_Four());

		do {
			System.out.println("enter an x and y for shot (eg \"2 4\"): ");
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			game.shoot(x, y);
			printGame(game);

		} while (game.isNotDoneYet());
		if (game.humanWon()) {
			System.out.println("you won!");
		} else if (game.computerWon()) {
			System.out.println("you lost!");
		}
		scanner.close();
	}

	/***
	 * Prints out computer player grid on top, then blank line, then user grid
	 * 
	 * @param game
	 */
	public static void printGame(Game game) {
		System.out.println(game.getComputerBoard().toStringShotsOnly());
		System.out.println();
		System.out.println(game.getPlayerBoard().toString());
	}

	private static boolean taken(int x1, int y1, int x2, int y2, boolean one) {
		int upperHeight = game.getPlayerBoard().getHeight();
		int upperWidth = game.getPlayerBoard().getWidth();
		if (one) {
			if (x1 < 0 || y1 < 0) {
				System.out.println("Out of bounds, please enter a legal coordinate pair");
				return false;
			} else if (x1 > upperHeight || y1 > upperWidth) {
				System.out.println("Out of bounds, please enter a legal coordinate pair");
				return false;
			} else {
				boolean taken = false;
				for (Ships ship : game.getPlayerBoard().getShips()) {
					if (ship.exists(x1, y1)) {
						taken = true;
						break;
					}
				}
				if (taken) {
					System.out.println("There is already a ship there, please enter a different coordinate pair");
					return false;
				} else {
					return true;
				}
			}
		} else {
			if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) {
				System.out.println("Out of bounds, please enter a legal coordinate pair");
				return false;
			} else if (x1 > upperHeight || y1 > upperWidth || x2 > upperHeight || y2 > upperWidth) {
				System.out.println("Out of bounds, please enter a legal coordinate pair");
				return false;
			} else {
				boolean taken = false;
				for (Ships ship : game.getPlayerBoard().getShips()) {
					if (x1 == x2) {
						for (int j = y1; j <= y2; j++) {
							if (ship.exists(x1, j)) {
								taken = true;
								break;
							}
						}
					} else {
						for (int j = x1; j <= x2; j++) {
							if (ship.exists(j, y1)) {
								taken = true;
								break;
							}
						}
					}
				}
				if (taken) {
					System.out.println("There is already a ship there, please enter a different coordinate pair");
					return false;
				} else {
					return true;
				}
			}
		}
	}
}
