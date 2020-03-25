package scrabble;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
    public static boolean gameGo = true;
    private UIFX ui = new UIFX();
    private Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    private void updateStage(){
        HBox score = ui.makeScoreDisplay();
        score.setAlignment(Pos.CENTER);
        GridPane boardMain = ui.makeBoardDisplay();
        VBox frame = ui.makeFrameDisplay();
        frame.setAlignment(Pos.CENTER);
        VBox screen = new VBox();
        screen.setAlignment(Pos.CENTER);
        screen.setPadding(new Insets(20, 20, 20, 0));
        screen.getChildren().addAll(score, boardMain, frame);
        Scene scene = new Scene(screen, 700, 730, Color.ANTIQUEWHITE);
        primaryStage.setTitle("Scrabble");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Goose.png")));
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        ui.initializePlayers();

        Thread cliCommands = new Thread(() -> {
            while(gameGo) {
                Platform.runLater(this::updateStage);
                ui.processCLI();
            }
        });

        cliCommands.setDaemon(true);
        cliCommands.start();

    }
}
