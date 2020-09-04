package main.ui;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.exception.BattleIsOverException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.NPC;
import main.model.graphics.scenes.DefeatScreen;
import main.model.graphics.scenes.VictoryScreen;
import main.model.scenarioSystem.Scenario;

import java.util.List;

public class Battle {
    private TurnOrderCompiler turnOrder;
    private List<CharacterUnit> activeCharacters;
    private List<CharacterUnit> enemyCharacters;
    private CharacterUnit activeCharacter;
    private Scenario activeScenario;
    private int cursor;

    public Battle(List<CharacterUnit> playableCharacters, Scenario scenario) {
        TacticBaseBattle.getInstance().setBattle(this);
        enemyCharacters = scenario.getListOfEnemies();
        turnOrder = new TurnOrderCompiler(playableCharacters, scenario.getListOfEnemies());
        this.activeScenario = scenario;
        startAnimations();
        playableCharacters.forEach(e -> e.getCharacterStatSheet().setAllStatsToMax());
        updateNextRound();
    }

    private void startAnimations() {
        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                double t  = (now - startNanoTime) / 1000000000.0;
                for (CharacterUnit unit : turnOrder.getFieldedCharacters()) {
                    unit.getCharacterSprite().updateImage(t, unit.getDirection());
                }
            }
        }.start();
    }

    private void updateNextRound() {
        this.activeCharacters = turnOrder.updateTurnOrder();
        if (activeCharacters.isEmpty()) updateNextRound();
        else {
            cursor = 0;
            startNewTurn(activeCharacters.get(cursor));
        }
    }

    private void startNewTurn(CharacterUnit activeCharacter) {
        if (activeCharacter.isAlive()) {
            this.setActiveCharacter(activeCharacter);
            if (TacticBaseBattle.getInstance().getPartyMemberList().contains(activeCharacter)) activeCharacter.startTurn();
            startOfTurnNotification(activeCharacter);
            // activeCharacter.startTurn(); called in startOfTurnNNotification
        } else endTurn();
    }

    private void startOfTurnNotification(CharacterUnit activeCharacter) {
        Popup startOfTurnPopup = new Popup();
        Label characterNameLabel = new Label("It is " + activeCharacter.getCharacterName() + "'s Turn");
        characterNameLabel.setId("startOfTurnPopup");
        startOfTurnPopup.getContent().add(characterNameLabel);
        startOfTurnPopup.setWidth(140);
        startOfTurnPopup.setHeight(100);

        startOfTurnPopup.centerOnScreen();
        startOfTurnPopup.setAutoHide(true);
        startOfTurnPopup.setOpacity(0);
        startOfTurnPopup.setOnShown(e -> new AnimationTimer() {
            private double opacity = 0;
            private long delay = 15_000_000;
            private long prevTime = 0;

            @Override
            public void handle(long now) {

                if ((now - prevTime) >= delay) {
                    opacity += 0.01;
                    startOfTurnPopup.setOpacity(opacity);
                }

                prevTime = now;

                if (opacity >= 1) {
                    stop();
                    if (activeCharacter.getClass() == NPC.class){
                        startOfTurnPopup.hide();
                        activeCharacter.startTurn();
                    }
                    Runtime.getRuntime().gc();
                }
            }
        }.start());

        Stage primaryStage = TacticBaseBattle.getInstance().getPrimaryStage();
        startOfTurnPopup.show(primaryStage, primaryStage.getWidth() / 2.00, primaryStage.getHeight() / 2.00);
    }

    private void setActiveCharacter(CharacterUnit activeCharacter) {
        this.activeCharacter = activeCharacter;
    }

    public void endTurn() {
        cursor++;
        if (cursor == activeCharacters.size()) {
            activeCharacters.clear();
            updateNextRound();
        } else {
            startNewTurn(activeCharacters.get(cursor));
        }
    }

    public TurnOrderCompiler getTurnOrder() {
        return turnOrder;
    }

    public CharacterUnit getActiveCharacter() { return activeCharacter; }

    public List<CharacterUnit> getEnemyCharacters() {
        return this.enemyCharacters;
    }

    public void removeDeadCharacter(CharacterUnit deadCharacter) {
        try{
            turnOrder.removeDeadCharacterFromFieldedCharacters(deadCharacter);
        } catch (BattleIsOverException e) {
            if (turnOrder.didUserWin()) {
                activeScenario.setCompleted(true);
                new VictoryScreen();
            }
            else new DefeatScreen();
        }
    }

    public Scenario getActiveScenario() {
        return this.activeScenario;
    }
}
