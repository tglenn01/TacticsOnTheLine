package main.model.boardSystem;

import main.model.characterSystem.CharacterUnit;
import main.model.graphics.scenes.BoardInterface;

public class Board {
    private static Board board;
    private int boardWidth;
    private int boardHeight;
    private BoardSpace[][] boardSpaces;

    public Board(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth * BoardSpace.BOARD_SPACE_SIZE;
        this.boardHeight = boardHeight * BoardSpace.BOARD_SPACE_SIZE;
        createBoardGraphics();
        board = this;
    }

    public static Board getInstance() {
        return board;
    }

    private void createBoardGraphics() {
        for (int xValue = 0; xValue < boardWidth; xValue++) {
            for (int yValue = 0; yValue < boardHeight; yValue++) {
                boardSpaces[xValue][yValue] = new BoardSpace(xValue, yValue);
            }
        }
    }

    public BoardSpace getBoardSpace(int xValue, int yValue) {
        return boardSpaces[xValue][yValue];
    }

    // get the boardSpace from the unit then use the unitMovement to display valid spaces within that range.
    public void displayValidSpaces(CharacterUnit unit, int unitMovement) {

    }

    public BoardSpace[][] getBoardSpaces() {
        return this.boardSpaces;
    }

    public void setCharacterToBoardSpace(CharacterUnit unit, int xValue, int yValue) {
        BoardSpace chosenBoardSpace =  boardSpaces[xValue][yValue];
        chosenBoardSpace.setOccupyingUnit(unit);
    }

    public void show() {
        new BoardInterface(this);
    }

    public int getBoardWidth() {
        return this.boardWidth;
    }

    public int getBoardHeight() {
        return this.boardHeight;
    }
}
