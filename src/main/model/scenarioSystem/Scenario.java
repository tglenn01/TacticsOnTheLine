package main.model.scenarioSystem;

import main.model.characterSystem.CharacterUnit;

import java.util.ArrayList;
import java.util.List;

public abstract class Scenario {
    protected String scenarioName;
    protected List<CharacterUnit> listOfEnemies;

    public Scenario() {
        listOfEnemies = new ArrayList<>();
        createListOfEnemies();
    }

    protected abstract void createListOfEnemies();

    public List<CharacterUnit> getListOfEnemies() {
        return listOfEnemies;
    }

    public String getScenarioName() {
        return scenarioName;
    }
}
