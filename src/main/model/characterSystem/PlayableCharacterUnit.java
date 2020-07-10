package main.model.characterSystem;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import main.exception.OutOfActionsException;
import main.exception.OutOfManaException;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.graphics.menus.AbilityMenu;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.model.jobSystem.Job;
import main.ui.TacticBaseBattle;

import java.util.List;

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
            this.getTarget(chosenAbility);
        } catch (OutOfActionsException outOfActionsException) {
            outOfActionsException.printOutOfActionsError();
        } catch (OutOfManaException e) {
            Popup outOfManaMessage = new Popup();
            outOfManaMessage.getContent().add(new Label("Out of Mana Choose Again"));
            outOfManaMessage.show(TacticBaseBattle.getInstance().getPrimaryStage());
        }
    }

    private void hasActionToken() throws OutOfActionsException {
        if (actionTokens <= 0) throw new OutOfActionsException();
    }

    public void useItem(Consumable item) {
        ConsumableAbility itemAbility = ConsumableItemInventory.getInstance().getItemAbility();
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
        Board board = TacticBaseBattle.getInstance().getCurrentBoard();

        board.displayValidAbilitySpaces(this, chosenAbility.getRange());
        List<CharacterUnit> possibleTargets = board.getUnitsInRangeOfAbility(this, chosenAbility);
        if (chosenAbility.targetsSelf()) {
            board.stopShowingAbilitySpaces();
            possibleTargets.clear();
            this.takeAction(chosenAbility, this);
        } else if (possibleTargets.isEmpty()) {
            board.stopShowingAbilitySpaces();
            AbilityMenu.display(this, this.abilityList);
        } else {
            for (CharacterUnit possibleTarget : possibleTargets) {
                EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (possibleTargets.contains(possibleTarget) && (event.getButton() == MouseButton.PRIMARY)) {
                            possibleTargets.clear();
                            board.stopShowingAbilitySpaces();
                            takeAction(chosenAbility, possibleTarget);
                        }
                        possibleTarget.getSprite().removeEventHandler(MouseEvent.MOUSE_RELEASED, this);
                    }
                };
                possibleTarget.getSprite().addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
            }
            for (BoardSpace boardSpace : TacticBaseBattle.getInstance().getCurrentBoard().getAbilityHighlightedSpace()) {
                boardSpace.setOnMouseClicked(event -> {
                    if (!possibleTargets.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                        possibleTargets.clear();
                        board.stopShowingAbilitySpaces();
                        if (!AbilityMenu.isDisplaying()) {
                            AbilityMenu.display(this, this.abilityList);
                        }
                    }
                });
            }
        }
    }

    public void getItemTarget(ConsumableAbility itemAbility, Consumable item) {
        Board board = TacticBaseBattle.getInstance().getCurrentBoard();

       board.displayValidAbilitySpaces(this, itemAbility.getRange());
        List<CharacterUnit> possibleTargets = board.getUnitsInRangeOfAbility(this, itemAbility);

        for (CharacterUnit possibleTarget : possibleTargets) {
            ImageView sprite = possibleTarget.getSprite();
            sprite.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (possibleTargets.contains(possibleTarget) && (event.getButton() == MouseButton.PRIMARY)) {
                        board.stopShowingAbilitySpaces();
                        possibleTargets.clear();
                        sprite.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                        takeItemAction(itemAbility, item, boardSpace.getOccupyingUnit());
                    }
                }
            });
        }
    }

    protected void takeNextAction() {
        AbilityMenu.display(this, this.abilityList);
    }
}
