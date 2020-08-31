package main.model.combatSystem.statusEffects;

import javafx.scene.image.Image;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.DecayingStatusEffect;

public class DefenseBuff extends DecayingStatusEffect {

    public DefenseBuff(CharacterUnit receivingUnit, int potency, int duration) {
        super(receivingUnit, potency, duration);
    }
    @Override
    protected void setCondensedName() {
        this.condensedName = "DEF_UP";
    }

    @Override
    protected void setIcon() {
        this.icon = new Image("resources/statusEffects/DefenseBuff.png");
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        receivingUnitStatSheet.addArmour(potency);
        System.out.println("Defense is now buffed to " + receivingUnitStatSheet.getArmour());
        this.amountChanged = potency;
    }

    @Override
    public void removeStatusEffect(CharacterUnit receivingUnit) {
        receivingUnit.getCharacterStatSheet().removeArmour(amountChanged);
    }


}
