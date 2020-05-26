package main.model.graphics.menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.graphics.icons.AbilityButton;
import main.model.itemSystem.ConsumableItemInventory;
import main.ui.Battle;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public class AbilityMenu extends Popup implements EventHandler<ActionEvent> {
    private CharacterUnit activeUnit;
    private Battle battle;
    private Ability chosenAbility;

    public AbilityMenu(CharacterUnit activeUnit, Battle battle, List<Ability> abilityList) {
        this.activeUnit = activeUnit;
        this.battle = battle;
        chosenAbility = null;
        Popup window = new Popup();

        List<AbilityButton> abilityButtonList = new ArrayList<>();
        for (Ability ability : abilityList) {
            AbilityButton abilityButton = new AbilityButton(ability);
            abilityButtonList.add(abilityButton);
            abilityButton.setOnAction(this);
        }
        VBox node = new VBox();
        node.getChildren().addAll(abilityButtonList);
        window.getContent().addAll(node);

        window.show(TacticBaseBattle.getInstance().getPrimaryStage());
    }

    private void openItemMenu() {
        if (ConsumableItemInventory.getInstance().isEmpty()) {
            Popup noItemsMessage = new Popup();
            noItemsMessage.getContent().add(new Label("No More Items, Choose Again"));
        } else {
            new ItemMenu(battle, activeUnit);
        }
    }

    @Override
    public void handle(ActionEvent event) {
        AbilityButton button = (AbilityButton) event.getSource();
        Ability chosenAbility = button.getAbility();
        if (chosenAbility.getClass() == ConsumableAbility.class) openItemMenu();
        else activeUnit.useAbility(battle, chosenAbility);
    }
}
