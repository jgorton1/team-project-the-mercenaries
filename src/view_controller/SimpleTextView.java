package view_controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.Game;

public class SimpleTextView extends BorderPane {

	private Game game;
	private TextField xCoord;
	private TextField yCoord;
	private Button button;
	private TextArea computerBoard;
	private TextArea humanBoard;

	public SimpleTextView(Game theModel) {
		game = theModel;
		initializePanel();
	}

	private void initializePanel() {
		GridPane outerPane = new GridPane();
		outerPane.setVgap(15);
		outerPane.setHgap(15);
		outerPane.setPadding(new Insets(0, 20, 10, 45));

		// Declare components
		GridPane inputs = new GridPane();
		inputs.setVgap(10);
		inputs.setHgap(10);
		Label humanLabel = new Label("Player board:");
		Label computerLabel = new Label("Computer board:");
		humanBoard = new TextArea();
		computerBoard = new TextArea();
		Label guide = new Label("Enter x and y coordinates below to fire a shot");
		Label xLabel = new Label("x-coordinate:");
		Label yLabel = new Label("y-coordinate:");
		xCoord = new TextField();
		yCoord = new TextField();
		button = new Button("Fire");

		// Lay them out
		inputs.add(xLabel, 1, 1);
		inputs.add(xCoord, 2, 1);
		inputs.add(yLabel, 1, 2);
		inputs.add(yCoord, 2, 2);
		inputs.add(button, 3, 2);
		outerPane.add(computerLabel, 1, 1);
		outerPane.add(computerBoard, 1, 2);
		outerPane.add(guide, 1, 3);
		outerPane.add(inputs, 1, 4);
		outerPane.add(humanLabel, 1, 5);
		outerPane.add(humanBoard, 1, 6);

		// Fill TextAreas
		Font font = new Font("Courier New", 22);
		computerBoard.setText(game.getComputerBoard().toStringShotsOnly());
		computerBoard.setEditable(false);
		computerBoard.setMouseTransparent(true);
		computerBoard.setStyle("-fx-border-color: black; border-width: 10;");
		computerBoard.setMaxWidth(300);
		computerBoard.setFont(font);
		humanBoard.setText(game.getPlayerBoard().toString());
		humanBoard.setEditable(false);
		humanBoard.setMouseTransparent(true);
		humanBoard.setStyle("-fx-border-color: black; border-width: 10;");
		humanBoard.setFont(font);
		humanBoard.setMaxWidth(300);

		// Button action
		button.setOnAction(new makeMove());

		setCenter(outerPane);
	}

	private class makeMove implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			System.out.println(game.getPlayerBoard().toString());
			String xString = xCoord.getText().trim();
			String yString = yCoord.getText().trim();

			if (!isValid(xString, yString))
				return;
			int x = Integer.parseInt(xString);
			int y = Integer.parseInt(yString);

			button.setText("Fire!");
			game.shoot(x, y);
			System.out.println(game.getComputerBoard().toStringShotsOnly());
			computerBoard.setText(game.getComputerBoard().toStringShotsOnly());
			humanBoard.setText(game.getPlayerBoard().toString());
			xCoord.setText("");
			yCoord.setText("");
			// if game is over, prevent new shots
			if (!game.isNotDoneYet()) {
				button.setMouseTransparent(true);
				if (game.computerWon()) {
					button.setText("Computer won");
				} else {
					button.setText("You won");
				}

			}
		}
	}

	private boolean isValid(String xString, String yString) {
		// Check if numeric
		if (!xString.matches("-?\\d+(\\.\\d+)?") || !yString.matches("-?\\d+(\\.\\d+)?")) {
			button.setText("Invalid choice");
			return false;
		}
		// Check if within bounds
		int x = Integer.parseInt(xString);
		int y = Integer.parseInt(yString);
		if (x >= 7 || y >= 7) {
			button.setText("Invalid choice");
			return false;
		}
		return true;
	}
}
