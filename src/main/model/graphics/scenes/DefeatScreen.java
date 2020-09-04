package main.model.graphics.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.model.graphics.DefaultScene;
import main.ui.TacticBaseBattle;

public class DefeatScreen extends DefaultScene {

    public DefeatScreen() {
        this.initializeGraphics();
    }

    protected void initializeGraphics() {
        Label outcomeLabel = new Label("The line has been drawn, you fall here forgotten with the times \n" +
                "Never to stand again");
        outcomeLabel.setId("headerLabel");
        outcomeLabel.setAlignment(Pos.CENTER_LEFT);
        outcomeLabel.setWrapText(true);
        Label returnToMainMenuLabel = new Label("Accept Fate, and conquer another day");
        returnToMainMenuLabel.setAlignment(Pos.CENTER_LEFT);
        returnToMainMenuLabel.setId("mainMenuElement");
        returnToMainMenuLabel.setOnMouseClicked(e -> new TitleScreen());

        VBox layout = new VBox();

        layout.getChildren().addAll(outcomeLabel, returnToMainMenuLabel);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(10.00);

        mainPane.getChildren().add(layout);
        animateBackground(mainScene, mainPane);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(mainScene);
    }
}
