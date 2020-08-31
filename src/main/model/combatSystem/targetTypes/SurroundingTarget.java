package main.model.combatSystem.targetTypes;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.TargetType;
import main.model.graphics.menus.BattleMenu;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public class SurroundingTarget extends TargetType {

    @Override
    public void setTargetTypeImage() {
        this.targetTypeImage = new ImageView(new Image("resources/targetTypeImages/SurroundingTargetType.png"));
    }


    @Override
    public List<BoardSpace> getTargetPattern(BoardSpace centreSpace, int range, Ability chosenAbility) {
        List<BoardSpace> possibleBoardSpaces = new ArrayList<>();
        Board currentBoard = TacticBaseBattle.getInstance().getCurrentBoard();
        BoardSpace[][] boardSpaces = currentBoard.getBoardSpaces();
        int xPos = centreSpace.getXCoordinate();
        int yPos = centreSpace.getYCoordinate();

        if (xPos - 1 >= 0) possibleBoardSpaces.add(boardSpaces[xPos - 1][yPos]);
        if (yPos - 1 >= 0) possibleBoardSpaces.add(boardSpaces[xPos][yPos - 1]);
        if (xPos + 1 < currentBoard.getBoardWidth()) possibleBoardSpaces.add(boardSpaces[xPos + 1][yPos]);
        if (yPos + 1 < currentBoard.getBoardHeight()) possibleBoardSpaces.add(boardSpaces[xPos][yPos + 1]);
        return possibleBoardSpaces;
    }

    @Override
    public void setHandlers(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces) {
        List<CharacterUnit> possibleTargets = new ArrayList<>();
        possibleBoardSpaces.forEach(space -> {
            if (space.isOccupied()) possibleTargets.add(space.getOccupyingUnit());
        });

        SurroundingTargetHandler surroundingTargetHandler = new SurroundingTargetHandler(activeUnit, chosenAbility, possibleBoardSpaces, possibleTargets);

        setHandlersToNodes(surroundingTargetHandler, possibleBoardSpaces, possibleTargets);
    }


    private class SurroundingTargetHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private Ability chosenAbility;
        private List<BoardSpace> possibleBoardSpaces;
        private List<CharacterUnit> possibleTargets;

        public SurroundingTargetHandler(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces, List<CharacterUnit> possibleTargets) {
            this.activeUnit = activeUnit;
            this.chosenAbility = chosenAbility;
            this.possibleBoardSpaces = possibleBoardSpaces;
            this.possibleTargets = possibleTargets;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                activeUnit.takeAction(chosenAbility, possibleBoardSpaces);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                BattleMenu.getInstance().displayCharacterMenu(activeUnit);
            }
        }
    }
}
