package main.model.graphics.icons;

import javafx.scene.control.Button;
import main.model.combatSystem.Ability;

public class AbilityButton extends Button {
    private Ability ability;

    public AbilityButton(Ability ability) {
        this.ability = ability;
        this.setText(ability.getAbilityName());
        this.setPrefSize(100,100);
    }

    public Ability getAbility() {
        return this.ability;
    }
}
