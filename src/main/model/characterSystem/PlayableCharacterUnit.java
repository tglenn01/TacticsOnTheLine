package main.model.characterSystem;

import main.model.combatSystem.Ability;
import main.ui.Battle;
import main.ui.UserInput;

import java.util.List;

public class PlayableCharacterUnit extends CharacterUnit {

    public PlayableCharacterUnit(String name) {
        super(name);
    }

    @Override
    public void takeAction(Battle battle) {
        System.out.println("It is " + this.characterName + "'s turn");
        displayAbilties();
        getChoosenAbility(battle);
    }


    private void displayAbilties() {
        List<Ability> availableAbilites = characterJob.getJobAbilityList();
        for (Ability ability : availableAbilites) {
            System.out.println(ability.getAbilityName() + ": " + ability.getManaCost() + " mana");
        }
    }

    protected void getChoosenAbility(Battle battle) {
        UserInput input = new UserInput();
        String command = input.getInput();

        for (Ability ability : characterJob.getJobAbilityList()) {
            if (command.equals(ability.getAbilityName())) {
                abilityTakeAction(battle, ability);
            }
        }
    }

    protected CharacterUnit getDefendingEnemy(Battle battle) {
        List<CharacterUnit> aliveEnemies = battle.getTurnOrder().getAliveEnemyCharacters();
        System.out.println("Choose an enemy to target");
        for (CharacterUnit enemy : aliveEnemies) {
            System.out.println(enemy.characterName);
        }

        UserInput input = new UserInput();
        String command = input.getInput();
        CharacterUnit choosenEnemy = null;

        for (CharacterUnit enemy : aliveEnemies) {
            if (command.equals(enemy.characterName)) {
                choosenEnemy = enemy;
            }
        }
        return choosenEnemy;
    }
}