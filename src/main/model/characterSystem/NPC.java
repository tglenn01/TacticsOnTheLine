package main.model.characterSystem;

import main.exception.NoTargetsInRangeException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.characterList.characterSprites.EnemySprite;
import main.model.combatSystem.Ability;
import main.model.combatSystem.TargetType;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.combatSystem.abilities.MovementAbility;
import main.model.combatSystem.targetTypes.SelfTarget;
import main.model.graphics.sceneElements.images.CharacterPortrait;
import main.model.itemSystem.Consumable;
import main.model.jobSystem.Job;
import main.ui.TacticBaseBattle;

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
        System.out.println("It is " + this.characterName + "'s turn");
        statusEffects.updateStatusEffect(this);
        this.actionTokens = ACTIONS_PER_TURN;
        this.movementToken = true;

        Ability chosenAbility = getChosenAbility(this.abilityList);
        useAbility(chosenAbility);
    }

    private List<BoardSpace> getSelfBoardSpace() {
        List<BoardSpace> selfBoardSpace = new LinkedList<>();
        selfBoardSpace.add(this.boardSpace);
        return selfBoardSpace;
    }

    private List<BoardSpace> getTargetBoardSpaces(Ability chosenAbility, Class characterUnitClass) {
        TargetType targetType = chosenAbility.getTargetType();
        List<BoardSpace> possibleBoardSpaces = targetType.getTargetPattern(this.boardSpace, chosenAbility.getRange(), chosenAbility);
        List<CharacterUnit> targetsInRange = new LinkedList<>();
        for (BoardSpace boardSpace : possibleBoardSpaces) {
            if (boardSpace.isOccupied() && (boardSpace.getOccupyingUnit().getClass() == characterUnitClass ||
                    boardSpace.getOccupyingUnit().getClass().getSuperclass() == characterUnitClass)) {
                targetsInRange.add(boardSpace.getOccupyingUnit());
            }
        }

        CharacterUnit targetUnit = targetLowestHealthTargetInRange(targetsInRange);
        possibleBoardSpaces.clear();
        possibleBoardSpaces = targetType.getTargetPattern(targetUnit.getBoardSpace(), chosenAbility.getAreaOfEffect() - 1, chosenAbility);

        return possibleBoardSpaces;
    }


    @Override
    public void takeNextAction() {
        if (this.actionTokens <= 0) TacticBaseBattle.getInstance().getBattle().endTurn();
        else {
            Ability chosenAbility = getChosenAbility(this.abilityList);
            useAbility(chosenAbility);
        }
    }

    @Override
    public void addExperienceAfterEffectResolves(Integer experience) {
        // NPC's don't gain experience
    }

    @Override
    public void useAbility(Ability chosenAbility) {
        List<BoardSpace> targetBoardSpaces;
        if (chosenAbility.getTargetType().getClass() == SelfTarget.class) {
            targetBoardSpaces = getSelfBoardSpace();
        } else if (chosenAbility.targetsAlly()) {
            targetBoardSpaces = getTargetBoardSpaces(chosenAbility, NPC.class);

        } else {
            targetBoardSpaces = getTargetBoardSpaces(chosenAbility, PlayableCharacterUnit.class);
        }
        this.takeAction(chosenAbility, targetBoardSpaces);
    }

    @Override
    public void useItem(Consumable item) {
        // stub
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



    // assume that there is at least one valid ability that will be in range and have enough mana for
    protected Ability getChosenAbility(List<Ability> possibleAbilities) {
        if (possibleAbilities.isEmpty()) return Job.defend;

        Random randomAbilitySelector = new Random();
        Ability randomAbility = possibleAbilities.get(randomAbilitySelector.nextInt(possibleAbilities.size()));



        if (randomAbility.getClass() == ConsumableAbility.class || randomAbility.getClass() == MovementAbility.class) {
            possibleAbilities.remove(randomAbility);
            randomAbility = getChosenAbility(possibleAbilities); // NPCs
            // can't use items or randomly move
        }
        try {
            randomAbility.hasEnoughMana(this);
            hasTargetInRange(randomAbility);
            System.out.println(this.characterName + " has used " + randomAbility.getAbilityName());
        } catch (Exception e) {
            possibleAbilities.remove(randomAbility);
            randomAbility = getChosenAbility(possibleAbilities); // keep repeating till it gets valid ability
        }

        return randomAbility;
    }

    private void hasTargetInRange(Ability randomAbility) throws NoTargetsInRangeException {
        TargetType targetType = randomAbility.getTargetType();
        int range = randomAbility.getRange();
        List<BoardSpace> spaces = targetType.getTargetPattern(this.boardSpace, range, randomAbility);
        boolean unitInRange = false;
        for (BoardSpace space : spaces) {
            if (space.isOccupied()) {
                if (randomAbility.targetsAlly() && space.getOccupyingUnit().getClass() == NPC.class) unitInRange = true;
                else if (!randomAbility.targetsAlly() && space.getOccupyingUnit().getClass().getSuperclass() == PlayableCharacterUnit.class) unitInRange = true;
            }
        }

        if (!unitInRange) throw new NoTargetsInRangeException();
    }
}
