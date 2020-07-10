package main.model.graphics.scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.model.graphics.DefaultScene;
import main.model.graphics.sceneElements.buttons.ScenarioButton;
import main.model.scenarioSystem.ScenarioOne;
import main.model.scenarioSystem.ScenarioTwo;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public class ScenarioSelectScreen extends DefaultScene implements EventHandler<ActionEvent> {
    private List<ScenarioButton> scenarioButtonList;

    public ScenarioSelectScreen() {
        this.scenarioButtonList = new ArrayList<>();
        initializeGraphics();
    }

    @Override
    protected void initializeGraphics() {
        Label scenarioTitle = new Label("Choose a Scenario");
        scenarioTitle.setAlignment(Pos.CENTER);

        HBox scenarios = getPossibleScenarios();

        Button returnButton = new Button("return");
        returnButton.setOnAction(e -> {
            new CharacterSelect(TacticBaseBattle.getInstance().getPartyMemberList());
        });
        returnButton.setAlignment(Pos.BOTTOM_LEFT);


        VBox layout = new VBox();
        layout.getChildren().addAll(scenarioTitle, scenarios, returnButton);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10.00);
        Scene scene = new Scene(layout, FINAL_WIDTH, FINAL_HEIGHT);
        addCSS(scene);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(scene);
    }

    private HBox getPossibleScenarios() {
        HBox scenarios = new HBox();

        ScenarioButton buttonOne = new ScenarioButton(new ScenarioOne());
        buttonOne.setOnAction(this);
        scenarioButtonList.add(buttonOne);
        ScenarioButton buttonTwo = new ScenarioButton(new ScenarioTwo());
        buttonTwo.setOnAction(this);
        scenarioButtonList.add(buttonTwo);

        scenarios.getChildren().addAll(scenarioButtonList);
        scenarios.setAlignment(Pos.CENTER);
        return scenarios;
    }

    @Override
    public void handle(ActionEvent event) {
        for (ScenarioButton button : scenarioButtonList) {
            if (event.getSource() == button) {
                TacticBaseBattle.getInstance().startBattle(button.getScenario());
            }
        }
    }
}
