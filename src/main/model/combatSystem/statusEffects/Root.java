package main.model.combatSystem.statusEffects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.DecayingStatusEffect;

public class Root extends DecayingStatusEffect {


    public Root(CharacterUnit receivingUnit, int potency, int duration) {
        super(receivingUnit, potency, duration);
    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "ROOT";
    }

    @Override
    protected void setIcon() {
        this.icon = new ImageView(new Image("resources/statusEffects/MovementDown.png"));
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {
        System.out.println(receivingUnit.getCharacterName() + " is rooted for " + this.duration);
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        this.amountChanged = receivingUnitStatSheet.getMovement();
        receivingUnitStatSheet.setMovement(0);
    }

    @Override
    public void removeStatusEffect(CharacterUnit receivingUnit) {
        receivingUnit.getCharacterStatSheet().setMovement(amountChanged);
    }
}
