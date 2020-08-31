package main.model.boardSystem;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import main.model.boardSystem.landTypes.GrassLandType;
import main.model.boardSystem.landTypes.LandType;
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


    public List<BoardSpace> getMovementArea(CharacterUnit activeUnit) {
        AreaFinder areaFinder = new AreaFinder(activeUnit);
        return areaFinder.findArea();
    }


    // remove the colour to either movement highlighted or normal
    public void stopShowingAbilitySpaces() {
        for (BoardSpace boardSpace : abilityHighlightedSpace) {
            if (movementHighlightedSpaces.get(boardSpace).isEmpty()) boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.NORMAL);
            else boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.NON_ACTIVE_UNIT_MOVEMENT_HIGHLIGHT_COLOUR);
        }
        abilityHighlightedSpace.clear();
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

    private class AreaFinder {
        private CharacterUnit activeUnit;
        private List<BoardSpace> path;

        AreaFinder(CharacterUnit activeUnit) {
            this.activeUnit = activeUnit;
            path = new ArrayList<>(); // doesn't care about order
        }

        public List<BoardSpace> findArea() {
            getMovementRange(activeUnit.getBoardSpace(), -1, activeUnit.getCharacterStatSheet().getMovement());
            return path;
        }


        private void getMovementRange(BoardSpace currentSpace, int prevDirection, int movementPoints) {
            if (movementPoints == 0) return;

            List<Pair<BoardSpace, Integer>> neighbouringBoardSpaces = getNeighbouringBoardSpace(currentSpace, prevDirection);
            for (Pair<BoardSpace, Integer> it : neighbouringBoardSpaces) {
                BoardSpace nextSpace = it.getKey();
                int direction = it.getValue();
                if (!path.contains(nextSpace) && nextSpace.isTerrainable()) path.add(nextSpace);
                if (nextSpace.isTerrainable()) getMovementRange(nextSpace, direction, movementPoints - 1);
            }
        }

        // if we came from down we don't want to go up therefore 0, (coming from above), will stop going down
        private List<Pair<BoardSpace, Integer>> getNeighbouringBoardSpace(BoardSpace currentSpace, int prevDirection)  {
            List<Pair<BoardSpace, Integer>> neighbouringBoardSpaces = new ArrayList<>();
            int yPos = currentSpace.getYCoordinate();
            int xPos = currentSpace.getXCoordinate();
            if (yPos - 1 >= 0 && prevDirection != 2) neighbouringBoardSpaces.add(new Pair<>(boardSpaces[xPos][yPos - 1], 0));
            if (xPos + 1 < boardSpaces[0].length && prevDirection != 3) neighbouringBoardSpaces.add(new Pair<>(boardSpaces[xPos + 1][yPos], 1));
            if (yPos + 1 < boardSpaces.length && prevDirection != 0) neighbouringBoardSpaces.add(new Pair<>(boardSpaces[xPos][yPos + 1], 2));
            if (xPos - 1 >= 0 && prevDirection != 1) neighbouringBoardSpaces.add(new Pair<>(boardSpaces[xPos - 1][yPos], 3));
            return neighbouringBoardSpaces;
        }
    }


    public void displayMovementSpaces(CharacterUnit activeUnit, List<BoardSpace> toHighlight) {
        for (BoardSpace boardSpace : toHighlight) {
            boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.NON_ACTIVE_UNIT_MOVEMENT_HIGHLIGHT_COLOUR);
            List<CharacterUnit> list = movementHighlightedSpaces.get(boardSpace);
            list.add(activeUnit);
            movementHighlightedSpaces.put(boardSpace, list);
        }
    }

    public void displayAbilitySpaces(List<BoardSpace> toHighlight, Ability chosenAbility) {
        for (BoardSpace boardSpace : toHighlight) {
            boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.ABILITY_HIGHLIGHT_COLOUR);
            abilityHighlightedSpace.add(boardSpace);
            EventHandler<MouseEvent> hoverHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (abilityHighlightedSpace.contains(boardSpace))
                       boardSpace.setBackground(boardSpace.getLandType().hoveredSpace());
                    else boardSpace.removeEventHandler(MouseEvent.MOUSE_ENTERED, this);
                }
            };
            boardSpace.setOnMouseEntered(hoverHandler);
            EventHandler<MouseEvent> exitedHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if (abilityHighlightedSpace.contains(boardSpace)) {
                        boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.ABILITY_HIGHLIGHT_COLOUR);
                    } else boardSpace.removeEventHandler(MouseEvent.MOUSE_ENTERED, this);
                }
            };
            boardSpace.setOnMouseExited(exitedHandler);
        }
    }

    public List<CharacterUnit> getPossibleTargets(List<BoardSpace> boardSpaces) {
        List<CharacterUnit> possibleTargets = new ArrayList<>();
        for (BoardSpace boardSpace : boardSpaces) {
            if (boardSpace.isOccupied()) possibleTargets.add(boardSpace.getOccupyingUnit());
        }
        return possibleTargets;
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
}
