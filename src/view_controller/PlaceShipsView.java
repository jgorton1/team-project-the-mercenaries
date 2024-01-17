package view_controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Coord;
import model.Game;
import model.Ship_Four;
import model.Ship_One;
import model.Ship_Three;
import model.Ship_Two;
import model.Ships;

/**
 * This is the view in which the user places ships byy dragging and dropping
 * 
 * @author jgort
 *
 */
public class PlaceShipsView extends BorderPane {
	private Game game;
	private Iterator<String> shipsToAdd;
	Label guide;
	private Canvas canvas;
	private GraphicsContext gc;
	private int width = BattleshipGUI.width; // - 50;
	private int height = BattleshipGUI.height; // - 50;
	private int heightOneBoard;
	private int secondBoardTop;
	private int yScale;
	private int xScale;
	private HashMap<String, Image> shipImages;
	private boolean selected;
	private ArrayList<ImageView> images;
	private boolean noShipsPlaced;

	public PlaceShipsView(Game theModel) {
		game = theModel;
		shipsToAdd = game.getShipsToAdd();
		initializePanel();
		drawOcean();
		drawBoards();
		drawShips();
        noShipsPlaced = true;
		registerHandlers();
	}

	private void drawShips() {
		int shipHeight = 40;
		yScale = heightOneBoard / 7;
		xScale = width / 7;
		// load in the ships
		Image shipFourImage = new Image("file:images/shipFour.png", 260, shipHeight, false, true, false);
		Image shipThreeImage = new Image("file:images/shipThree.png", 200, shipHeight, false, true, false);
		Image shipTwoImage = new Image("file:images/shipTwo.png", 130, shipHeight, false, true, false);
		Image shipOneImage = new Image("file:images/shipOne.png", 60, shipHeight, false, true, false);
		Image shipFourUp = new Image("file:images/shipFourUp.png", shipHeight, 170, false, true, false);
		Image shipThreeUp = new Image("file:images/shipThreeUp.png", shipHeight, 125, false, true, false);
		Image shipTwoUp = new Image("file:images/shipTwoUp.png", shipHeight, 85, false, true, false);
		Image shipOneUp = new Image("file:images/shipOneUp.png", shipHeight, 40, false, true, false);
		// put them in a hashmap so we can find them easily
		shipImages = new HashMap<>();
		shipImages.put("shipFour-r", shipFourImage);
		shipImages.put("shipThree-r", shipThreeImage);
		shipImages.put("shipTwo-r", shipTwoImage);
		shipImages.put("shipOne-r", shipOneImage);
		shipImages.put("shipFour-u", shipFourUp);
		shipImages.put("shipThree-u", shipThreeUp);
		shipImages.put("shipTwo-u", shipTwoUp);
		shipImages.put("shipOne-u", shipOneUp);
		// add all the ships
		Pane pane = new Pane();
		int offset = 0;
		images = new ArrayList<>();
		while (shipsToAdd.hasNext()) {
			String ship = shipsToAdd.next();
			System.out.println(ship);
			images.add(addMovableImage(ship, pane, offset));
			offset += 50;

		}
		setTop(pane); // add the Pane object to the center of the BorderPane
		Label move = new Label("Click on ship to move it, right-click to rotate, click middle mouse\n button on a ship to randomize (before placing any ships)");
		move.setTextFill(Color.BLACK);
		move.setFont(new Font("Calibri", 18));
		move.setMouseTransparent(true);
		setCenter(move);
		setAlignment(pane, Pos.CENTER); // center the Pane object in the center region

	}

