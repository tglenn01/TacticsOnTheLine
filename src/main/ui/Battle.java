package main.ui;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.exception.BattleIsOverException;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.scenes.DefeatScreen;
import main.model.graphics.scenes.VictoryScreen;
import main.model.scenarioSystem.Scenario;

import java.util.List;

public class Battle {
    private TurnOrderCompiler turnOrder;
    private List<CharacterUnit> activeCharacters;
    private List<CharacterUnit> enemyCharacters;
    private CharacterUnit activeCharacter;
    private int cursor;

    public Battle(List<CharacterUnit> playableCharacters, Scenario scenario) {
        TacticBaseBattle.getInstance().setBattle(this);
        enemyCharacters = scenario.getListOfEnemies();
        turnOrder = new TurnOrderCompiler(playableCharacters, scenario.getListOfEnemies());
        battle();
        updateNextRound();
    }

    private void battle() {
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
            Popup startOfTurnPopup = new Popup();
            startOfTurnPopup.getContent().add(new Label("It is " + activeCharacter.getCharacterName() + "'s Turn"));
            startOfTurnPopup.setWidth(140);
            startOfTurnPopup.setHeight(100);
            Stage primaryStage = TacticBaseBattle.getInstance().getPrimaryStage();
           // startOfTurnPopup.show(primaryStage);
            startOfTurnPopup.centerOnScreen();
            startOfTurnPopup.setAutoHide(true);

            startOfTurnPopup.show(primaryStage, primaryStage.getWidth() / 2.00, primaryStage.getHeight() / 2.00);
            this.activeCharacter = activeCharacter;
            activeCharacter.startTurn();
        } else endTurn();
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
            if (turnOrder.didUserWin()) new VictoryScreen();
            else new DefeatScreen();
        }
    }
}
