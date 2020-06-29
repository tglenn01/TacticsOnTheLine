package main.model.combatSystem.statusEffects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.model.combatSystem.Ability;
import main.model.combatSystem.StatusEffect;

import java.io.FileInputStream;

public class DefenseBuff extends StatusEffect {

    public DefenseBuff(Ability.AbilityType abilityType, int amountChanged, int duration) {
        super(abilityType, amountChanged, duration);

    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "DEF_UP";
    }

    @Override
    protected void setIcon() {
        try {
            FileInputStream input = new FileInputStream("D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\DefenseBuff.png");
            Image image = new Image(input);
            this.icon = new ImageView(image);
        } catch (Exception e) {
            //
        }
    }
}
