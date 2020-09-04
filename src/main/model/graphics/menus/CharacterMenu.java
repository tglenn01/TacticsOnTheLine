package main.model.graphics.menus;

import javafx.scene.control.Button;
import main.model.characterSystem.CharacterUnit;
import main.model.battleSystem.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

// CharacterMenu, AbilityMenu, and ItemMenu all share the same window and when one is present than it will place
// it's own scene on top of the window
public class CharacterMenu extends DefaultMenu {
    public CharacterMenu() {
        super();
    }

    protected List<Button> setButtons(CharacterUnit activeUnit, BattleMenu battleMenu) {
        List<Button> buttonList = new ArrayList<>();


        Button attackButton = getAttackButton(activeUnit, battleMenu);
        Button moveButton = getMovementButton(activeUnit, battleMenu);
        Button abilitiesButton = getAbilitiesButton(activeUnit, battleMenu);
        Button itemButton = getItemButton(activeUnit, battleMenu);
        Button statusButton = getStatusButton(activeUnit, battleMenu);
        Button  endTurnButton = getEndTurnButton(battleMenu);


        if (!activeUnit.hasActionToken()) {
            disableButton(attackButton);
            disableButton(abilitiesButton);
            disableButton(itemButton);
        }

        if (!activeUnit.hasMovementToken()) {
            disableButton(moveButton);
        }

        buttonList.add(attackButton);
        buttonList.add(moveButton);
        buttonList.add(abilitiesButton);
        buttonList.add(itemButton);
        buttonList.add(statusButton);
        buttonList.add(endTurnButton);

        buttonList.forEach(button -> button.setId("defaultNode"));

        return buttonList;
    }

    private Button getAttackButton(CharacterUnit activeUnit, BattleMenu battleMenu) {
        Button attackButton = new Button("Attack");
        attackButton.setOnMouseClicked(e ->  {
            battleMenu.close();
            activeUnit.useAbility(TacticBaseBattle.getInstance().getBattle().getActiveCharacter().getBasicAttack());
        });
        return attackButton;
    }

    private Button getMovementButton(CharacterUnit activeUnit, BattleMenu battleMenu) {
        Button moveButton = new Button("Move");
        moveButton.setOnMouseClicked(e -> {
            battleMenu.close();
            activeUnit.useAbility(activeUnit.getMovementAbility());
        });
        return moveButton;
    }

    private Button getAbilitiesButton(CharacterUnit unit, BattleMenu battleMenu) {
        Button abilitiesButton = new Button("Ability");
        abilitiesButton.setOnMouseClicked(e -> {
            battleMenu.displayAbilityMenu(unit);
        });
        return abilitiesButton;
    }

    private Button getItemButton(CharacterUnit unit, BattleMenu battleMenu) {
        Button itemButton = new Button("Item");
        itemButton.setOnMouseClicked(e -> {
            battleMenu.displayItemMenu(unit);
        });
        return itemButton;
    }

    private Button getStatusButton(CharacterUnit activeUnit, BattleMenu battleMenu) {
        Button statusButton = new Button("Status");
        statusButton.setOnMouseClicked(e -> {
            if (!CharacterStatsMenu.isDisplaying()) {
                battleMenu.close();
                CharacterStatsMenu.display(activeUnit);
            }
        });
        return statusButton;
    }

    private Button getEndTurnButton(BattleMenu battleMenu) {
        Button endTurnButton = new Button("End Turn");
        endTurnButton.setOnMouseClicked(e -> {
            battleMenu.close();
            TacticBaseBattle.getInstance().getBattle().endTurn();
        });
        return endTurnButton;
    }

    private void disableButton(Button button) {
        button.setDisable(true);
    }
}
