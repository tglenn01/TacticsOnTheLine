package main.model.combatSystem.abilities;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.graphics.menus.BattleMenu;
import main.ui.TacticBaseBattle;

import java.util.LinkedList;
import java.util.List;

public class MovementAbility extends Ability {

    public MovementAbility() {
        super("Move", 0, 0, 0,
                 "Move your character");
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> targetedBoardSpaces) throws AttackMissedException, UnitIsDeadException {
        BoardSpace destination = targetedBoardSpaces.get(0);
        activeUnit.setBoardSpace(destination);
    }

    protected void resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        // this will not be called as it is called from the super takeAction which will not resolve in this class
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
        return getNormalTargetPattern(activeUnit.getBoardSpace(), activeUnit.getCharacterStatSheet().getMovement(), this);
    }

    @Override
    protected void setHandlers(CharacterUnit activeUnit, List<BoardSpace> possibleBoardSpaces) {
        ApplyBoardHandler applyBoardHandler = new ApplyBoardHandler(activeUnit, this, possibleBoardSpaces);

        for (BoardSpace boardSpace : possibleBoardSpaces) {
            boardSpace.addEventHandler(MouseEvent.MOUSE_CLICKED, applyBoardHandler);
        }

        // clicking on the sprite will open the menu and therefore must also close the event handlers to avoid bugs
    }

    protected class ApplyBoardHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private Ability chosenAbility;
        private List<BoardSpace> possibleBoardSpaces;
        private EventHandler<MouseEvent> characterUnitOpensMenuHandler;


        public ApplyBoardHandler(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces) {
            this.activeUnit = activeUnit;
            this.chosenAbility = chosenAbility;
            this.possibleBoardSpaces = possibleBoardSpaces;


            characterUnitOpensMenuHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(activeUnit);
                    removeBoardHandler(possibleBoardSpaces, this);
                    event.consume();
                }
            };
        }

        @Override
        public void handle(MouseEvent event) {
            BoardSpace destination = (BoardSpace) event.getSource();
            if (event.getButton() == MouseButton.PRIMARY && !destination.isOccupied()) {
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(activeUnit);
                removeBoardHandler(possibleBoardSpaces, this);
                activeUnit.getCharacterSprite().removeEventHandler(MouseEvent.MOUSE_CLICKED, characterUnitOpensMenuHandler);

                List<BoardSpace> targetedBoardSpaces = new LinkedList<>();
                targetedBoardSpaces.add(destination);

                activeUnit.takeAction(chosenAbility, targetedBoardSpaces);
            } else if (event.getButton() == MouseButton.SECONDARY)  {
                removeBoardHandler(possibleBoardSpaces, this);
                activeUnit.getCharacterSprite().removeEventHandler(MouseEvent.MOUSE_CLICKED, characterUnitOpensMenuHandler);
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(activeUnit);
                BattleMenu.getInstance().displayCharacterMenu(activeUnit);
            }
        }
    }

}
