package main.model.scenarioSystem;

import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.boardSystem.tiles.GrassLandType;
import main.model.boardSystem.tiles.WaterLandType;
import main.model.boardSystem.tiles.WoodsType;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.NPC;
import main.model.jobSystem.jobs.*;

import java.util.List;

import static main.model.boardSystem.BoardSpace.BOARD_SPACE_SIZE;

public class ScenarioTwo extends Scenario {

    public ScenarioTwo() {
        super();
        scenarioName = "Woods";
    }

    protected void createListOfEnemies() {
        listOfEnemies.add(new NPC(new Warrior(), "Savage"));
        listOfEnemies.add(new NPC(new Lancer(), "SpearMan"));
        listOfEnemies.add(new NPC(new Noble(), "Prince"));
        listOfEnemies.add(new NPC(new Cleric(), "Healer"));
        listOfEnemies.add(new NPC(new BattleMage(), "Caster"));
        listOfEnemies.add(new NPC(new Archer(), "Hunter"));
    }

    @Override
    protected void setBoardLayout() {
        this.scenarioBoard = new Board(8, 8);
        for (BoardSpace[] boardSpaceArray : scenarioBoard.getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                boardSpace.setLandType(new GrassLandType());
                boardSpace.setMaxSize(BOARD_SPACE_SIZE, BOARD_SPACE_SIZE);
                boardSpace.setMinSize(BOARD_SPACE_SIZE, BOARD_SPACE_SIZE);
            }
        }
        scenarioBoard.getBoardSpace(1, 1).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(2, 1).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(2, 2).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(4, 1).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(5, 1).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(6, 1).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(6, 3).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(7, 3).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(7, 4).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(5, 4).setLandType(new WaterLandType());
        scenarioBoard.getBoardSpace(3, 4).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(3, 5).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(3, 6).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(4, 6).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(5, 6).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(6, 6).setLandType(new WoodsType());
        scenarioBoard.getBoardSpace(1, 6).setLandType(new WaterLandType());
        scenarioBoard.getBoardSpace(1, 7).setLandType(new WaterLandType());
    }

    @Override
    protected void setAllyCharacters(List<CharacterUnit> playableCharacters) {
        //assert(playableCharacters.size() == 4);
        scenarioBoard.setCharacterToBoardSpace(playableCharacters.get(0), 1, 0);
        scenarioBoard.setCharacterToBoardSpace(playableCharacters.get(1), 3, 0);
        scenarioBoard.setCharacterToBoardSpace(playableCharacters.get(2), 0, 2);
        scenarioBoard.setCharacterToBoardSpace(playableCharacters.get(3), 0, 4);
    }

    @Override
    protected void setEnemies() {
        //assert(listOfEnemies.size() == 6)
        scenarioBoard.setCharacterToBoardSpace(listOfEnemies.get(0), 4, 4); // Warrior
        scenarioBoard.setCharacterToBoardSpace(listOfEnemies.get(1), 5, 3); // Lancer
        scenarioBoard.setCharacterToBoardSpace(listOfEnemies.get(2), 5, 5); // Noble
        scenarioBoard.setCharacterToBoardSpace(listOfEnemies.get(3), 7, 6); // Cleric
        scenarioBoard.setCharacterToBoardSpace(listOfEnemies.get(4), 6, 4); // BattleMage
        scenarioBoard.setCharacterToBoardSpace(listOfEnemies.get(5), 7, 1); // Archer
    }
}
