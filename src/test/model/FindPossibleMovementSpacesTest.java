package test.model;

import javafx.embed.swing.JFXPanel;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.boardSystem.landTypes.WaterLandType;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.NPC;
import main.model.characterSystem.characterList.Graham;
import main.model.jobSystem.jobs.nobleJob.Noble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindPossibleMovementSpacesTest {
    CharacterUnit testUnit;


    @BeforeEach
    void runBefore() {
        JFrame frame = new JFrame("Testing FX Frame for initalized data");
        JFXPanel jfxPanel = new JFXPanel();
        frame.add(jfxPanel);
        testUnit = new Graham();
    }

    /*
        Simple Board
        0   0   0   0   0
        0   0   0   0   0
        0   0   2   0   0
        0   0   0   0   0
        0   0   0   0   0
        - 0 is empty space
        - 1 is blocked space
        - 2 is activeUnit
     */
    @Test
    void testSimpleSpaces() {
        Board simpleBoard = new Board(5, 5);
        testUnit.getCharacterStatSheet().setMovement(2);
        simpleBoard.setCharacterToBoardSpace(testUnit, 2, 2);

        List<BoardSpace> spaces = simpleBoard.getMovementArea(testUnit);
        assertTrue(spaces.contains(simpleBoard.getBoardSpace(2, 0)));
        assertTrue(spaces.contains(simpleBoard.getBoardSpace(1, 1)));
        assertTrue(spaces.contains(simpleBoard.getBoardSpace(2, 1)));
        assertTrue(spaces.contains(simpleBoard.getBoardSpace(3, 1)));
        assertTrue(spaces.contains(simpleBoard.getBoardSpace(0, 2)));
        assertTrue(spaces.contains(simpleBoard.getBoardSpace(1, 2)));
        assertTrue(spaces.contains(simpleBoard.getBoardSpace(3, 2)));
        assertTrue(spaces.contains(simpleBoard.getBoardSpace(4, 2)));
        assertTrue(spaces.contains(simpleBoard.getBoardSpace(1, 3)));
        assertTrue(spaces.contains(simpleBoard.getBoardSpace(2, 3)));
        assertTrue(spaces.contains(simpleBoard.getBoardSpace(3, 3)));
        assertTrue(spaces.contains(simpleBoard.getBoardSpace(2, 4)));
    }


    /*
        Complex Board
        0   0   0   0   0   0   0
        0   0   0   0   1   1   0
        0   0   1   0   0   0   0
        0   0   1   2   0   0   0
        0   0   0   0   0   0   0
        0   0   0   1   0   0   0
        0   0   0   0   0   0   0
        - 0 is empty space
        - 1 is blocked space
        - 2 is activeUnit
     */

    @Test
    void testComplexSpaces() {
        Board complexBoard = new Board(7, 7);
        NPC npc1 = new NPC(new Noble(), "NPC1");
        NPC npc2 = new NPC(new Noble(), "NPC2");
        NPC npc3 = new NPC(new Noble(), "NPC3");
        complexBoard.setCharacterToBoardSpace(testUnit, 3, 3);
        complexBoard.setCharacterToBoardSpace(npc1, 2, 2);
        complexBoard.setCharacterToBoardSpace(npc2, 2, 3);
        complexBoard.setCharacterToBoardSpace(npc3, 5, 1);
        complexBoard.getBoardSpace(4, 1).setLandType(new WaterLandType());
        complexBoard.getBoardSpace(3, 5).setLandType(new WaterLandType());

        testUnit.getCharacterStatSheet().setMovement(3);

        List<BoardSpace> spaces = complexBoard.getMovementArea(testUnit);

        // Valid Spaces
        assertTrue(spaces.contains(complexBoard.getBoardSpace(3, 0)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(2, 1)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(3, 1)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(3, 2)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(4, 2)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(5, 2)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(4, 3)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(5, 3)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(6, 3)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(1, 4)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(2, 4)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(3, 4)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(4, 4)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(5, 4)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(2, 5)));
        assertTrue(spaces.contains(complexBoard.getBoardSpace(4, 5)));

        // Invalid Spaces
        assertFalse(spaces.contains(complexBoard.getBoardSpace(3, 3)));
        assertFalse(spaces.contains(complexBoard.getBoardSpace(2, 2)));
        assertFalse(spaces.contains(complexBoard.getBoardSpace(2, 3)));
        assertFalse(spaces.contains(complexBoard.getBoardSpace(5, 1)));
        assertFalse(spaces.contains(complexBoard.getBoardSpace(4, 1)));
        assertFalse(spaces.contains(complexBoard.getBoardSpace(3, 5)));
    }



    /*
        Unit Blocking Board
        0   2   1   0   1   0
        0   1   0   0   0   0
        0   0   1   0   0   0
        0   0   0   0   0   0
        0   0   0   0   0   0
        0   0   0   0   0   0
        - 0 is empty space
        - 1 is blocked space
        - 2 is activeUnit
     */
    @Test
    void testUnitBlocking() {
        Board unitBlockingBoard = new Board(6, 6);
        testUnit.getCharacterStatSheet().setMovement(4);
        unitBlockingBoard.setCharacterToBoardSpace(testUnit, 1, 0);
        unitBlockingBoard.setCharacterToBoardSpace(new NPC(new Noble(), "NPC1"), 2, 0);
        unitBlockingBoard.setCharacterToBoardSpace(new NPC(new Noble(), "NPC2"), 4, 0);
        unitBlockingBoard.setCharacterToBoardSpace(new NPC(new Noble(), "NPC3"), 1, 1);
        unitBlockingBoard.setCharacterToBoardSpace(new NPC(new Noble(), "NPC4"), 2, 2);

        List<BoardSpace> spaces = unitBlockingBoard.getMovementArea(testUnit);

        // Valid Spaces
        assertTrue(spaces.contains(unitBlockingBoard.getBoardSpace(0, 0)));
        assertTrue(spaces.contains(unitBlockingBoard.getBoardSpace(0, 1)));
        assertTrue(spaces.contains(unitBlockingBoard.getBoardSpace(0, 2)));
        assertTrue(spaces.contains(unitBlockingBoard.getBoardSpace(0, 3)));
        assertTrue(spaces.contains(unitBlockingBoard.getBoardSpace(1, 2)));

        // Invalid Spaces
        assertFalse(spaces.contains(unitBlockingBoard.getBoardSpace(1, 0)));
        assertFalse(spaces.contains(unitBlockingBoard.getBoardSpace(2, 0)));
        assertFalse(spaces.contains(unitBlockingBoard.getBoardSpace(4, 0)));
        assertFalse(spaces.contains(unitBlockingBoard.getBoardSpace(1, 1)));
        assertFalse(spaces.contains(unitBlockingBoard.getBoardSpace(2, 2)));

        // Out Of Range Spaces
        Integer[][] outOfRangeSpaces = {{0,4},{0,5},{1,3},{1,4},{1,5},{2,1},{2,3},{2,5},{2,5},
                {3,0},{3,1},{3,2},{3,3},{3,4},{3,5},{4,1},{4,2},{4,3},{4,4},{4,5},{5,0},{5,1},{5,2},{5,3},{5,4},{5,5}};
        for (Integer[] outOfRangeSpace : outOfRangeSpaces) {
            assertFalse(spaces.contains(unitBlockingBoard.getBoardSpace(outOfRangeSpace[0], outOfRangeSpace[1])),
                    "Failed at space "+ outOfRangeSpace[0] + ", " + outOfRangeSpace[1]);
        }
    }

}
