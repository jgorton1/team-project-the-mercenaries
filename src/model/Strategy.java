package model;

/**
 * Interface for strategies: must be able to get a move given a board
 * 
 * @author jgort
 *
 */
public interface Strategy {

	public Coord getMove(Board playerBoard);

}
