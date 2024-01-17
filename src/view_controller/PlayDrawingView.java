package view_controller;

import java.io.File;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Side;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;

import javafx.scene.input.MouseButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaMarkerEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Board;
import model.Coord;
import model.Game;
import model.Ships;

/**
 * Playdrawingview is a drawing view of the game of battleship, ships are drawn
 * in as images, ships have already been placed, and the user can click to make
 * shots. Animations are shown and the game stops when someone has won.
 * 
 * @author jgort, andres
 *
 */
public class PlayDrawingView extends BorderPane {

	private Game game;
	private Canvas canvas;
	private Canvas shots;
	private GraphicsContext shotsGc;
	private GraphicsContext gc;
	private int width = BattleshipGUI.width; // - 50;
	private int height = BattleshipGUI.height; // - 50;
	private int heightOneBoard;
	private int secondBoardTop;
	private int xScale; // multiply xScale*xCoord
	private int yScale; // multiply yScale*yCoord, then add secondBoardTop to reach user board
	private Timeline timeline;
	private MediaPlayer mediaPlayer;
	private ContextMenu powerupMenu = new ContextMenu();
	private Text balance;

	private PathTransition pathTransition;
	private Group root;

	public PlayDrawingView(Game theModel) {
		game = theModel;
		initializeCanvas();
		drawOcean();
		drawBoards();
		drawShips();
		registerHandler();
		if (game.powerUpsOn())
			setUpMenu();
		//game.getComputerBoard().addObserver(this);
		//game.getPlayerBoard().addObserver(this);
		//game.getComputerPowerUp().addObserver(this);
		//game.getPlayerPowerUp().addObserver(this);
		this.setRight(new Label("Balance: 10"));

	}

	private void setUpMenu() {
		// can't think of a better easier way than to hard code this
		balance = new Text("Starting balance: " + game.getPlayerCurrency());
		gc.fillText(balance.getText(), 3 * width / 4, heightOneBoard + 30);
		MenuItem colShot = new MenuItem("Column shot - 4");
		colShot.setOnAction(event -> {
			int x = Integer
					.parseInt(((ContextMenu) ((MenuItem) event.getSource()).getParentPopup()).getId().substring(0, 1));
			int y = Integer
					.parseInt(((ContextMenu) ((MenuItem) event.getSource()).getParentPopup()).getId().substring(1, 2));
			game.getPlayerPowerUp().columnShot(x, y);
			putShots(game.getPlayerPowerUp().getNewHits(), false);
			
			// TODO shoots the shots but gets a bit complicated to keep animations, sounds,
			// etc
		});
		MenuItem rowShot = new MenuItem("Row shot - 4");
		rowShot.setOnAction(event -> {
			int x = Integer
					.parseInt(((ContextMenu) ((MenuItem) event.getSource()).getParentPopup()).getId().substring(0, 1));
			int y = Integer
					.parseInt(((ContextMenu) ((MenuItem) event.getSource()).getParentPopup()).getId().substring(1, 2));
			game.getPlayerPowerUp().rowShot(x, y);
			putShots(game.getPlayerPowerUp().getNewHits(), false);
		});

		MenuItem squareShot = new MenuItem("Square shot - 6");
		squareShot.setOnAction(event -> {
			int x = Integer
					.parseInt(((ContextMenu) ((MenuItem) event.getSource()).getParentPopup()).getId().substring(0, 1));
			int y = Integer
					.parseInt(((ContextMenu) ((MenuItem) event.getSource()).getParentPopup()).getId().substring(1, 2));
			game.getPlayerPowerUp().squareShot(x, y);
			putShots(game.getPlayerPowerUp().getNewHits(), false);

		});
		MenuItem smallSquareShot = new MenuItem("Small quare shot - 3");
		smallSquareShot.setOnAction(event -> {
			int x = Integer
					.parseInt(((ContextMenu) ((MenuItem) event.getSource()).getParentPopup()).getId().substring(0, 1));
			int y = Integer
					.parseInt(((ContextMenu) ((MenuItem) event.getSource()).getParentPopup()).getId().substring(1, 2));
			game.getPlayerPowerUp().smallSquareShot(x, y);
			putShots(game.getPlayerPowerUp().getNewHits(), false);

		});

		// Set the 'disabled' property to true
		powerupMenu.getItems().addAll(colShot, rowShot, squareShot, smallSquareShot);
		checkAllPrices();
	}
	

