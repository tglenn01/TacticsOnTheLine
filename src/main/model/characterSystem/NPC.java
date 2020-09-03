package main.model.characterSystem;

import javafx.util.Pair;
import main.exception.OutOfManaException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.characterList.characterSprites.EnemySprite;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.graphics.sceneElements.images.CharacterPortrait;
import main.model.itemSystem.Consumable;
import main.model.jobSystem.Job;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static main.model.graphics.sceneElements.images.CharacterPortrait.ESTELLE_PORTRAIT;

public class NPC extends CharacterUnit {

    public NPC(Job job, String name) {
        this.characterName = name;
        setJob(job);
        this.characterStatSheet = new StatSheet(this.characterJob);
    }

    @Override
    protected void setPersonalStatBonuses() {
        //
    }

    @Override
    protected void setCharacterSprite() {
        this.sprite = new EnemySprite(this);
    }

    @Override
    protected void setCharacterPortrait() {
        this.characterPortrait = new CharacterPortrait(ESTELLE_PORTRAIT);
    }

    protected void addPersonalAbilityToAbilityList() {
        //
    }


    @Override
    public void startTurn() {
        TacticBaseBattle.getInstance().getBattle().endTurn();
        /*System.out.println("It is " + this.characterName + "'s turn");
        statusEffects.updateStatusEffect(this);
        this.actionTokens = ACTIONS_PER_TURN;
        this.movementToken = true;
        List<BoardSpace> targetableEnemySpaces = getTargetableEnemySpaces();
        List<BoardSpace> targetableAllySpaces = getTargetableAllySpaces();

        if (isEnemyInRange(targetableEnemySpaces) && actionTokens > 0) targetEnemy(targetableEnemySpaces);
        else if (isAllyInRange(targetableAllySpaces) && actionTokens > 0) supportAlly(targetableAllySpaces);
        else takeMovement(Job.move);*/
    }

    private List<BoardSpace> getTargetableEnemySpaces() {
        List<BoardSpace> targetableEnemySpaces = new LinkedList<>();
        for (Ability ability : abilityList) {
            //if (!ability.targetsAlly()) targetableEnemySpaces.addAll(ability.getTargetedBoardSpaces(this));
        }
        return targetableEnemySpaces;
    }

    private List<BoardSpace> getTargetableAllySpaces() {
        List<BoardSpace> targetableAllySpaces = new LinkedList<>();
        for (Ability ability : abilityList) {
            //if (ability.targetsAlly()) targetableAllySpaces.addAll(ability.getTargetedBoardSpaces(this));
        }
        return targetableAllySpaces;
    }

    @Override
    public void takeNextAction() {
        List<BoardSpace> targetableEnemySpaces = getTargetableEnemySpaces();
        List<BoardSpace> targetableAllySpaces = getTargetableAllySpaces();
        if (isEnemyInRange(targetableEnemySpaces) && actionTokens > 0) targetEnemy(targetableEnemySpaces);
        else if (isAllyInRange(targetableAllySpaces) && actionTokens > 0) supportAlly(targetableAllySpaces);
        else if (movementToken) takeMovement(Job.move);
        else TacticBaseBattle.getInstance().getBattle().endTurn();
    }

    @Override
    public void useAbility(Ability chosenAbility) {
        // stub
    }

    @Override
    public void useItem(Consumable item) {
        // stub
    }


    public void takeMovement(Ability movementAbility) {
        CharacterUnit closetTarget = getClosetTarget();
        if (closetTarget != null) {
            List<BoardSpace> range = getMovementRange();
            BoardSpace validSpace = getClosetSpaceToTarget(range, closetTarget);
            List<BoardSpace> validSpaceList = new ArrayList<>();
            validSpaceList.add(validSpace);
            this.takeAction(movementAbility, validSpaceList);
        } else {
            this.movementToken = false;
            takeNextAction();
        }

    }


    private boolean isEnemyInRange(List<BoardSpace> damageActionRange) {
        for (BoardSpace boardSpace : damageActionRange) {
            if (TacticBaseBattle.getInstance().getPartyMemberList().contains(boardSpace.getOccupyingUnit())) {
                if (isAbleToReachWithCurrentMana(boardSpace)) return true;
            }
        }
        return false;
    }

    private boolean isAllyInRange(List<BoardSpace> supportActionRange) {
        if (!characterJob.hasSupportingAbility()) return false;
        for (BoardSpace boardSpace : supportActionRange) {
            if (TacticBaseBattle.getInstance().getBattle().getEnemyCharacters().contains(boardSpace.getOccupyingUnit())) {
                if (isAbleToReachWithCurrentMana(boardSpace)) return true;
            }
        }
        return false;
    }

