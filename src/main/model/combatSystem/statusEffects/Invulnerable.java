package main.model.combatSystem.statusEffects;

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
        //
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {

    }
}
