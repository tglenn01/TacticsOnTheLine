package main.model.combatSystem.statusEffects;

import javafx.scene.image.Image;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.DecayingStatusEffect;

public class AttackDebuff extends DecayingStatusEffect {

    public AttackDebuff(CharacterUnit receivingUnit, int potency, int duration) {
        super(receivingUnit, potency, duration);
    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "ATK_DN";
    }

    @Override
    protected void setIcon() {
        this.icon = new Image("resources/statusEffects/AttackDebuff.png");
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        if (receivingUnitStatSheet.getStrength() >= potency) {
            receivingUnitStatSheet.removeStrength(potency);
            System.out.println("Attack is now debuffed to " + receivingUnitStatSheet.getStrength());
            this.amountChanged = potency;
        } else {
            int initialStrength = receivingUnitStatSheet.getStrength();
            receivingUnitStatSheet.removeStrength(initialStrength); // sets strength at 0
            System.out.println("Attack is now debuffed to " + receivingUnitStatSheet.getStrength());
            this.amountChanged = initialStrength;
        }
    }

    @Override
    public void removeStatusEffect(CharacterUnit receivingUnit) {
        receivingUnit.getCharacterStatSheet().addStrength(amountChanged);
    }
}
