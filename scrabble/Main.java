package scrabble;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static java.lang.Thread.sleep;


public class Main extends Application {
    public static boolean gameGo = true;
    private UI ui = new UI();
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    private void openingStage() {
        Scene scene = ui.makeIntroScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateStage() {
        Scene scene = ui.makeStage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Scrabble");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Goose.png")));
        ui.initializePlayers();
        openingStage();
        sleep(2000);

        Thread cliCommands = new Thread(() -> {
            while (gameGo) {
                Platform.runLater(this::updateStage);
                ui.processCLI();
            }
        });

        cliCommands.setDaemon(true);
        cliCommands.start();
    }
}
