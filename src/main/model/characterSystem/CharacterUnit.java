package main.model.characterSystem;

import main.exception.AttackMissedException;
import main.exception.BattleIsOverException;
import main.exception.UnitIsDeadException;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.ui.Battle;

import java.util.ArrayList;
import java.util.List;

public abstract class CharacterUnit {
    protected enum CharacterType {ALLY, ENEMY};
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

    public abstract void startTurn(Battle battle) throws BattleIsOverException;

    protected abstract Ability getChosenAbility(Battle battle);

    protected void takeAction(Battle battle, Ability ability, CharacterUnit receivingUnit)
            throws BattleIsOverException {
        try {
            System.out.println(this.characterName + " used " + ability.getAbilityName());
            ability.takeAction(this, receivingUnit);
        } catch (AttackMissedException attackMissedException) {
            attackMissedException.printMissedAttackMessage();
        } catch (UnitIsDeadException unitIsDeadException) {
            unitIsDeadException.printDeathMessage();
            battle.removeDeadCharacter(unitIsDeadException.getDeadUnit()); // throws BattleIsOverException
        }
    }

    protected abstract void takeActionOnce(Battle battle, Ability ability) throws BattleIsOverException;

    protected CharacterUnit getSingleTarget(Battle battle, Ability ability) {
        if (ability.getAbilityName().equals("Defend")) return this;
        List<CharacterUnit> unitOptions;
        if (ability.targetsAlly()) {
            unitOptions = getUnitOptions(battle, CharacterType.ALLY);
        } else {
            unitOptions = getUnitOptions(battle, CharacterType.ENEMY);
        }
        return getReceivingUnit(unitOptions);
    }

    protected void takeActionMultipleTimes(Battle battle, Ability ability) throws BattleIsOverException {
        List<CharacterUnit> possibleTargets;
        if (ability.targetsAlly()) {
            possibleTargets = new ArrayList<>(getUnitOptions(battle, CharacterType.ALLY));
        } else {
            possibleTargets = new ArrayList<>(getUnitOptions(battle, CharacterType.ENEMY));
        }
        List<CharacterUnit> chosenTargets = getMultipleTargets(ability, possibleTargets);
        for (CharacterUnit chosenTarget : chosenTargets) {
            takeAction(battle, ability, chosenTarget);
        }
    }

    protected abstract List<CharacterUnit> getMultipleTargets(Ability ability, List<CharacterUnit> possibleTargets);

    protected abstract List<CharacterUnit> getUnitOptions(Battle battle, CharacterType type);

    protected abstract CharacterUnit getReceivingUnit(List<CharacterUnit> unitOptions);

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
