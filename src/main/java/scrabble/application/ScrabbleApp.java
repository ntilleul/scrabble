package scrabble.application;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import scrabble.model.game.*;
import scrabble.model.letter.Letter;
import scrabble.model.letter.Word;
import scrabble.model.player.Player;
import scrabble.utilities.Exceptions.InvalidPositionException;
import scrabble.utilities.Utility;

import java.util.ArrayList;
import java.util.List;

public class ScrabbleApp extends Application {

    @Override
    public void start(Stage setPlayerStage) {

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
                setPlayerStage.close();
                gameStage(strPlayer1, strPlayer2);
            }
        });

        VBox mainLayout = new VBox(20, titleLabel, player1Box, player2Box, valideButton, messageLabel);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-padding: 25px;");

        Scene scene = new Scene(mainLayout, 400, 300);
        setPlayerStage.setTitle("Sélection des joueurs");
        setPlayerStage.setScene(scene);
        setPlayerStage.show();
    }

    private void gameStage(String strPlayer1, String strPlayer2) {
        Stage secondaryStage = new Stage();

        Game game = new Game(strPlayer1);

        Player player1 = game.getPlayer();
        List<Letter> ls = new ArrayList<>();
        player1.draw(ls);

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
                try {
                    game.verifWord(wordString);
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
                    List<Object> pos = openPositionSelector(secondaryStage, game, word.getLetters());
                    if (pos.isEmpty()) {
                        err.setText("Sélection de position annulée.");
                        return;
                    }
                    Direction direction = (Direction) pos.get(0);
                    int xBack = (int) pos.get(1);
                    int yBack = (int) pos.get(2);
                    player1.getRid(word.getLetters());
                    board.placeWord(word, direction, yBack, xBack);
                    refreshBoardDisplay(board, gridPane);
                    game.incrementWordCount();
                    // TODO : ajouter points au bon joueur par la suite
                    game.countPoints(word.getLetters(), player1);
                    updatePlayerScore(player1, lblPlayer1);

                    game.makerPlayerDraw(player1, word.size());
                    deck.getChildren().clear();
                    deck.getChildren().addAll(getDeckPrinting(player1.getLetters()));
                    tf_word.clear();
                    if (game.verifWin(game)) {
                        secondaryStage.close();
                        endGameStage(player1.getName(), player1.getPoint());
                    }
                } catch (Exception e) {
                    err.setText(e.getMessage());
                }
            }
        });
        HBox btns = new HBox(10, btn_changeDeck, btn_jouer);
        VBox.setMargin(btns, new Insets(0, 20, 20, 20));

        VBox vbox = new VBox(lblPlayer1, deck, tf_word, err, btns);
        VBox.setMargin(deck, new Insets(0, 0, 20, 20));
        VBox.setMargin(err, new Insets(0, 0, 0, 20));
        VBox.setMargin(tf_word, new Insets(0, 0, 0, 20));

        BorderPane root = new BorderPane();
        root.setTop(lblTop);
        BorderPane.setAlignment(lblTop, Pos.CENTER);
        root.setBottom(vbox);
        root.setCenter(gridPane);

        Scene scene = new Scene(root, 1000, 900);
        root.setStyle("-fx-background-color: lightgrey;");
        secondaryStage.setTitle("Scrabble");
        secondaryStage.setScene(scene);
        secondaryStage.setResizable(true);
        secondaryStage.setAlwaysOnTop(true);
        secondaryStage.show();
    }

    private List<Object> openPositionSelector(Stage primaryStage, Game game, List<Letter> word) {
        List<Object> pos = new ArrayList<Object>();

        Stage stage = new Stage();
        Label title = new Label("Choisisez la direction / position");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

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
                        pos.add(direction);
                        pos.add(xBack);
                        pos.add(yBack);
                        stage.close();
                    } catch (Exception e) {
                        err_general.setText(e.getMessage());
                    }
                }
            }
        });

        Button btn_cancel = new Button("Annuler");
        btn_cancel.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                pos.clear();
                stage.close();
            }
        });

        GridPane grid = new GridPane();
        grid.add(lbl_direction, 0, 0);
        grid.add(cb_direction, 0, 1);
        grid.add(err_direction, 0, 2);
        grid.add(lbl_position, 1, 0);
        grid.add(tf_position, 1, 1);
        grid.add(err_position, 1, 2);

        grid.setHgap(10);
        grid.setPadding(new Insets(0, 0, 0, 50));

        HBox buttonBox = new HBox(10, btn_accept, btn_cancel);
        buttonBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, title, grid, err_general, buttonBox);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 200);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("choix de position");
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
        return pos;
    }

    private static final int TILE_SIZE = 40;

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
                gridPane.add(label, j + 1, i + 1);
            }
        }
        return gridPane;
    }

    private List<Character> askLetterJoker(Stage primaryStage, int nJoker) {
        List<Character> chars = new ArrayList<Character>();
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

        btn_valider.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                String caracString = textField.getText().toUpperCase();
                err.setText("");
                if (caracString.length() != nJoker) {
                    err.setText("Veuillez rentrer " + Integer.toString(nJoker) + " caractères.");
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
                if (err.getText().equals(""))
                    stage.close();
            }
        });

        btn_annuler.setOnMouseClicked(event -> {
            chars.clear();  // Clear the list to indicate cancellation
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

    public List<Label> getDeckPrinting(List<Letter> letters) {
        List<Label> deck = new ArrayList<Label>();
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

    private void refreshBoardDisplay(Board board, GridPane gridPane) {
        System.out.println(true);
        for (int i = 0; i < Board.getSize(); i++) {
            for (int j = 0; j < Board.getSize(); j++) {
                Tile tile = board.getTile(i, j);
                Label label = (Label) gridPane.getChildren().get(i * Board.getSize() + j);
                label.setText(tile.toString());
            }
        }
    }

    public void endGameStage(String playerName, int playerScore) {
        Stage endGameStage = new Stage();
        Label lblEndGame = new Label("Fin de la partie\n" + playerName + ": " + playerScore + " points");
        lblEndGame.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button btnEndGame = new Button("Quitter");
        btnEndGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                endGameStage.close();
            }
        });

        VBox endGameLayout = new VBox(20, lblEndGame, btnEndGame);
        endGameLayout.setAlignment(Pos.CENTER);
        endGameLayout.setStyle("-fx-padding: 25px;");

        Scene scene = new Scene(endGameLayout, 400, 300);
        endGameStage.setTitle("Fin de la partie");
        endGameStage.setScene(scene);
        endGameStage.show();
    }

    public void updatePlayerScore(Player player, Label lblPlayer) {
        lblPlayer.setText("Score " + player.getName() + " : " + player.getPoint());
    }

    public static void main(String[] args) {
        launch(args);
    }
}