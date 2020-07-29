package main.model.jobSystem.jobs.gunnerJob.gunnerAbilities;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.exception.MenuOpenedException;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.graphics.menus.BattleMenu;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BuckShotAbility extends PhysicalAbility {

    public BuckShotAbility() {
        super("Buckshot", 6, 4, 1, 20, .85,
                "Fire a cluster exploding on enemies in a unique patter");
    }


    @Override
    protected boolean targetsSelf() {
        return false;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return false;
    }

    @Override
    public boolean targetsAlly() {
        return false;
    }

    @Override
    protected List<BoardSpace> getBoardSpaces(CharacterUnit activeUnit) {
        List<BoardSpace> possibleBoardSpaces = new ArrayList<>();
        BoardSpace activeUnitBoardSpace = activeUnit.getBoardSpace();
        Board currentBoard = TacticBaseBattle.getInstance().getCurrentBoard();
        BoardSpace[][] boardSpaces = currentBoard.getBoardSpaces();
        int xPos = activeUnitBoardSpace.getXCoordinate();
        int yPos = activeUnitBoardSpace.getYCoordinate();

        for (int i = 5; i <= range; i++) {
            if (xPos - i >= 0)
                possibleBoardSpaces.add(boardSpaces[xPos - i][yPos]); // left
            if (xPos + i < currentBoard.getBoardWidth())
                possibleBoardSpaces.add(boardSpaces[xPos + i][yPos]); // right
            if (yPos + i < currentBoard.getBoardHeight())
                possibleBoardSpaces.add(boardSpaces[xPos][yPos + i]); // up
            if (yPos - i >= 0)
                possibleBoardSpaces.add(boardSpaces[xPos][yPos - i]); // down
            if (i == range - 1) {
                if ((xPos - i >= 0) && (yPos + i >= 0))
                    possibleBoardSpaces.add(boardSpaces[xPos - i][yPos + i]);
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
        return possibleBoardSpaces;
    }


    @Override
    protected void setHandlers(CharacterUnit activeUnit, List<BoardSpace> possibleBoardSpaces) throws MenuOpenedException {
        List<BoardSpace> left = new LinkedList<>();
        List<BoardSpace> right = new LinkedList<>();
        List<BoardSpace> up = new LinkedList<>();
        List<BoardSpace> down = new LinkedList<>();

        BoardSpace currentBoardSpace = activeUnit.getBoardSpace();
        int xPos = currentBoardSpace.getXCoordinate();
        int yPos = currentBoardSpace.getYCoordinate();

        for (BoardSpace boardSpace : possibleBoardSpaces) {
            int spaceX = boardSpace.getXCoordinate();
            int spaceY = boardSpace.getYCoordinate();

            if (spaceX > xPos && (yPos - 1 <= spaceY) && (yPos - 1 >= spaceY))
                right.add(boardSpace);
            else if (spaceX < xPos && (yPos - 1 <= spaceY) && (yPos - 1 >= spaceY))
                left.add(boardSpace);
            else if (spaceY > yPos && (xPos - 1 <= spaceX) && (xPos + 1 >= spaceX))
                up.add(boardSpace);
            else if (spaceY < yPos && (xPos - 1 <= spaceX) && (xPos + 1 >= spaceX))
                down.add(boardSpace);
        }

        ApplyBoardHandler applyBoardHandler = new ApplyBoardHandler(activeUnit, this, possibleBoardSpaces,
                left, right, up, down);

        for (BoardSpace boardSpace : possibleBoardSpaces) {
            boardSpace.addEventHandler(MouseEvent.MOUSE_CLICKED, applyBoardHandler);
        }
    }

    private class ApplyBoardHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private Ability chosenAbility;
        private List<BoardSpace> possibleBoardSpaces;
        private List<BoardSpace> left;
        private List<BoardSpace> right;
        private List<BoardSpace> up;
        private List<BoardSpace> down;

        public ApplyBoardHandler(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces,
                                 List<BoardSpace> left, List<BoardSpace> right, List<BoardSpace> up, List<BoardSpace> down) {
            this.activeUnit = activeUnit;
            this.chosenAbility = chosenAbility;
            this.possibleBoardSpaces = possibleBoardSpaces;
            this.left = left;
            this.right = right;
            this.up = up;
            this.down = down;
        }

        @Override
        public void handle(MouseEvent event) {
            BoardSpace destination = (BoardSpace) event.getSource();
            if (event.getButton() == MouseButton.PRIMARY && !destination.isOccupied()) {
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                removeBoardHandler(possibleBoardSpaces, this);

                if (left.contains(destination)) activeUnit.takeAction(chosenAbility, left);
                else if (right.contains(destination)) activeUnit.takeAction(chosenAbility, right);
                else if (up.contains(destination)) activeUnit.takeAction(chosenAbility, up);
                else activeUnit.takeAction(chosenAbility, down);

            } else if (event.getButton() == MouseButton.SECONDARY)  {
                removeBoardHandler(possibleBoardSpaces, this);
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                BattleMenu.getInstance().displayCharacterMenu(activeUnit);
            }
        }
    }
}
