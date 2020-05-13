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
        Ability choosenAbility = getChoosenAbility(battle);
        abilityTakeAction(battle, choosenAbility);
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

    protected Ability getChoosenAbility(Battle battle) {
        Ability ability = this.characterJob.getJobAbilityList().get(0); // Ability 0 is attack
        try {
            ability.hasEnoughMana(this);
        } catch (OutOfManaException e) {
            getChoosenAbility(battle);
        }
        return ability;
    }
}
