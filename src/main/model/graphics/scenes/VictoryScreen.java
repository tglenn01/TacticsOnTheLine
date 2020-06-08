package main.model.graphics.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.model.graphics.DefaultScene;
import main.ui.TacticBaseBattle;

public class VictoryScreen extends DefaultScene {

    public VictoryScreen() {
        this.initializeGraphics();
    }

    protected void initializeGraphics() {
        Label tacticOnTheLine = new Label("The line has been drawn, you will carry on at least one day more.");
        tacticOnTheLine.setAlignment(Pos.CENTER_LEFT);
        Button returnToScenarioSelect = new Button("Explore your next adventure");
        returnToScenarioSelect.setAlignment(Pos.CENTER_LEFT);
        returnToScenarioSelect.setOnAction(e -> new ScenarioSelectScreen());

        VBox layout = new VBox();
        layout.getChildren().addAll(tacticOnTheLine, returnToScenarioSelect);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(10.00);
        Scene scene = new Scene(layout, FINAL_WIDTH, FINAL_HEIGHT);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(scene);
    }
}
