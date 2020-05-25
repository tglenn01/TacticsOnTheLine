package main.model.graphics.icons;

import javafx.scene.control.Button;
import main.model.scenarioSystem.Scenario;

public class ScenarioButton extends Button {
    private Scenario scenario;

    public ScenarioButton(Scenario scenario) {
        this.scenario = scenario;
        setText(scenario.getScenarioName());
    }

    public Scenario getScenario() {
        return this.scenario;
    }
}
