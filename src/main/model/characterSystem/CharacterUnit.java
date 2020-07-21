package main.model.characterSystem;

import javafx.scene.image.ImageView;
import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.combatSystem.abilities.MovementAbility;
import main.model.graphics.sceneElements.images.CharacterPortrait;
import main.model.graphics.sceneElements.images.CharacterSprite;
import main.model.itemSystem.Consumable;
import main.model.jobSystem.Job;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class CharacterUnit {
    protected final int ACTIONS_PER_TURN = 1;
    protected int actionTokens;
    protected boolean movementToken;

    protected String characterName;
    protected Job characterJob;
    protected List<Ability> abilityList = new ArrayList<>();
    protected StatSheet characterStatSheet;
    protected CharacterStatusEffects statusEffects;
    protected CharacterPortrait characterPortrait;
    protected CharacterSprite sprite;
    protected BoardSpace boardSpace;
    protected boolean isAlive;
    protected int direction = 2; // 0 = up, 1 = right, 2 = down, 3 = left
    protected boolean movementRangeIsVisable;


    public CharacterUnit() {
        setPersonalStatBonuses();
        setCharacterSprite();
        setCharacterPortrait();
        //this.setPersonalGrowthRate();
        this.isAlive = true;
        this.movementRangeIsVisable = false;
        statusEffects = new CharacterStatusEffects();

    }

    protected abstract void setPersonalStatBonuses();
    protected abstract void setCharacterSprite();
    protected abstract void setCharacterPortrait();
    protected abstract void addPersonalAbilityToAbilityList();


    //protected abstract void setPersonalGrowthRate();

    public abstract void startTurn();

    public abstract void useAbility(Ability chosenAbility);

    public abstract void useItem(Consumable item);

    public abstract void takeMovement(Ability movementAbility);

    public void takeAction(Ability ability, List<BoardSpace> chosenBoardSpaces) {
        try {
            ability.payManaCost(this); // we know that we have enough mana to cast
            ability.takeAction(this, chosenBoardSpaces);
        } catch (AttackMissedException attackMissedException) {
            attackMissedException.printMissedAttackMessage();
        } catch (UnitIsDeadException unitIsDeadException) {
            unitIsDeadException.printDeathMessage();
            TacticBaseBattle.getInstance().getBattle().removeDeadCharacter(unitIsDeadException.getDeadUnit());
        } finally {
            removeActionToken(ability);
            if (actionTokens <= 0 && !movementToken) TacticBaseBattle.getInstance().getBattle().endTurn();
            else takeNextAction();
        }
    }

    public void takeItemAction(ConsumableAbility itemAbility, Consumable consumable, List<BoardSpace> possibleBoardSpaces) {
        itemAbility.takeAction(consumable, possibleBoardSpaces);
        removeActionToken(itemAbility);
        if (actionTokens <= 0 && !movementToken) TacticBaseBattle.getInstance().getBattle().endTurn();
        else takeNextAction();
    }

    protected abstract void takeNextAction();

    //public void movementComplete(Battle battle) {
    //   this.movementToken = false;
    //    if (actionTokens <= 0) battle.endTurn();
    //    else takeNextAction();
    //}

    public void setJob(Job job) {
        this.characterJob = job;
        this.abilityList = new ArrayList<>(job.getJobAbilityList());
        if (characterStatSheet != null) characterStatSheet.updateStatSheetAccordingToJob(job);
    }



    public void setBoardSpace(BoardSpace boardSpace) {
        if (this.boardSpace != null && this.boardSpace.getOccupyingUnit() == this) this.boardSpace.removeOccupyingUnit();
        this.boardSpace = boardSpace;
        if (boardSpace.getOccupyingUnit() != this) {
            boardSpace.setOccupyingUnit(this);
        }
    }



    public void setAlive(boolean deathStatus) {
        this.isAlive = deathStatus;
    }

    public String getCharacterName() {
        return characterName;
    }

    public Job getCharacterJob() {
        return characterJob;
    }

    public StatSheet getCharacterStatSheet() {
        return characterStatSheet;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public CharacterStatusEffects getStatusEffects() {
        return this.statusEffects;
    }

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
        Board board = TacticBaseBattle.getInstance().getCurrentBoard();
        List<BoardSpace> possibleSpaces = new LinkedList<>();
        for (Map.Entry<BoardSpace, List<CharacterUnit>> entry : board.getMovementHighlightedSpaces().entrySet()) {
            if (entry.getValue().contains(this)) possibleSpaces.add(entry.getKey());
        }

        return possibleSpaces;
    }

    public List<BoardSpace> getActionRange() {
        List<BoardSpace> damageActionRange = getDamageActionRange();
        List<BoardSpace> supportActionRance = getSupportActionRange();
        if (damageActionRange.size() > supportActionRance.size()) return damageActionRange;
        else return supportActionRance;
    }

    public List<BoardSpace> getActionRangeOfGivenAbility(Ability ability) {
        Board board = TacticBaseBattle.getInstance().getCurrentBoard();
        List<BoardSpace> possibleSpaces = new LinkedList<>();
        for (BoardSpace[] boardSpaceArray : board.getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                if (boardSpace.isValidAbilitySpace(this.boardSpace, ability.getRange())) {
                    possibleSpaces.add(boardSpace);
                }
            }
        }
        return possibleSpaces;
    }

    // ability.getRange will ways be greater than 1
    public List<BoardSpace> getTargetedBoardSpacesForAreaOfEffect(Ability ability) {
        Board board = TacticBaseBattle.getInstance().getCurrentBoard();
        List<BoardSpace> possibleSpaces = new LinkedList<>();

        for (BoardSpace[] boardSpaceArray : board.getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {

                int simpleX = boardSpace.getXCoordinate() - this.boardSpace.getXCoordinate();
                int simpleY = boardSpace.getYCoordinate() - this.boardSpace.getYCoordinate();

                if ((Math.abs(simpleX) + Math.abs(simpleY)) < ability.getAreaOfEffect()) {
                    possibleSpaces.add(boardSpace);
                }
            }
        }
        return possibleSpaces;
    }

    protected List<BoardSpace> getDamageActionRange() {
        Board board = TacticBaseBattle.getInstance().getCurrentBoard();
        List<BoardSpace> possibleSpaces = new LinkedList<>();
        for (BoardSpace[] boardSpaceArray : board.getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                if (boardSpace.isValidAbilitySpace(this.boardSpace, this.getCharacterJob().getMaxDamageAbilityReach())) {
                    possibleSpaces.add(boardSpace);
                }
            }
        }
        return possibleSpaces;
    }

    protected List<BoardSpace> getSupportActionRange() {
        Board board = TacticBaseBattle.getInstance().getCurrentBoard();
        List<BoardSpace> possibleSpaces = new LinkedList<>();
        for (BoardSpace[] boardSpaceArray : board.getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                if (boardSpace.isValidAbilitySpace(this.boardSpace, this.getCharacterJob().getMaxSupportingAbilityReach())) {
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
        if (usedAbility.endsTurn()) {
            this.actionTokens = 0;
            this.movementToken = false;
        } else if (usedAbility.getClass() == MovementAbility.class) {
            this.movementToken = false;
        } else this.actionTokens--;
    }

    public List<Ability> getAbilityList() {
        return this.abilityList;
    }

    public void setActionToken(int actionsLeft) {
        this.actionTokens = actionsLeft;
    }

    public void setMovementToken(boolean hasMovement) {
        this.movementToken = hasMovement;
    }

    public int getDirection() {
        return this.direction;
    }


    public CharacterSprite getCharacterSprite() {
        return this.sprite;
    }
}

