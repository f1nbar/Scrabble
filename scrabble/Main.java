package scrabble;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{

    public static Scrabble scrabble; // variable holding scrabble game object

    public static void main(String[] args) {
        scrabble = new Scrabble();
        launch(args);
        scrabble.runGame();
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane board = scrabble.getUi().boardDisplay();
        HBox frame = scrabble.getUi().frameDisplay();
        VBox screen = new VBox();
        screen.getChildren().addAll(board, frame);
        primaryStage.setScene(new Scene(screen, 1000, 1000));
        primaryStage.show();
    }
}
