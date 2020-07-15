package main.model.boardSystem;

import main.model.boardSystem.tiles.GrassLandType;
import main.model.boardSystem.tiles.LandType;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;

import java.util.*;

public class Board {
    private double boardWidth;
    private double boardHeight;
    private BoardSpace[][] boardSpaces;
    private Map<BoardSpace, List<CharacterUnit>> movementHighlightedSpaces = new HashMap<>();
    private List<BoardSpace> abilityHighlightedSpace = new ArrayList<>();

    public Board(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        boardSpaces = new BoardSpace[boardWidth][boardHeight];
        createBoardGraphics();
    }

    private void createBoardGraphics() {
        for (int xValue = 0; xValue < boardWidth; xValue++) {
            for (int yValue = 0; yValue < boardHeight; yValue++) {
                boardSpaces[xValue][yValue] = new BoardSpace(new GrassLandType(), xValue, yValue);
                movementHighlightedSpaces.put(boardSpaces[xValue][yValue], new LinkedList<>());
            }
        }
    }

    public void setCharacterToBoardSpace(CharacterUnit unit, int xValue, int yValue) {
        BoardSpace chosenBoardSpace = boardSpaces[xValue][yValue];
        chosenBoardSpace.setOccupyingUnit(unit);
    }

    public BoardSpace getBoardSpace(int xValue, int yValue) {
        return this.boardSpaces[xValue][yValue];
    }

    // get the boardSpace from the unit then use the unitMovement to display valid spaces within that range.
    public void displayValidMovementSpaces(CharacterUnit unit, int movement) {
        BoardSpace currentSpace = unit.getBoardSpace();

        for (BoardSpace[] possibleSpaces : this.boardSpaces) {
            for (BoardSpace possibleSpace : possibleSpaces) {
                if (possibleSpace.isValidMovementSpace(currentSpace, movement)) {
                    possibleSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.MOVEMENT_HIGHLIGHT_COLOUR);
                    List<CharacterUnit> list = movementHighlightedSpaces.get(possibleSpace);
                    list.add(unit);
                    movementHighlightedSpaces.put(possibleSpace, list);
                }
            }
        }
    }

    public void displayMovementSpaces(CharacterUnit activeUnit, List<BoardSpace> toHighlight) {
        for (BoardSpace boardSpace : toHighlight) {
            boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.MOVEMENT_HIGHLIGHT_COLOUR);
            List<CharacterUnit> list = movementHighlightedSpaces.get(boardSpace);
            list.add(activeUnit);
            movementHighlightedSpaces.put(boardSpace, list);
        }
    }

    public void displayAbilitySpaces(List<BoardSpace> toHighlight) {
        for (BoardSpace boardSpace : toHighlight) {
            boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.ABILITY_HIGHLIGHT_COLOUR);
            abilityHighlightedSpace.add(boardSpace);
        }
    }

    public List<CharacterUnit> getPossibleTargets(List<BoardSpace> boardSpaces) {
        List<CharacterUnit> possibleTargets = new ArrayList<>();
        for (BoardSpace boardSpace : boardSpaces) {
            if (boardSpace.isOccupied()) possibleTargets.add(boardSpace.getOccupyingUnit());
        }
        return possibleTargets;
    }
    
    public void displayValidAbilitySpaces(CharacterUnit unit, int range) {
        BoardSpace currentSpace = unit.getBoardSpace();

        for (BoardSpace[] boardSpaceArray : this.boardSpaces) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                if (boardSpace.isValidAbilitySpace(currentSpace, range)) {
                    abilityHighlightedSpace.add(boardSpace);
                    boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.ABILITY_HIGHLIGHT_COLOUR);
                }
            }
        }
    }

    public void stopShowingMovementSpaces(CharacterUnit unit) {
        for (BoardSpace[] possibleSpaces : this.boardSpaces) {
            for (BoardSpace possibleSpace : possibleSpaces) {
                List<CharacterUnit> list = movementHighlightedSpaces.get(possibleSpace);
                list.remove(unit);
                if (list.isEmpty()) {
                    possibleSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.NORMAL);
                }
            }
        }
    }

    // remove the colour to either movement highlighted or normal
    public void stopShowingAbilitySpaces() {
        for (BoardSpace boardSpace : abilityHighlightedSpace) {
            if (movementHighlightedSpaces.get(boardSpace).isEmpty()) boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.NORMAL);
            else boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.MOVEMENT_HIGHLIGHT_COLOUR);
        }
        abilityHighlightedSpace.clear();
    }

    public List<CharacterUnit> getUnitsInRangeOfAbility(CharacterUnit activeUnit, Ability chosenAbility) {
        List<CharacterUnit> unitsInRangeOfAbilities = new ArrayList<>();
        for (BoardSpace boardSpace : abilityHighlightedSpace) {
            if (boardSpace.isOccupied()) {
                if (chosenAbility.targetsAlly()) unitsInRangeOfAbilities.add(boardSpace.getOccupyingUnit()); // can include oneself
                else if (boardSpace.getOccupyingUnit() != activeUnit) unitsInRangeOfAbilities.add(boardSpace.getOccupyingUnit());; //don't include oneself
            }
        }
        return unitsInRangeOfAbilities;
    }

    // find the closest boardSpace to the given board space. Start by checking above, right, down, left.
    // the x and y cursor must stay within the bounds of the board
    public BoardSpace getClosetBoardSpace(BoardSpace boardSpace) {
        int xCursor = 1;
        int yCursor = 1;
        int xValue = boardSpace.getXCoordinate();
        int yValue = boardSpace.getYCoordinate();
        BoardSpace closetBoardSpace = null;
        while (closetBoardSpace == null) {
            if (yValue - yCursor >= 0 && !boardSpaces[xValue][yValue - yCursor].isOccupied()) // up
                closetBoardSpace = boardSpaces[xValue][yValue - yCursor];
            else if (xValue + xCursor < this.boardWidth && !boardSpaces[xValue + xCursor][yValue].isOccupied()) // right
                closetBoardSpace = boardSpaces[xValue + xCursor][yValue];
            else if (yValue + yCursor < this.boardHeight && !boardSpaces[xValue][yValue + yCursor].isOccupied()) // left
                closetBoardSpace = boardSpaces[xValue][yValue + yCursor];
            else if (xValue - xCursor >= this.boardWidth && !boardSpaces[xValue - xCursor][yValue].isOccupied()) // down
                closetBoardSpace = boardSpaces[xValue - xCursor][yValue];

            xCursor++;
            yCursor++;
        }
        return closetBoardSpace;
    }

    public BoardSpace[][] getBoardSpaces() {
        return this.boardSpaces;
    }


    public double getBoardWidth() {
        return this.boardWidth;
    }

    public double getBoardHeight() {
        return this.boardHeight;
    }

    public Map<BoardSpace, List<CharacterUnit>> getMovementHighlightedSpaces() {
        return movementHighlightedSpaces;
    }

    public boolean isAbilitySpacesShowing() {
        return !abilityHighlightedSpace.isEmpty();
    }

    public List<BoardSpace> getAbilityHighlightedSpace() {
        return this.abilityHighlightedSpace;
    }
}
