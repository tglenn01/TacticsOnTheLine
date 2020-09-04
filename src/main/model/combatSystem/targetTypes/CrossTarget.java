package main.model.combatSystem.targetTypes;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.boardSystem.landTypes.LandType;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.TargetType;
import main.model.graphics.menus.AbilityPreview;
import main.model.graphics.menus.BattleMenu;
import main.model.graphics.sceneElements.images.CharacterSprite;
import main.model.battleSystem.TacticBaseBattle;

import java.util.LinkedList;
import java.util.List;

public class CrossTarget extends TargetType {

    @Override
    public void setTargetTypeImage() {
        this.targetTypeImage = new ImageView(new Image("resources/targetTypeImages/CrossTargetType.png"));
    }



    @Override
    public List<BoardSpace> getTargetPattern(BoardSpace centreSpace, int range, Ability chosenAbility) {
        List<BoardSpace> possibleBoardSpaces = new LinkedList<>();
        Board currentBoard = TacticBaseBattle.getInstance().getCurrentBoard();
        BoardSpace[][] boardSpaces = currentBoard.getBoardSpaces();
        int rootX = centreSpace.getXCoordinate();
        int rootY = centreSpace.getYCoordinate();

        for (int i = 1; i <= range; i++) {
            if (rootX - i >= 0)
                possibleBoardSpaces.add(boardSpaces[rootX - i][rootY]); // left
            if (rootX + i < currentBoard.getBoardWidth())
                possibleBoardSpaces.add(boardSpaces[rootX + i][rootY]); // right
            if (rootY + i < currentBoard.getBoardHeight())
                possibleBoardSpaces.add(boardSpaces[rootX][rootY + i]); // up
            if (rootY - i >= 0)
                possibleBoardSpaces.add(boardSpaces[rootX][rootY - i]); // down
            if (i == range - 1) {
                if ((rootX - i >= 0) && (rootY + 1 >= 0))
                    possibleBoardSpaces.add(boardSpaces[rootX - i][rootY + 1]);
                if ((rootX - i >= 0) && (rootY - 1 >= 0))
                    possibleBoardSpaces.add(boardSpaces[rootX - i][rootY - 1]);
                if ((rootX + i < currentBoard.getBoardWidth()) && (rootY - 1 >= 0))
                    possibleBoardSpaces.add(boardSpaces[rootX + i][rootY - 1]);
                if ((rootX + i < currentBoard.getBoardWidth()) && (rootY + 1 < currentBoard.getBoardHeight()))
                    possibleBoardSpaces.add(boardSpaces[rootX + i][rootY + 1]);
                if ((rootX - 1 >= 0) && (rootY - i >= 0))
                    possibleBoardSpaces.add(boardSpaces[rootX - 1][rootY - i]);
                if ((rootX - 1 >= 0) && (rootY + i < currentBoard.getBoardHeight()))
                    possibleBoardSpaces.add(boardSpaces[rootX - 1][rootY + i]);
                if ((rootX + 1 < currentBoard.getBoardWidth()) && (rootY - i >= 0))
                    possibleBoardSpaces.add(boardSpaces[rootX + 1][rootY - i]);
                if ((rootX + 1 < currentBoard.getBoardWidth()) && (rootY + i < currentBoard.getBoardHeight()))
                    possibleBoardSpaces.add(boardSpaces[rootX + 1][rootY + i]);
            }
        }
        return possibleBoardSpaces;
    }


    @Override
    public void setHandlers(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces) {
        List<BoardSpace> left = new LinkedList<>();
        List<BoardSpace> right = new LinkedList<>();
        List<BoardSpace> up = new LinkedList<>();
        List<BoardSpace> down = new LinkedList<>();

        BoardSpace currentBoardSpace = activeUnit.getBoardSpace();
        int rootX = currentBoardSpace.getXCoordinate();
        int rootY = currentBoardSpace.getYCoordinate();

        for (BoardSpace boardSpace : possibleBoardSpaces) {
            int spaceX = boardSpace.getXCoordinate();
            int spaceY = boardSpace.getYCoordinate();

            if (spaceX > rootX && (rootY - 1 <= spaceY) && (spaceY <= rootY + 1))
                right.add(boardSpace);
            else if (spaceX < rootX && (rootY - 1 <= spaceY) && (spaceY <= rootY + 1))
                left.add(boardSpace);
            else if (spaceY < rootY && (rootX - 1 <= spaceX) && (spaceX <= rootX + 1))
                up.add(boardSpace);
            else if (spaceY > rootY && (rootX - 1 <= spaceX) && (spaceX <= rootX + 1))
                down.add(boardSpace);
        }

        List<CharacterUnit> possibleTargets = new LinkedList<>();
        possibleBoardSpaces.forEach(space -> {
            if (space.isOccupied()) possibleTargets.add(space.getOccupyingUnit());
            space.addEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler);
            space.addEventHandler(MouseEvent.MOUSE_EXITED, exitHandler);
        });

