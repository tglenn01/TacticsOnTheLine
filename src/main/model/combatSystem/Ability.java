package main.model.combatSystem;

import main.exception.AttackMissedException;
import main.exception.OutOfManaException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;

public abstract class Ability {
    protected String abilityName;
    protected int manaCost;
    protected int range;
    protected int areaOfEffect;


    public Ability(String abilityName, int manaCost, int range, int areaOfEffect) {
        this.abilityName = abilityName;
        this.manaCost = manaCost;
        this.range = range;
        this.areaOfEffect = areaOfEffect;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public abstract void takeAction(CharacterUnit activeUnit, CharacterUnit reciveingUnit)
            throws OutOfManaException, AttackMissedException, UnitIsDeadException;

    public int getManaCost() {
        return manaCost;
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
