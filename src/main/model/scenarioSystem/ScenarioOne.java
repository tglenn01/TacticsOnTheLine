package main.model.scenarioSystem;

import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.NPC;
import main.model.jobSystem.jobs.Noble;

import java.util.List;

import static main.model.graphics.DefaultScene.FINAL_HEIGHT;
import static main.model.graphics.DefaultScene.FINAL_WIDTH;

public class ScenarioOne extends Scenario {

    public ScenarioOne() {
        super();
        scenarioName = "Training";
    }

    protected void createListOfEnemies() {
        listOfEnemies.add(new NPC(new Noble(), "Dummy"));
    }

    @Override
    protected void setBoardLayout() {
        this.scenarioBoard = new Board(8, 8);
        for (BoardSpace[] boardSpaceArray : scenarioBoard.getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                boardSpace.setLandType(BoardSpace.LandType.GRASS);
                boardSpace.setWidth(FINAL_WIDTH / scenarioBoard.getBoardWidth());
                boardSpace.setHeight(FINAL_HEIGHT / scenarioBoard.getBoardHeight());
            }
        }
        scenarioBoard.getBoardSpace(5, 5).setLandType(BoardSpace.LandType.WATER);
    }

    @Override
    protected void setAllyCharacters(List<CharacterUnit> playableCharacters) {
        int i = 0;
        for (CharacterUnit ally : playableCharacters) {
            scenarioBoard.setCharacterToBoardSpace(ally, i, 0);
            i++;
        }
    }

    @Override
    protected void setEnemies() {
        int i = 0;
        for (CharacterUnit enemy : listOfEnemies) {
            scenarioBoard.setCharacterToBoardSpace(enemy, i, 3);
            i++;
        }
    }
}
