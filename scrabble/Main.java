package scrabble;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
        StackPane indicator = ui.makePlayerTurnIndicator();
        indicator.setAlignment(Pos.CENTER);

        HBox score = new HBox(120);
        score.getChildren().addAll(indicator, ui.makeScoreDisplay());
        score.setAlignment(Pos.CENTER);

        GridPane boardMain = ui.makeBoardDisplay();
        boardMain.setAlignment(Pos.CENTER);

        VBox frame = ui.makeFrameDisplay();
        frame.setAlignment(Pos.CENTER);

        HBox middle = new HBox();
        middle.getChildren().addAll(boardMain, frame);
        middle.setAlignment(Pos.CENTER_RIGHT);

        VBox screen = new VBox();
        screen.setAlignment(Pos.CENTER);
        screen.setPadding(new Insets(20, 20, 20, 0));
        screen.getChildren().addAll(score,middle);

        Scene scene = new Scene(screen, 750, 620, Color.ANTIQUEWHITE);

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
