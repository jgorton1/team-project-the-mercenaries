package model;

/**
 * This is the player's bank account that contains the in-game currency for the
 * power-up system.
 */
public class CurrencyBank {

	private int balance;

	public CurrencyBank() {
		balance = 0;
	}

	/**
	 * Adds one unit to the currency account.
	 */
	public void add() {
		balance += 1;
	}

	/**
	 * This class adds a specified amount of currency to the bank.
	 * 
	 * @param amount The amount of monetary units to be deposited.
	 */
	public void add(int amount) {
		balance += amount;
	}

	/**
	 * This class removes a specified amount of money to from the bank, if there is
	 * enough money in it.
	 * 
	 * @param cost The amount of money to be withdrawn.
	 * @return A boolean value determining whether there is enough money in the
	 *         account to withdraw the specified amount.
	 */
	public boolean spend(int cost) {
		if (balance - cost >= 0) {
			balance -= cost;
			return true;
		} else
			return false;
	}

	/**
	 * A getter method for the amount of money in the currency account.
	 * 
	 * @return The amount of money units in the bank account.
	 */
	public int getBalance() {
		return balance;
	}
}
