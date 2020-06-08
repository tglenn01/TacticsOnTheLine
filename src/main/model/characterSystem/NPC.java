package main.model.characterSystem;

import javafx.util.Pair;
import main.exception.OutOfManaException;
import main.model.boardSystem.BoardSpace;
import main.model.combatSystem.Ability;
import main.model.itemSystem.Consumable;
import main.model.jobSystem.Job;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static main.model.characterSystem.CharacterPortrait.ENEMY_PORTRAIT;
import static main.model.characterSystem.CharacterSprite.ENEMY_SPRITE;

public class NPC extends CharacterUnit {

    public NPC(Job job, String name) {
        this.characterName = name;
        this.characterJob = job;
        this.characterStatSheet = new StatSheet(this.characterJob);
        this.setCharacterPortrait(ENEMY_PORTRAIT);
        this.setCharacterSprite(ENEMY_SPRITE);
    }

    @Override
    public void startTurn() {
        System.out.println("It is " + this.characterName + "'s turn");
        statusEffects.updateStatusEffect(this);
        this.actionTokens = 2;
        List<BoardSpace> damageActionRange = getDamageActionRange();
        List<BoardSpace> supportActionRange = getSupportActionRange();
        if (isEnemyInRange(damageActionRange)) targetEnemy(damageActionRange);
        else if (isAllyInRange(supportActionRange)) supportAlly(supportActionRange);
        else takeMovement(Job.move);
    }

    @Override
    protected void takeNextAction() {
        List<BoardSpace> damageActionRange = getDamageActionRange();
        List<BoardSpace> supportActionRange = getSupportActionRange();
        if (isEnemyInRange(damageActionRange)) targetEnemy(damageActionRange);
        else if (isAllyInRange(supportActionRange)) supportAlly(supportActionRange);
        else takeMovement(Job.move);
    }

    @Override
    public void useAbility(Ability chosenAbility) {
        // stub
    }

    @Override
    public void useItem(Consumable item) {
        // stub
    }

    @Override
    public void takeMovement(Ability movementAbility) {
        CharacterUnit closetTarget = getClosetTarget();
        if (closetTarget != null) {
            TacticBaseBattle.getInstance().getCurrentBoard().displayValidMovementSpaces(this, this.getCharacterStatSheet().getMovement());
            List<BoardSpace> range = getMovementRange();
            TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(this);
            BoardSpace validSpace = getClosetSpaceToTarget(range, closetTarget);

            this.boardSpace.removeOccupyingUnit();
            validSpace.setOccupyingUnit(this);
            movementComplete(TacticBaseBattle.getInstance().getBattle());
        } else {
            this.actionTokens = 0;
            TacticBaseBattle.getInstance().getBattle().endTurn();
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
        this.takeAction(chosenAbility, abilitiesThatCanTargetUnit.getKey());
    }

    private void supportAlly(List<BoardSpace> supportActionRange) {
        List<CharacterUnit> possibleTargets = getAlliesInRange(supportActionRange);
        Pair<CharacterUnit, List<Ability>> abilitiesThatCanTargetUnit = getReceivingAllyAndPossibleAbilities(possibleTargets);
        Ability chosenAbility = getChosenAbility(abilitiesThatCanTargetUnit.getValue());
        this.takeAction(chosenAbility, abilitiesThatCanTargetUnit.getKey());
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
        BoardSpace currentSpace = this.getBoardSpace();
        int movementRange = this.characterStatSheet.getMovement() + this.characterJob.getMaxTotalAbilityReach(); // only move if enemy is in range of an ability + movement
        CharacterUnit target = null;
        for (CharacterUnit unit : TacticBaseBattle.getInstance().getBattle().getTurnOrder().getFieldedCharacters()) {
            BoardSpace boardSpace = unit.getBoardSpace();
            int differenceInXCord = boardSpace.getXCoordinate() - currentSpace.getXCoordinate();
            int differenceInYCord = boardSpace.getYCoordinate() - currentSpace.getYCoordinate();
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



        if (chosenAbility.getAbilityType() == Ability.AbilityType.ITEM) {
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
        BoardSpace receivingUnitBoardSpace = receivingUnit.getBoardSpace();
        return receivingUnitBoardSpace.isValidAbilitySpace(this.boardSpace, chosenAbility.getRange());
    }
}
