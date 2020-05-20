package main.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import main.model.graphics.scenes.MainMenu;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TacticBaseBattle.getInstance().setPrimaryStage(primaryStage);
        primaryStage.setTitle("Tactics On The Line");
        new MainMenu();
        primaryStage.show();
    }
}
