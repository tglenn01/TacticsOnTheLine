package main.model.combatSystem;

import main.exception.AttackMissedException;
import main.exception.OutOfManaException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.itemSystem.Consumable;

public abstract class Ability {
    public enum AbilityType {DAMAGE, HEAL, ATTACK_BUFF, DEFENSE_BUFF, ATTACK_DEBUFF, DEFENSE_DEBUFF, ITEM, MANA_GAIN, MOVEMENT}
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

    public abstract void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit)
            throws AttackMissedException, UnitIsDeadException;

    public void takeAction(CharacterUnit characterUnit, CharacterUnit receivingUnit, Consumable item) {}

    public int getManaCost() {
        return manaCost;
    }

    public String getAbilityDescription() {
        return abilityDescription;
    }

    public AbilityType getAbilityType() {
        return this.abilityType;
    }

    public int getAreaOfEffect() { return this.areaOfEffect;}

    public void hasEnoughMana(CharacterUnit activeUnit) throws OutOfManaException {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        if (activeUnitStatSheet.getMana() >= manaCost) {
            activeUnitStatSheet.setMana(activeUnitStatSheet.getMana() - manaCost);
        } else {
            throw new OutOfManaException();
        }
    }

    public boolean isAreaOfEffect() {
        return this.areaOfEffect > 1;
    }

    public boolean targetsAlly() {
        return this.abilityType == Ability.AbilityType.HEAL ||
                this.abilityType == Ability.AbilityType.ATTACK_BUFF ||
                this.abilityType == Ability.AbilityType.DEFENSE_BUFF ||
                this.abilityType == Ability.AbilityType.ITEM ||
                this.abilityType == AbilityType.MANA_GAIN;
    }

    public boolean targetsSelf() {
        return this.abilityName.equals("Defend") ||
                this.abilityName.equals("Focus");
    }

    public boolean isUnique() {
        return !this.abilityName.equals("Attack") &&
                !this.abilityName.equals("Defend") &&
                !this.abilityName.equals("Item") &&
                !this.abilityName.equals("Move");
    }

    public int getRange() {
        return this.range;
    }

    public boolean isUnitInRange(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return false; // stub
//        boolean validTarget = false;
//        for (BoardSpace boardSpace : TacticBaseBattle.getInstance().getCurrentBoard().getHighlightedBoardSpaces()) {
//            if (boardSpace.getOccupyingUnit() == receivingUnit) {
//                validTarget = true;
//                break;
//            }
//        }
//        return validTarget;
    }
}
