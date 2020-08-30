package main.model.combatSystem.targetTypes;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.TargetType;
import main.model.graphics.menus.BattleMenu;
import main.model.graphics.sceneElements.images.CharacterSprite;
import main.ui.TacticBaseBattle;

import java.util.LinkedList;
import java.util.List;

public class CrossTarget extends TargetType {
    @Override
    public List<BoardSpace> getTargetPattern(BoardSpace centreSpace, int range, Ability chosenAbility) {
        List<BoardSpace> possibleBoardSpaces = new LinkedList<>();
        Board currentBoard = TacticBaseBattle.getInstance().getCurrentBoard();
        BoardSpace[][] boardSpaces = currentBoard.getBoardSpaces();
        int xPos = centreSpace.getXCoordinate();
        int yPos = centreSpace.getYCoordinate();

        for (int i = 0; i <= range; i++) {
            if (xPos - i >= 0)
                possibleBoardSpaces.add(boardSpaces[xPos - i][yPos]); // left
            if (xPos + i < currentBoard.getBoardWidth())
                possibleBoardSpaces.add(boardSpaces[xPos + i][yPos]); // right
            if (yPos + i < currentBoard.getBoardHeight())
                possibleBoardSpaces.add(boardSpaces[xPos][yPos + i]); // up
            if (yPos - i >= 0)
                possibleBoardSpaces.add(boardSpaces[xPos][yPos - i]); // down
            if (i == range - 1) {
                if ((xPos - i >= 0) && (yPos + 1 >= 0))
                    possibleBoardSpaces.add(boardSpaces[xPos - i][yPos + 1]);
                if ((xPos - i >= 0) && (yPos - 1 >= 0))
                    possibleBoardSpaces.add(boardSpaces[xPos - i][yPos - 1]);
                if ((xPos + i < currentBoard.getBoardWidth()) && (yPos - 1 >= 0))
                    possibleBoardSpaces.add(boardSpaces[xPos + i][yPos - 1]);
                if ((xPos + i < currentBoard.getBoardWidth()) && (yPos + 1 < currentBoard.getBoardHeight()))
                    possibleBoardSpaces.add(boardSpaces[xPos + i][yPos + 1]);
                if ((xPos - 1 >= 0) && (yPos - i >= 0))
                    possibleBoardSpaces.add(boardSpaces[xPos - 1][yPos - i]);
                if ((xPos - 1 >= 0) && (yPos + i < currentBoard.getBoardHeight()))
                    possibleBoardSpaces.add(boardSpaces[xPos - 1][yPos + i]);
                if ((xPos + 1 < currentBoard.getBoardWidth()) && (yPos - i >= 0))
                    possibleBoardSpaces.add(boardSpaces[xPos + 1][yPos - i]);
                if ((xPos + 1 < currentBoard.getBoardWidth()) && (yPos + i < currentBoard.getBoardHeight()))
                    possibleBoardSpaces.add(boardSpaces[xPos + 1][yPos + i]);
            }
        }
        return removeDuplicates(possibleBoardSpaces);
    }

    private List<BoardSpace> removeDuplicates(List<BoardSpace> possibleBoardSpaces) {
        List<BoardSpace> filteredList = new LinkedList<>();
        for (BoardSpace boardSpace : possibleBoardSpaces) {
            if (!filteredList.contains(boardSpace)) filteredList.add(boardSpace);
        }
        return filteredList;
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
            BoardSpace destination;
            if (event.getSource().getClass() == BoardSpace.class) {
                 destination = (BoardSpace) event.getSource();
            } else {
                 CharacterSprite sprite = (CharacterSprite) event.getSource();
                 destination = sprite.getUnit().getBoardSpace();
            }

            if (event.getButton() == MouseButton.PRIMARY) {
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);

                if (left.contains(destination)) activeUnit.takeAction(chosenAbility, left);
                else if (right.contains(destination)) activeUnit.takeAction(chosenAbility, right);
                else if (up.contains(destination)) activeUnit.takeAction(chosenAbility, up);
                else activeUnit.takeAction(chosenAbility, down);

            } else if (event.getButton() == MouseButton.SECONDARY)  {
                removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                BattleMenu.getInstance().displayCharacterMenu(activeUnit);
            }
        }
    }
}
