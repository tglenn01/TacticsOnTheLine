package main.model.graphics.sceneElements.list;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.StatusEffect;


public class StatusEffectList extends VBox {

    public StatusEffectList(CharacterUnit unit) {
        for (StatusEffect statusEffect : unit.getStatusEffects().getStatusEffects()) {
            VBox node = new VBox();
            ImageView icon = statusEffect.getIcon();
            String condensedName = statusEffect.getCondensedName();
            int duration = statusEffect.getDuration();
            Label condensedNameLabel = new Label(condensedName);
            Label durationLabel = new Label(Integer.toString(duration));
            node.getChildren().addAll(icon, condensedNameLabel, durationLabel);
            node.setAlignment(Pos.CENTER);

            this.getChildren().add(node);
            this.setAlignment(Pos.CENTER);
        }
    }
}
