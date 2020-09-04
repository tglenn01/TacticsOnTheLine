package main.model.combatSystem.abilities;

import javafx.scene.Node;
import javafx.scene.control.Label;
import main.exception.AbilityTargetsSelfException;
import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.exception.UnitIsInvulnerableException;
import main.model.characterSystem.CharacterStatusEffects;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.graphics.sceneElements.images.HealthBar;
import main.ui.TacticBaseBattle;

public abstract class DamageAbility extends Ability {
    protected int damage;
    protected double accuracy;

    public DamageAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                         int damage, double accuracy, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityDescription);
        this.damage = damage;
        this.accuracy = accuracy;
    }

    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit targetedUnit) {
        try {
            checkIfAbilityHitsOneself(activeUnit, targetedUnit); // cannot damage oneself
            checkIfAbilityHit(activeUnit, targetedUnit);
            checkIfUnitIsInvulnerable(targetedUnit);
            calculateDamageDone(activeUnit, targetedUnit);
            return true;
        } catch (AbilityTargetsSelfException abilityTargetsSelfExceptions) {
            return false;
        } catch (AttackMissedException attackMissedException) {
            attackMissedException.printMissedAttackMessage();
            effectPopupAnimation(targetedUnit, "MISS", "fancyNode");
            return false;
        } catch (UnitIsInvulnerableException unitIsInvulnerableException) {
            CharacterStatusEffects csf = targetedUnit.getStatusEffects();
            csf.removePermanentStatusEffect(csf.getInvulnerable());
            unitIsInvulnerableException.printInvulnerableMessage();
            return false;
        } catch (UnitIsDeadException unitIsDeadException) {
            unitIsDeadException.printDeathMessage();
            TacticBaseBattle.getInstance().getBattle().removeDeadCharacter(unitIsDeadException.getDeadUnit());
            return true;
        }
    }

    protected abstract void applyAdditionalEffects(CharacterUnit activeUnit, CharacterUnit receivingUnit);


    private void calculateDamageDone(CharacterUnit activeUnit, CharacterUnit receivingUnit) throws UnitIsDeadException {
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        int defenderHealth = receivingUnitStatSheet.getHealth();
        int damage = calculateDamage(activeUnit, receivingUnit);
        applyAdditionalEffects(activeUnit, receivingUnit);

        HealthBar healthBar = new HealthBar(receivingUnit, receivingUnit.getCharacterStatSheet().getMaxHealth(), defenderHealth, defenderHealth - damage);
        healthBar.showAndWait();

        effectPopupAnimation(receivingUnit, Integer.toString(damage), "damageNode");


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
        double activeUnitChanceToHit = this.accuracy + (activeUnitStatSheet.getDexterity() / 100.00)
                - receivingUnitStatSheet.getDexterity() / 100.00;
        double receivingUnitChanceToDodge = Math.random();
        if (receivingUnitChanceToDodge > activeUnitChanceToHit) {
            throw new AttackMissedException(receivingUnit);
        }
    }

    private void checkIfUnitIsInvulnerable(CharacterUnit receivingUnit) throws UnitIsInvulnerableException {
        CharacterStatusEffects csf = receivingUnit.getStatusEffects();
        if (csf.hasInvulnerable()) throw new UnitIsInvulnerableException();
    }

    private void checkIfAbilityHitsOneself(CharacterUnit activeUnit, CharacterUnit targetedUnit) throws AbilityTargetsSelfException {
        if (activeUnit == targetedUnit) throw new AbilityTargetsSelfException();
    }

    protected abstract int calculateDamage(CharacterUnit activeUnit, CharacterUnit receivingUnit);

    @Override
    public Node getExpectedResultsLabel(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        int expectedDamage = calculateDamage(activeUnit, receivingUnit);
        return new Label(Integer.toString(expectedDamage));
    }

    @Override
    public int getHitChance(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        double activeUnitChanceToHit = this.accuracy + (activeUnitStatSheet.getDexterity() / 100.00)
                - (receivingUnitStatSheet.getDexterity() / 100.00);
        activeUnitChanceToHit *= 100;
        int hitPercent = (int) activeUnitChanceToHit;
        if (hitPercent > 100) hitPercent = 100;
        return hitPercent;
    }
}