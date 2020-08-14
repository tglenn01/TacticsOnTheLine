package main.model.characterSystem;

import javafx.scene.control.Label;
import javafx.stage.Popup;
import main.exception.MenuOpenedException;
import main.exception.OutOfActionsException;
import main.exception.OutOfManaException;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.combatSystem.abilities.MovementAbility;
import main.model.graphics.menus.BattleMenu;
import main.model.graphics.menus.LevelUpMenu;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.model.jobSystem.Job;
import main.ui.TacticBaseBattle;

public abstract class PlayableCharacterUnit extends CharacterUnit {
    protected Ability personalAbility;
    protected StatBonus personalStatBonus;

    public PlayableCharacterUnit() {
        setBaseJob();
        this.characterStatSheet = new StatSheet(this.characterJob);
        setPersonalAbility();
        setGrowthRate();
        addPersonalAbilityToAbilityList();
        addStatBonusToStats();
    }

    protected abstract void setPersonalAbility();

    protected abstract void setGrowthRate();

    protected abstract void setBaseJob();

    protected void addPersonalAbilityToAbilityList() {
        this.abilityList.add(personalAbility);
    }

    protected void addStatBonusToStats() {
        this.characterStatSheet.addStatBonus(personalStatBonus);
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

    @Override
    public void setJob(Job job) {
        super.setJob(job);
        if (this.personalAbility != null) addPersonalAbilityToAbilityList();
        if (this.personalStatBonus != null && this.characterStatSheet != null)
            this.characterStatSheet.addStatBonus(personalStatBonus);
    }

    public void useAbility(Ability chosenAbility) {
        try {
            hasActionToken(chosenAbility);
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
            ConsumableAbility consumableAbility = ConsumableItemInventory.getInstance().getItemAbility();
            hasActionToken(consumableAbility);
            consumableAbility.getTargets(this, item);
        } catch (OutOfActionsException outOfActionsException) {
            outOfActionsException.printOutOfActionsError();
        }  catch (MenuOpenedException e) {
            // menu is opened and no actions are taken
        }
    }

    // if it is a movement ability, if movement token is false don't take action
    // if it is a normal ability, if we have no action token then don't take action
    private void hasActionToken(Ability chosenAbility) throws OutOfActionsException {
        if (chosenAbility.getClass() == MovementAbility.class && !movementToken) throw new OutOfActionsException();
        else if (chosenAbility.getClass() != MovementAbility.class && actionTokens <= 0) throw new OutOfActionsException();
    }

    public void takeNextAction() {
        if (!LevelUpMenu.isDisplaying) BattleMenu.getInstance().displayCharacterMenu(this);
    }
}
