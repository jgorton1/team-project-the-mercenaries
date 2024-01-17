package model;

public class Ship_Three extends Ships {
	public Ship_Three(Coord coord, boolean up) {
		super(coord, 3, up);
		type = "Three";
	}

	public Ship_Three(int start_x, int start_y, int end_x, int end_y) {
		this.size = 3;
		this.location = new int[3][2];
		initializeLocation(start_x, start_y, end_x, end_y);
		this.type = "Three";
		this.isSunk = false;
		this.hits = new boolean[3];
	}

	private void initializeLocation(int start_x, int start_y, int end_x, int end_y) {
		if (start_x == end_x) {
			for (int i = 0; i < 3; i++) {
				this.location[i][0] = start_x;
				this.location[i][1] = start_y + i;
			}
		} else if (start_y == end_y) {
			for (int i = 0; i < 3; i++) {
				this.location[i][0] = start_x + i;
				this.location[i][1] = start_y;
			}
		}
	}

}