        CrossTargetHandler crossTargetHandler = new CrossTargetHandler(activeUnit, chosenAbility, possibleBoardSpaces, possibleTargets,
                left, right, up, down);


        setHandlersToNodes(crossTargetHandler, possibleBoardSpaces, possibleTargets);
    }

    private class CrossTargetHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private Ability chosenAbility;
        private List<BoardSpace> possibleBoardSpaces;
        private List<CharacterUnit> possibleTargets;
        private List<BoardSpace> left;
        private List<BoardSpace> right;
        private List<BoardSpace> up;
        private List<BoardSpace> down;
        private List<BoardSpace> highlightedBoardSpaces = new LinkedList<>();
        private List<AbilityPreview> displayingAbilityPreview = new LinkedList<>();
        private int cursor = 0;

        public CrossTargetHandler(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces, List<CharacterUnit> possibleTargets,
                                  List<BoardSpace> left, List<BoardSpace> right, List<BoardSpace> up, List<BoardSpace> down) {
            this.activeUnit = activeUnit;
            this.chosenAbility = chosenAbility;
            this.possibleBoardSpaces = possibleBoardSpaces;
            this.possibleTargets = possibleTargets;
            this.left = left;
            this.right = right;
            this.up = up;
            this.down = down;
        }

        @Override
        public void handle(MouseEvent event) {
            if (repeatingCall(event)) return;
            BoardSpace destination;
            if (event.getSource().getClass() == BoardSpace.class) {
                 destination = (BoardSpace) event.getSource();
            } else {
                 CharacterSprite sprite = (CharacterSprite) event.getSource();
                 destination = sprite.getUnit().getBoardSpace();
            }


            if (event.getButton() == MouseButton.PRIMARY) {
                if (cursor == 0) {
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));

                    if (left.contains(destination)) highlightedBoardSpaces.addAll(left);
                    else if (right.contains(destination)) highlightedBoardSpaces.addAll(right);
                    else if (up.contains(destination)) highlightedBoardSpaces.addAll(up);
                    else highlightedBoardSpaces.addAll(down);

                    for (BoardSpace highlightedSpace : highlightedBoardSpaces) {
                        if (highlightedSpace.isOccupied()) {
                            AbilityPreview abilityPreview = new AbilityPreview(activeUnit, highlightedSpace.getOccupyingUnit(), chosenAbility);
                            displayingAbilityPreview.add(abilityPreview);
                        }
                    }

                    highlightedBoardSpaces.forEach(space -> space.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.HOVER_HIGHLIGHT_COLOR));
                    cursor++;
                } else if (highlightedBoardSpaces.contains(destination)) {
                    TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                    removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));
                    displayingAbilityPreview.forEach(Stage::close);

                    if (left.contains(destination)) activeUnit.takeAction(chosenAbility, left);
                    else if (right.contains(destination)) activeUnit.takeAction(chosenAbility, right);
                    else if (up.contains(destination)) activeUnit.takeAction(chosenAbility, up);
                    else activeUnit.takeAction(chosenAbility, down);
                }


            } else if (event.getButton() == MouseButton.SECONDARY)  {
                if (cursor == 0) {
                    removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));
                    TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                    BattleMenu.getInstance().displayCharacterMenu(activeUnit);
                } else {
                    highlightedBoardSpaces.forEach(space -> space.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.ABILITY_HIGHLIGHT_COLOUR));
                    highlightedBoardSpaces.clear();
                    displayingAbilityPreview.forEach(Stage::close);
                    possibleBoardSpaces.forEach(space -> space.addEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
                    possibleBoardSpaces.forEach(space -> space.addEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));
                    cursor--;
                }

            }
        }

        private boolean repeatingCall(Event event) {
            if (event.getSource().getClass() == BoardSpace.class) {
                BoardSpace boardSpace = (BoardSpace) event.getSource();
                return boardSpace.isOccupied();
            }
            return false;
        }
    }
}
