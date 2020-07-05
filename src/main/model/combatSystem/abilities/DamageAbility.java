package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.exception.UnitIsInvulnerableException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterStatusEffects;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.ui.TacticBaseBattle;

public abstract class DamageAbility extends Ability {
    protected int damage;
    protected double accuracy;

    public DamageAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                         AbilityType abilityType, int damage, double accuracy, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
        this.damage = damage;
        this.accuracy = accuracy;
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit) throws
            AttackMissedException, UnitIsDeadException {
        if (isAreaOfEffect()) calculateAreaOfEffect(activeUnit, receivingUnit);
        else calculateSingleTarget(activeUnit, receivingUnit);
    }

    private void calculateSingleTarget(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        try {
            checkIfAbilityHit(activeUnit, receivingUnit);
            checkIfUnitIsInvulnerable(receivingUnit);
            calculateDamageDone(activeUnit, receivingUnit);
        } catch (AttackMissedException attackMissedException) {
            attackMissedException.printMissedAttackMessage();
        } catch (UnitIsInvulnerableException unitIsInvulnerableException) {
            receivingUnit.getStatusEffects().removeInvulnerable();
            unitIsInvulnerableException.printInvulnerableMessage();
        } catch (UnitIsDeadException unitIsDeadException) {
            unitIsDeadException.printDeathMessage();
            TacticBaseBattle.getInstance().getBattle().removeDeadCharacter(unitIsDeadException.getDeadUnit());
        }
    }

    private void calculateAreaOfEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        for (BoardSpace targetedBoardSpace : receivingUnit.getTargetedBoardSpacesForAreaOfEffect(this)) {
            if (targetedBoardSpace.isOccupied()) {
                CharacterUnit targetedUnit = targetedBoardSpace.getOccupyingUnit();
                try {
                    checkIfAbilityHit(activeUnit, targetedUnit);
                    checkIfUnitIsInvulnerable(receivingUnit);
                    calculateDamageDone(activeUnit, targetedUnit);
                } catch (AttackMissedException attackMissedException) {
                    attackMissedException.printMissedAttackMessage();
                } catch (UnitIsInvulnerableException unitIsInvulnerableException) {
                    receivingUnit.getStatusEffects().removeInvulnerable();
                    unitIsInvulnerableException.printInvulnerableMessage();
                } catch (UnitIsDeadException unitIsDeadException) {
                    unitIsDeadException.printDeathMessage();
                    TacticBaseBattle.getInstance().getBattle().removeDeadCharacter(unitIsDeadException.getDeadUnit());
                }
            }
        }
    }


    private void calculateDamageDone(CharacterUnit activeUnit, CharacterUnit receivingUnit) throws UnitIsDeadException {
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        int defenderHealth = receivingUnitStatSheet.getHealth();
        int damage =  calculateDamage(activeUnit, receivingUnit);
        defenderHealth = defenderHealth - damage;
        if (defenderHealth < 0) defenderHealth = 0;
        System.out.println(activeUnit.getCharacterName() + " dealt " + damage + " to " +
                receivingUnit.getCharacterName() + ", " +
                receivingUnit.getCharacterName() + " is now at " + defenderHealth + "!");
        if (defenderHealth <= 0) {
            receivingUnitStatSheet.setHealth(0);
            receivingUnit.setAlive(false);
            throw new UnitIsDeadException(receivingUnit);
        }
        receivingUnitStatSheet.setHealth(defenderHealth);
    }

    private void checkIfAbilityHit(CharacterUnit activeUnit, CharacterUnit receivingUnit) throws AttackMissedException {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        double activeUnitChanceToHit = this.accuracy + (activeUnitStatSheet.getDexterity() / 100.00);
        double receivingUnitChanceToDodge = Math.random() + receivingUnitStatSheet.getDexterity() / 100.00;
        if (receivingUnitChanceToDodge > activeUnitChanceToHit) {
            throw new AttackMissedException(receivingUnit);
        }
    }

    private void checkIfUnitIsInvulnerable(CharacterUnit receivingUnit) throws UnitIsInvulnerableException {
        CharacterStatusEffects csf = receivingUnit.getStatusEffects();
        if (csf.hasInvulnerable()) throw new UnitIsInvulnerableException();
    }

    protected abstract int calculateDamage(CharacterUnit activeUnit, CharacterUnit receivingUnit);

    public boolean targetsAlly() {
        return false;
    }
}
