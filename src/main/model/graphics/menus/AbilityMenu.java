package main.model.graphics.menus;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import main.exception.OutOfManaException;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.combatSystem.abilities.MovementAbility;
import main.model.graphics.sceneElements.images.AbilityDescription;
import main.model.jobSystem.BasicAttackAbility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AbilityMenu extends DefaultMenu {

    protected List<Button> setButtons(CharacterUnit unit, BattleMenu battleMenu) {
        List<Button> abilityButtonList = new ArrayList<>();

        getAbilityButtons(unit, battleMenu, abilityButtonList);
        Button returnButton = getReturnButton(unit, battleMenu);
        abilityButtonList.add(returnButton);

        return abilityButtonList;
    }

    private void getAbilityButtons(CharacterUnit unit, BattleMenu battleMenu, List<Button> abilityButtonList) {
        List<Ability> abilityList = new ArrayList<>(unit.getAbilityList());
        removeDuplicateAbilities(abilityList);
        for (Ability ability : abilityList) {
            Button abilityButton = new Button(ability.getAbilityName());
            try {
                ability.hasEnoughMana(unit);
            } catch (OutOfManaException e) {
                abilityButton.setDisable(true);
            }

            AbilityDescription abilityDescription = new AbilityDescription(ability);
            abilityButtonList.add(abilityButton);
            abilityButton.setOnMousePressed(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    battleMenu.close();
                    unit.useAbility(ability);
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    abilityDescription.display(e.getScreenX(), e.getScreenY());
                }
            });

            abilityButton.setOnMouseReleased(e -> {
                if (abilityDescription.isShowing()) abilityDescription.close();
            });
        }
    }

    private void removeDuplicateAbilities(List<Ability> abilityList) {
        List<Ability> toRemove = new LinkedList<>();
        for (Ability ability : abilityList) {
            if (ability.getClass() == BasicAttackAbility.class || ability.getClass() == MovementAbility.class
            || ability.getClass() == ConsumableAbility.class) toRemove.add(ability);
        }
        abilityList.removeAll(toRemove);
    }
}
