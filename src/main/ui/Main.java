package main.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import main.model.graphics.scenes.MainMenu;

import static main.model.graphics.DefaultScene.FINAL_HEIGHT;
import static main.model.graphics.DefaultScene.FINAL_WIDTH;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TacticBaseBattle.getInstance().setPrimaryStage(primaryStage);
        primaryStage.setTitle("Tactics On The Line");
        primaryStage.setMinWidth(FINAL_WIDTH);
        primaryStage.setMaxWidth(FINAL_WIDTH);
        primaryStage.setMaxHeight(FINAL_HEIGHT);
        primaryStage.setMinHeight(FINAL_HEIGHT);
        new MainMenu();
        primaryStage.show();
    }
}
