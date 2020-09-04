package main.ui;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.model.battleSystem.TacticBaseBattle;
import main.model.graphics.scenes.TitleScreen;
import main.persistance.ReadSaveData;

import java.io.FileInputStream;

import static main.model.graphics.DefaultScene.FINAL_HEIGHT;
import static main.model.graphics.DefaultScene.FINAL_WIDTH;

public class Main extends Application {
    public static final String SAVE_DATA_FILE = "./data/saveData.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TacticBaseBattle.getInstance().setPrimaryStage(primaryStage);
        loadSaveData();



        primaryStage.setTitle("Tactics On The Line");
        primaryStage.setMinWidth(FINAL_WIDTH);
        primaryStage.setMinHeight(FINAL_HEIGHT);

        Font.loadFont(new FileInputStream("src/resources/fonts/tube-of-corn/TubeOfCorn.ttf"), 20);

        new TitleScreen();
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    private void loadSaveData() {
        try {
            new ReadSaveData(SAVE_DATA_FILE);
        } catch (Exception e) {
            //
        }
    }
}
