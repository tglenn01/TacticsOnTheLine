package main.model.characterSystem;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import main.exception.OutOfManaException;
import main.model.boardSystem.BoardSpace;
import main.model.combatSystem.Ability;
import main.model.graphics.menus.AbilityMenu;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.ui.Battle;
import main.ui.TacticBaseBattle;

public abstract class PlayableCharacterUnit extends CharacterUnit {

    public PlayableCharacterUnit() {
    }

    @Override
    public void startTurn(Battle battle) {
        System.out.println("It is " + this.characterName + "'s turn, they have " +
                characterStatSheet.getMana() + " mana");
        statusEffects.updateStatusEffect(this);
        //startOfTurnNotification();
        this.actionTokens = ACTIONS_PER_TURN;
        this.getSprite().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!AbilityMenu.isDisplaying()) {
                    AbilityMenu.display(getCharacterUnit(), battle, getCharacterJob().getJobAbilityList());
                }
                getCharacterUnit().getSprite().removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        });
    }

    private void startOfTurnNotification() {
        Label turnStartNotification = new Label("It is " + this.characterName + "'s Turn");
    }

    public void useAbility(Battle battle, Ability chosenAbility) {
        try {
            chosenAbility.hasEnoughMana(this);
            chosenAbility.displayAbilityRange(this);
            this.getTarget(battle, chosenAbility);
        } catch (OutOfManaException e) {
            Popup outOfManaMessage = new Popup();
            outOfManaMessage.getContent().add(new Label("Out of Mana Choose Again"));
            outOfManaMessage.show(TacticBaseBattle.getInstance().getPrimaryStage());
        }
    }

    public void useItem(Battle battle, Consumable item) {
        Ability itemAbility = ConsumableItemInventory.getInstance().getItemAbility();
        itemAbility.displayAbilityRange(this);
        this.getItemTarget(battle, itemAbility, item);
    }

    public void takeMovement(Ability movementAbility) {
        try {
            movementAbility.takeAction(this, null);
        } catch (Exception e) {

        }
    }

    public void getTarget(Battle battle, Ability ability) {
        if (ability.targetsSelf()) this.takeAction(battle, ability, this);
        for (BoardSpace[] boardSpaceArray : TacticBaseBattle.getInstance().getCurrentBoard().getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                if (boardSpace.isOccupied()) {
                    ImageView sprite = boardSpace.getOccupyingUnit().getSprite();
                    sprite.setOnMouseClicked(e -> {
                        if (ability.isUnitInRange(this, boardSpace.getOccupyingUnit())) {
                            this.takeAction(battle, ability, boardSpace.getOccupyingUnit());
                        }
                    });
                }
            }
        }
    }

    public void getItemTarget(Battle battle, Ability ability, Consumable item) {
        for (BoardSpace[] boardSpaceArray : TacticBaseBattle.getInstance().getCurrentBoard().getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                if (boardSpace.isOccupied()) {
                    ImageView sprite = boardSpace.getOccupyingUnit().getSprite();
                    sprite.setOnMouseClicked(e -> {
                        if (ability.isUnitInRange(this, boardSpace.getOccupyingUnit())) {
                            TacticBaseBattle.getInstance().getCurrentBoard().stopShowingDisplayedSpaces(this);
                            ability.takeAction(this, boardSpace.getOccupyingUnit(), item);
                        }
                    });
                }
            }
        }
    }

    public CharacterUnit getCharacterUnit() {
        return this;
    }

    protected void takeNextAction() {
        AbilityMenu.display(this, TacticBaseBattle.getInstance().getBattle(), this.getCharacterJob().getJobAbilityList());
    }
}