    // checks if there is an ability that has enough mana that can reach the target
    private boolean isAbleToReachWithCurrentMana(BoardSpace boardSpace) {
        for (Ability ability : this.characterJob.getJobAbilityList()) {
            if (isUnitInRangeOfAbility(ability, boardSpace.getOccupyingUnit()) && hasEnoughMana(ability.getManaCost(), this.getCharacterStatSheet().getMana())) {
                return true;
            }
        }
        return false;
    }

    private void targetEnemy(List<BoardSpace> damageActionRange) {
        List<CharacterUnit> possibleTargets = getEnemiesInRange(damageActionRange);
        Pair<CharacterUnit, List<Ability>> abilitiesThatCanTargetUnit = getReceivingEnemyAndPossibleAbilities(possibleTargets);
        Ability chosenAbility = getChosenAbility(abilitiesThatCanTargetUnit.getValue());
        List<BoardSpace> targetedBoardSpaces = new LinkedList<>();
        targetedBoardSpaces.add(abilitiesThatCanTargetUnit.getKey().getBoardSpace());
        this.takeAction(chosenAbility, targetedBoardSpaces);
    }

    private void supportAlly(List<BoardSpace> supportActionRange) {
        List<CharacterUnit> possibleTargets = getAlliesInRange(supportActionRange);
        Pair<CharacterUnit, List<Ability>> abilitiesThatCanTargetUnit = getReceivingAllyAndPossibleAbilities(possibleTargets);
        Ability chosenAbility = getChosenAbility(abilitiesThatCanTargetUnit.getValue());
        List<BoardSpace> targetedBoardSpaces = new LinkedList<>();
        targetedBoardSpaces.add(abilitiesThatCanTargetUnit.getKey().getBoardSpace());
        this.takeAction(chosenAbility, targetedBoardSpaces);
    }

    private List<CharacterUnit> getEnemiesInRange(List<BoardSpace> damageActionRange) {
        List<CharacterUnit> possibleTargets = new ArrayList<>();
        for (BoardSpace boardSpace : damageActionRange) {
            if (TacticBaseBattle.getInstance().getPartyMemberList().contains(boardSpace.getOccupyingUnit())) {
                possibleTargets.add(boardSpace.getOccupyingUnit());
            }
        }
        return possibleTargets;
    }

    private List<CharacterUnit> getAlliesInRange(List<BoardSpace> supportActionRange) {
        List<CharacterUnit> possibleTargets = new ArrayList<>();
        for (BoardSpace boardSpace : supportActionRange) {
            if (TacticBaseBattle.getInstance().getBattle().getTurnOrder().getAliveEnemyCharacters().contains(boardSpace.getOccupyingUnit())) {
                possibleTargets.add(boardSpace.getOccupyingUnit());
            }
        }
        return possibleTargets;
    }

    private Pair<CharacterUnit, List<Ability>> getReceivingEnemyAndPossibleAbilities(List<CharacterUnit> possibleTargets) {
        CharacterUnit receivingUnit = targetLowestHealthTargetInRange(possibleTargets);
        List<Ability> possibleAbilities = getPossibleOffensiveAbilities(receivingUnit);
        if (!possibleAbilities.isEmpty()) {
            return new Pair<>(receivingUnit, possibleAbilities);
        } else {
            possibleTargets.remove(receivingUnit);
            return getReceivingEnemyAndPossibleAbilities(possibleTargets);
        }
    }

    private Pair<CharacterUnit, List<Ability>> getReceivingAllyAndPossibleAbilities(List<CharacterUnit> possibleTargets) {
        CharacterUnit receivingUnit = targetLowestHealthTargetInRange(possibleTargets);
        List<Ability> possibleAbilities = getPossibleDefensiveAbilities(receivingUnit);
        if (!possibleAbilities.isEmpty()) {
            return new Pair<>(receivingUnit, possibleAbilities);
        } else {
            possibleTargets.remove(receivingUnit);
            return getReceivingAllyAndPossibleAbilities(possibleTargets);
        }
    }

    private CharacterUnit targetLowestHealthTargetInRange(List<CharacterUnit> possibleTargets) {
        CharacterUnit receivingUnit = null;
        for (CharacterUnit possibleTarget : possibleTargets) {
            if (receivingUnit == null) receivingUnit = possibleTarget;
            else if (possibleTarget.getCharacterStatSheet().getHealth() <
                    receivingUnit.getCharacterStatSheet().getHealth()) receivingUnit = possibleTarget;
        }
        return receivingUnit;
    }

