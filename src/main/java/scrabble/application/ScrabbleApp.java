package scrabble.application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import scrabble.model.game.Board;
import scrabble.model.game.Game;
import scrabble.model.game.Tile;
import scrabble.model.letter.Letter;
import scrabble.model.player.Player;
import scrabble.utilities.Utility;
import scrabble.utilities.Exceptions.InvalidPositionException;
import scrabble.model.game.Direction;

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

        Game game = new Game(strPlayer1);

        Player player1 = game.getPlayer();

        Label lblPlayer1 = new Label("Score " + player1.getName() + " : " + player1.getPoint());
        Label lblTop = new Label("SCRABBLE");

        Board board = game.getBoard();
        GridPane gridPane = createBoardGridPane(board);

        HBox deck = new HBox();
        deck.getChildren().addAll(getDeckPrinting(player1.getLetters()));
        TextField tf_word = new TextField();
        Label err = new Label();
        err.setTextFill(Color.RED);

        Button btn_changeDeck = new Button("Changer le deck");
        btn_changeDeck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                err.setText("");
                game.refillPlayerDeck();
                deck.getChildren().clear();
                deck.getChildren().addAll(getDeckPrinting(player1.getLetters()));
            }
        });
        Button btn_jouer = new Button("Jouer le mot");
        btn_jouer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                err.setText("");
                String wordString = tf_word.getText().toUpperCase();
                List<Letter> word = game.createWord(wordString);
                try {
                    if (game.verifWord(wordString))
                        System.out.println(true);
                    openPositionSelector(secondaryStage, game, word);

                } catch (Exception e) {
                    err.setText(e.getMessage());
                }
            }
        });

        HBox btns = new HBox(btn_changeDeck, btn_jouer);

        VBox vbox = new VBox(lblPlayer1, deck, tf_word, err, btns);

        BorderPane root = new BorderPane();
        root.setTop(lblTop);
        BorderPane.setAlignment(lblTop, Pos.CENTER);
        root.setLeft(vbox);
        root.setCenter(gridPane);
        root.setStyle("-fx-padding: 25px;");

        Scene scene = new Scene(root, 1000, 800);
        secondaryStage.setTitle("Scrabble");
        secondaryStage.setScene(scene);
        secondaryStage.show();
    }

    private void openPositionSelector(Stage primaryStage, Game game, List<Letter> word) {
        Stage stage = new Stage();
        Label title = new Label("Choisisez la direction / position");

        Label lbl_direction = new Label("direction");
        ComboBox<String> cb_direction = new ComboBox<>();
        cb_direction.getItems().addAll("HORIZONTAL", "VERTICAL");
        Label err_direction = new Label();
        err_direction.setTextFill(Color.RED);

        Label lbl_position = new Label("position");
        TextField tf_position = new TextField();
        Label err_position = new Label();
        err_position.setTextFill(Color.RED);

        Label err_general = new Label();
        err_general.setTextFill(Color.RED);

        Button btn_accept = new Button("valider");
        btn_accept.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                err_direction.setText("");
                err_position.setText("");
                err_general.setText("");

                Direction direction = Direction.HORIZONTAL;
                String position;
                int xBack = 0;
                int yBack = 0;

                if (cb_direction.getValue() == "HORIZONTAL")
                    direction = Direction.HORIZONTAL;
                else if (cb_direction.getValue() == "VERTICAL")
                    direction = Direction.VERTICAL;
                else
                    err_direction.setText("Direction vide !");

                position = tf_position.getText().toUpperCase();

                if (position.equals(""))
                    err_position.setText("position vide !");
                else {
                    if (position.length() != 2 && position.length() != 3)
                        err_position.setText("position invalide (XY)");
                    else {
                        char xFront = position.charAt(0);
                        try {
                            int yFront = Integer.parseInt(position.substring(1));

                            try {
                                int[] coords = Utility.frontToBackCoord(xFront, yFront);
                                xBack = coords[0];
                                yBack = coords[1];

                            } catch (InvalidPositionException e) {
                                err_position.setText(e.getMessage());
                            }
                        } catch (Exception e) {
                            err_position.setText("position invalide (XY)");
                        }

                    }
                }
                if (err_direction.getText().equals("") || err_position.getText().equals("")) {
                    try {
                        game.canPlay(word, xBack, yBack, direction);
                    } catch (Exception e) {
                        err_general.setText(e.getMessage());
                    }
                }

            }
        });

        GridPane grid = new GridPane();
        grid.add(lbl_direction, 0, 0);
        grid.add(cb_direction, 0, 1);
        grid.add(err_direction, 0, 2);
        grid.add(lbl_position, 1, 0);
        grid.add(tf_position, 1, 1);
        grid.add(err_position, 1, 2);

        VBox root = new VBox(title, grid, err_general, btn_accept);
        Scene scene = new Scene(root, 500, 200);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("choix de position");
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
    }

    private static final int TILE_SIZE = 40;

    private GridPane createBoardGridPane(Board board) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < Board.getSize(); i++) {
            for (int j = 0; j < Board.getSize(); j++) {
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

    public List<Label> getDeckPrinting(List<Letter> letters) {
        List<Label> deck = new ArrayList<Label>();
        for (Letter letter : letters) {
            deck.add(new Label(Character.toString(letter.getValue())));
        }
        return deck;
    }

    public static void main(String[] args) {
        launch(args);
    }
}