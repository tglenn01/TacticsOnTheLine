package main.model.characterSystem;

import main.exception.BattleIsOverException;
import main.exception.OutOfManaException;
import main.model.combatSystem.Ability;
import main.model.itemSystem.ConsumableItemInventory;
import main.model.jobSystem.Job;
import main.ui.Battle;
import main.ui.UserInput;

import java.util.ArrayList;
import java.util.List;

public class PlayableCharacterUnit extends CharacterUnit {

    public PlayableCharacterUnit(Job job, String name) {
        super(job, name);
    }

    @Override
    public void startTurn(Battle battle) throws BattleIsOverException {
        System.out.println("It is " + this.characterName + "'s turn, they have " +
                characterStatSheet.getMana() + " mana");
        updateStatusEffect();
        Ability chosenAbility = getChosenAbility(battle);
        System.out.println(this.characterName + " has used " + chosenAbility.getAbilityName());
        if (chosenAbility.isAreaOfEffect()) takeActionMultipleTimes(battle, chosenAbility);
        else takeActionOnce(battle, chosenAbility);
    }

    protected Ability getChosenAbility(Battle battle) {
        displayAbilities();
        UserInput input = new UserInput();
        String command = input.getInput();
        Ability chosenAbility = null;
        for (Ability ability : characterJob.getJobAbilityList()) {
            if (command.equals(ability.getAbilityName())) {
                try {
                    ability.hasEnoughMana(this);
                    chosenAbility = ability;
                } catch (OutOfManaException e) {
                    System.out.println("Insufficient Mana: you have " + this.characterStatSheet.getMana() + " left");
                    System.out.println("Choose a different ability");
                    chosenAbility = getChosenAbility(battle);
                }
            }
        }
        if (chosenAbility == null) {
            System.out.println("Not a valid ability, please choose again");
            chosenAbility = getChosenAbility(battle);
        }

        if (chosenAbility.getAbilityType() == Ability.AbilityType.ITEM) {
            if (ConsumableItemInventory.getInstance().isEmpty()) {
                System.out.println("You do not have any items, choose a different ability");
                getChosenAbility(battle);
            }
        }
        return chosenAbility;
    }

    private void displayAbilities() {
        List<Ability> availableAbilities = characterJob.getJobAbilityList();
        for (Ability ability : availableAbilities) {
            System.out.println(ability.getAbilityName() + " (" + ability.getManaCost() + " mana): " +
                    ability.getAbilityDescription());
        }
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
