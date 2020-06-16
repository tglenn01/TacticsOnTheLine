package main.model.graphics.sceneElements.images;

import javafx.scene.control.Label;
import main.model.combatSystem.Ability;

public class AbilityImage extends Label {
    private Ability ability;

    public AbilityImage(Ability ability) {
        this.ability = ability;
        this.setText(ability.getAbilityName());
    }

    public Ability getAbility() {
        return this.ability;
    }
}
