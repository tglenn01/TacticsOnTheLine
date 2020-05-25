package main.model.graphics.icons;

import javafx.scene.control.Label;
import main.model.combatSystem.Ability;

public class AbilityIcon extends Label {
    private Ability ability;

    public AbilityIcon(Ability ability) {
        this.ability = ability;
        this.setText(ability.getAbilityName());
    }

    public Ability getAbility() {
        return this.ability;
    }
}
