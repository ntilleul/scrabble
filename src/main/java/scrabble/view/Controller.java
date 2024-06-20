package scrabble.view;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import scrabble.model.board.Board;
import scrabble.model.board.Direction;
import scrabble.model.board.Multiplier;
import scrabble.model.board.Tile;
import scrabble.model.game.Game;
import scrabble.model.player.Player;
import scrabble.model.words.Letter;
import scrabble.model.words.Word;
import scrabble.utilities.Utility;
import scrabble.utilities.exceptions.InvalidPositionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller {
    private static final int TILE_SIZE = 40;

    public void gameStage(String strPlayer1) {
        Stage secondaryStage = new Stage();

        Game game = new Game(strPlayer1);
        Player player1 = game.getPlayer();

        Label lblPlayer1 = new Label("Score " + player1.getName() + " : " + player1.getPoint());
        lblPlayer1.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px;");

        Label lblTop = new Label("SCRABBLE");
        lblTop.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-padding: 20px;");

        Board board = game.getBoard();
        GridPane gridPane = createBoardGridPane(board);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-padding: 10px;");

        HBox deck = new HBox();
        deck.getChildren().addAll(getDeckPrinting(player1.getLetters()));
        deck.setSpacing(5);

        TextField tf_word = new TextField();
        tf_word.setMaxWidth(200);
        Label err = new Label();
        err.setTextFill(Color.RED);

        Button btn_changeDeck = new Button("Changer le deck");
        btn_changeDeck.setOnMouseClicked(event -> {
            err.setText("");
            game.refillPlayerDeck();
            deck.getChildren().clear();
            deck.getChildren().addAll(getDeckPrinting(player1.getLetters()));
        });
        Button btn_jouer = new Button("Jouer le mot");
        btn_jouer.setOnMouseClicked(event -> {
            err.setText("");
            String wordString = tf_word.getText().toUpperCase();
            try {
                game.verifyWord(wordString);
                Word word;
                int nJoker = game.nJokerInWord(wordString);
                if (nJoker > 0) {
                    List<Character> chars = askLetterJoker(secondaryStage, nJoker);
                    if (chars.isEmpty()) {
                        err.setText("Choix de lettre pour joker annulé.");
                        return;
                    }
                    word = new Word(wordString, chars);
                } else
                    word = new Word(wordString);
                List<Object> position = openPositionSelector(secondaryStage, game, word);
                if (position.isEmpty()) {
                    err.setText("Sélection de position annulée.");
                    return;
                }
                Direction direction = (Direction) position.get(0);
                int xBack = (int) position.get(1);
                int yBack = (int) position.get(2);
                player1.getRid(word.getLetters());
                board.placeWord(word, direction, xBack, yBack);
                refreshBoardDisplay(board, gridPane);
                game.incrementWordCount();
                player1.addPoint(game.countPoints(player1, xBack, yBack, direction));
                updatePlayerScore(player1, lblPlayer1);

                game.makePlayerDraw(player1, word.getSize());
                deck.getChildren().clear();
                deck.getChildren().addAll(getDeckPrinting(player1.getLetters()));
                tf_word.clear();
                if (game.verifyWin(game)) {
                    secondaryStage.close();
                    endGameStage(player1.getName(), player1.getPoint());
                }
            } catch (Exception e) {
                err.setText(e.getMessage());
            }
        });
        HBox btns = new HBox(10, btn_changeDeck, btn_jouer);
        VBox.setMargin(btns, new Insets(0, 20, 20, 20));

        VBox vbox = new VBox(lblPlayer1, deck, tf_word, err, btns);
        VBox.setMargin(deck, new Insets(0, 0, 20, 20));
        VBox.setMargin(err, new Insets(0, 0, 0, 20));
        VBox.setMargin(tf_word, new Insets(0, 0, 0, 20));

        BorderPane root = new BorderPane();
        HBox hbox = createTopCoordinatesHBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setTranslateX(20);
        hbox.setTranslateY(20);
        root.setTop(hbox);
        VBox vboxLeft = createLeftCoordinatesVBox();
        vboxLeft.setAlignment(Pos.CENTER_RIGHT);
        vboxLeft.setTranslateX(170);
        root.setLeft(vboxLeft);
        root.setCenter(gridPane);
        BorderPane.setAlignment(lblTop, Pos.CENTER);
        root.setBottom(vbox);
        root.setCenter(gridPane);

        Scene scene = new Scene(root, 1000, 900);
        root.setStyle("-fx-background-color: lightgrey; -fx-alignment: center");
        secondaryStage.setTitle("Scrabble");
        secondaryStage.setScene(scene);
        secondaryStage.setResizable(false);
        secondaryStage.setAlwaysOnTop(true);
        secondaryStage.show();
    }
    public void endGameStage(String playerName, int playerScore) {
        Stage endGameStage = new Stage();
        Label lblEndGame = new Label("Fin de la partie\n" + playerName + ": " + playerScore + " points");
        lblEndGame.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button btnEndGame = new Button("Quitter");
        btnEndGame.setOnMouseClicked(event -> endGameStage.close());

        VBox endGameLayout = new VBox(20, lblEndGame, btnEndGame);
        endGameLayout.setAlignment(Pos.CENTER);
        endGameLayout.setStyle("-fx-padding: 25px;");

        Scene scene = new Scene(endGameLayout, 400, 300);
        endGameStage.setTitle("Fin de la partie");
        endGameStage.setScene(scene);
        endGameStage.show();
    }

    private HBox createTopCoordinatesHBox() {
        HBox topCoordinatesHBox = new HBox();
        char columnChar = 'A';
        for (int i = 0; i < Board.getSize(); i++) {
            Label coordinateLabel = new Label(String.valueOf(columnChar));
            coordinateLabel.setMinSize(TILE_SIZE, TILE_SIZE);
            coordinateLabel.setMaxSize(TILE_SIZE, TILE_SIZE);
            coordinateLabel.setStyle("-fx-border-color: transparent; -fx-alignment: center; -fx-padding: 5px;");
            topCoordinatesHBox.getChildren().add(coordinateLabel);
            columnChar++;
        }
        return topCoordinatesHBox;
    }
    private VBox createLeftCoordinatesVBox() {
        VBox leftCoordinatesVBox = new VBox();
        for (int i = 1; i <= Board.getSize(); i++) {
            Label coordinateLabel = new Label(String.valueOf(i));
            coordinateLabel.setMinSize(TILE_SIZE, TILE_SIZE);
            coordinateLabel.setMaxSize(TILE_SIZE, TILE_SIZE);
            coordinateLabel.setStyle("-fx-border-color: transparent; -fx-alignment: center; -fx-padding: 5px;");
            leftCoordinatesVBox.getChildren().add(coordinateLabel);
        }
        return leftCoordinatesVBox;
    }

    // TODO: possiblement refaire la création du board
    private GridPane createBoardGridPane(Board board) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < Board.getSize(); i++) {
            for (int j = 0; j < Board.getSize(); j++) {
                Tile tile = board.getTile(i, j);
                Label label = new Label(tile.toString());
                label.setMinSize(TILE_SIZE, TILE_SIZE);
                label.setMaxSize(TILE_SIZE, TILE_SIZE);
                label.setStyle(
                        "-fx-border-color: black; -fx-alignment: center; -fx-font-size: 16; -fx-background-color: "
                                + tile.getMultiplier().getColor());
                if (tile.getMultiplier().equals(Multiplier.LETTER_3) || tile.getMultiplier().equals(Multiplier.WORD_3))
                    label.setTextFill(Color.WHITE);
                gridPane.add(label, i + 1, j + 1);
            }
        }
        return gridPane;
    }
    private void refreshBoardDisplay(Board board, GridPane gridPane) {
        for (int i = 0; i < Board.getSize(); i++) {
            for (int j = 0; j < Board.getSize(); j++) {
                Tile tile = board.getTile(i, j);
                Label label = (Label) gridPane.getChildren().get(i * Board.getSize() + j);
                label.setText(tile.toString());
            }
        }
    }

    public List<Label> getDeckPrinting(List<Letter> letters) {
        List<Label> deck = new ArrayList<>();
        for (Letter letter : letters) {
            Label letterInDeck = new Label(Character.toString(letter.getValue()));
            letterInDeck.setMinSize(25, 35);
            letterInDeck.setMaxSize(25, 35);
            letterInDeck.setStyle(
                    "-fx-border-color: black; -fx-alignment: center; -fx-font-size: 20px; -fx-background-color:white;");
            deck.add(letterInDeck);
        }
        return deck;
    }
    private List<Character> askLetterJoker(Stage primaryStage, int nJoker) {
        List<Character> chars = new ArrayList<>();
        Stage stage = new Stage();
        Label title = new Label("Par quel lettre voulez-vous remplacer le joker ?");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        TextField textField = new TextField();
        textField.setStyle("-fx-font-size: 14px; -fx-pref-width: 200px;");
        Button btn_valider = new Button("Valider");
        Button btn_annuler = new Button("Annuler");
        Label err = new Label();
        err.setTextFill(Color.RED);

        VBox vbox = new VBox(title, textField, err, btn_valider, btn_annuler);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: lightgrey; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        Scene scene = new Scene(vbox, 400, 200);

        btn_valider.setOnMouseClicked((EventHandler<Event>) event -> {
            String caracString = textField.getText().toUpperCase();
            err.setText("");
            if (caracString.length() != nJoker) {
                err.setText("Veuillez rentrer " + nJoker + " caractères.");
            } else {
                int i = 0;
                while (i < caracString.length()) {
                    if (!(caracString.charAt(i) >= 'A' && caracString.charAt(i) <= 'Z')) {
                        err.setText("Veuillez ne rentrer que les caractères alphabetique");
                        break;
                    } else {
                        chars.add(caracString.charAt(i));
                        i++;
                    }
                }
            }
            if (err.getText().isEmpty())
                stage.close();
        });

        btn_annuler.setOnMouseClicked(event -> {
            chars.clear(); // Clear the list to indicate cancellation
            stage.close();
        });

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.setTitle("choix du joker");
        stage.showAndWait();
        return chars;

    }
    public void updatePlayerScore(Player player, Label lblPlayer) {
        lblPlayer.setText("Score " + player.getName() + " : " + player.getPoint());
    }

    private List<Object> openPositionSelector(Stage primaryStage, Game game, Word word) {
        List<Object> pos = new ArrayList<>();

        Stage stage = new Stage();
        Label title = new Label("Choisisez la direction / position");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label lbl_direction = new Label("direction");
        ComboBox<String> cb_direction = new ComboBox<>();
        cb_direction.getItems().addAll("HORIZONTAL", "VERTICAL");
        cb_direction.getSelectionModel().selectFirst();
        Label err_direction = new Label();
        err_direction.setTextFill(Color.RED);

        Label lbl_position = new Label("position");
        TextField tf_position = new TextField();
        Label err_position = new Label();
        err_position.setTextFill(Color.RED);

        Label err_general = new Label();
        err_general.setTextFill(Color.RED);

        Button btn_accept = new Button("valider");
        btn_accept.setOnMouseClicked((EventHandler<Event>) event -> {
            err_direction.setText("");
            err_position.setText("");
            err_general.setText("");

            Direction direction = Direction.HORIZONTAL;
            String position;
            int xBack = 0;
            int yBack = 0;

            if (Objects.equals(cb_direction.getValue(), "HORIZONTAL"))
                direction = Direction.HORIZONTAL;
            else if (Objects.equals(cb_direction.getValue(), "VERTICAL"))
                direction = Direction.VERTICAL;
            else
                err_direction.setText("Direction vide !");

            position = tf_position.getText().toUpperCase();

            if (position.isEmpty())
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
            if (err_direction.getText().isEmpty() || err_position.getText().isEmpty()) {
                try {
                    game.canPlay(word, xBack, yBack, direction);
                    pos.add(direction);
                    pos.add(xBack);
                    pos.add(yBack);
                    stage.close();
                } catch (Exception e) {
                    err_general.setText(e.getMessage());
                }

            }
        });
        return pos;
    }



}