package main.model.combatSystem;

import javafx.animation.PathTransition;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Popup;
import javafx.util.Duration;
import main.exception.OutOfManaException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.NPC;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.combatSystem.abilities.MovementAbility;
import main.model.combatSystem.abilities.personalAbilities.DeactivateAbility;
import main.model.combatSystem.targetTypes.AreaTarget;
import main.model.combatSystem.targetTypes.SelfTarget;
import main.model.jobSystem.BasicAttackAbility;
import main.model.jobSystem.DefendAbility;

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

    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> chosenBoardSpaces) {
        List<CharacterUnit> effectedUnits = new LinkedList<>();
        for (BoardSpace boardSpace : chosenBoardSpaces) {
            if (boardSpace.isOccupied()) {
                CharacterUnit receivingUnit = boardSpace.getOccupyingUnit();
                boolean effectResolved = resolveEffect(activeUnit, receivingUnit);
                if (effectResolved) effectedUnits.add(receivingUnit);
            }
        }
        if (this.getClass() != MovementAbility.class && !effectedUnits.isEmpty())
            calculateExperience(activeUnit, effectedUnits);
    }

    public abstract Node getExpectedResultsLabel(CharacterUnit activeUnit, CharacterUnit receivingUnit);
    public abstract int getHitChance(CharacterUnit activeUnit, CharacterUnit receivingUnit);

    protected void calculateExperience(CharacterUnit activeUnit, List<CharacterUnit> effectedUnits) {
        activeUnit.addExperienceAfterEffectResolves(getAverageLevelOfTargets(effectedUnits));
    }

    protected Integer getAverageLevelOfTargets(List<CharacterUnit> effectedUnits) {
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



    public boolean isUnique() {
        return this.getClass() != BasicAttackAbility.class &&
                this.getClass() != DefendAbility.class &&
                this.getClass() != ConsumableAbility.class &&
                this.getClass() != MovementAbility.class;
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
        return this.getClass() == DefendAbility.class|| this.getClass() == DeactivateAbility.class;
    }



    protected static void effectPopupAnimation(CharacterUnit receivingUnit, String effectAmount, String id) {
        Runtime.getRuntime().gc();
        Popup damageOutcomePopup = new Popup();
        Label damageLabel = new Label(effectAmount);
        damageOutcomePopup.getContent().add(damageLabel);
        damageLabel.setId(id);

        damageOutcomePopup.setOnShown(handle -> {
            Bounds bounds = receivingUnit.getCharacterSprite().localToScreen(receivingUnit.getCharacterSprite().getBoundsInLocal());
            Path path = new Path();
            int minX = (int) bounds.getMinX();
            int minY = (int) bounds.getMaxY();
            int maxX = (int) bounds.getMaxX();
            int maxY = (int) bounds.getMaxY();
            int boundsWidth = (int) bounds.getWidth();
            int boundsHeight = (int) bounds.getHeight();
            MoveTo moveTo = new MoveTo(minX + (Integer.divideUnsigned(boundsWidth, 2)), minY - (Integer.divideUnsigned(boundsHeight, 2)));

            double random = Math.random();
            double endLocation;
            boolean sweepFlag;
            if (random > 0.49) {
                endLocation = maxX + 15;
                sweepFlag = true;
            }
            else {
                endLocation = minX - 15;
                sweepFlag = false;
            }

            ArcTo arcTo = new ArcTo(6, 9, 0, endLocation, maxY - 15, false, sweepFlag);
            path.getElements().add(moveTo);
            path.getElements().add(arcTo);

            PathTransition pathTransition = new PathTransition();
            pathTransition.setPath(path);
            pathTransition.setNode(damageOutcomePopup.getContent().get(0));
            pathTransition.setCycleCount(1);
            pathTransition.setDuration(Duration.seconds(1));
            pathTransition.setOnFinished(e -> {
                damageOutcomePopup.hide();
                Runtime.getRuntime().gc();
            });
            pathTransition.play();
        });
        damageOutcomePopup.show(receivingUnit.getCharacterSprite(), 0 ,0);
    }


    public String getAbilityName() {
        return abilityName;
    }
    protected abstract boolean targetsSelf();
    protected abstract boolean isAreaOfEffect();
    public abstract boolean targetsAlly();
    public abstract String getEffectType();
    public void setRange(int newRange) {
        this.range = newRange;
    }
    public int getRange() {
        return this.range;
    }
    public TargetType getTargetType() {
        return this.targetType;
    }
}
