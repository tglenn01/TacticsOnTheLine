package main.model.characterSystem;

import javafx.scene.image.ImageView;
import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.BoardSpace;
import main.model.combatSystem.Ability;
import main.model.graphics.menus.AbilityMenu;
import main.model.itemSystem.Consumable;
import main.model.jobSystem.Job;
import main.ui.Battle;
import main.ui.TacticBaseBattle;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class CharacterUnit {
    protected enum CharacterType {ALLY, ENEMY};
    protected final int ACTIONS_PER_TURN = 2;
    protected String characterName;
    protected Job characterJob;
    protected StatSheet characterStatSheet;
    protected boolean isAlive;
    protected CharacterStatusEffects statusEffects;
    protected BoardSpace boardSpace;
    protected CharacterPortrait characterPortrait;
    protected CharacterSprite sprite;
    protected int actionTokens;
    protected boolean movementRangeIsVisable;


    public CharacterUnit() {
        this.isAlive = true;
        this.movementRangeIsVisable = false;
        statusEffects = new CharacterStatusEffects();
    }

    public abstract void startTurn(Battle battle);

    public abstract void useAbility(Battle battle, Ability chosenAbility);

    public abstract void useItem(Battle battle, Consumable item);

    public abstract void takeMovement(Ability movementAbility);

    protected void takeAction(Battle battle, Ability ability, CharacterUnit receivingUnit) {
        try {
            ability.takeAction(this, receivingUnit);
        } catch (AttackMissedException attackMissedException) {
            attackMissedException.printMissedAttackMessage();
        } catch (UnitIsDeadException unitIsDeadException) {
            unitIsDeadException.printDeathMessage();
            battle.removeDeadCharacter(unitIsDeadException.getDeadUnit());
        } finally {
            removeActionToken();
            if (actionTokens == 0) battle.endTurn();
            else takeNextAction();
        }
    }

    protected abstract void takeNextAction();

    public void movementComplete(Battle battle) {
        removeActionToken();
        if (actionTokens == 0) battle.endTurn();
        else AbilityMenu.display(this, battle, this.getCharacterJob().getJobAbilityList());
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

    public void setMovementRangeIsVisable(boolean visable) {
        this.movementRangeIsVisable = visable;
    }

    public boolean isMovementRangeIsVisable() {
        return this.movementRangeIsVisable;
    }

    public List<BoardSpace> getMovementRange() {
        List<BoardSpace> possibleSpaces = new LinkedList<>();
        for (Map.Entry<BoardSpace, List<CharacterUnit>> entry : TacticBaseBattle.getInstance().getCurrentBoard().getHighlightedBoardSpaces().entrySet()) {
            if (entry.getValue().contains(this)) possibleSpaces.add(entry.getKey());
        }

        return possibleSpaces;
    }

    public List<BoardSpace> getActionRange() {
        List<BoardSpace> possibleSpaces = new LinkedList<>();
        for (BoardSpace[] boardSpaceArray : TacticBaseBattle.getInstance().getCurrentBoard().getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                if (boardSpace.inRange(this.boardSpace, this.getCharacterJob().getMaxAbilityRange())) {
                    possibleSpaces.add(boardSpace);
                }
            }
        }
        return possibleSpaces;
    }

    protected boolean hasEnoughMana(int abilityManaCost, int characterMana) {
        return abilityManaCost < characterMana;
    }

    protected void removeActionToken() {
        this.actionTokens--;
    }
}

