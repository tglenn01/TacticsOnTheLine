package main.model.graphics.menus;

import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.characterSystem.CharacterUnit;
import main.model.itemSystem.ConsumableItemInventory;
import main.ui.TacticBaseBattle;

public class BattleMenu extends Stage {
    private static BattleMenu battleMenu;
    private CharacterMenu characterMenu;
    private AbilityMenu abilityMenu;
    private ItemMenu itemMenu;

    private BattleMenu() {
        this.initOwner(TacticBaseBattle.getInstance().getPrimaryStage());
        this.setOpacity(.90);
        this.initStyle(StageStyle.UTILITY);
        this.setResizable(false);

        this.characterMenu = new CharacterMenu();
        this.abilityMenu = new AbilityMenu();
        this.itemMenu = new ItemMenu();
    }

    public static BattleMenu getInstance() {
        if (battleMenu == null) battleMenu = new BattleMenu();
        return battleMenu;
    }

    public void displayCharacterMenu(CharacterUnit unit) {
        this.setScene(characterMenu.getMenu(unit, this));
        this.setTitle("Menu");
        if (!this.isShowing()) this.show();
    }

    public void displayAbilityMenu(CharacterUnit unit) {
        this.setScene(abilityMenu.getMenu(unit, this));
        this.setTitle("Ability Menu");
    }

    public void displayItemMenu(CharacterUnit unit) {
        if (ConsumableItemInventory.getInstance().isEmpty()) {
            Popup noItemsMessage = new Popup();
            noItemsMessage.getContent().add(new Label("No More Items, Choose Again"));
        } else {
            this.setScene(itemMenu.getMenu(unit, this));
            this.setTitle("Item Menu");
        }
    }
}
