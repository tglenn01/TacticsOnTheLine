package main.model.combatSystem;

import javafx.scene.image.ImageView;
import main.model.characterSystem.CharacterUnit;

public abstract class StatusEffect {
    protected String condensedName;
    protected ImageView icon;


    public StatusEffect(CharacterUnit receivingUnit, int potency) {
        setCondensedName();
        setIcon();
        applyStatusEffect(receivingUnit, potency);
    }

    public ImageView getIcon() {
        return this.icon;
    }

    public String getCondensedName() {
        return this.condensedName;
    }

    protected abstract void setCondensedName();
    protected abstract void setIcon();
    protected abstract void applyStatusEffect(CharacterUnit receivingUnit, int potency);
}
