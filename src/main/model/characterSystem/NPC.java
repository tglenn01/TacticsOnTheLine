package main.model.characterSystem;

import main.exception.BattleIsOverException;
import main.exception.OutOfManaException;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.ui.Battle;

import java.util.List;
import java.util.Random;

public class NPC extends CharacterUnit {

    public NPC(Job job, String name) {
        super(job, name);
    }

    @Override
    public void takeAction(Battle battle) throws BattleIsOverException {
        System.out.println("It is " + this.characterName + "'s turn");
        Ability choosenAbility = getChosenAbility(battle);
        abilityTakeAction(battle, choosenAbility);
    }

    @Override
    protected void takeActionOnce(Battle battle, Ability ability) throws BattleIsOverException {
        
    }

    @Override
    protected void takeActionMultipleTimes(Battle battle, Ability ability) throws BattleIsOverException {

    }

    @Override
    protected List<CharacterUnit> getMultipleTargets(Ability ability, List<CharacterUnit> possibleTargets) {
        return null;
    }

    @Override
    protected CharacterUnit getSingleTarget(Battle battle, Ability ability) {
        return null;
    }

    @Override
    protected CharacterUnit getDefendingEnemy(Battle battle) {
        List<CharacterUnit> partyList = battle.getTurnOrder().getAlivePlayableCharacters();
        Random partyMemeberSelector = new Random();
        return partyList.get(partyMemeberSelector.nextInt(partyList.size()));
    }

    @Override
    protected CharacterUnit getSupportedAlly(Battle battle) {
        return this;
    }

    protected Ability getChosenAbility(Battle battle) {
        Ability ability = this.characterJob.getJobAbilityList().get(0); // Ability 0 is attack
        try {
            ability.hasEnoughMana(this);
        } catch (OutOfManaException e) {
            getChosenAbility(battle);
        }
        return ability;
    }

    @Override
    protected CharacterUnit getReceivingUnit(List<CharacterUnit> unitOptions) {
        return null;
    }
}
