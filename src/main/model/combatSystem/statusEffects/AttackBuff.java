package main.model.combatSystem.statusEffects;

import javafx.scene.image.Image;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.DecayingStatusEffect;

public class AttackBuff extends DecayingStatusEffect {

    public AttackBuff(CharacterUnit receivingUnit, int potency, int duration) {
        super(receivingUnit, potency, duration);
    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "ATK_UP";
    }

    @Override
    protected void setIcon() {
        this.icon = new Image("resources/statusEffects/AttackBuff.png");
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        receivingUnitStatSheet.addStrength(potency);
        System.out.println("Attack is now buffed to " + receivingUnitStatSheet.getStrength());
        this.amountChanged = potency;
    }

    @Override
    public void removeStatusEffect(CharacterUnit receivingUnit) {
        receivingUnit.getCharacterStatSheet().removeStrength(amountChanged);
    }
}
