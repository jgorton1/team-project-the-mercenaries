package model;

public class Ship_Four extends Ships {

	// see constructor in ships
	public Ship_Four(Coord coord, boolean up) {
		super(coord, 4, up);
		type = "Four";
	}

	public Ship_Four(int start_x, int start_y, int end_x, int end_y) {
		this.size = 4;
		this.location = new int[4][2];
		initializeLocation(start_x, start_y, end_x, end_y);
		this.type = "Four";
		this.isSunk = false;
		this.hits = new boolean[4];
	}

	private void initializeLocation(int start_x, int start_y, int end_x, int end_y) {
		if (start_x == end_x) {
			for (int i = 0; i < 4; i++) {
				this.location[i][0] = start_x;
				this.location[i][1] = start_y + i;
			}
		} else if (start_y == end_y) {
			for (int i = 0; i < 4; i++) {
				this.location[i][0] = start_x + i;
				this.location[i][1] = start_y;
			}
		}
	}

}
