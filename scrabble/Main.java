package scrabble;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public boolean gameGo = true;
    private UIFX ui = new UIFX();
    private Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    private void updateStage(){
        GridPane boardMain = ui.makeBoardDisplay();
        VBox frame = ui.getFrameDisplay();
        VBox screen = new VBox();
        screen.getChildren().addAll(boardMain, frame);
        Scene scene = new Scene(screen, 1000, 1000);
        primaryStage.setTitle("Scrabble");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        updateStage();

        Thread cliCommands = new Thread(() -> {
            while(gameGo) {
                ui.processCLI();
                Platform.runLater(this::updateStage);
            }
        });

        cliCommands.setDaemon(true);
        cliCommands.start();

    }
}
