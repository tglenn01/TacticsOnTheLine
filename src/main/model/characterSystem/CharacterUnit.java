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

    public abstract void startTurn();

    public abstract void useAbility(Ability chosenAbility);

    public abstract void useItem(Consumable item);

    public abstract void takeMovement(Ability movementAbility);

    protected void takeAction(Ability ability, CharacterUnit receivingUnit) {
        try {
            ability.takeAction(this, receivingUnit);
        } catch (AttackMissedException attackMissedException) {
            attackMissedException.printMissedAttackMessage();
        } catch (UnitIsDeadException unitIsDeadException) {
            unitIsDeadException.printDeathMessage();
            TacticBaseBattle.getInstance().getBattle().removeDeadCharacter(unitIsDeadException.getDeadUnit());
        } finally {
            removeActionToken(ability);
            if (actionTokens == 0) TacticBaseBattle.getInstance().getBattle().endTurn();
            else takeNextAction();
        }
    }

    protected abstract void takeNextAction();

    public void movementComplete(Battle battle) {
        removeActionToken(Job.move);
        if (actionTokens == 0) battle.endTurn();
        else AbilityMenu.display(this, this.getCharacterJob().getJobAbilityList());
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
        for (Map.Entry<BoardSpace, List<CharacterUnit>> entry : TacticBaseBattle.getInstance().getCurrentBoard().getMovementHighlightedSpaces().entrySet()) {
            if (entry.getValue().contains(this)) possibleSpaces.add(entry.getKey());
        }

        return possibleSpaces;
    }

    public List<BoardSpace> getActionRange() {
        List<BoardSpace> possibleSpaces = new LinkedList<>();
        for (BoardSpace[] boardSpaceArray : TacticBaseBattle.getInstance().getCurrentBoard().getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                if (boardSpace.isValidAbilitySpace(this.boardSpace, this.getCharacterJob().getMaxAbilityRange())) {
                    possibleSpaces.add(boardSpace);
                }
            }
        }
        return possibleSpaces;
    }

    protected boolean hasEnoughMana(int abilityManaCost, int characterMana) {
        return abilityManaCost < characterMana;
    }

    protected void removeActionToken(Ability usedAbility) {
        if (usedAbility.getAbilityName().equals("Defend")) this.actionTokens = 0;
        else this.actionTokens--;
    }
}

