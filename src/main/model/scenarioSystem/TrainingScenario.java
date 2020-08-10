package main.model.scenarioSystem;

import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.boardSystem.landTypes.GrassLandType;
import main.model.boardSystem.landTypes.WaterLandType;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.NPC;
import main.model.jobSystem.jobs.clericJob.Cleric;
import main.model.jobSystem.jobs.nobleJob.Noble;

import java.util.List;

import static main.model.boardSystem.BoardSpace.BOARD_SPACE_SIZE;

public class TrainingScenario extends Scenario {

    public TrainingScenario() {
        super();
        scenarioName = "Training";
    }

    protected void createListOfEnemies() {
        listOfEnemies.add(new NPC(new Noble(), "Dummy"));
        listOfEnemies.add(new NPC(new Cleric(), "Cleric"));
    }

    @Override
    protected void setBoardLayout() {
        this.scenarioBoard = new Board(10, 10);
        for (BoardSpace[] boardSpaceArray : scenarioBoard.getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                boardSpace.setLandType(new GrassLandType());
                boardSpace.setMaxSize(BOARD_SPACE_SIZE, BOARD_SPACE_SIZE);
                boardSpace.setMinSize(BOARD_SPACE_SIZE, BOARD_SPACE_SIZE);
            }
        }
        scenarioBoard.getBoardSpace(5, 5).setLandType(new WaterLandType());
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
        int i = 1;
        for (CharacterUnit enemy : listOfEnemies) {
            scenarioBoard.setCharacterToBoardSpace(enemy, (int) (scenarioBoard.getBoardWidth() - i),
                    (int) (scenarioBoard.getBoardHeight() - i));
            i++;
        }
    }
}
