package main.model.combatSystem.statusEffects;

import javafx.scene.image.Image;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.PermanentStatusEffect;

public class Invulnerable extends PermanentStatusEffect {

    // the potency will be the untouched and will represent how many uses their originally were
    public Invulnerable(CharacterUnit receivingUnit, int uses) {
        super(receivingUnit, uses, uses);
    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "INVUL";
    }

    @Override
    protected void setIcon() {
        this.icon = new Image("resources/statusEffects/Invulnerable.png");
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {

    }
}
