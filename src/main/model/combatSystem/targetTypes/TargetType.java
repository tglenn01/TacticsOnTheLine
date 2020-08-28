package main.model.combatSystem.targetTypes;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.graphics.menus.BattleMenu;
import main.model.graphics.sceneElements.images.CharacterSprite;
import main.ui.TacticBaseBattle;

import java.util.LinkedList;
import java.util.List;

public abstract class TargetType {

    public abstract List<BoardSpace> getTargetPattern(BoardSpace centreSpace, int range, Ability chosenAbility);

    // Overloaded in MovementTarget private class in the MovementAbility class, which will display
    // movement spaces instead which requires activeUnit
    public void displayTargets(CharacterUnit activeUnit, List<BoardSpace> possibleBoardSpaces) {
        TacticBaseBattle.getInstance().getCurrentBoard().displayAbilitySpaces(possibleBoardSpaces);
    }

    public abstract void setHandlers(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces);

    public void addAreaOfEffectToTargetedBoardSpace(Ability chosenAbility, BoardSpace targetBoardSpace, List<BoardSpace> targetedBoardSpaces) {
        TargetType areaOfEffectTargetType = new AreaTarget();
        List<BoardSpace> effectedBoardSpaces = areaOfEffectTargetType.getTargetPattern(targetBoardSpace, chosenAbility.getAreaOfEffect() - 1, chosenAbility);
        for (BoardSpace effectedSpace : effectedBoardSpaces) {
            if (effectedSpace.isOccupied()) targetedBoardSpaces.add(effectedSpace);
        }
    }

    // remove handlers from list of CharacterUnit
    public void removeTargetHandler(List<CharacterUnit> possibleTargets, EventHandler<MouseEvent> applyTargetHandler) {
        for (CharacterUnit possibleTarget : possibleTargets) {
            possibleTarget.getCharacterSprite().getSprite().removeEventHandler(MouseEvent.MOUSE_CLICKED, applyTargetHandler);
        }
    }


    // remove handlers from list of BoardSpace
    public void removeBoardHandler(List<BoardSpace> boardSpaces, EventHandler<MouseEvent> returnToMenuHandler) {
        for (BoardSpace boardSpace : boardSpaces) {
            boardSpace.removeEventHandler(MouseEvent.MOUSE_CLICKED, returnToMenuHandler);
        }

    }

    protected class ApplyTargetHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private Ability chosenAbility;
        private List<BoardSpace> possibleBoardSpaces;
        private List<CharacterUnit> possibleTargets;
        private EventHandler<MouseEvent> returnToMenuHandler;


        public ApplyTargetHandler(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces,
                                  List<CharacterUnit> possibleTargets, EventHandler<MouseEvent> returnToMenuHandler) {
            this.activeUnit = activeUnit;
            this.chosenAbility = chosenAbility;
            this.possibleBoardSpaces = possibleBoardSpaces;
            this.possibleTargets = possibleTargets;
            this.returnToMenuHandler = returnToMenuHandler;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                CharacterSprite targetSprite = (CharacterSprite) event.getSource();
                CharacterUnit targetUnit = targetSprite.getUnit();

                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                removeBoardHandler(possibleBoardSpaces, returnToMenuHandler);
                removeTargetHandler(possibleTargets, this);

                List<BoardSpace> targetedBoardSpaces = new LinkedList<>();
                addAreaOfEffectToTargetedBoardSpace(chosenAbility, targetUnit.getBoardSpace(), targetedBoardSpaces);

                activeUnit.takeAction(chosenAbility, targetedBoardSpaces);
            }
        }
    }

    protected class ReturnToMenuHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private List<BoardSpace> possibleBoardSpaces;
        private List<CharacterUnit> possibleTargets;
        private EventHandler<MouseEvent> applyTargetHandler;

        public ReturnToMenuHandler(CharacterUnit activeUnit, List<BoardSpace> possibleBoardSpaces,
                                   List<CharacterUnit> possibleTargets) {
            this.activeUnit = activeUnit;
            this.possibleBoardSpaces = possibleBoardSpaces;
            this.possibleTargets = possibleTargets;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
                removeBoardHandler(possibleBoardSpaces, this);
                removeTargetHandler(possibleTargets, applyTargetHandler);
                BattleMenu.getInstance().displayCharacterMenu(activeUnit);
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
            }
        }

        public void setApplyTargetHandler(EventHandler<MouseEvent> applyTargetHandler) {
            this.applyTargetHandler = applyTargetHandler;
        }
    }




}
