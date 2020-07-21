package main.ui;

import javafx.stage.Stage;
import main.model.boardSystem.Board;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.graphics.scenes.CharacterSelect;
import main.model.graphics.scenes.ScenarioSelectScreen;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.model.itemSystem.items.GlassShieldConsumable;
import main.model.itemSystem.items.HealthPotionConsumable;
import main.model.itemSystem.items.LiquidCourageConsumable;
import main.model.itemSystem.items.ManaPotionConsumable;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.archerJob.Archer;
import main.model.jobSystem.jobs.bardJob.Bard;
import main.model.jobSystem.jobs.battleMageJob.BattleMage;
import main.model.jobSystem.jobs.clericJob.Cleric;
import main.model.jobSystem.jobs.gunnerJob.Gunner;
import main.model.jobSystem.jobs.lancerJob.Lancer;
import main.model.jobSystem.jobs.nobleJob.Noble;
import main.model.jobSystem.jobs.thiefJob.Thief;
import main.model.jobSystem.jobs.warriorJob.Warrior;
import main.model.scenarioSystem.Scenario;

import java.util.ArrayList;
import java.util.List;

public class TacticBaseBattle {
    private static TacticBaseBattle tacticBaseBattle;
    private List<CharacterUnit> partyMemberList;
    private List<Job> availableJobs;
    private Stage primaryStage;
    private Board currentBoard;
    private Battle battle;

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
        availableJobs.add(new Gunner());
        availableJobs.add(new Lancer());
        availableJobs.add(new Noble());
        availableJobs.add(new Thief());
        availableJobs.add(new Warrior());
        StatSheet.updateMaxStats(availableJobs);
    }

    private void initializeItems() {
        ConsumableItemInventory consumables = ConsumableItemInventory.getInstance();
        Consumable healthPotion = new HealthPotionConsumable();
        Consumable manaPotion = new ManaPotionConsumable();
        Consumable liquidCourage = new LiquidCourageConsumable();
        Consumable glassShield = new GlassShieldConsumable();
        consumables.addConsumableItem(healthPotion);
        consumables.addConsumableItem(manaPotion);
        consumables.addConsumableItem(liquidCourage);
        consumables.addConsumableItem(glassShield);
    }

    // have the player choose the classes of the characters
    public void characterSelect() {
        new CharacterSelect();
    }

    // have the user pick a scenario
    public void scenarioSelect() {
        new ScenarioSelectScreen();
    }

    public void startBattle(Scenario scenario) {
        scenario.displayScenario(partyMemberList);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setPartyMemberList(List<CharacterUnit> partyMemberList) {
        this.partyMemberList = partyMemberList;
    }

    public void setCurrentBoard(Board board) {
        this.currentBoard = board;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public List<Job> getAvailableJobs() {
        return this.availableJobs;
    }

    public Board getCurrentBoard() {
        return this.currentBoard;
    }

    public List<CharacterUnit> getPartyMemberList() {
        return this.partyMemberList;
    }

    public Battle getBattle() {
        return this.battle;
    }
}
