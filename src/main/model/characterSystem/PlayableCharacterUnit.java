package main.model.characterSystem;

import main.exception.AttackMissedException;
import main.exception.OutOfManaException;
import main.exception.UnitIsDeadException;
import main.model.combatSystem.*;
import main.ui.Battle;
import main.ui.UserInput;

import java.util.List;

public class PlayableCharacterUnit extends CharacterUnit {

    public PlayableCharacterUnit(String name) {
        super(name);
    }

    @Override
    public void takeAction(Battle battle) {
        System.out.println("It is " + characterName + "'s turn");
        displayAbilties();
        getChoosenAbility(battle);
    }


    private void displayAbilties() {
        List<Ability> availableAbilites = characterJob.getJobAbilityList();
        for (Ability ability : availableAbilites) {
            System.out.println(ability.getAbilityName());
        }
    }

    private void getChoosenAbility(Battle battle) {
        UserInput input = new UserInput();
        String command = input.getInput();

        for (Ability ability : characterJob.getJobAbilityList()) {
            if (command.equals(ability.getAbilityName())) {
                abilityTakeAction(battle, ability);
            }
        }
    }

    private CharacterUnit getDefendingEnemy(Battle battle) {
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

    private void abilityTakeAction(Battle battle, Ability ability) {
        try {
            ability.takeAction(this, getDefendingEnemy(battle));
        } catch (OutOfManaException e) {
            System.out.println("Insufficent Mana: you have: " + this.characterStatSheet.getMana() + " left");
            System.out.println("Choose a different ability");
            getChoosenAbility(battle);
        } catch (AttackMissedException e) {
            System.out.println("You're attack missed :(");
        } catch (UnitIsDeadException e) {
            System.out.println(e.getDeadUnit().characterName + "has died");
        }
    }
}
