package main.model.characterSystem;

import main.exception.BattleIsOverException;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.ui.Battle;
import main.ui.UserInput;

import java.util.List;

public class PlayableCharacterUnit extends CharacterUnit {

    public PlayableCharacterUnit(Job job, String name) {
        super(job, name);
    }

    @Override
    public void takeAction(Battle battle) throws BattleIsOverException {
        System.out.println("It is " + this.characterName + "'s turn");
        displayAbilities();
        Ability choosenAbility = getChoosenAbility(battle);
        abilityTakeAction(battle, choosenAbility);
    }


    private void displayAbilities() {
        List<Ability> availableAbilities = characterJob.getJobAbilityList();
        for (Ability ability : availableAbilities) {
            System.out.println(ability.getAbilityName() + " (" + ability.getManaCost() + " mana): " +
                    ability.getAbilityDescription());
        }
    }

    protected Ability getChoosenAbility(Battle battle) {
        UserInput input = new UserInput();
        String command = input.getInput();
        Ability choosenAbility = null;
        for (Ability ability : characterJob.getJobAbilityList()) {
            if (command.equals(ability.getAbilityName())) {
                choosenAbility = ability;
            }
        }
        if (choosenAbility == null) {
            System.out.println("Not a valid ability, please choose again");
            choosenAbility = getChoosenAbility(battle);
        }
        return choosenAbility;
    }

    @Override
    protected CharacterUnit getDefendingEnemy(Battle battle) {
        List<CharacterUnit> aliveEnemies = battle.getTurnOrder().getAliveEnemyCharacters();
        System.out.println("Choose an enemy to target");
        return askUserToChooseUnit(aliveEnemies);
    }

    @Override
    protected CharacterUnit getSupportedAlly(Battle battle) {
        List<CharacterUnit> aliveAllies = battle.getTurnOrder().getAlivePlayableCharacters();
        System.out.println("Choose an ally to target");
        return askUserToChooseUnit(aliveAllies);
    }

    private CharacterUnit askUserToChooseUnit(List<CharacterUnit> unitOptions) {
        for (CharacterUnit unit : unitOptions) {
            System.out.println(unit.getCharacterName());
        }

        UserInput input = new UserInput();
        String command = input.getInput();
        CharacterUnit choosenUnit = null;

        for (CharacterUnit unit : unitOptions) {
            if (command.equals(unit.characterName)) {
                choosenUnit = unit;
            }
        }
        if (choosenUnit == null) {
            System.out.println("Not a valid option, please choose again");
            choosenUnit = askUserToChooseUnit(unitOptions);
        }
        return choosenUnit;
    }
}
