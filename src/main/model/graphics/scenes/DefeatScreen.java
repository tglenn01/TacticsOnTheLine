package main.model.graphics.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.model.graphics.DefaultScene;
import main.ui.TacticBaseBattle;

public class DefeatScreen extends DefaultScene {

    public DefeatScreen() {
        this.initializeGraphics();
    }

    protected void initializeGraphics() {
        Label tacticOnTheLine = new Label("The line has been drawn, you fall here forgotten with the times \n" +
                "Never to stand again");
        tacticOnTheLine.setAlignment(Pos.CENTER_LEFT);
        Button returnToMainMenuButton = new Button("Accept Fate, and conquer another day");
        returnToMainMenuButton.setAlignment(Pos.CENTER_LEFT);
        returnToMainMenuButton.setOnAction(e -> new TitleScreen());

        VBox layout = new VBox();
        layout.getChildren().addAll(tacticOnTheLine, returnToMainMenuButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(10.00);
        Scene scene = new Scene(layout, FINAL_WIDTH, FINAL_HEIGHT);
        addCSS(scene);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(scene);
    }
}
