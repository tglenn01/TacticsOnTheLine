package main.model.graphics.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.model.graphics.DefaultScene;
import main.ui.TacticBaseBattle;

public class TitleScreen extends DefaultScene {

    public TitleScreen() {
        this.initializeGraphics();
    }

    protected void initializeGraphics() {
        Label tacticOnTheLine = new Label("Tactics On The Line");
        tacticOnTheLine.setId("tacticsOnTheLineLabel");
        tacticOnTheLine.setAlignment(Pos.CENTER_LEFT);
        Label chooseCharacterButton = new Label("Start New Adventure");
        chooseCharacterButton.setId("mainMenuElement");
        chooseCharacterButton.setAlignment(Pos.CENTER_LEFT);
        chooseCharacterButton.setOnMouseClicked(e -> TacticBaseBattle.getInstance().characterSelect());

        Label closeButton = new Label("Exit");
        closeButton.setId("mainMenuElement");
        closeButton.setOnMouseClicked(e -> TacticBaseBattle.getInstance().getPrimaryStage().close());

        VBox layout = new VBox();
        layout.setId("background");
        layout.getChildren().addAll(tacticOnTheLine, chooseCharacterButton, closeButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(10.00);
        Scene scene = new Scene(layout, FINAL_WIDTH, FINAL_HEIGHT);
        addCSS(scene);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(scene);
    }
}
