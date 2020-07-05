package main.model.combatSystem.statusEffects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.DecayingStatusEffect;

import java.io.FileInputStream;

public class AttackDebuff extends DecayingStatusEffect {

    public AttackDebuff(CharacterUnit receivingUnit, int potency, int duration) {
        super(receivingUnit, potency, duration);
    }

    @Override
    protected void setAbilityType() {
        this.abilityType = Ability.AbilityType.ATTACK_DEBUFF;
    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "ATK_DN";
    }

    @Override
    protected void setIcon() {
        try {
            FileInputStream input = new FileInputStream("D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\statusEffects\\AttackDebuff.png");
            Image image = new Image(input);
            this.icon = new ImageView(image);
        } catch (Exception e) {
            //
        }
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
}
