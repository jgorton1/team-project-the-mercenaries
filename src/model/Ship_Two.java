package model;

public class Ship_Two extends Ships {
	public Ship_Two(Coord coord, boolean up) {
		super(coord, 2, up);
		type = "Two";
	}

	public Ship_Two(int start_x, int start_y, int end_x, int end_y) {
		this.size = 2;
		this.location = new int[2][2];
		location[0][0] = start_x;
		location[0][1] = start_y;
		location[1][0] = end_x;
		location[1][1] = end_y;
		this.type = "Two";
		this.isSunk = false;
		this.hits = new boolean[2];
	}
}
