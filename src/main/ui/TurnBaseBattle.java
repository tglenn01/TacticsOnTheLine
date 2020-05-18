package main.ui;

import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.PlayableCharacterUnit;
import main.model.combatSystem.Ability;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.*;
import main.model.scenarioSystem.Scenario;
import main.model.scenarioSystem.ScenarioOne;
import main.model.scenarioSystem.ScenarioTwo;

import java.util.ArrayList;
import java.util.List;


public class TurnBaseBattle {
    private static int PARTY_SIZE = 4;
    private List<CharacterUnit> partyMemberList;
    private List<Job> availableJobs;

    public TurnBaseBattle() {
        partyMemberList = new ArrayList<>();
        availableJobs = new ArrayList<>();
        initializeJobs();
        initializeCharacters();
        initializeItems();
        Scenario scenario = initializeScenario();
        new Battle(partyMemberList, scenario);
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

    private void initializeCharacters() {
        System.out.println("Choose your party members!");
        for (int i = 1; i <= PARTY_SIZE; i++) {
            createNewPartyMember(i);
        }
    }

    private void createNewPartyMember(int characterPosition) {
        System.out.println("Party Member " + characterPosition + "!");
        System.out.println("Choose One:");
        for (Job job : availableJobs) {
            System.out.println(job.getJobTitle());
        }

        Job characterJob = askUserForJob();

        CharacterUnit partyMember = new PlayableCharacterUnit(characterJob, characterJob.getJobTitle());
        partyMemberList.add(partyMember);
        availableJobs.remove(characterJob);
        System.out.println("You have choosen a " + characterJob.getJobTitle() + "!");
    }

    private Job askUserForJob() {
        UserInput input = new UserInput();
        String command = input.getInput();
        Job characterJob = null;
        for (Job job : availableJobs) {
            if (command.equals(job.getJobTitle())) {
                characterJob = job;
            }
        }
        if (characterJob == null) {
            System.out.println("Not A valid option, please choose again");
            characterJob = askUserForJob();
        }
        return characterJob;
    }

    private void initializeItems() {
        ConsumableItemInventory consumables = ConsumableItemInventory.getInstance();
        Consumable healthPotion = new Consumable("Health_Potion", 10,
                1, Ability.AbilityType.HEAL);
        Consumable manaPotion = new Consumable("Mana_Potion", 15,
                1, Ability.AbilityType.MANA_GAIN);
        consumables.addConsumableItem(healthPotion);
        consumables.addConsumableItem(manaPotion);
    }

    private Scenario initializeScenario() {
        List<Scenario> scenarioList = listScenarios();
        UserInput input = new UserInput();
        String command = input.getInput();
        Scenario battleScenario = null;
        for (Scenario scenario : scenarioList) {
            if (command.equals(scenario.getScenarioName())) {
                battleScenario = scenario;
            }
        }
        if (battleScenario == null) {
            System.out.println("Not a valid option, please choose again");
            battleScenario = initializeScenario();
        }
        return battleScenario;
    }

    private List<Scenario> listScenarios() {
        List<Scenario> scenarioList = new ArrayList<>();
        Scenario scenarioOne = new ScenarioOne();
        Scenario scenarioTwo = new ScenarioTwo();
        System.out.println(scenarioOne.getScenarioName());
        System.out.println(scenarioTwo.getScenarioName());
        scenarioList.add(scenarioOne);
        scenarioList.add(scenarioTwo);
        return scenarioList;
    }
}
