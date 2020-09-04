package main.model.combatSystem.targetTypes;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.model.boardSystem.BoardSpace;
import main.model.boardSystem.landTypes.LandType;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.TargetType;
import main.model.graphics.menus.BattleMenu;
import main.model.battleSystem.TacticBaseBattle;

import java.util.LinkedList;
import java.util.List;

public class MovementTarget extends TargetType {
    private EventHandler<MouseEvent> exitMovementHandler = event -> {
        BoardSpace boardSpace = (BoardSpace) event.getSource();
        boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.NON_ACTIVE_UNIT_MOVEMENT_HIGHLIGHT_COLOUR);
    };

    @Override
    public void setTargetTypeImage() {
        this.targetTypeImage = new ImageView(new Image("resources/targetTypeImages/AreaTargetType.png"));
    }

    @Override
    public List<BoardSpace> getTargetPattern(BoardSpace centreSpace, int range, Ability chosenAbility) {
        return TacticBaseBattle.getInstance().getCurrentBoard().getMovementArea(centreSpace.getOccupyingUnit());
    }

    @Override
    public void displayTargets(CharacterUnit activeUnit, List<BoardSpace> possibleBoardSpaces) {
        TacticBaseBattle.getInstance().getCurrentBoard().displayMovementSpaces(activeUnit, possibleBoardSpaces);
    }

    @Override
    public void setHandlers(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces) {
        for (BoardSpace boardSpace : possibleBoardSpaces) {
            boardSpace.addEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler);
            boardSpace.addEventHandler(MouseEvent.MOUSE_EXITED, exitMovementHandler);
        }

        MovementTargetHandler movementTargetHandler = new MovementTargetHandler(activeUnit, chosenAbility, possibleBoardSpaces);
        setHandlersToNodes(movementTargetHandler, possibleBoardSpaces, null);
    }

    protected class MovementTargetHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private Ability chosenAbility;
        private List<BoardSpace> possibleBoardSpaces;


        public MovementTargetHandler(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces) {
            this.activeUnit = activeUnit;
            this.chosenAbility = chosenAbility;
            this.possibleBoardSpaces = possibleBoardSpaces;
        }

        @Override
        public void handle(MouseEvent event) {
            BoardSpace destination = (BoardSpace) event.getSource();
            if (event.getButton() == MouseButton.PRIMARY && !destination.isOccupied()) {
                possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
                possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_EXITED, exitMovementHandler));
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(activeUnit);
                removeHandlersFromNodes(this, possibleBoardSpaces, null);

                List<BoardSpace> targetedBoardSpaces = new LinkedList<>();
                targetedBoardSpaces.add(destination);

                activeUnit.takeAction(chosenAbility, targetedBoardSpaces);
            } else if (event.getButton() == MouseButton.SECONDARY)  {
                possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
                possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_EXITED, exitMovementHandler));
                removeHandlersFromNodes(this, possibleBoardSpaces, null);
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(activeUnit);
                BattleMenu.getInstance().displayCharacterMenu(activeUnit);
            }
        }
    }
}

