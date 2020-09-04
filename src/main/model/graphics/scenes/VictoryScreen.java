package main.model.graphics.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.model.graphics.DefaultScene;
import main.persistance.WriteSaveData;
import main.model.battleSystem.TacticBaseBattle;

import java.io.IOException;

import static main.ui.Main.SAVE_DATA_FILE;

public class VictoryScreen extends DefaultScene {

    public VictoryScreen() {
        this.initializeGraphics();
    }

    protected void initializeGraphics() {
        Label outcomeLabel = new Label("The line has been drawn, you will carry on at least one day more.");
        outcomeLabel.setWrapText(true);
        outcomeLabel.setId("headerLabel");
        outcomeLabel.setAlignment(Pos.CENTER_LEFT);
        Label returnToScenarioSelect = new Label("Explore your next adventure");
        returnToScenarioSelect.setId("mainMenuElement");
        returnToScenarioSelect.setAlignment(Pos.CENTER_LEFT);
        returnToScenarioSelect.setOnMouseClicked(e -> new ScenarioSelectScreen());
        TacticBaseBattle.getInstance().getBattle().getActiveScenario().setCompleted(true);
        Label saveDataButton = new Label("Save Characters and Levels for your next adventure");
        saveDataButton.setId("mainMenuElement");
        saveDataButton.setOnMouseClicked(e -> {
            try {
                new WriteSaveData(SAVE_DATA_FILE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox();
        layout.getChildren().addAll(outcomeLabel, returnToScenarioSelect, saveDataButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(10.00);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(mainScene);
        mainPane.getChildren().add(layout);
        animateBackground(mainScene, mainPane);
    }
}