	private void checkAllPrices() {
		// perhaps a hashmap would be better
		checkPrices(powerupMenu.getItems().get(0), 4);
		checkPrices(powerupMenu.getItems().get(1), 4);
		checkPrices(powerupMenu.getItems().get(2), 6);
		checkPrices(powerupMenu.getItems().get(3), 3);

	}

	private void checkPrices(MenuItem item, int i) {
		if (game.getPlayerCurrency() < i) {
			item.setDisable(true);
		} else {
			item.setDisable(false);
		}

	}

	private void initializeCanvas() {
		canvas = new Canvas(width, height);
		gc = canvas.getGraphicsContext2D();
		shots = new Canvas(width, height);
		shotsGc = shots.getGraphicsContext2D();
		shotsGc.setGlobalBlendMode(BlendMode.SRC_OVER);
		StackPane pane = new StackPane();
		pane.getChildren().addAll(canvas, shots);
		setCenter(pane);

	}
    // DRAWING/ NOT MECHANICS OF SHOTS (AND ANIMATIONS)
	private void drawOcean() {
		Image water = new Image("https://tinyurl.com/4xpske83", false);
		BackgroundImage imageBackground = new BackgroundImage(water, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		this.setBackground(new Background(imageBackground));
	}

	private void drawBoards() {
		// Draw board rectangles
		heightOneBoard = height / 2 - 30;
		secondBoardTop = heightOneBoard + 65;
		gc.strokeRect(0, 0, width, heightOneBoard);
		gc.strokeRect(0, secondBoardTop, width, heightOneBoard);

		// vertical lines
		int lineDiv = width / 7;
		for (int i = 1; i < 7; i++) {
			gc.strokeLine(lineDiv * i, 0, lineDiv * i, heightOneBoard);
			gc.strokeLine(lineDiv * i, secondBoardTop, lineDiv * i, height);
		}
		// horizontal lines
		int lineH = heightOneBoard / 7;
		for (int i = 1; i < 7; i++) {
			gc.strokeLine(0, lineH * i, width, lineH * i);
			gc.strokeLine(0, secondBoardTop + lineH * i, width, secondBoardTop + lineH * i);
		}
	}

	private void drawShips() {

		yScale = heightOneBoard / 7;
		xScale = width / 7;

		for (Ships ship : game.getPlayerBoard().getShips()) {
			int[][] coords = ship.getLocation();
			int x = coords[0][0]; // assuming first coord is always leftmost
			int y = coords[0][1]; // assuming first coord is always upmost

			drawAShip(ship, new Coord(x, y), true);
		}
	}
	private MediaPlayer play(String string) {

		String path = new File(string).toURI().toString();
		System.out.println(path);
		Media media = new Media(path);
		media.getMarkers().put("display airstrike", Duration.millis(8000));
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		return mediaPlayer;

	}
    /**
     * Shows the computer's ship if the coord is part of a completely sunk ship
     * @param coord
     */
	private void showIfSunk(Coord coord) {
		Board compBoard = game.getComputerBoard();
		for (Ships s : compBoard.getShips()) {
			for (Coord c : s.getCoords()) {
				if (c.equals(coord)) {
					if (s.isSunk()) {
						String type = s.getType();
						Coord firstCoord = s.getCoords().get(0);
						boolean up = s.isUp();
						Ships newShip = game.getNewShipFromString(type, firstCoord, up);
						drawAShip(newShip, firstCoord, false);
						for (Coord hit : s.getCoords()) {
							hitAnimation(hit, false, true);
						}
					}
				}
			}
		}
	}
	private void drawAShip(Ships ship, Coord coord, boolean onPlayer) {
		int shipHeight = 40;
		int spacing = 15;
		int size = ship.getSize();
		int x = coord.getX();
		int y = coord.getY();
		Image shipFourImage = new Image("file:images/shipFour.png", 260, shipHeight, false, true, false);
		Image shipThreeImage = new Image("file:images/shipThree.png", 200, shipHeight, false, true, false);
		Image shipTwoImage = new Image("file:images/shipTwo.png", 130, shipHeight, false, true, false);
		Image shipOneImage = new Image("file:images/shipOne.png", 60, shipHeight, false, true, false);
		Image shipFourUp = new Image("file:images/shipFourUp.png", shipHeight, 170, false, true, false);
		Image shipThreeUp = new Image("file:images/shipThreeUp.png", shipHeight, 125, false, true, false);
		Image shipTwoUp = new Image("file:images/shipTwoUp.png", shipHeight, 85, false, true, false);
		Image shipOneUp = new Image("file:images/shipOneUp.png", shipHeight, 40, false, true, false);

		double yPos = 0;
		if (onPlayer)
			yPos = secondBoardTop + yScale * y;
		else
			yPos = yScale * y;

		if (ship.isUp()) {
			if (size == 4)
				gc.drawImage(shipFourUp, xScale * x + spacing, yPos);
			else if (size == 3)
				gc.drawImage(shipThreeUp, xScale * x + spacing, yPos);
			else if (size == 2)
				gc.drawImage(shipTwoUp, xScale * x + spacing, yPos);
			else
				gc.drawImage(shipOneUp, xScale * x + spacing, yPos);
		} else {
			if (size == 4)
				gc.drawImage(shipFourImage, xScale * x, yPos);
			else if (size == 3)
				gc.drawImage(shipThreeImage, xScale * x, yPos);
			else if (size == 2)
				gc.drawImage(shipTwoImage, xScale * x, yPos);
			else
				gc.drawImage(shipOneImage, xScale * x, yPos);
		}
	}
	private void rocketAnimation(Coord coord, boolean onPlayer) {
		// not an issue, simply do not call this function
		System.out.println("rocket");
		double xPos = coord.getX() * xScale;
		double yPos = coord.getY() * yScale;
		ImageView rocket;
		int yStart = 0;
		if (onPlayer) {
			yStart = 0;
			yPos += secondBoardTop;
			rocket = new ImageView(new Image("file:images/rocketDown.png", width / 7 - 20, heightOneBoard / 7 - 10,
					false, false, false));
		} else {
			rocket = new ImageView(new Image("file:images/rocketUp.png", width / 7 - 20, heightOneBoard / 7 - 10, false,
					false, false));
			yStart = height - 50;
		}

		Path path = new Path();
		path.getElements().add(new MoveTo(xPos + 30, yStart));
		path.getElements().add(new LineTo(xPos + 30, yPos + 30));
		pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(2300));
		pathTransition.setNode(rocket);
		pathTransition.setPath(path);
		pathTransition.play();
		root = new Group(this, rocket);
		BattleshipGUI.getStage().getScene().setRoot(root);
		pathTransition.setOnFinished(new Finished(rocket));
	}

