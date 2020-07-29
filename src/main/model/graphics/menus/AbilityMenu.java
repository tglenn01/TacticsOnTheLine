package main.model.graphics.menus;

import javafx.scene.control.Button;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.combatSystem.abilities.MovementAbility;
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
            abilityButtonList.add(abilityButton);
            abilityButton.setOnMouseClicked(e -> {
                battleMenu.close();
                unit.useAbility(ability);
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
