package main.model.characterSystem;

import main.exception.AttackMissedException;
import main.exception.BattleIsOverException;
import main.exception.UnitIsDeadException;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.ui.Battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CharacterUnit {
    protected enum CharacterType {ALLY, ENEMY};
    protected String characterName;
    protected Job characterJob;
    protected StatSheet characterStatSheet;
    protected boolean isAlive;
    protected Map<Ability.AbilityType, Integer> statusEffectDuration;

    public CharacterUnit(Job job, String name) {
        this.characterName = name;
        this.characterJob = job;
        this.characterStatSheet = new StatSheet(this.characterJob);
        this.isAlive = true;
        statusEffectDuration = new HashMap<>();
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

    public void addStatusEffect(Ability.AbilityType statusEffect, int duration) {
        if (statusEffectDuration.containsKey(statusEffect)) {
            int value = statusEffectDuration.get(statusEffect);
            int newValue = value + duration;
            statusEffectDuration.put(statusEffect, newValue);
        } else statusEffectDuration.put(statusEffect, duration);
    }

    protected void updateStatusEffect() {
        for (Map.Entry<Ability.AbilityType, Integer> entry : statusEffectDuration.entrySet()) {
            int newDuration = entry.getValue();
            newDuration--;
            if (newDuration == 0) {
                statusEffectDuration.remove(entry.getKey());
                System.out.println(entry.getKey() + " Has ended");
                removeStatusEffect(entry);
            } else {
                entry.setValue(newDuration);
                System.out.println(entry.getKey() + " for " + newDuration);
            }
        }
    }

    private void removeStatusEffect(Map.Entry<Ability.AbilityType, Integer> entry) {
        Ability.AbilityType statusEffect = entry.getKey();
        if (statusEffect == Ability.AbilityType.ATTACK_BUFF ||
                statusEffect == Ability.AbilityType.ATTACK_DEBUFF) {
            getCharacterStatSheet().revertStrength();
        } if (statusEffect == Ability.AbilityType.DEFENSE_BUFF ||
                statusEffect == Ability.AbilityType.DEFENSE_DEBUFF) {
            getCharacterStatSheet().revertArmour();
        }
        statusEffectDuration.remove(entry);
    }
}
