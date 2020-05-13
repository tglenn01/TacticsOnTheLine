package main.model.characterSystem;

import main.exception.BattleIsOverException;
import main.exception.OutOfManaException;
import main.model.combatSystem.Ability;
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
    public void takeAction(Battle battle) throws BattleIsOverException {
        System.out.println("It is " + this.characterName + "'s turn");
        displayAbilities();
        Ability chosenAbility = getChosenAbility(battle);
        if (chosenAbility.isAreaOfEffect()) takeActionMultipleTimes(battle, chosenAbility);
        else takeActionOnce(battle, chosenAbility);
    }

    private void displayAbilities() {
        List<Ability> availableAbilities = characterJob.getJobAbilityList();
        for (Ability ability : availableAbilities) {
            System.out.println(ability.getAbilityName() + " (" + ability.getManaCost() + " mana): " +
                    ability.getAbilityDescription());
        }
    }

    protected Ability getChosenAbility(Battle battle) {
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
        return chosenAbility;
    }

    protected void takeActionOnce(Battle battle, Ability ability) throws BattleIsOverException {
        CharacterUnit receivingUnit = getSingleTarget(battle, ability);
        abilityTakeAction(battle, ability, receivingUnit);
    }

    protected CharacterUnit getSingleTarget(Battle battle, Ability ability) {
        CharacterUnit targetedUnit;
        if (ability.getAbilityName().equals("Defend")) return this;
        if (abilityTargetsAlly(ability)) {
            System.out.println("Choose an Ally:");
            targetedUnit = getReceivingUnit(battle.getTurnOrder().getAlivePlayableCharacters());
        } else {
            System.out.println("Choose an Enemy:");
            targetedUnit = getReceivingUnit(battle.getTurnOrder().getAliveEnemyCharacters());
        }
        return targetedUnit;
    }

    protected void takeActionMultipleTimes(Battle battle, Ability ability) throws BattleIsOverException {
        List<CharacterUnit> possibleTargets;
        System.out.println("Choose " + ability.getAreaOfEffect() + " targets");
        if (abilityTargetsAlly(ability)) {
            possibleTargets = new ArrayList<>(battle.getTurnOrder().getAlivePlayableCharacters());
        } else {
            possibleTargets = new ArrayList<>(battle.getTurnOrder().getAliveEnemyCharacters());
        }
        List<CharacterUnit> chosenTargets = getMultipleTargets(ability, possibleTargets);
        for (CharacterUnit chosenTarget : chosenTargets) {
            abilityTakeAction(battle, ability, chosenTarget);
        }
    }

    protected List<CharacterUnit> getMultipleTargets(Ability ability, List<CharacterUnit> possibleTargets) {
        List<CharacterUnit> chosenTargets = new ArrayList<>();
        for (int i = 1; i <= ability.getAreaOfEffect(); i++) {
            CharacterUnit receivingUnit = getReceivingUnit(possibleTargets);
            chosenTargets.add(receivingUnit);
            possibleTargets.remove(receivingUnit);
        }
        return chosenTargets;
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
