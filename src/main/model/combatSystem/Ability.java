package main.model.combatSystem;

import main.exception.AttackMissedException;
import main.exception.OutOfManaException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;

public abstract class Ability {
    public enum AbilityType {DAMAGE, HEAL, ATTACK_BUFF, DEFENSE_BUFF, ATTACK_DEBUFF, DEFENSE_DEBUFF}
    protected String abilityName;
    protected int manaCost;
    protected int range;
    protected int areaOfEffect;
    protected AbilityType abilityType;
    protected String abilityDescription;


    public Ability(String abilityName, int manaCost, int range, int areaOfEffect,
                   AbilityType abilityType, String abilityDescription) {
        this.abilityName = abilityName;
        this.manaCost = manaCost;
        this.range = range;
        this.areaOfEffect = areaOfEffect;
        this.abilityType = abilityType;
        this.abilityDescription = abilityDescription;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public abstract void takeAction(CharacterUnit activeUnit, CharacterUnit reciveingUnit)
            throws OutOfManaException, AttackMissedException, UnitIsDeadException;

    public int getManaCost() {
        return manaCost;
    }

    public String getAbilityDescription() {
        return abilityDescription;
    }

    public AbilityType getAbilityType() {
        return this.abilityType;
    }

    protected void hasEnoughMana(CharacterUnit activeUnit) throws OutOfManaException {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        if (activeUnitStatSheet.getMana() >= manaCost) {
            activeUnitStatSheet.setMana(activeUnitStatSheet.getMana() - manaCost);
        } else {
            throw new OutOfManaException();
        }
    }


}
