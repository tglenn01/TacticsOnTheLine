package main.model.combatSystem.targetTypes;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.graphics.menus.BattleMenu;
import main.model.graphics.sceneElements.images.CharacterSprite;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.ui.TacticBaseBattle;

import java.util.LinkedList;
import java.util.List;

public class ItemTarget extends AreaTarget {

    public void setHandlers(CharacterUnit activeUnit, Consumable item, List<BoardSpace> possibleBoardSpaces) {
        List<CharacterUnit> possibleTargets = TacticBaseBattle.getInstance().getCurrentBoard().getPossibleTargets(possibleBoardSpaces);

        ApplyItemTargetHandler applyItemTargetHandler = new ApplyItemTargetHandler(activeUnit, item, possibleBoardSpaces, possibleTargets);

        setHandlersToNodes(applyItemTargetHandler, possibleBoardSpaces, possibleTargets);
    }

    // the same targeting as AreaTarget only we take an itemActionInsteadOf
    private class ApplyItemTargetHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private Consumable item;
        private List<BoardSpace> possibleBoardSpaces;
        private List<CharacterUnit> possibleTargets;


        public ApplyItemTargetHandler(CharacterUnit activeUnit, Consumable item, List<BoardSpace> possibleBoardSpaces,
                                  List<CharacterUnit> possibleTargets) {
            this.activeUnit = activeUnit;
            this.item = item;
            this.possibleBoardSpaces = possibleBoardSpaces;
            this.possibleTargets = possibleTargets;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                CharacterSprite targetSprite = (CharacterSprite) event.getSource();
                CharacterUnit targetUnit = targetSprite.getUnit();

                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);

                List<BoardSpace> targetedBoardSpaces = new LinkedList<>();
                ConsumableAbility consumableAbility = ConsumableItemInventory.getInstance().getItemAbility();

                addAreaOfEffectToTargetedBoardSpace(consumableAbility, targetUnit.getBoardSpace(), targetedBoardSpaces);

                activeUnit.takeItemAction(consumableAbility, item, targetedBoardSpaces);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                BattleMenu.getInstance().displayCharacterMenu(activeUnit);
            }
        }
    }
}
