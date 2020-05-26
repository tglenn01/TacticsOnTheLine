package main.model.characterSystem;

import main.exception.BattleIsOverException;
import main.exception.OutOfManaException;
import main.model.combatSystem.Ability;
import main.model.itemSystem.Consumable;
import main.model.jobSystem.Job;
import main.ui.Battle;

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
    public void startTurn(Battle battle) throws BattleIsOverException {
        System.out.println("It is " + this.characterName + "'s turn");
        statusEffects.updateStatusEffect(this);
        Ability chosenAbility = getChosenAbility(battle);
        if (chosenAbility.isAreaOfEffect()) takeActionMultipleTimes(battle, chosenAbility);
        else takeActionOnce(battle, chosenAbility);
    }

    @Override
    public void useAbility(Battle battle, Ability chosenAbility) {
        // stub
    }

    @Override
    public void useItem(Battle battle, Consumable item) {
        // stub
    }

    protected Ability getChosenAbility(Battle battle) {
        Random randomAbilitySelector = new Random();
        List<Ability> jobAbilityList = this.getCharacterJob().getJobAbilityList();
        Ability chosenAbility = jobAbilityList.get(randomAbilitySelector.nextInt(jobAbilityList.size()));
        if (chosenAbility.getAbilityType() == Ability.AbilityType.ITEM) {
            chosenAbility = getChosenAbility(battle); // NPCs can't use items
        }
        try {
            chosenAbility.hasEnoughMana(this);
        } catch (OutOfManaException e) {
            getChosenAbility(battle); // keep repeating till it gets valid ability
        }
        System.out.println(this.characterName + " has used " + chosenAbility.getAbilityName());
        return chosenAbility;
    }

    @Override
    protected List<CharacterUnit> getMultipleTargets(Ability ability, List<CharacterUnit> possibleTargets) {
        List<CharacterUnit> chosenTargets = new ArrayList<>();
        for (int i = 1; i <= ability.getAreaOfEffect(); i++) {
            CharacterUnit receivingUnit = getReceivingUnit(possibleTargets);
            chosenTargets.add(receivingUnit);
            possibleTargets.remove(receivingUnit);
        }
        return chosenTargets;
    }

   protected List<CharacterUnit> getUnitOptions(Battle battle, CharacterType type) {
        if (type == CharacterType.ALLY) return battle.getTurnOrder().getAliveEnemyCharacters();
        else return battle.getTurnOrder().getAlivePlayableCharacters();
   }

    protected CharacterUnit getReceivingUnit(List<CharacterUnit> unitOptions) {
        Random receivingUnitSelector = new Random();
        return unitOptions.get(receivingUnitSelector.nextInt(unitOptions.size()));
    }
}