	private ImageView addMovableImage(String ship, Pane pane, int offset) {
		String len = ship.substring(0, 1).toUpperCase() + ship.substring(1);
		Image image = shipImages.get("ship" + len + "-r");
		ImageView imageView = new ImageView(image);
		// r for right, u for up
		String side = "r";
		// id is for example "four-u"
		imageView.setId(ship + "-" + side);
		imageView.setLayoutY(offset);

		pane.getChildren().add(imageView);
		pane.setPadding(new Insets(20));
		selected = false;
		EventHandler<MouseEvent> mouseClickedHandler = (MouseEvent me) -> {
			if (me.getButton() == MouseButton.SECONDARY) {
				if (imageView.getId().contains("-r")) {
					String newId = imageView.getId().split("-")[0] + "-u";
					imageView.setId(newId);
					String length = imageView.getId().split("-")[0].substring(0, 1).toUpperCase()
							+ imageView.getId().split("-")[0].substring(1);
					imageView.setImage(shipImages.get("ship" + length + "-u"));

				} else {
					String newId = imageView.getId().split("-")[0] + "-r";
					imageView.setId(newId);
					String length = imageView.getId().split("-")[0].substring(0, 1).toUpperCase()
							+ imageView.getId().split("-")[0].substring(1);
					imageView.setImage(shipImages.get("ship" + length + "-r"));
				}
				return;
			}
			if (me.getButton() == MouseButton.MIDDLE) {
				if (noShipsPlaced) {
					game.setShipsForPlayer();
					BattleshipGUI.drawView = new PlayDrawingView(game);
					BattleshipGUI.getStage().getScene().setRoot(BattleshipGUI.drawView);
				}
			}
			if (!selected) {
				selected = true;
				for (ImageView view : images) {
					if (view != imageView) {
						view.setMouseTransparent(true);
					}
				}
				return;
			}
			System.out.println("released");
			double y = me.getSceneY();
			double x = me.getSceneX();
			double minY = 330;

			// check if in board
			if (y < minY || y > 630)
				return;
			// check if valid ship location, add to game
			int xCoord = 0;
			int yCoord = 0;
			for (int i = 0; i < 7; i++) {
				if (xScale * i <= x && x < xScale * (i + 1)) {
					xCoord = i;
				}
			}
			for (int i = 0; i < 7; i++) {
				if (yScale * i <= y - minY && y - minY < yScale * (i + 1) + 3) {
					yCoord = i;
				}
			}
			Ships newShip = null;
			Coord coord = new Coord(xCoord, yCoord);
			System.out.println(coord.toString());
			if (imageView.getId().split("-")[0].equals("four")) {
				newShip = new Ship_Four(coord, imageView.getId().split("-")[1].equals("u"));
			} else if (imageView.getId().split("-")[0].equals("three")) {
				newShip = new Ship_Three(coord, imageView.getId().split("-")[1].equals("u"));
			} else if (imageView.getId().split("-")[0].equals("two")) {
				newShip = new Ship_Two(coord, imageView.getId().split("-")[1].equals("u"));
			} else if (imageView.getId().split("-")[0].equals("one")) {
				newShip = new Ship_One(coord, imageView.getId().split("-")[1].equals("u"));
			}
			if (game.getPlayerBoard().tryAddShip(newShip)) {
				noShipsPlaced = false;
				selected = false;
				for (ImageView view : images) {
					if (view != imageView) {
						view.setMouseTransparent(false);
					}
				}
				System.out.println("ship added");
				// remove handlers, make invisible to mouse
				// imageView.removeEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
				imageView.setMouseTransparent(true);
				imageView.setTranslateX(xScale * xCoord - imageView.getLayoutX());
				imageView.setTranslateY(yScale * yCoord - imageView.getLayoutY() + minY + 10);
				System.out.println(game.getPlayerBoard().toString());
				// imageView.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedHandler);

			}

			if (game.getPlayerBoard().getShips().size() == 5) {
				BattleshipGUI.drawView = new PlayDrawingView(game);
				BattleshipGUI.getStage().getScene().setRoot(BattleshipGUI.drawView);
			}

		};
		EventHandler<MouseEvent> mouseMovedHandler = (MouseEvent me) -> {
			if (selected) {
				// Move the image to follow the mouse
				imageView.setTranslateX(me.getSceneX() - 20 - imageView.getLayoutX());
				imageView.setTranslateY(me.getSceneY() - 20 - imageView.getLayoutY());
			}
		};

		imageView.setOnMouseClicked(mouseClickedHandler);

		imageView.setOnMouseMoved(mouseMovedHandler);
		return imageView;

	}

	private void registerHandlers() {
		// TODO Auto-generated method stub

	}

	private void drawBoards() {
		// Draw board rectangles
		heightOneBoard = height / 2 - 40;
		secondBoardTop = heightOneBoard + 65;
		gc.strokeRect(0, secondBoardTop, width, heightOneBoard);

		// vertical lines
		int lineDiv = width / 7;
		for (int i = 1; i < 7; i++) {
			gc.strokeLine(lineDiv * i, 0, lineDiv * i, heightOneBoard);
		}
		// horizontal lines
		int lineH = heightOneBoard / 7;
		for (int i = 0; i < 8; i++) {
			gc.strokeLine(0, lineH * i, width, lineH * i);

		}
	}

	private void drawOcean() {
		Image water = new Image("https://tinyurl.com/4xpske83", false);
		BackgroundImage imageBackground = new BackgroundImage(water, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		this.setBackground(new Background(imageBackground));
	}

	private void initializePanel() {
		// Declare components
		canvas = new Canvas(width, height / 2);
		gc = canvas.getGraphicsContext2D();
		setBottom(canvas);

	}

}
