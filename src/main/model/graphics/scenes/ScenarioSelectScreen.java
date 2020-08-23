package main.model.graphics.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.model.graphics.DefaultScene;
import main.model.scenarioSystem.Scenario;
import main.model.scenarioSystem.TrainingScenario;
import main.model.scenarioSystem.WoodsScenario;
import main.ui.TacticBaseBattle;

public class ScenarioSelectScreen extends DefaultScene {

    public ScenarioSelectScreen() {
        initializeGraphics();
    }

    @Override
    protected void initializeGraphics() {
        Label scenarioTitle = new Label("Choose a Scenario");
        scenarioTitle.setAlignment(Pos.CENTER);

        HBox scenarios = this.getPossibleScenarios();

        Button returnButton = new Button("return");
        returnButton.setOnAction(e -> {
            new CharacterSelect(TacticBaseBattle.getInstance().getPartyMemberList());
        });
        returnButton.setAlignment(Pos.BOTTOM_LEFT);


        VBox layout = new VBox();
        layout.setId("defaultBackground");
        layout.getChildren().addAll(scenarioTitle, scenarios, returnButton);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(150.00);
        Scene scenarioSelectScene = new Scene(layout, FINAL_WIDTH, FINAL_HEIGHT);
        addCSS(scenarioSelectScene);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(scenarioSelectScene);
    }

    private HBox getPossibleScenarios() {
        HBox scenarios = new HBox();

        Scenario trainingScenario = new TrainingScenario();
        Scenario woodsScenario = new WoodsScenario();

        Button trainingScenarioButton = new Button(trainingScenario.getScenarioName());
        trainingScenarioButton.setPrefSize(200, 150);
        trainingScenarioButton.setId("fancyNode");
        Button woodsScenarioButton = new Button(woodsScenario.getScenarioName());
        woodsScenarioButton.setPrefSize(200, 150);
        woodsScenarioButton.setId("fancyNode");

        trainingScenarioButton.setOnMouseClicked(e -> TacticBaseBattle.getInstance().startBattle(trainingScenario));
        woodsScenarioButton.setOnMouseClicked(event -> TacticBaseBattle.getInstance().startBattle(woodsScenario));

        scenarios.getChildren().addAll(trainingScenarioButton, woodsScenarioButton);
        scenarios.setAlignment(Pos.CENTER);
        scenarios.setSpacing(50.0);

        return scenarios;
    }
}
