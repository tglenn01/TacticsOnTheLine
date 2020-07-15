package main.model.characterSystem;

import javafx.scene.control.Label;
import javafx.stage.Popup;
import main.exception.MenuOpenedException;
import main.exception.OutOfActionsException;
import main.exception.OutOfManaException;
import main.model.combatSystem.Ability;
import main.model.graphics.menus.AbilityMenu;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.model.jobSystem.Job;
import main.ui.TacticBaseBattle;

public abstract class PlayableCharacterUnit extends CharacterUnit {
    protected Ability personalAbility;
    protected StatBonus personalStatBonus;

    public PlayableCharacterUnit() {
        setBaseJob();
        this.characterStatSheet = new StatSheet(this.characterJob, this.personalStatBonus);
        setPersonalAbility();
        addPersonalAbilityToAbilityList();
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
        TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(this);
        this.actionTokens = ACTIONS_PER_TURN;
        this.movementToken = !this.getStatusEffects().isRooted(); // if not rooted true, else false
    }

    private void startOfTurnNotification() {
        Label turnStartNotification = new Label("It is " + this.characterName + "'s Turn");
    }

    @Override
    public void setJob(Job job) {
        super.setJob(job);
        if (this.personalAbility != null) addPersonalAbilityToAbilityList();
    }

    public void useAbility(Ability chosenAbility) {
        try {
            hasActionToken();
            chosenAbility.hasEnoughMana(this);
            chosenAbility.getTargets(this);
        } catch (OutOfActionsException outOfActionsException) {
            outOfActionsException.printOutOfActionsError();
        } catch (OutOfManaException e) {
            Popup outOfManaMessage = new Popup();
            outOfManaMessage.getContent().add(new Label("Out of Mana Choose Again"));
            outOfManaMessage.show(TacticBaseBattle.getInstance().getPrimaryStage());
        } catch (MenuOpenedException e) {
            // menu is opened and no actions are taken
        }
    }

    public void useItem(Consumable item) {
        try {
            hasActionToken();
            ConsumableItemInventory.getInstance().getItemAbility().getTargets(this, item);
        } catch (OutOfActionsException outOfActionsException) {
            outOfActionsException.printOutOfActionsError();
        }  catch (MenuOpenedException e) {
            // menu is opened and no actions are taken
        }

    }

    private void hasActionToken() throws OutOfActionsException {
        if (actionTokens <= 0) throw new OutOfActionsException();
    }

    public void takeMovement(Ability movementAbility) {
        try {
            if (movementToken) this.takeAction(movementAbility, null);
            else {
                Popup noMovement = new Popup();
                noMovement.getContent().add(new Label("Unit can no longer Move"));
                noMovement.show(TacticBaseBattle.getInstance().getPrimaryStage());
            }
        } catch (Exception e) {
            // can't die in movement
        }
    }

    protected void takeNextAction() {
        AbilityMenu.display(this, this.abilityList);
    }
}
