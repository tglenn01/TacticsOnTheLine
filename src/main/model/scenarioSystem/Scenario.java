package main.model.scenarioSystem;

import main.model.boardSystem.Board;
import main.model.characterSystem.CharacterUnit;

import java.util.ArrayList;
import java.util.List;

public abstract class Scenario {
    protected String scenarioName;
    protected List<CharacterUnit> listOfEnemies;
    protected Board scenarioBoard;

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

    public void displayScenario(List<CharacterUnit> playableCharacter) {
        setBoardLayout();
        setAllyCharacters(playableCharacter);
        setEnemies();
        scenarioBoard.show();
    }

    protected abstract void setBoardLayout();

    protected abstract void setAllyCharacters(List<CharacterUnit> playableCharacters);

    protected abstract void setEnemies();
}
