package main.model.characterSystem;

import main.exception.AttackMissedException;
import main.exception.BattleIsOverException;
import main.exception.UnitIsDeadException;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.ui.Battle;

import java.util.List;

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

    protected void abilityTakeAction(Battle battle, Ability ability, CharacterUnit receivingUnit)
            throws BattleIsOverException {
        try {
            ability.takeAction(this, receivingUnit);
        } catch (AttackMissedException attackMissedException) {
            attackMissedException.printMissedAttackMessage();
        } catch (UnitIsDeadException unitIsDeadException) {
            unitIsDeadException.printDeathMessage();
            battle.removeDeadCharacter(unitIsDeadException.getDeadUnit()); // throws BattleIsOverException
        }
    }

    public abstract void takeAction(Battle battle) throws BattleIsOverException;

    protected abstract void takeActionOnce(Battle battle, Ability ability) throws BattleIsOverException;

    protected abstract void takeActionMultipleTimes(Battle battle, Ability ability) throws BattleIsOverException;

    protected abstract List<CharacterUnit> getMultipleTargets(Ability ability, List<CharacterUnit> possibleTargets);

    protected abstract CharacterUnit getSingleTarget(Battle battle, Ability ability);

    protected abstract Ability getChosenAbility(Battle battle);

    protected abstract CharacterUnit getReceivingUnit(List<CharacterUnit> unitOptions);


    // return true Ability is of type HEAL, ATTACK_BUFF, or DEFENSE_BUFF
    protected boolean abilityTargetsAlly(Ability ability) {
        return ability.getAbilityType() == Ability.AbilityType.HEAL ||
                ability.getAbilityType() == Ability.AbilityType.ATTACK_BUFF ||
                ability.getAbilityType() == Ability.AbilityType.DEFENSE_BUFF;
    }

    public void setJob(Job job) {
        this.characterJob = job;
        characterStatSheet.updateStatSheetAccordingToJob(job);
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

    public void setAlive(boolean deathStatus) {
        this.isAlive = deathStatus;
    }
}
