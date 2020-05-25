package main.model.graphics.scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.model.graphics.DefaultScene;
import main.ui.TacticBaseBattle;

public class MainMenu extends DefaultScene implements EventHandler<ActionEvent> {

    public MainMenu() {
        this.initializeGraphics();
    }

    protected void initializeGraphics() {
        Label tacticOnTheLine = new Label("Tactics On The Line");
        tacticOnTheLine.setAlignment(Pos.CENTER_LEFT);
        Button chooseCharacterButton = new Button("Start New Adventure");
        chooseCharacterButton.setAlignment(Pos.CENTER_LEFT);
        chooseCharacterButton.setOnAction(this);

        VBox layout = new VBox();
        layout.getChildren().addAll(tacticOnTheLine, chooseCharacterButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(10.00);
        Scene scene = new Scene(layout, FINAL_WIDTH, FINAL_HEIGHT);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(scene);
    }

    @Override
    public void handle(ActionEvent event) {
        TacticBaseBattle.getInstance().characterSelect();
    }
}
