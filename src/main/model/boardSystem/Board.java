package main.model.boardSystem;

import javafx.event.EventHandler;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;
import main.model.boardSystem.tiles.GrassTile;
import main.model.characterSystem.CharacterUnit;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final DataFormat CHARACTER_UNIT_DATA = new DataFormat("CharacterUnit");
    private double boardWidth;
    private double boardHeight;
    private BoardSpace[][] boardSpaces;
    private List<BoardSpace> highlightedBoardSpaces = new ArrayList<>();
    private CharacterUnit activeCharacter;

    public Board(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        boardSpaces = new BoardSpace[boardWidth][boardHeight];
        createBoardGraphics();
    }

    private void createBoardGraphics() {
        for (int xValue = 0; xValue < boardWidth; xValue++) {
            for (int yValue = 0; yValue < boardHeight; yValue++) {
                boardSpaces[xValue][yValue] = new BoardSpace(new GrassTile(), xValue, yValue);
            }
        }
    }

    public BoardSpace getBoardSpace(int xValue, int yValue) {
        return this.boardSpaces[xValue][yValue];
    }

    // get the boardSpace from the unit then use the unitMovement to display valid spaces within that range.
    public void displayValidSpaces(CharacterUnit unit, int range) {
        BoardSpace currentSpace = unit.getBoardSpace();

        for (BoardSpace[] possibleSpaces : this.boardSpaces) {
            for (BoardSpace possibleSpace : possibleSpaces) {
                if (possibleSpace.isValidSpace(currentSpace, range)) {
                    possibleSpace.highlightSpace(true);
                    highlightedBoardSpaces.add(possibleSpace);
                }
            }
        }
    }

    public void stopShowingDisplayedSpaces() {
        for (BoardSpace boardSpace : highlightedBoardSpaces) {
            boardSpace.highlightSpace(false);
        }
        highlightedBoardSpaces.clear();
    }

    public BoardSpace[][] getBoardSpaces() {
        return this.boardSpaces;
    }

    public void setCharacterToBoardSpace(CharacterUnit unit, int xValue, int yValue) {
        BoardSpace chosenBoardSpace =  boardSpaces[xValue][yValue];

        unit.getSprite().setOnMouseClicked(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                displayValidSpaces(unit, unit.getCharacterStatSheet().getMovement());

                if (unit == activeCharacter) {
                    for (BoardSpace highlightedSpace : highlightedBoardSpaces) {
                        highlightedSpace.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            public void handle(MouseEvent event) {
                                BoardSpace chosenBoardSpace = (BoardSpace) event.getSource();
                                unit.getBoardSpace().removeOccupyingUnit();
                                stopShowingDisplayedSpaces();
                                setCharacterToBoardSpace(unit, chosenBoardSpace.getXCoordinate(), chosenBoardSpace.getYCoordinate());
                            }
                        });
                    }
                }
            }
        });

        chosenBoardSpace.setOccupyingUnit(unit);
    }

    public void setActiveCharacter(CharacterUnit activeCharacter) {
        this.activeCharacter = activeCharacter;
    }

    public double getBoardWidth() {
        return this.boardWidth;
    }

    public double getBoardHeight() {
        return this.boardHeight;
    }
}
