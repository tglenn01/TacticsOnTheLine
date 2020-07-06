package main.model.combatSystem.statusEffects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.DecayingStatusEffect;

import java.io.FileInputStream;

public class DefenseBuff extends DecayingStatusEffect {

    public DefenseBuff(CharacterUnit receivingUnit, int potency, int duration) {
        super(receivingUnit, potency, duration);
    }

    @Override
    protected void setAbilityType() {
        this.abilityType = Ability.AbilityType.DEFENSE_BUFF;
    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "DEF_UP";
    }

    @Override
    protected void setIcon() {
        try {
            FileInputStream input = new FileInputStream("D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\statusEffects\\DefenseBuff.png");
            Image image = new Image(input);
            this.icon = new ImageView(image);
        } catch (Exception e) {
            //
        }
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        receivingUnitStatSheet.addArmour(potency);
        System.out.println("Defense is now buffed to " + receivingUnitStatSheet.getArmour());
        this.amountChanged = potency;
    }

    @Override
    protected void removeStatusEffect(CharacterUnit receivingUnit) {
        receivingUnit.getCharacterStatSheet().removeArmour(amountChanged);
    }


}
