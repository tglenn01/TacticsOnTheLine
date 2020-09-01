package main.model.combatSystem.statusEffects;

import javafx.scene.image.Image;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.DecayingStatusEffect;

public class DefenseDebuff extends DecayingStatusEffect {

    public DefenseDebuff(CharacterUnit receivingUnit, int potency, int duration) {
        super(receivingUnit, potency, duration);
    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "DEF_DN";
    }

    @Override
    protected void setIcon() {
        this.icon = new Image("resources/statusEffects/DefenseDebuff.png");
    }

    public static Image getStaticIcon() {
        return new Image("resources/statusEffects/DefenseDebuff.png");
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        if (receivingUnitStatSheet.getArmour() >= potency) {
            receivingUnitStatSheet.removeArmour(potency);
            System.out.println("Armour is now debuffed to " + receivingUnitStatSheet.getArmour());
            this.amountChanged = potency;
        } else {
            int initialArmour = receivingUnitStatSheet.getArmour();
            receivingUnitStatSheet.removeStrength(initialArmour); // sets armour at 0
            System.out.println("Armour is now debuffed to " + receivingUnitStatSheet.getArmour());
            this.amountChanged = initialArmour;
        }
    }

    @Override
    public void removeStatusEffect(CharacterUnit receivingUnit) {
        receivingUnit.getCharacterStatSheet().addArmour(amountChanged);
    }
}