	private class Finished implements EventHandler<ActionEvent> {
		ImageView rocket;

		Finished(ImageView image) {
			rocket = image;
		}

		@Override
		public void handle(ActionEvent arg0) {
			root.getChildren().remove(rocket);
		}
	}
	private void hitAnimation(Coord coord, boolean onPlayer, boolean hit) {
		double xPos = coord.getX() * xScale + 4;
		double yPos = 0;
		if (onPlayer)
			yPos = secondBoardTop + coord.getY() * yScale + 3;
		else
			yPos = coord.getY() * yScale + 3;
		timeline = new Timeline(new KeyFrame(Duration.millis(120), new AnimateStarter(xPos, yPos, hit)));
		timeline.setCycleCount(9);
		timeline.play();
	}

	private class AnimateStarter implements EventHandler<ActionEvent> {
		private int tic = 0;
		double sx = 0, sy = 0, sw = 180, sh = 180, dx = 0, dy = 0, dw = 40, dh = 40;
		int sxIncrement, ticReset, syIncrement, tics;
		private Image spritesheet;

		public AnimateStarter(double xPos, double yPos, boolean hit) {
			dy = yPos;
			dx = xPos;
			shotsGc.setFill(Color.TRANSPARENT);
			if (hit) {
				spritesheet = new Image("file:images/pngwing.com.png");
				sxIncrement = 190;
				syIncrement = 190;
				ticReset = 5;
				tics = 10;
			} else {
				spritesheet = new Image("file:images/splashmiss2.png");
				sxIncrement = 196;
				syIncrement = 120;
				ticReset = 4;
				tics = 8;
				dw = 60;
			}
			shotsGc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);
			
		}

