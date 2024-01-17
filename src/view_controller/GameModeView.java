package view_controller;

import java.io.File;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Game;

/**
 * Start view of the game, user can choose difficulty and powerups, and is then
 * redirected to placeshipsview
 * 
 * @author jgort
 *
 */
public class GameModeView extends BorderPane {
	Game game;

	/**
	 * gamemodeview allows modification of the game, so it is in the constructor
	 * 
	 * @param game
	 */
	public GameModeView(Game game) {
		this.game = game;
		initializePanel();
	}

	private void initializePanel() {
		// TODO Auto-generated method stub
		Image image = new Image(new File("SeparateSameAmurratsnake-size_restricted.gif").toURI().toString(), 0, 675,
				false, false);
		BackgroundImage imageBackground = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		this.setBackground(new Background(imageBackground));
		CheckBox powerUps = new CheckBox("Power ups?");
		powerUps.setTextFill(Color.WHITE);
		powerUps.setOnAction(event -> {
			game.setPowerUps(((CheckBox) event.getSource()).isSelected());

		});
		Button easy = new Button("Easy");
		easy.setOnAction(event -> {
			game.setStrategyEasy();
			BattleshipGUI.getStage().getScene().setRoot(BattleshipGUI.shipsView());
		});
		Button hard = new Button("Hard");
		hard.setOnAction(event -> {
			game.setStrategyHard();
			BattleshipGUI.getStage().getScene().setRoot(BattleshipGUI.shipsView());

		});
		// intermediate
		HBox buttons = new HBox();
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(20);
		buttons.getChildren().addAll(powerUps, easy, hard);
		this.setBottom(buttons);
		this.setPadding(new Insets(40));

		Text battleship = new Text("BATTLESHIP");
		battleship.setFont(new Font("Impact", 6));
		this.setCenter(battleship);
		// Canvas canvas = new Canvas (300, 300);
		/// this.setCenter(canvas);
		ScaleTransition st = new ScaleTransition(Duration.millis(2000), battleship);
		st.setByX(10f);
		st.setByY(10f);
		st.setCycleCount(1);
		st.setAutoReverse(false);

		st.play();

	}
}
