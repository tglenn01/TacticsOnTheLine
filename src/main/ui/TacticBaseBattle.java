package main.ui;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.*;
import main.model.scenarioSystem.Scenario;

import java.util.ArrayList;
import java.util.List;

public class TacticBaseBattle {
    private static TacticBaseBattle tacticBaseBattle;
    private List<CharacterUnit> partyMemberList;
    private List<Job> availableJobs;

    private TacticBaseBattle() {
        partyMemberList = new ArrayList<>();
        availableJobs = new ArrayList<>();
        initializeGraphics();
        initializeJobs();
        initializeCharacters();
        initializeItems();
        Scenario scenario = initializeScenario();
        new Battle(partyMemberList, scenario);
    }

    public static TacticBaseBattle getInstance() {
        if (tacticBaseBattle == null) {
            return new TacticBaseBattle();
        } else return tacticBaseBattle;
    }

    // show main menu screen
    private void initializeGraphics() {

    }

    private void initializeJobs() {
        availableJobs.add(new Archer());
        availableJobs.add(new Bard());
        availableJobs.add(new BattleMage());
        availableJobs.add(new Cleric());
        availableJobs.add(new Lancer());
        availableJobs.add(new Noble());
        availableJobs.add(new Warrior());
    }

    // have the player choose the classes of the characters
    private void initializeCharacters() {

    }

    private void initializeItems() {
        ConsumableItemInventory consumables = ConsumableItemInventory.getInstance();
        Consumable healthPotion = new Consumable("Health Potion", 10,
                1, Ability.AbilityType.HEAL);
        Consumable manaPotion = new Consumable("Mana Potion", 15,
                1, Ability.AbilityType.MANA_GAIN);
        consumables.addConsumableItem(healthPotion);
        consumables.addConsumableItem(manaPotion);
    }

    // have the user pick a scenario
    private Scenario initializeScenario() {

        return null;
    }
}
