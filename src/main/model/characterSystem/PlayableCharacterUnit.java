package main.model.characterSystem;

import javafx.scene.control.Label;
import javafx.stage.Popup;
import main.exception.BattleIsOverException;
import main.exception.OutOfManaException;
import main.model.combatSystem.Ability;
import main.model.graphics.menus.AbilityMenu;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.ui.Battle;
import main.ui.UserInput;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayableCharacterUnit extends CharacterUnit {

    public PlayableCharacterUnit() {
    }

    @Override
    public void startTurn(Battle battle) throws BattleIsOverException {
        System.out.println("It is " + this.characterName + "'s turn, they have " +
                characterStatSheet.getMana() + " mana");
        statusEffects.updateStatusEffect(this);
        new AbilityMenu(this, battle, characterJob.getJobAbilityList());
    }

    public void useAbility(Battle battle, Ability chosenAbility) {
        try {
            chosenAbility.hasEnoughMana(this);
            chosenAbility.displayAbilityRange(this);
            CharacterUnit receivingUnit = this.getTarget(battle, chosenAbility);
            this.takeAction(battle, chosenAbility, receivingUnit);
        } catch (OutOfManaException e) {
            Popup outOfManaMessage = new Popup();
            outOfManaMessage.getContent().add(new Label("Out of Mana Choose Again"));
        }
    }

    public void useItem(Battle battle, Consumable item) {
        Ability itemAbility = ConsumableItemInventory.getInstance().getItemAbility();
        itemAbility.displayAbilityRange(this);
        CharacterUnit receivingUnit = this.getTarget(battle, itemAbility);
        itemAbility.takeAction(this, receivingUnit, item);
    }


    protected List<CharacterUnit> getMultipleTargets(Ability ability, List<CharacterUnit> possibleTargets) {
        List<CharacterUnit> chosenTargets = new ArrayList<>();
        int amountOfTimesCast = ability.getAreaOfEffect();
        int amountOfEnemies = possibleTargets.size();
        if (amountOfTimesCast > amountOfEnemies) amountOfTimesCast = amountOfEnemies;
        for (int i = amountOfTimesCast; i != 0; i--) {
            System.out.println("Choose " + i + " more targets");
            CharacterUnit receivingUnit = getReceivingUnit(possibleTargets);
            chosenTargets.add(receivingUnit);
            possibleTargets.remove(receivingUnit);
        }
        return chosenTargets;
    }

    protected List<CharacterUnit> getUnitOptions(Battle battle, CharacterType type) {
        if (type == CharacterType.ALLY) {
            System.out.println("Choose from fielded allies:");
            return battle.getTurnOrder().getAlivePlayableCharacters();
        } else {
            System.out.println("Choose from opposing enemies:");
            return battle.getTurnOrder().getAliveEnemyCharacters();
        }
    }

    protected CharacterUnit getReceivingUnit(List<CharacterUnit> unitOptions) {
        for (CharacterUnit unit : unitOptions) {
            System.out.println(unit.getCharacterName());
        }

        UserInput input = new UserInput();
        String command = input.getInput();
        CharacterUnit chosenUnit = null;

        for (CharacterUnit unit : unitOptions) {
            if (command.equals(unit.characterName)) {
                chosenUnit = unit;
            }
        }
        if (chosenUnit == null) {
            System.out.println("Not a valid option, please choose again");
            chosenUnit = getReceivingUnit(unitOptions);
        }
        return chosenUnit;
    }
}
