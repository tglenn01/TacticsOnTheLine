package main.model.characterSystem;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;
import main.exception.OutOfManaException;
import main.model.boardSystem.BoardSpace;
import main.model.combatSystem.Ability;
import main.model.graphics.menus.AbilityMenu;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.ui.TacticBaseBattle;

import java.util.List;

public abstract class PlayableCharacterUnit extends CharacterUnit {

    public PlayableCharacterUnit() {
    }

    @Override
    public void startTurn() {
        System.out.println("It is " + this.characterName + "'s turn, they have " +
                characterStatSheet.getMana() + " mana");
        statusEffects.updateStatusEffect(this);
        //startOfTurnNotification();
        this.actionTokens = ACTIONS_PER_TURN;
    }

    private void startOfTurnNotification() {
        Label turnStartNotification = new Label("It is " + this.characterName + "'s Turn");
    }

    public void useAbility(Ability chosenAbility) {
        try {
            chosenAbility.hasEnoughMana(this);
            TacticBaseBattle.getInstance().getCurrentBoard().displayValidAbilitySpaces(this, chosenAbility.getRange());
            this.getTarget(chosenAbility);
        } catch (OutOfManaException e) {
            Popup outOfManaMessage = new Popup();
            outOfManaMessage.getContent().add(new Label("Out of Mana Choose Again"));
            outOfManaMessage.show(TacticBaseBattle.getInstance().getPrimaryStage());
        }
    }

    public void useItem(Consumable item) {
        Ability itemAbility = ConsumableItemInventory.getInstance().getItemAbility();
        TacticBaseBattle.getInstance().getCurrentBoard().displayValidAbilitySpaces(this, itemAbility.getRange());
        this.getItemTarget(itemAbility, item);
    }

    public void takeMovement(Ability movementAbility) {
        try {
            movementAbility.takeAction(this, null);
        } catch (Exception e) {
            // can't die in movement
        }
    }

    public void getTarget(Ability ability) {
        if (ability.targetsSelf()) this.takeAction(ability, this);
        List<CharacterUnit> possibleTargets = TacticBaseBattle.getInstance().getCurrentBoard().getUnitsInRangeOfAbility(this);
        if (possibleTargets == null) AbilityMenu.display(this, this.getCharacterJob().getJobAbilityList());
        else {
            for (CharacterUnit possibleTarget : possibleTargets) {
                possibleTarget.getSprite().setOnMouseClicked(e -> {
                    TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                    takeAction(ability, possibleTarget);
                    e.consume();
                });
            }
        }
    }

    public void getItemTarget(Ability ability, Consumable item) {
        for (BoardSpace[] boardSpaceArray : TacticBaseBattle.getInstance().getCurrentBoard().getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                if (boardSpace.isOccupied()) {
                    ImageView sprite = boardSpace.getOccupyingUnit().getSprite();
                    sprite.setOnMouseClicked(e -> {
                        if (ability.isUnitInRange(this, boardSpace.getOccupyingUnit())) {
                            TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(this);
                            ability.takeAction(this, boardSpace.getOccupyingUnit(), item);
                        }
                    });
                }
            }
        }
    }

    protected void takeNextAction() {
        AbilityMenu.display(this, this.getCharacterJob().getJobAbilityList());
    }
}