		/*
		 * The images to draw are know as spritesheet (6 images) Use method drawImage
		 * with 9 arguments: drawImage(theImage, sx, sy, sw, sh, dx, dy, dw, dh) sx the
		 * source rectangle's X coordinate position. sy the source rectangle's Y
		 * coordinate position. sw the source rectangle's width. sh the source
		 * rectangle's height. dx the destination rectangle's X coordinate position. dy
		 * the destination rectangle's Y coordinate position. dw the destination
		 * rectangle's width. dh the destination rectangle's height.
		 */
		@Override
		public void handle(ActionEvent arg0) {
			tic++;

			if (tic == tics) {
				timeline.stop();
			}
			sx += sxIncrement;
			if (tic % ticReset == 0) {
				sx = 0;
				sy += syIncrement;
			}
			shotsGc.clearRect(dx, dy, dw, dh);
			shotsGc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);

			//System.out.println("handled");
		}

	}
    // MOUSE EVENT HANDLER
	private class MouseListener implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent click) {
			// if clicked, immediately set mouse transparent
			// only set back to visible when the computer's animations are done running
			//  this way double click is not an issue!!!!!!!!!!!!!!!!!!1!
			setMouseTransparent(true);
			System.out.println("invisible");
			double y = click.getSceneY();
			double x = click.getSceneX();
			if (heightOneBoard - 3 < y) {
				setMouseTransparent(false);
			    System.out.println("visible");
				return;
			}
			// Get the x and y for tile clicked
			int xCoord = 0;
			int yCoord = 0;
			for (int i = 0; i < 7; i++) {
				if (xScale * i <= x && x < xScale * (i + 1)) {
					xCoord = i;
				}
			}
			for (int i = 0; i < 7; i++) {
				if (yScale * i <= y && y < yScale * (i + 1) + 3) {
					yCoord = i;
				}
			}
			if (game.isNotDoneYet()) {
				if (click.getButton() == MouseButton.PRIMARY || !game.powerUpsOn()) {
					// normal shot
					
					registerHit(new Coord(xCoord, yCoord));
				} else {
					if (game.powerUpsOn()) {
						checkAllPrices();
						System.out.println("menu");
						powerupMenu.show(shots, Side.TOP, x, y);
						powerupMenu.setId(xCoord + "" + yCoord);
						powerupMenu.setOnAutoHide(event -> {
						setMouseTransparent(false);
						System.out.println("visible");
					
					});
					}
				}
			}
		}
	}

	private void registerHandler() {
		gc.strokeText("Click on the board above to make a move", width / 4, heightOneBoard + 30);
		if (game.powerUpsOn()) {
			gc.strokeText("Right click to use a powerup", width / 4, heightOneBoard + 50);
		}
		shots.setOnMouseClicked(new MouseListener());
	}
    /**
     * called after a normal shot by user
     * @param coord
     */
	private void registerHit(Coord coord) {
		boolean normalShot = game.shoot(coord);
		// update computer board
		rocketAnimation(coord, false);
		
		play("sounds/whoosh.mp3").setOnEndOfMedia(() -> {
			System.out.println("play whoosh");
			String sound;
			if (game.getComputerBoard().isCoordHit(coord)) {
				hitAnimation(coord, false, true);
				showIfSunk(coord);
				sound = "sounds/hq-explosion-6288.mp3";
			} else {
				hitAnimation(coord, false, false);
				sound = "sounds/Cannonball-Splash-A1-wwwfesliyanstudioscom.mp3";
			}
			// prevent multiple shots at once
			play(sound).setOnEndOfMedia(() -> {
				// if player wins, computer does not move
				// update player board
				if (!game.humanWon()) {
					if (normalShot){
						putComputerShot();
					} else {
						putShots(game.getComputerPowerUp().getNewHits(), true);
					}
				}
					

			});
			//setMouseTransparent(false);
		});

		System.out.println(game.getComputerBoard().toStringShotsOnly());
		System.out.println(game.getPlayerBoard());
		
		checkGameOver();
	}
	/**
	 * putShots plays an airstrike sound and displays the strikes
	 * if the computer is the one shooting, then at the end, the user will be able to click again
	 * if the user just used a powerup, then the computer will play
	 * @param newHits - array of coordinates that have been shot on the board,
	 * but are still waiting to be displayed
	 * @param shootingPlayerBoard - are we shooting the player board? if so this is true
	 */
	private void putShots(ArrayList<Coord> newHits, boolean shootingPlayerBoard) {
		//mediaPlayer.getMarkers().put("display airstrike", Duration.millis(8000));
		//mediaPlayer.setOnMarker();
		play("sounds/airstrike-143106.mp3").setOnEndOfMedia(() -> {
			
			if (shootingPlayerBoard) {
				setMouseTransparent(false);
				System.out.println("visible");
			} else {
				computerTurn();
			}
		});
		mediaPlayer.setOnMarker((MediaMarkerEvent event) -> {
			for (Coord coord: newHits) {
				if (shootingPlayerBoard)
					hitAnimation(coord, shootingPlayerBoard, game.getPlayerBoard().isCoordHit(coord));
				else {
					hitAnimation(coord, shootingPlayerBoard, game.getComputerBoard().isCoordHit(coord));
					showIfSunk(coord);
				}
				
			}
		});
		checkGameOver();
		
		
		
	}

	/**
     * checks if game is over, and then the computer shoots if game is not over
     * only to be used after user powerup shot!!!! game.shoot in registerhit will already get a new computer hit
     * perhaps this could be refactored 
     */
	private void computerTurn() {
		setMouseTransparent(true);
		System.out.println("invisible");
		checkGameOver();
		game.nextTurn();
		if (game.isNotDoneYet()) {
			// if computer shoots normally, we display it, otherwise the powerup shot will cause it to be displayed, w/o rocket
			if (game.computerShoot())
				putComputerShot();
			else {
				putShots(game.getComputerPowerUp().getNewHits(), true);
			}
				
		}
	}

	private void putComputerShot() {
		Coord coordAI = game.getAIShot();
		rocketAnimation(coordAI, true);
		play("sounds/whoosh.mp3").setOnEndOfMedia(() -> {
			System.out.println("play whoosh");
			if (game.getPlayerBoard().isCoordHit(coordAI)) {
				hitAnimation(coordAI, true, true);
				play("sounds/hq-explosion-6288.mp3").setOnEndOfMedia(() -> {
					setMouseTransparent(false);
					System.out.println("visible");
				});
			} else {
				hitAnimation(coordAI, true, false);
				play("sounds/Cannonball-Splash-A1-wwwfesliyanstudioscom.mp3").setOnEndOfMedia(() -> {
					setMouseTransparent(false);
					System.out.println("visible");
				});
			}
			
		});
		checkGameOver();
	}
	

	private void checkGameOver() {
		if (!game.isNotDoneYet()) {
			shots.setDisable(true);
			setMouseTransparent(true);
			int textHeight = heightOneBoard + 50;
			int textWidth = width / 2 - 40;
			if(game.powerUpsOn())
				textWidth = width - 100;
			
			if (game.computerWon()) {
				gc.strokeText("Computer won", textWidth, textHeight);
			} else {
				gc.strokeText("You won!", textWidth, textHeight);
			}
		}
	}
  
	

}
