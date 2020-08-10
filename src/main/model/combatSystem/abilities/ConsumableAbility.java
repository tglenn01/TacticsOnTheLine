package main.model.combatSystem.abilities;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.exception.MenuOpenedException;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.sceneElements.images.CharacterSprite;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.ui.TacticBaseBattle;

import java.util.LinkedList;
import java.util.List;

public class ConsumableAbility extends SupportiveAbility {

    public ConsumableAbility() {
        super("Item", 0, 1, 1, "Use an item on an ally");
    }

    public void getTargets(CharacterUnit activeUnit, Consumable item) throws MenuOpenedException {
        List<BoardSpace> possibleBoardSpaces = getTargetedBoardSpaces(activeUnit);
        Board currentBoard = TacticBaseBattle.getInstance().getCurrentBoard();
        currentBoard.displayAbilitySpaces(possibleBoardSpaces);

        setHandlers(activeUnit, possibleBoardSpaces, item);
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
    protected void resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {

    }

    public void takeAction(Consumable item, List<BoardSpace> targetedBoardSpaces) {
        for (BoardSpace boardSpace : targetedBoardSpaces) {
            if (boardSpace.isOccupied()) {
                CharacterUnit receivingUnit = boardSpace.getOccupyingUnit();
                item.applyItem(receivingUnit);
            }
        }
        ConsumableItemInventory.getInstance().removeConsumableItem(item);
    }

    public boolean targetsAlly() {
        return true;
    }


    protected void setHandlers(CharacterUnit activeUnit, List<BoardSpace> possibleBoardSpaces, Consumable item) throws MenuOpenedException {
        List<CharacterUnit> possibleTargets = TacticBaseBattle.getInstance().getCurrentBoard().getPossibleTargets(possibleBoardSpaces);

        ReturnToMenuHandler returnToMenuHandler = new ReturnToMenuHandler(activeUnit, possibleBoardSpaces, possibleTargets);
        ApplyItemTargetHandler applyItemTargetHandler = new ApplyItemTargetHandler(activeUnit, item, possibleBoardSpaces, possibleTargets, returnToMenuHandler);
        returnToMenuHandler.setApplyTargetHandler(applyItemTargetHandler);

        for (CharacterUnit possibleTarget : possibleTargets) {
            possibleTarget.getCharacterSprite().addEventHandler(MouseEvent.MOUSE_CLICKED, applyItemTargetHandler);
        }

        for (BoardSpace boardSpace : possibleBoardSpaces) {
            boardSpace.addEventHandler(MouseEvent.MOUSE_CLICKED, returnToMenuHandler);
        }
    }

    private class ApplyItemTargetHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private Consumable item;
        private List<BoardSpace> possibleBoardSpaces;
        private List<CharacterUnit> possibleTargets;
        private EventHandler<MouseEvent> returnToMenuHandler;


        public ApplyItemTargetHandler(CharacterUnit activeUnit, Consumable item, List<BoardSpace> possibleBoardSpaces,
                                      List<CharacterUnit> possibleTargets, EventHandler<MouseEvent> returnToMenuHandler) {
            this.activeUnit = activeUnit;
            this.item = item;
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
                //targetedBoardSpaces.add(targetUnit.getBoardSpace());
                addTargetsToTargetedBoardSpaces(targetUnit.getBoardSpace(), targetedBoardSpaces);

                activeUnit.takeItemAction(ConsumableItemInventory.getInstance().getItemAbility(), item, targetedBoardSpaces);
            }
        }
    }

    @Override
    public List<BoardSpace> getTargetedBoardSpaces(CharacterUnit activeUnit) {
        return getNormalTargetPattern(activeUnit.getBoardSpace(), this.range, this);
    }
}
