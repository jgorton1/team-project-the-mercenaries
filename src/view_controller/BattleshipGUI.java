package view_controller;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Game;

/**
 * This is the main class, sets all of the views, intitialzes game, etc
 * 
 * @author jgort
 *
 */
public class BattleshipGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private static Game game;
	private static BorderPane window;
	private static SimpleTextView textView;
	private static PlaceShipsView shipsView;
	private GameModeView modeView;
	protected static PlayDrawingView drawView;
	public static final int width = 475;
	public static final int height = 675;
	static Node currentView;
	private static Stage guiStage;

	public void start(Stage stage) {
		guiStage = stage;
		stage.setTitle("Battleship");
		window = new BorderPane();
		Scene scene = new Scene(window, width, height);
		stage.setResizable(false);
		game = new Game();

		textView = new SimpleTextView(game);
		shipsView = new PlaceShipsView(game);
		modeView = new GameModeView(game);
		setViewTo(modeView);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * gets stage so the root can be changed
	 * 
	 * @return stage
	 */
	public static Stage getStage() {
		return guiStage;
	}

	private void setViewTo(Node newView) {
		window.setCenter(null);
		currentView = newView;
		window.setCenter((Node) currentView);
	}

	public static SimpleTextView textView() {
		return textView;
	}

	/**
	 * gets the ships view so the view can be changed to it
	 * 
	 * @return
	 */
	public static PlaceShipsView shipsView() {
		return shipsView;
	}
}
