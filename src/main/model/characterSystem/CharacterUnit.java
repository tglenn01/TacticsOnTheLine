package main.model.characterSystem;

import javafx.scene.image.ImageView;
import main.exception.AttackMissedException;
import main.exception.BattleIsOverException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.BoardSpace;
import main.model.combatSystem.Ability;
import main.model.itemSystem.Consumable;
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
    protected CharacterStatusEffects statusEffects;
    protected BoardSpace boardSpace;
    protected CharacterPortrait characterPortrait;
    protected CharacterSprite sprite;


    public CharacterUnit() {
        this.isAlive = true;
        statusEffects = new CharacterStatusEffects();
    }

    public abstract void startTurn(Battle battle) throws BattleIsOverException;

    public abstract void useAbility(Battle battle, Ability chosenAbility);

    public abstract void useItem(Battle battle, Consumable item);

    protected void takeAction(Battle battle, Ability ability, CharacterUnit receivingUnit) {
        try {
            ability.takeAction(this, receivingUnit);
        } catch (AttackMissedException attackMissedException) {
            attackMissedException.printMissedAttackMessage();
        } catch (UnitIsDeadException unitIsDeadException) {
            unitIsDeadException.printDeathMessage();
            battle.removeDeadCharacter(unitIsDeadException.getDeadUnit());
        }
    }

    public CharacterUnit getTarget(Battle battle, Ability ability) {
        return null; // get character
    }

    public void takeActionOnce(Battle battle, Ability ability) {
        CharacterUnit receivingUnit = getSingleTarget(battle, ability);
        takeAction(battle, ability, receivingUnit);
    }

    protected CharacterUnit getSingleTarget(Battle battle, Ability ability) {
        if (ability.isSelfBuff()) return this;
        List<CharacterUnit> unitOptions;
        if (ability.targetsAlly()) {
            unitOptions = getUnitOptions(battle, CharacterType.ALLY);
        } else {
            unitOptions = getUnitOptions(battle, CharacterType.ENEMY);
        }
        return getReceivingUnit(unitOptions);
    }

    public void takeActionMultipleTimes(Battle battle, Ability ability) {
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

    public void movementComplete() {
        // take away action token
    }

    public void setJob(Job job) {
        this.characterJob = job;
        characterStatSheet.updateStatSheetAccordingToJob(job);
    }

    public void setBoardSpace(BoardSpace boardSpace) {
        this.boardSpace = boardSpace;
        if (boardSpace.getOccupyingUnit() != this) {
            boardSpace.setOccupyingUnit(this);
        }
    }

    public void setCharacterPortrait(String fileLocation) {
        this.characterPortrait = new CharacterPortrait(fileLocation);
    }


    public void setCharacterSprite(String fileLocation) {
        this.sprite = new CharacterSprite(this, fileLocation);
    }

    public void setAlive(boolean deathStatus) { this.isAlive = deathStatus; }

    public String getCharacterName() { return characterName; }

    public Job getCharacterJob() { return characterJob; }

    public StatSheet getCharacterStatSheet() {
        return characterStatSheet;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public CharacterStatusEffects getStatusEffects() { return this.statusEffects; }

    public BoardSpace getBoardSpace() {
        return this.boardSpace;
    }

    public ImageView getCharacterPortrait() {
        return this.characterPortrait.getPortrait();
    }

    public ImageView getSprite() {
        return this.sprite.getSprite();
    }
}
