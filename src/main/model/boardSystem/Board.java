package main.model.boardSystem;

import main.model.boardSystem.tiles.GrassTile;
import main.model.characterSystem.CharacterUnit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Board {
    private double boardWidth;
    private double boardHeight;
    private BoardSpace[][] boardSpaces;
    private Map<BoardSpace, List<CharacterUnit>> highlightedBoardSpaces = new HashMap<>();

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
                highlightedBoardSpaces.put(boardSpaces[xValue][yValue], new LinkedList<>());
            }
        }
    }

    public void setCharacterToBoardSpace(CharacterUnit unit, int xValue, int yValue) {
        BoardSpace chosenBoardSpace = boardSpaces[xValue][yValue];
        chosenBoardSpace.setOccupyingUnit(unit);
    }

//    private void movementAction(CharacterUnit activeCharacter) {
//
//        for (BoardSpace highlightedSpace : highlightedBoardSpaces) {
//            highlightedSpace.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                public void handle(MouseEvent event) {
//                    //event.getButton() == MouseButton.SECONDARY;
//                    BoardSpace boardSpace = (BoardSpace) event.getSource();
//                    activeCharacter.getBoardSpace().removeOccupyingUnit();
//                    setCharacterToBoardSpace(activeCharacter, boardSpace.getXCoordinate(), boardSpace.getYCoordinate());
//                    activeCharacter.movementComplete(TacticBaseBattle.getInstance().getBattle());
//
//                    for (BoardSpace space : highlightedBoardSpaces) {
//                        space.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
//                    }
//                    stopShowingDisplayedSpaces();
//                    event.consume();
//                }
//            });
//        }
//    }

//    private void viewRange(CharacterUnit nonActiveCharacter) {
//        for (BoardSpace[] boardSpaceArray : boardSpaces) {
//            for (BoardSpace boardSpace : boardSpaceArray) {
//                boardSpace.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        stopShowingDisplayedSpaces();
//                        for (BoardSpace space : boardSpaceArray) {
//                            space.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
//                        }
//                        event.consume();
//                    }
//                });
//            }
//        }
//    }

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
                    List<CharacterUnit> list = highlightedBoardSpaces.get(possibleSpace);
                    list.add(unit);
                    highlightedBoardSpaces.put(possibleSpace, list);
                }
            }
        }
    }

    public void stopShowingDisplayedSpaces(CharacterUnit unit) {
        BoardSpace currentSpace = unit.getBoardSpace();

        for (BoardSpace[] possibleSpaces : this.boardSpaces) {
            for (BoardSpace possibleSpace : possibleSpaces) {
                List<CharacterUnit> list = highlightedBoardSpaces.get(possibleSpace);
                list.remove(unit);
                if (list.isEmpty()) {
                    possibleSpace.highlightSpace(false);
                }
            }
        }
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

    public Map<BoardSpace, List<CharacterUnit>> getHighlightedBoardSpaces() {
        return highlightedBoardSpaces;
    }
}
