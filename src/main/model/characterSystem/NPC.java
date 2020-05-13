package main.model.characterSystem;

import main.exception.BattleIsOverException;
import main.exception.OutOfManaException;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.ui.Battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NPC extends CharacterUnit {

    public NPC(Job job, String name) {
        super(job, name);
    }

    @Override
    public void startTurn(Battle battle) throws BattleIsOverException {
        System.out.println("It is " + this.characterName + "'s turn");
        updateStatusEffect();
        Ability chosenAbility = getChosenAbility(battle);
        if (chosenAbility.isAreaOfEffect()) takeActionMultipleTimes(battle, chosenAbility);
        else takeActionOnce(battle, chosenAbility);
    }

    protected Ability getChosenAbility(Battle battle) {
        Ability ability = this.characterJob.getJobAbilityList().get(4); // Ability 0 is attack
        try {
            ability.hasEnoughMana(this);
        } catch (OutOfManaException e) {
            ability = this.characterJob.getJobAbilityList().get(0);
            //getChosenAbility(battle);
        }
        return ability;
    }

    @Override
    protected void takeActionOnce(Battle battle, Ability ability) throws BattleIsOverException {
        CharacterUnit receivingUnit = getSingleTarget(battle, ability);
        takeAction(battle, ability, receivingUnit);
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