    private List<Ability> getPossibleOffensiveAbilities(CharacterUnit receivingUnit) {
        List<Ability> possibleAbilities = new ArrayList<>();
        for (Ability ability : this.getCharacterJob().getJobAbilityList()) {
            if (!ability.targetsAlly() && isUnitInRangeOfAbility(ability, receivingUnit)) {
                try {
                    ability.hasEnoughMana(this);
                    possibleAbilities.add(ability);
                } catch (OutOfManaException e) {
                    // don't add if not enough mana
                }
            }
        }
        return possibleAbilities;
    }

    private List<Ability> getPossibleDefensiveAbilities(CharacterUnit receivingUnit) {
        List<Ability> possibleAbilities = new ArrayList<>();
        for (Ability ability : this.getCharacterJob().getJobAbilityList()) {
            if (ability.targetsAlly() && isUnitInRangeOfAbility(ability, receivingUnit)) {
                try {
                    ability.hasEnoughMana(this);
                    possibleAbilities.add(ability);
                } catch (OutOfManaException e) {
                    // don't add if not enough mana
                }
            }
        }
        return possibleAbilities;
    }

    // for every board space check to see if it is the closet unit to this unit
    private CharacterUnit getClosetTarget() {
        int movementRange = this.characterStatSheet.getMovement() + this.characterJob.getMaxTotalAbilityReach(); // only move if enemy is in range of an ability + movement
        CharacterUnit target = null;
        for (CharacterUnit unit : TacticBaseBattle.getInstance().getBattle().getTurnOrder().getAlivePlayableCharacters()) {
            BoardSpace boardSpace = unit.getBoardSpace();
            int differenceInXCord = boardSpace.getXCoordinate() - this.boardSpace.getXCoordinate();
            int differenceInYCord = boardSpace.getYCoordinate() - this.boardSpace.getYCoordinate();
            int targetsRangeFromThisUnit = Math.abs(differenceInXCord) + Math.abs(differenceInYCord);

            if (targetsRangeFromThisUnit < movementRange && boardSpace.getOccupyingUnit() != this) {
                movementRange = targetsRangeFromThisUnit;
                target = boardSpace.getOccupyingUnit();
            }
        }
        return target;
    }


    private BoardSpace getClosetSpaceToTarget(List<BoardSpace> range, CharacterUnit closetTarget) {
        BoardSpace chosenBoardSpace = this.boardSpace;
        int closetDistanceSoFar = Math.abs(this.boardSpace.getXCoordinate() - closetTarget.getBoardSpace().getXCoordinate())
                + Math.abs(this.boardSpace.getYCoordinate() - closetTarget.getBoardSpace().getYCoordinate());
        for (BoardSpace boardSpace : range) {
            int differenceInXCord = boardSpace.getXCoordinate() - closetTarget.getBoardSpace().getXCoordinate();
            int differenceInYCord = boardSpace.getYCoordinate() - closetTarget.getBoardSpace().getYCoordinate();
            int spacesBoardSpaceIsFromTarget = Math.abs(differenceInXCord) + Math.abs(differenceInYCord);

            if (spacesBoardSpaceIsFromTarget < closetDistanceSoFar && !boardSpace.isOccupied() && boardSpace.getLandType().isTerrainable()) {
                chosenBoardSpace = boardSpace;
                closetDistanceSoFar = spacesBoardSpaceIsFromTarget;
            }
        }
        return chosenBoardSpace;
    }

    // assume that there is at least one valid ability that will be in range and have enough mana for
    protected Ability getChosenAbility(List<Ability> possibleAbilities) {
        if (possibleAbilities.isEmpty()) return Job.defend;

        Random randomAbilitySelector = new Random();
        Ability chosenAbility = possibleAbilities.get(randomAbilitySelector.nextInt(possibleAbilities.size()));



        if (chosenAbility.getClass() == ConsumableAbility.class) {
            possibleAbilities.remove(chosenAbility);
            chosenAbility = getChosenAbility(possibleAbilities); // NPCs can't use items
        }
        try {
            chosenAbility.hasEnoughMana(this);
            System.out.println(this.characterName + " has used " + chosenAbility.getAbilityName());
        } catch (OutOfManaException e) {
            possibleAbilities.remove(chosenAbility);
            chosenAbility = getChosenAbility(possibleAbilities); // keep repeating till it gets valid ability
        }

        return chosenAbility;
    }

    private boolean isUnitInRangeOfAbility(Ability chosenAbility, CharacterUnit receivingUnit) {
        //List<BoardSpace> abilityRange = chosenAbility.getTargetedBoardSpaces(this);
        //return abilityRange.contains(receivingUnit.getBoardSpace());
        return false;
    }
}
