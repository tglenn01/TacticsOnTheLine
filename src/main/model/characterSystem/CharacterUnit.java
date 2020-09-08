package main.model.characterSystem;

import javafx.scene.image.ImageView;
import main.model.battleSystem.TacticBaseBattle;
import main.model.boardSystem.BoardSpace;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.combatSystem.abilities.MovementAbility;
import main.model.graphics.menus.LevelUpMenu;
import main.model.graphics.sceneElements.images.CharacterPortrait;
import main.model.graphics.sceneElements.images.CharacterSprite;
import main.model.itemSystem.Consumable;
import main.model.jobSystem.BasicAttackAbility;
import main.model.jobSystem.Job;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

public abstract class CharacterUnit {
    protected final int ACTIONS_PER_TURN = 1;

    protected String characterName;
    protected Job characterJob;
    protected BoardSpace boardSpace;
    protected List<Ability> abilityList = new ArrayList<>();
    protected StatSheet characterStatSheet;
    protected CharacterPortrait characterPortrait;
    protected CharacterSprite sprite;
    protected CharacterStatusEffects statusEffects;
    protected ExperiencePoints experiencePoints;


    protected int actionTokens;
    protected boolean movementToken;
    protected boolean isAlive;
    protected boolean movementRangeIsVisable;
    protected int direction = 2; // 0 = up, 1 = right, 2 = down, 3 = left

    public CharacterUnit() {
        setPersonalStatBonuses();
        setCharacterSprite();
        setCharacterPortrait();
        this.isAlive = true;
        this.movementRangeIsVisable = false;
        statusEffects = new CharacterStatusEffects();
        experiencePoints = new ExperiencePoints();
    }

    public void setDataFromSaveData(String jobTitle, int totalExperience, JSONArray maxStats) {
        this.setJob(TacticBaseBattle.getInstance().getJobFromJobTitle(jobTitle));
        int level = 0;
        while (totalExperience >= 100) {
            level++;
            totalExperience -= 100;
        }
        experiencePoints.setLevel(level);
        experiencePoints.setCurrentExperience(totalExperience);

        List<Integer> statsValue = new ArrayList<>();
        for (int i = 0; i < maxStats.size(); i++) {
            Long stat = (Long) maxStats.get(i);
            statsValue.add(i, stat.intValue());
        }
        characterStatSheet.setMaxHealth(statsValue.get(0));
        characterStatSheet.setMaxMana(statsValue.get(1));
        characterStatSheet.setBaseStrength(statsValue.get(2));
        characterStatSheet.setBaseMagic(statsValue.get(3));
        characterStatSheet.setBaseArmour(statsValue.get(4));
        characterStatSheet.setBaseResistance(statsValue.get(5));
        characterStatSheet.setBaseSpeed(statsValue.get(6));
        characterStatSheet.setBaseDexterity(statsValue.get(7));
        characterStatSheet.setMovement(statsValue.get(8));
    }


    protected abstract void setPersonalStatBonuses();

    protected abstract void setCharacterSprite();

    protected abstract void setCharacterPortrait();

    protected abstract void addPersonalAbilityToAbilityList();

    public abstract void startTurn();

    public abstract void useAbility(Ability chosenAbility);

    public abstract void useItem(Consumable item);

    public void takeAction(Ability ability, List<BoardSpace> chosenBoardSpaces) {
        payManaCost(ability); // we know that we have enough mana to cast
        ability.takeAction(this, chosenBoardSpaces);
        removeActionToken(ability);
        if (!LevelUpMenu.isDisplaying && !TacticBaseBattle.getInstance().getBattle().getTurnOrder().isBattleOver()) {
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

    protected void removeActionToken(Ability usedAbility) {
        if (usedAbility.endsTurn()) {
            this.actionTokens = 0;
            this.movementToken = false;
        } else if (usedAbility.getClass() == MovementAbility.class) {
            this.movementToken = false;
        } else this.actionTokens--;
    }

    public abstract void takeNextAction();

    public void setJob(Job job) {
        this.characterJob = job;
        this.abilityList = new ArrayList<>(job.getJobAbilityList());
        if (characterStatSheet != null) characterStatSheet.updateStatSheetAccordingToJob(job);
    }


    public void setBoardSpace(BoardSpace newBoardSpace) {
        if (this.boardSpace != null && this.boardSpace.getOccupyingUnit() == this)
            this.boardSpace.removeOccupyingUnit();
        this.boardSpace = newBoardSpace;
        if (newBoardSpace.getOccupyingUnit() != this) {
            newBoardSpace.setOccupyingUnit(this);
        }
    }

    private void payManaCost(Ability chosenAbility) {
        StatSheet activeUnitStatSheet = this.getCharacterStatSheet();
        activeUnitStatSheet.setMana(activeUnitStatSheet.getMana() - chosenAbility.getManaCost());
    }

    public abstract void addExperienceAfterEffectResolves(Integer averageLevel);

    public Ability getBasicAttack() {
        for (Ability ability : abilityList) {
            if (ability.getClass() == BasicAttackAbility.class) return ability;
        }
        return null;
    }

    public Ability getMovementAbility() {
        for (Ability ability : abilityList) {
            if (ability.getClass() == MovementAbility.class) return ability;
        }
        return null;
    }

    public String getCharacterName() {
        return characterName;
    }

    public Job getCharacterJob() {
        return characterJob;
    }

    public BoardSpace getBoardSpace() {
        return this.boardSpace;
    }

    public List<Ability> getAbilityList() {
        return this.abilityList;
    }

    public StatSheet getCharacterStatSheet() {
        return characterStatSheet;
    }

    public CharacterPortrait getCharacterPortrait() {
        return this.characterPortrait;
    }

    public ImageView getSpriteImageView() {
        return this.sprite.getSprite();
    }

    public CharacterSprite getCharacterSprite() {
        return this.sprite;
    }

    public CharacterStatusEffects getStatusEffects() {
        return this.statusEffects;
    }

    public ExperiencePoints getExperiencePoints() {
        return this.experiencePoints;
    }

    public int getLevel() {
        return this.experiencePoints.getLevel();
    }

    public int getActionTokens() {
        return this.actionTokens;
    }

    public boolean getMovementToken() {
        return this.movementToken;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setAlive(boolean deathStatus) {
        this.isAlive = deathStatus;
    }

    public void setMovementRangeIsVisable(boolean visable) {
        this.movementRangeIsVisable = visable;
    }

    public void setMovementToken(boolean hasMovement) {
        this.movementToken = hasMovement;
    }

    public void setActionToken(int actionsLeft) {
        this.actionTokens = actionsLeft;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isMovementRangeIsVisable() {
        return this.movementRangeIsVisable;
    }

    protected boolean hasEnoughMana(int abilityManaCost, int characterMana) {
        return abilityManaCost < characterMana;
    }

    public boolean hasActionToken() {
        return this.actionTokens != 0;
    }

    public boolean hasMovementToken() {
        return this.movementToken;
    }
}

