package main.model.combatSystem;

import javafx.scene.image.Image;
import main.model.characterSystem.CharacterUnit;

public abstract class StatusEffect {
    protected String condensedName;
    protected Image icon;


    public StatusEffect(CharacterUnit receivingUnit, int potency) {
        setCondensedName();
        setIcon();
        applyStatusEffect(receivingUnit, potency);
        addStatusEffectToCharacterStatusEffects(receivingUnit);
        receivingUnit.getCharacterSprite().updateStatusEffectIndicatorToSprite();
    }

    public Image getIcon() {
        return this.icon;
    }

    public String getCondensedName() {
        return this.condensedName;
    }

    public abstract int getDurationInformation();
    protected abstract void setCondensedName();
    protected abstract void setIcon();
    protected abstract void applyStatusEffect(CharacterUnit receivingUnit, int potency);
    protected abstract void addStatusEffectToCharacterStatusEffects(CharacterUnit receivingUnit);
}
