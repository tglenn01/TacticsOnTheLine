package main.model.combatSystem;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.targetTypes.AreaTarget;
import main.model.graphics.menus.BattleMenu;
import main.model.graphics.sceneElements.images.CharacterSprite;
import main.ui.TacticBaseBattle;

import java.util.LinkedList;
import java.util.List;

public abstract class TargetType {
    protected ImageView targetTypeImage;

    public TargetType() {
        setTargetTypeImage();
    }

    public abstract void setTargetTypeImage();

    public ImageView getTargetTypeImage() {
        return this.targetTypeImage;
    }

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


    protected void setHandlersToNodes(EventHandler<MouseEvent> handle, List<BoardSpace> possibleBoardSpaces, List<CharacterUnit> possibleTargets) {
        if (possibleBoardSpaces != null) {
            for (BoardSpace boardSpace : possibleBoardSpaces) {
                boardSpace.addEventHandler(MouseEvent.MOUSE_CLICKED, handle);
            }
        }

        if (possibleTargets != null) {
            for (CharacterUnit unit : possibleTargets) {
                unit.getCharacterSprite().addEventHandler(MouseEvent.MOUSE_CLICKED, handle);
            }
        }
    }

    protected void removeHandlersFromNodes(EventHandler<MouseEvent> handle, List<BoardSpace> possibleBoardSpaces, List<CharacterUnit> possibleTargets) {
        if (possibleBoardSpaces != null) {
            for (BoardSpace boardSpace : possibleBoardSpaces) {
                boardSpace.removeEventHandler(MouseEvent.MOUSE_CLICKED, handle);
            }
        }

        if (possibleTargets != null) {
            for (CharacterUnit unit : possibleTargets) {
                unit.getCharacterSprite().removeEventHandler(MouseEvent.MOUSE_CLICKED, handle);
            }
        }
    }


    protected class ApplyTargetHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private Ability chosenAbility;
        private List<BoardSpace> possibleBoardSpaces;
        private List<CharacterUnit> possibleTargets;


        public ApplyTargetHandler(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces,
                                  List<CharacterUnit> possibleTargets) {
            this.activeUnit = activeUnit;
            this.chosenAbility = chosenAbility;
            this.possibleBoardSpaces = possibleBoardSpaces;
            this.possibleTargets = possibleTargets;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY && (event.getSource().getClass().getSuperclass() == CharacterSprite.class)) {

                CharacterSprite targetSprite = (CharacterSprite) event.getSource();
                CharacterUnit targetUnit = targetSprite.getUnit();
                if (targetUnit == activeUnit) return;

                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);

                List<BoardSpace> targetedBoardSpaces = new LinkedList<>();
                addAreaOfEffectToTargetedBoardSpace(chosenAbility, targetUnit.getBoardSpace(), targetedBoardSpaces);

                activeUnit.takeAction(chosenAbility, targetedBoardSpaces);

            } else if (event.getButton() == MouseButton.SECONDARY) {
                removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);
                BattleMenu.getInstance().displayCharacterMenu(activeUnit);
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
            }
        }
    }
}
