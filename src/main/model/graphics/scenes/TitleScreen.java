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
        //Pane titlePane = new Pane();

        Label tacticOnTheLine = new Label("Tactics On The Line");
        tacticOnTheLine.setId("headerLabel");
        tacticOnTheLine.setAlignment(Pos.CENTER_LEFT);

        Label chooseCharacterButton = new Label("Start New Adventure");
        chooseCharacterButton.setId("mainMenuElement");
        chooseCharacterButton.setAlignment(Pos.CENTER_LEFT);
        chooseCharacterButton.setOnMouseClicked(e -> new CharacterSelect());

        Label closeButton = new Label("Exit");
        closeButton.setId("mainMenuElement");
        closeButton.setOnMouseClicked(e -> TacticBaseBattle.getInstance().getPrimaryStage().close());

        VBox layout = new VBox();
        layout.getChildren().addAll(tacticOnTheLine, chooseCharacterButton, closeButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(10.00);
        //titlePane.getChildren().add(layout);

        mainPane.getChildren().add(layout);
        animateBackground(mainScene, mainPane);

    }

    private void fadeToCharacterSelect(Pane fadeOutPane) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1));
        fadeOut.setNode(fadeOutPane);
        fadeOut.setFromValue(1.00);
        fadeOut.setToValue(0.00);
        fadeOut.setOnFinished(e -> {
            mainPane.getChildren().remove(fadeOutPane);
            new CharacterSelect();
        });
        fadeOut.play();
    }
}
