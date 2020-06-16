package main.model.combatSystem;

import javafx.scene.image.ImageView;

public abstract class StatusEffect {
    protected Ability.AbilityType abilityType;
    protected int amountChanged;
    protected int duration;
    protected String condensedName;
    protected ImageView icon;


    public StatusEffect(Ability.AbilityType abilityType, int amountChanged, int duration) {
        this.abilityType = abilityType;
        this.amountChanged = amountChanged;
        this.duration = duration;
        setCondensedName();
        setIcon();
    }

    public Ability.AbilityType getAbilityType() {
        return this.abilityType;
    }

    public int getAmountChanged() {
        return this.amountChanged;
    }

    public int getDuration() {
        return this.duration;
    }

    public ImageView getIcon() {
        return this.icon;
    }

    public String getCondensedName() {
        return this.condensedName;
    }

    public void setDuration(int newDuration) {
        this.duration = newDuration;
    }

    protected abstract void setCondensedName();
    protected abstract void setIcon();
}
