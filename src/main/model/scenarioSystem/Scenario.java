package main.model.scenarioSystem;

import main.model.boardSystem.Board;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.scenes.BattleInterface;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public abstract class Scenario {
    protected String scenarioName;
    protected List<CharacterUnit> listOfEnemies;
    protected Board scenarioBoard;
    protected boolean completed = false;

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
        TacticBaseBattle.getInstance().setCurrentBoard(this.scenarioBoard);
        new BattleInterface(this.scenarioBoard, this);
    }

    protected abstract void setBoardLayout();

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    protected abstract void setAllyCharacters(List<CharacterUnit> playableCharacters);

    protected abstract void setEnemies();
}
