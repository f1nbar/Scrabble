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

import static java.lang.Thread.sleep;


public class Main extends Application {
    public static boolean gameGo = true;
    private UIFX ui = new UIFX();
    private Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    private void openingStage(){
        Scene scene = ui.makeIntroScene();
        primaryStage.setTitle("Scrabble");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Goose.png")));
        primaryStage.show();
    }
    private void updateStage(){
        Scene scene = ui.makeStage();
        primaryStage.setTitle("Scrabble");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Goose.png")));
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        this.primaryStage = primaryStage;
        ui.initializePlayers();
        openingStage();
        sleep(2000);

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
