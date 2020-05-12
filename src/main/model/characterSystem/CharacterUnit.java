package main.model.characterSystem;

import main.exception.AttackMissedException;
import main.exception.OutOfManaException;
import main.exception.UnitIsDeadException;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.ui.Battle;

public abstract class CharacterUnit {
    protected String characterName;
    protected Job characterJob;
    protected StatSheet characterStatSheet;
    protected boolean isAlive;

    public CharacterUnit(Job job, String name) {
        this.characterName = name;
        this.characterJob = job;
        this.characterStatSheet = new StatSheet(this.characterJob);
        this.isAlive = true;
    }

    protected void abilityTakeAction(Battle battle, Ability ability) {
        CharacterUnit targetedUnit;
        targetedUnit = getTargetEnemy(battle, ability);

        try {
            ability.takeAction(this, targetedUnit);
        } catch (OutOfManaException e) {
            System.out.println("Insufficient Mana: you have " + this.characterStatSheet.getMana() + " left");
            System.out.println("Choose a different ability");
            getChoosenAbility(battle);
        } catch (AttackMissedException e) {
            System.out.println("Their attack missed :(");
        } catch (UnitIsDeadException e) {
            System.out.println(e.getDeadUnit().getCharacterName() + " has died");
            battle.removeDeadCharacter(e.getDeadUnit());
        }
    }

    private CharacterUnit getTargetEnemy(Battle battle, Ability ability) {
        CharacterUnit targetedUnit;
        if (abilityTargetsAlly(ability)) {
            targetedUnit = getSupportedAlly(battle);
        } else {
            targetedUnit = getDefendingEnemy(battle);
        }
        return targetedUnit;
    }

    // return true Ability is of type HEAL, ATTACK_BUFF, or DEFENSE_BUFF
    private boolean abilityTargetsAlly(Ability ability) {
        return ability.getAbilityType() == Ability.AbilityType.HEAL ||
                ability.getAbilityType() == Ability.AbilityType.ATTACK_BUFF ||
                ability.getAbilityType() == Ability.AbilityType.DEFENSE_BUFF;
    }

    public void setJob(Job job) {
        this.characterJob = job;
        characterStatSheet.updateStatSheetAccordingToJob(job);
    }

    public void setAlive(boolean deathStatus) {
        this.isAlive = deathStatus;
    }

    public StatSheet getCharacterStatSheet() {
        return characterStatSheet;
    }

    public Job getCharacterJob() {
        return characterJob;
    }

    public String getCharacterName() {
        return characterName;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    protected abstract void getChoosenAbility(Battle battle);

    protected abstract CharacterUnit getDefendingEnemy(Battle battle);

    protected abstract CharacterUnit getSupportedAlly(Battle battle);

    public abstract void takeAction(Battle battle);
}
