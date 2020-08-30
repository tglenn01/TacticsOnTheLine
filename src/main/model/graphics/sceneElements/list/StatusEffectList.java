package main.model.graphics.sceneElements.list;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.StatusEffect;


public class StatusEffectList extends HBox {

    public StatusEffectList(CharacterUnit unit) {
        for (StatusEffect statusEffect : unit.getStatusEffects()) {
            HBox statusEffectToolTip = new HBox();
            ImageView icon = statusEffect.getIcon();
            icon.setFitWidth(50.00);
            icon.setFitHeight(50.00);
            String condensedName = statusEffect.getCondensedName();

            int durationInformation = statusEffect.getDurationInformation();

            Label condensedNameLabel = new Label(condensedName);
            Label durationLabel = new Label(Integer.toString(durationInformation));
            statusEffectToolTip.getChildren().addAll(icon, condensedNameLabel, durationLabel);
            statusEffectToolTip.setAlignment(Pos.CENTER);
            statusEffectToolTip.setSpacing(4.00);
            this.getChildren().add(statusEffectToolTip);
        }

        Color color = new Color(0.7, 0.3, 0.2, 1);
        BackgroundFill background_fill = new BackgroundFill(color,
                new CornerRadii(5), new Insets(0));
        Background background = new Background(background_fill);

        this.setBackground(background);
        this.setSpacing(12.00);
        this.setAlignment(Pos.CENTER);
    }
}
