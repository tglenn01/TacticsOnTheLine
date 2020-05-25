package main.model.boardSystem;

import main.model.characterSystem.CharacterUnit;

public class Board {
    private double boardWidth;
    private double boardHeight;
    private BoardSpace[][] boardSpaces;

    public Board(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        boardSpaces = new BoardSpace[boardWidth][boardHeight];
        createBoardGraphics();
    }

    private void createBoardGraphics() {
        for (int xValue = 0; xValue < boardWidth; xValue++) {
            for (int yValue = 0; yValue < boardHeight; yValue++) {
                boardSpaces[xValue][yValue] = new BoardSpace(BoardSpace.LandType.GRASS, xValue, yValue);
            }
        }
    }

    public BoardSpace getBoardSpace(int xValue, int yValue) {
        return this.boardSpaces[xValue][yValue];
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

    public double getBoardWidth() {
        return this.boardWidth;
    }

    public double getBoardHeight() {
        return this.boardHeight;
    }
}
