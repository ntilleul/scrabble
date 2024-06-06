package scrabble.application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import scrabble.model.game.Board;
import scrabble.model.game.Game;
import scrabble.model.game.Tile;
import scrabble.model.player.Player;


public class ScrabbleApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label titleLabel = new Label("SCRABBLE");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label lblPlayer1 = new Label("Joueur 1: ");
        TextField tfPlayer1 = new TextField();

        Label lblPlayer2 = new Label("Joueur 2: ");
        TextField tfPlayer2 = new TextField();


        HBox player1Box = new HBox(10, lblPlayer1, tfPlayer1);
        HBox player2Box = new HBox(10, lblPlayer2, tfPlayer2);


        Button valideButton = new Button("VALIDER");
        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);

        valideButton.setOnAction(event -> {
            String strPlayer1 = tfPlayer1.getText();
            String strPlayer2 = tfPlayer2.getText();

            if (strPlayer1.isEmpty() || strPlayer2.isEmpty()) {
                messageLabel.setText("Veuillez remplir les champs pour les deux joueurs.");
            } else {
                messageLabel.setText("");
                primaryStage.close();
                openSecondaryStage(strPlayer1, strPlayer2);
            }
        });

        VBox mainLayout = new VBox(20, titleLabel, player1Box, player2Box, valideButton, messageLabel);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-padding: 25px;");

        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setTitle("Scrabble");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openSecondaryStage(String strPlayer1, String strPlayer2) {
        Stage secondaryStage = new Stage();

        Game game = new Game();

        Player player1 = game.getPlayer();

        Label lblPlayer1 = new Label("Score joueur1: " + player1.getPoint());
        Label lblTop = new Label("SCRABBLE");

        Board board = game.getBoard();
        GridPane gridPane = createBoardGridPane(board);

        BorderPane root = new BorderPane();
        root.setTop(lblTop);
        root.setAlignment(lblTop, Pos.CENTER);
        root.setLeft(lblPlayer1);
        root.setCenter(gridPane);
        root.setStyle("-fx-padding: 25px;");

        Scene scene = new Scene(root, 1000, 800);
        secondaryStage.setTitle("Scrabble");
        secondaryStage.setScene(scene);
        secondaryStage.show();
    }

    private static final int TILE_SIZE = 40;

    private GridPane createBoardGridPane(Board board) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Tile tile = board.getTile(i, j);
                Label label = new Label(tile.toString());
                label.setMinSize(TILE_SIZE, TILE_SIZE);
                label.setMaxSize(TILE_SIZE, TILE_SIZE);
                label.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-font-size: 16;");

                gridPane.add(label, j, i);
            }
        }
        return gridPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}