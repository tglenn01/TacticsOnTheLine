package main.model.characterSystem;

import main.exception.OutOfManaException;
import main.model.boardSystem.BoardSpace;
import main.model.combatSystem.Ability;
import main.model.itemSystem.Consumable;
import main.model.jobSystem.Job;
import main.ui.Battle;
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
    public void startTurn(Battle battle) {
        System.out.println("It is " + this.characterName + "'s turn");
        statusEffects.updateStatusEffect(this);
        if (isTargetableInRange()) targetUnit();
        else takeMovement(Job.move);
    }

    @Override
    public void useAbility(Battle battle, Ability chosenAbility) {
        // stub
    }

    @Override
    public void useItem(Battle battle, Consumable item) {
        // stub
    }

    @Override
    public void takeMovement(Ability movementAbility) {
        CharacterUnit closetTarget = getClosetTarget();
        if (closetTarget != null) {
            List<BoardSpace> range = getMovementRange();
            BoardSpace validSpace = getClosetSpace(range, closetTarget);

            this.boardSpace.removeOccupyingUnit();
            validSpace.setOccupyingUnit(this);
            movementComplete(TacticBaseBattle.getInstance().getBattle());
        } else {
            this.actionTokens = 0;
            TacticBaseBattle.getInstance().getBattle().endTurn();
        }

    }

    @Override
    protected void takeNextAction() {
        if (isTargetableInRange()) targetUnit();
        else takeMovement(Job.move);
    }

    private boolean isTargetableInRange() {
        for (BoardSpace boardSpace : getActionRange()) {
            if (this.getCharacterJob().hasSupportingAbility()) {
                if (boardSpace.getOccupyingUnit() != null) return true;
            } else if (isEnemyInRange()) return true;
        }
        return false;
    }

    private void targetUnit() {
        if (isEnemyInRange()) targetEnemy();
        else if (isNPCInRange() && this.getCharacterJob().hasSupportingAbility()) supportAlly();
        else this.takeAction(TacticBaseBattle.getInstance().getBattle(), Job.defend, this);
    }

    private void targetEnemy() {
        List<CharacterUnit> possibleTargets = new ArrayList<>();
        getEnemiesInRange(possibleTargets);
        CharacterUnit receivingUnit = targetLowestHealthTargetInRange(possibleTargets);
        List<Ability> possibleAbilities = getPossibleOffensiveAbilities(receivingUnit);
        Ability chosenAbility = getChosenAbility(possibleAbilities);
        this.takeAction(TacticBaseBattle.getInstance().getBattle(), chosenAbility, receivingUnit);
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

    private void getEnemiesInRange(List<CharacterUnit> possibleTargets) {
        for (BoardSpace boardSpace : getActionRange()) {
            if (TacticBaseBattle.getInstance().getPartyMemberList().contains(boardSpace.getOccupyingUnit())) {
                possibleTargets.add(boardSpace.getOccupyingUnit());
            }
        }
    }


    private boolean isNPCInRange() {
        return false;
    }

    private void supportAlly() {
        if (hasSupportAbility()) giveSupport();
        else this.takeAction(TacticBaseBattle.getInstance().getBattle(), Job.defend, this);
    }

    private boolean hasSupportAbility() {
        return false;
    }

    private void giveSupport() {
        List<CharacterUnit> possibleTargets = new ArrayList<>();
        getAlliesInRange(possibleTargets);
        CharacterUnit receivingUnit = targetLowestHealthTargetInRange(possibleTargets);
        List<Ability> possibleAbilities = getPossibleDefensiveAbilities(receivingUnit);
        Ability chosenAbility = getChosenAbility(possibleAbilities);
        this.takeAction(TacticBaseBattle.getInstance().getBattle(), chosenAbility, receivingUnit);
    }

    private void getAlliesInRange(List<CharacterUnit> possibleTargets) {
        for (BoardSpace boardSpace : getActionRange()) {
            if (TacticBaseBattle.getInstance().getBattle().getTurnOrder().getAliveEnemyCharacters().contains(boardSpace.getOccupyingUnit())) {
                possibleTargets.add(boardSpace.getOccupyingUnit());
            }
        }
    }


    private boolean isEnemyInRange() {
        for (BoardSpace space : getActionRange()) {
            if (TacticBaseBattle.getInstance().getPartyMemberList().contains(space.getOccupyingUnit())) {
                for (Ability ability : this.characterJob.getJobAbilityList()) {
                    if (isUnitInRangeOfAbility(ability, space.getOccupyingUnit()) && hasEnoughMana(ability.getManaCost(), this.getCharacterStatSheet().getMana())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // for every board space check to see if it is the closet unit to this unit
    private CharacterUnit getClosetTarget() {
        BoardSpace currentSpace = this.getBoardSpace();
        int movementRange = this.characterStatSheet.getMovement() + this.characterJob.getMaxAbilityRange(); // only move if enemy is in range of an ability + movement
        CharacterUnit target = null;
        for (BoardSpace[] boardSpaceArray : TacticBaseBattle.getInstance().getCurrentBoard().getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                if (TacticBaseBattle.getInstance().getPartyMemberList().contains(boardSpace.getOccupyingUnit())) {
                    int differenceInXCord = boardSpace.getXCoordinate() - currentSpace.getXCoordinate();
                    int differenceInYCord = boardSpace.getYCoordinate() - currentSpace.getYCoordinate();
                    int targetsRangeFromThisUnit = Math.abs(differenceInXCord) + Math.abs(differenceInYCord);

                    if (targetsRangeFromThisUnit < movementRange) {
                        movementRange = targetsRangeFromThisUnit;
                        target = boardSpace.getOccupyingUnit();
                    }
                }
            }
        }
        return target;
    }


    private BoardSpace getClosetSpace(List<BoardSpace> range, CharacterUnit closetTarget) {
        BoardSpace chosenBoardSpace = this.boardSpace;
        int closetDistanceSoFar = Math.abs(this.boardSpace.getXCoordinate() - closetTarget.getBoardSpace().getXCoordinate()) + Math.abs(this.boardSpace.getYCoordinate() - closetTarget.getBoardSpace().getYCoordinate());
        for (BoardSpace boardSpace : range) {
            int differenceInXCord = boardSpace.getXCoordinate() - closetTarget.getBoardSpace().getXCoordinate();
            int differenceInYCord = boardSpace.getYCoordinate() - closetTarget.getBoardSpace().getYCoordinate();
            int spacesBoardSpaceIsFromTarget = Math.abs(differenceInXCord) + Math.abs(differenceInYCord);

            if (spacesBoardSpaceIsFromTarget < closetDistanceSoFar && !boardSpace.isOccupied()) {
                chosenBoardSpace = boardSpace;
                closetDistanceSoFar = spacesBoardSpaceIsFromTarget;
            }
        }
        return chosenBoardSpace;
    }

    // assume that there is at least one valid ability that will be in range and have enough mana for
    protected Ability getChosenAbility(List<Ability> possibleAbilities) {
        Random randomAbilitySelector = new Random();
        Ability chosenAbility = possibleAbilities.get(randomAbilitySelector.nextInt(possibleAbilities.size()));


        if (chosenAbility.getAbilityType() == Ability.AbilityType.ITEM) {
            chosenAbility = getChosenAbility(possibleAbilities); // NPCs can't use items
        }
        try {
            chosenAbility.hasEnoughMana(this);
        } catch (OutOfManaException e) {
            getChosenAbility(possibleAbilities); // keep repeating till it gets valid ability
        }

        System.out.println(this.characterName + " has used " + chosenAbility.getAbilityName());
        return chosenAbility;
    }


    private boolean isUnitInRangeOfAbility(Ability chosenAbility, CharacterUnit receivingUnit) {
        BoardSpace receivingUnitBoardSpace = receivingUnit.getBoardSpace();
        return receivingUnitBoardSpace.inRange(this.boardSpace, chosenAbility.getRange());
    }

    private List<Ability> getPossibleOffensiveAbilities(CharacterUnit receivingUnit) {
        List<Ability> possibleAbilities = new ArrayList<>();
        for (Ability ability : this.getCharacterJob().getJobAbilityList()) {
            if (!ability.targetsAlly() && isUnitInRangeOfAbility(ability, receivingUnit)) {
                try {
                    ability.hasEnoughMana(this);
                    possibleAbilities.add(ability);
                } catch (OutOfManaException e) {
                    // don't add if not mana
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
                    // don't add if not mana
                }
            }
        }
        return possibleAbilities;
    }
}
