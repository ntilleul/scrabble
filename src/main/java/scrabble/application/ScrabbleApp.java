package scrabble.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;


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
            String player1 = tfPlayer1.getText();
            String player2 = tfPlayer2.getText();

            if (player1.isEmpty() || player2.isEmpty()) {
                messageLabel.setText("Veuillez remplir les champs pour les deux joueurs.");
            } else {
                messageLabel.setText("");
                //TODO: afficher le jeu Scrabble
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

    //todo: methode pour afficher Scrabble

    public static void main(String[] args) {
        launch(args);
    }
}
