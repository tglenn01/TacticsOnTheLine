package main.model.characterSystem;

import main.ui.Battle;

import java.util.List;
import java.util.Random;

public class NPC extends CharacterUnit {

    public NPC(String name) {
        super(name);
    }

    @Override
    public void takeAction(Battle battle) {
        System.out.println("It is " + this.characterName + "'s turn");
        getChoosenAbility(battle);
    }

    @Override
    protected CharacterUnit getDefendingEnemy(Battle battle) {
        List<CharacterUnit> partyList = battle.getTurnOrder().getAlivePlayableCharacters();
        Random partyMemeberSelector = new Random();
        return partyList.get(partyMemeberSelector.nextInt(partyList.size()));
    }

    protected void getChoosenAbility(Battle battle) {
        CharacterUnit choosenPlayableCharacter = getDefendingEnemy(battle);
        abilityTakeAction(battle, this.characterJob.getJobAbilityList().get(0)); // Ability is attack
    }
}
