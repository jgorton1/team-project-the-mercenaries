package model;

public class Ship_One extends Ships {
	public Ship_One(Coord coord, boolean up) {
		super(coord, 1, up);
		type = "One";
	}

	public Ship_One(int start_x, int start_y) {
		this.size = 1;
		this.location = new int[1][2];
		location[0][0] = start_x;
		location[0][1] = start_y;
		this.type = "One";
		this.isSunk = false;
		this.hits = new boolean[1];
	}

	public Ship_One(Coord coord) {
		this.size = 1;
		this.location = new int[1][2];
		location[0][0] = coord.getX();
		location[0][1] = coord.getY();
		this.type = "One";
		this.isSunk = false;
		this.hits = new boolean[1];
	}

}
