package main.model.combatSystem;

import javafx.scene.Node;
import main.exception.AttackMissedException;
import main.exception.OutOfManaException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.NPC;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.abilities.MovementAbility;
import main.model.combatSystem.targetTypes.AreaTarget;
import main.model.combatSystem.targetTypes.SelfTarget;

import java.util.LinkedList;
import java.util.List;

public abstract class Ability {
    protected String abilityName;
    protected int manaCost;
    protected int range;
    protected int areaOfEffect;
    protected String abilityDescription;

    protected TargetType targetType;

    public Ability(String abilityName, int manaCost, int range, int areaOfEffect, String abilityDescription) {
        this.abilityName = abilityName;
        this.manaCost = manaCost;
        this.range = range;
        this.areaOfEffect = areaOfEffect;
        this.abilityDescription = abilityDescription;
        setTargetType();
    }

    // may be overridden by specific abilities that have unique targetTypes
    protected void setTargetType() {
        if (this.targetsSelf()) this.targetType = new SelfTarget();
        else this.targetType = new AreaTarget();
    }

    public String getAbilityName() {
        return abilityName;
    }



    protected abstract boolean targetsSelf();

    protected abstract boolean isAreaOfEffect();

    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> chosenBoardSpaces)
            throws AttackMissedException, UnitIsDeadException {
        List<CharacterUnit> effectedUnits = new LinkedList<>();
        for (BoardSpace boardSpace : chosenBoardSpaces) {
            if (boardSpace.isOccupied()) {
                CharacterUnit receivingUnit = boardSpace.getOccupyingUnit();
                boolean effectResolved = resolveEffect(activeUnit, receivingUnit);
                if (effectResolved) effectedUnits.add(receivingUnit);
            }
        }
        if (this.getClass() != MovementAbility.class && !effectedUnits.isEmpty())
            addExperienceAfterEffectResolves(activeUnit, effectedUnits);
    }

    public abstract Node getExpectedResultsLabel(CharacterUnit activeUnit, CharacterUnit receivingUnit);
    public abstract int getHitChance(CharacterUnit activeUnit, CharacterUnit receivingUnit);

    protected void addExperienceAfterEffectResolves(CharacterUnit activeUnit, List<CharacterUnit> effectedUnits) {
        activeUnit.getExperiencePoints().addExperiencePoints(activeUnit, getAverageLevel(effectedUnits));
    }

    protected Integer getAverageLevel(List<CharacterUnit> effectedUnits) {
        int totalLevels = 0;
        for (CharacterUnit effectedUnit : effectedUnits) {
            totalLevels += effectedUnit.getLevel();
        }

        return totalLevels / effectedUnits.size();
    }

    // Return true if effect resolved, otherwise false
    protected abstract boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit);

    public int getManaCost() {
        return manaCost;
    }

    public String getAbilityDescription() {
        return abilityDescription;
    }

    public int getAreaOfEffect() {
        return this.areaOfEffect;
    }


    public void hasEnoughMana(CharacterUnit activeUnit) throws OutOfManaException {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        if (activeUnitStatSheet.getMana() <= manaCost) throw new OutOfManaException();
    }

    public void payManaCost(CharacterUnit activeUnit) {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        activeUnitStatSheet.setMana(activeUnitStatSheet.getMana() - manaCost);
    }

    public abstract boolean targetsAlly();

    public boolean isUnique() {
        return !this.abilityName.equals("Attack") &&
                !this.abilityName.equals("Defend") &&
                !this.abilityName.equals("Item") &&
                !this.abilityName.equals("Move");
    }

    public abstract String getEffectType();

    public int getRange() {
        return this.range;
    }

    public TargetType getTargetType() {
        return this.targetType;
    }


    // calls this.getRange() as some abilities have updating ranges which will override this.getRange() to give their
    // unique range (example being Rescue Abilities range is based off current magic)
    public void getTargets(CharacterUnit activeUnit) {
        List<BoardSpace> possibleBoardSpaces = this.targetType.getTargetPattern(activeUnit.getBoardSpace(), this.getRange(), this);
        if (activeUnit.getClass() == NPC.class);
        else {
            this.targetType.displayTargets(activeUnit, possibleBoardSpaces);
            this.targetType.setHandlers(activeUnit, this, possibleBoardSpaces);
        }
    }

    public boolean endsTurn() {
        return this.abilityName.equals("Defend") || this.abilityName.equals("Deactivate");
    }

    public void setRange(int newRange) {
        this.range = newRange;
    }
}
