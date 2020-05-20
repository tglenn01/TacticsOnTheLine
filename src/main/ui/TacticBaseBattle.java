package main.ui;

import javafx.stage.Stage;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.graphics.scenes.CharacterSelect;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.*;
import main.model.scenarioSystem.Scenario;
import main.model.scenarioSystem.ScenarioOne;

import java.util.ArrayList;
import java.util.List;

public class TacticBaseBattle {
    private static TacticBaseBattle tacticBaseBattle;
    private List<CharacterUnit> partyMemberList;
    private List<Job> availableJobs;
    private Stage primaryStage;

    private TacticBaseBattle() {
        partyMemberList = new ArrayList<>();
        availableJobs = new ArrayList<>();
        initializeJobs();
        initializeItems();
    }

    public static TacticBaseBattle getInstance() {
      if (tacticBaseBattle == null) {
          tacticBaseBattle = new TacticBaseBattle();
      }
      return tacticBaseBattle;
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

    private void initializeItems() {
        ConsumableItemInventory consumables = ConsumableItemInventory.getInstance();
        Consumable healthPotion = new Consumable("Health Potion", 10,
                1, Ability.AbilityType.HEAL);
        Consumable manaPotion = new Consumable("Mana Potion", 15,
                1, Ability.AbilityType.MANA_GAIN);
        consumables.addConsumableItem(healthPotion);
        consumables.addConsumableItem(manaPotion);
    }

    // have the player choose the classes of the characters
    public void characterSelect() {
        new CharacterSelect();
    }

    // have the user pick a scenario
    public void scenarioSelect() {
        Scenario scenario = new ScenarioOne();
        scenario.displayScenario(partyMemberList);
    }

    public void startBattle(Scenario scenario) {
        // start battle
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public List<Job> getAvailableJobs() {
        return this.availableJobs;
    }
}
