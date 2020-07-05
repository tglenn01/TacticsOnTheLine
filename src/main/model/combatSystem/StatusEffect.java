package main.model.combatSystem;

import javafx.scene.image.ImageView;

public abstract class StatusEffect {
    protected Ability.AbilityType abilityType;
    protected String condensedName;
    protected ImageView icon;


    public StatusEffect(Ability.AbilityType abilityType) {
        this.abilityType = abilityType;
        setCondensedName();
        setIcon();
    }

    public Ability.AbilityType getAbilityType() {
        return this.abilityType;
    }

    public ImageView getIcon() {
        return this.icon;
    }

    public String getCondensedName() {
        return this.condensedName;
    }

    protected abstract void setCondensedName();
    protected abstract void setIcon();
}
