package main.model.combatSystem.statusEffects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.DecayingStatusEffect;

public class Blind extends DecayingStatusEffect {

    public Blind(CharacterUnit receivingUnit, int potency, int duration) {
        super(receivingUnit, potency, duration);
    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "BLIND";
    }

    @Override
    protected void setIcon() {
        this.icon = new ImageView(new Image("resources/statusEffects/MovementDown.png"));
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {
        System.out.println(receivingUnit.getCharacterName() + " is blinded for " + potency);
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        receivingUnitStatSheet.removeDexterity(potency);
        this.amountChanged = potency;
    }

    @Override
    public void removeStatusEffect(CharacterUnit receivingUnit) {
        receivingUnit.getCharacterStatSheet().setMovement(amountChanged);
    }
}
