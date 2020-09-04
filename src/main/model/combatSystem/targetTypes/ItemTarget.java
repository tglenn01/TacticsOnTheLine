package main.model.combatSystem.targetTypes;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.model.boardSystem.BoardSpace;
import main.model.boardSystem.landTypes.LandType;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.graphics.menus.BattleMenu;
import main.model.graphics.sceneElements.images.CharacterSprite;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.model.battleSystem.TacticBaseBattle;

import java.util.LinkedList;
import java.util.List;

public class ItemTarget extends AreaTarget {

    public void setHandlers(CharacterUnit activeUnit, Consumable item, List<BoardSpace> possibleBoardSpaces) {
        List<CharacterUnit> possibleTargets = TacticBaseBattle.getInstance().getCurrentBoard().getPossibleTargets(possibleBoardSpaces);
        possibleBoardSpaces.forEach(space -> space.addEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
        possibleBoardSpaces.forEach(space -> space.addEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));


        ItemTargetHandler itemTargetHandler = new ItemTargetHandler(activeUnit, item, possibleBoardSpaces, possibleTargets);

        setHandlersToNodes(itemTargetHandler, possibleBoardSpaces, possibleTargets);
    }

    // the same targeting as AreaTarget only we take an itemActionInsteadOf
    private class ItemTargetHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private Consumable item;
        private List<BoardSpace> possibleBoardSpaces;
        private List<CharacterUnit> possibleTargets;
        private int cursor = 0;
        private CharacterUnit highlightedUnit;


        public ItemTargetHandler(CharacterUnit activeUnit, Consumable item, List<BoardSpace> possibleBoardSpaces,
                                 List<CharacterUnit> possibleTargets) {
            this.activeUnit = activeUnit;
            this.item = item;
            this.possibleBoardSpaces = possibleBoardSpaces;
            this.possibleTargets = possibleTargets;
        }

        @Override
        public void handle(MouseEvent event) {
            if (repeatingCall(event)) return;;

            if (event.getButton() == MouseButton.SECONDARY) {
                if (cursor == 0) {
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));
                    removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);
                    TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                    BattleMenu.getInstance().displayItemMenu(activeUnit);
                } else {
                    possibleBoardSpaces.forEach(space -> space.addEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
                    possibleBoardSpaces.forEach(space -> space.addEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));
                    highlightedUnit.getBoardSpace().changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.ABILITY_HIGHLIGHT_COLOUR);
                    maintainHighlightOnMouseSpace(event);
                    cursor--;
                }
            } else if (event.getButton() == MouseButton.PRIMARY) {
                if (event.getSource().getClass().getSuperclass() != CharacterSprite.class) return;
                CharacterSprite sprite = (CharacterSprite) event.getSource();
                CharacterUnit targetUnit = sprite.getUnit();

                if (cursor == 0) {
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));
                    targetUnit.getBoardSpace().changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.HOVER_HIGHLIGHT_COLOR);
                    cursor++;
                    highlightedUnit = targetUnit;
                } else if (cursor == 1 && targetUnit == highlightedUnit) {
                    TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                    removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);

                    List<BoardSpace> targetedBoardSpaces = new LinkedList<>();
                    ConsumableAbility consumableAbility = ConsumableItemInventory.getInstance().getItemAbility();

                    activeUnit.takeItemAction(consumableAbility, item, targetedBoardSpaces);
                }


            }
        }

        // as boardSpace and characterSprite overlap, the handle is called twice, this prevents that
        private boolean repeatingCall(Event event) {
            if (event.getSource().getClass() == BoardSpace.class) {
                BoardSpace boardSpace = (BoardSpace) event.getSource();
                return boardSpace.isOccupied();
            }
            return false;
        }

        private void maintainHighlightOnMouseSpace(MouseEvent event) {
            BoardSpace boardSpace;
            if (event.getSource().getClass() == BoardSpace.class) {
                boardSpace = (BoardSpace) event.getSource();

            } else {
                CharacterSprite sprite = (CharacterSprite) event.getSource();
                boardSpace = sprite.getUnit().getBoardSpace();
            }
            if (possibleBoardSpaces.contains(boardSpace))
                boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.HOVER_HIGHLIGHT_COLOR);
        }
    }
}
