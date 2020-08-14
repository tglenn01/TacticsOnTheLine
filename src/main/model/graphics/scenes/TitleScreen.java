package main.model.graphics.scenes;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.model.graphics.DefaultScene;
import main.ui.TacticBaseBattle;

public class TitleScreen extends DefaultScene {

    public TitleScreen() {
        this.initializeGraphics();
    }

    protected void initializeGraphics() {
        Pane titlePane = new Pane();
        Label tacticOnTheLine = new Label("Tactics On The Line");
        tacticOnTheLine.setId("tacticsOnTheLineLabel");
        tacticOnTheLine.setAlignment(Pos.CENTER_LEFT);
        Label chooseCharacterButton = new Label("Start New Adventure");
        chooseCharacterButton.setId("mainMenuElement");
        chooseCharacterButton.setAlignment(Pos.CENTER_LEFT);
        chooseCharacterButton.setOnMouseClicked(e -> fadeToCharacterSelect(titlePane));

        Label closeButton = new Label("Exit");
        closeButton.setId("mainMenuElement");
        closeButton.setOnMouseClicked(e -> TacticBaseBattle.getInstance().getPrimaryStage().close());

        VBox layout = new VBox();
        layout.getChildren().addAll(tacticOnTheLine, chooseCharacterButton, closeButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(10.00);
        titlePane.getChildren().add(layout);
        titlePane.setId("defaultBackground");

        mainPane.getChildren().add(titlePane);
        animateBackground(mainScene, mainPane);
    }

    private void fadeToCharacterSelect(Pane fadeOutPane) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1));
        fade.setNode(fadeOutPane);
        fade.setFromValue(1.00);
        fade.setToValue(0.50);
        fade.setOnFinished(e -> {
            mainPane.getChildren().remove(fadeOutPane);
            new CharacterSelect();
        });
    }
}
