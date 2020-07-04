package main.model.characterSystem;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import main.exception.OutOfManaException;
import main.model.combatSystem.Ability;
import main.model.graphics.menus.AbilityMenu;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.ui.TacticBaseBattle;

import java.util.List;

public abstract class PlayableCharacterUnit extends CharacterUnit {
    protected Ability personalAbility;
    protected StatBonus personalStatBonus;

    public PlayableCharacterUnit() {
        this.setPersonalAbility();
        this.setBaseJob();
        this.characterStatSheet = new StatSheet(this.characterJob, this.personalStatBonus);
    }

    protected abstract void setPersonalAbility();

    protected abstract void setBaseJob();

    protected void addPersonalAbilityToAbilityList() {
        this.abilityList.add(personalAbility);
    }

    @Override
    public void startTurn() {
        System.out.println("It is " + this.characterName + "'s turn, they have " +
                characterStatSheet.getMana() + " mana");
        statusEffects.updateStatusEffect(this);
        //startOfTurnNotification();
        this.actionTokens = ACTIONS_PER_TURN;
        this.movementToken = true;
    }

    private void startOfTurnNotification() {
        Label turnStartNotification = new Label("It is " + this.characterName + "'s Turn");
    }

    public void useAbility(Ability chosenAbility) {
        try {
            chosenAbility.hasEnoughMana(this);
            this.getTarget(chosenAbility);
        } catch (OutOfManaException e) {
            Popup outOfManaMessage = new Popup();
            outOfManaMessage.getContent().add(new Label("Out of Mana Choose Again"));
            outOfManaMessage.show(TacticBaseBattle.getInstance().getPrimaryStage());
        }
    }

    public void useItem(Consumable item) {
        Ability itemAbility = ConsumableItemInventory.getInstance().getItemAbility();
        this.getItemTarget(itemAbility, item);
    }

    public void takeMovement(Ability movementAbility) {
        try {
            if (movementToken) movementAbility.takeAction(this, null);
            else {
                Popup noMovement = new Popup();
                noMovement.getContent().add(new Label("Unit can no longer Move"));
                noMovement.show(TacticBaseBattle.getInstance().getPrimaryStage());
            }
        } catch (Exception e) {
            // can't die in movement
        }
    }

    public void getTarget(Ability chosenAbility) {
        TacticBaseBattle.getInstance().getCurrentBoard().displayValidAbilitySpaces(this, chosenAbility.getRange());
        List<CharacterUnit> possibleTargets = TacticBaseBattle.getInstance().getCurrentBoard().getUnitsInRangeOfAbility(this, chosenAbility);
        if (chosenAbility.targetsSelf()) {
            TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
            this.takeAction(chosenAbility, this);
        } else if (possibleTargets.isEmpty()) {
            TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
            AbilityMenu.display(this, this.getCharacterJob().getJobAbilityList());
        } else {
            for (CharacterUnit possibleTarget : possibleTargets) {
                EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (possibleTargets.contains(possibleTarget) && (event.getButton() == MouseButton.PRIMARY)) {
                            possibleTargets.clear();
                            TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                            takeAction(chosenAbility, possibleTarget);
                        }
                        possibleTarget.getSprite().removeEventHandler(MouseEvent.MOUSE_RELEASED, this);
                    }
                };
                possibleTarget.getSprite().addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
            }
        }
    }

    public void getItemTarget(Ability itemAbility, Consumable item) {
        TacticBaseBattle.getInstance().getCurrentBoard().displayValidAbilitySpaces(this, itemAbility.getRange());
        List<CharacterUnit> possibleTargets = TacticBaseBattle.getInstance().getCurrentBoard().getUnitsInRangeOfAbility(this, itemAbility);

        for (CharacterUnit possibleTarget : possibleTargets) {
            ImageView sprite = possibleTarget.getSprite();
            sprite.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (possibleTargets.contains(possibleTarget) && (event.getButton() == MouseButton.PRIMARY)) {
                        TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                        possibleTargets.clear();
                        sprite.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                        takeItemAction(itemAbility, boardSpace.getOccupyingUnit(), item);
                    }
                }
            });
        }
    }

    protected void takeNextAction() {
        AbilityMenu.display(this, this.getCharacterJob().getJobAbilityList());
    }
}
