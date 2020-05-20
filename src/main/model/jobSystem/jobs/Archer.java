package main.model.jobSystem.jobs;

import javafx.scene.chart.XYChart;
import main.model.characterSystem.StatSheet;
import main.model.jobSystem.Job;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;

public class Archer extends Job {

    public Archer() {
        jobTitle = "Archer";
    }


    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability snipe = new PhysicalAbility("Snipe", 2, 6, 1,
                Ability.AbilityType.DAMAGE,8, .95,
                "Accurate attack from afar to damage a single foe");
        Ability hinder = new StatusEffectAbility("Hinder", 7, 6, 1, 3,
                Ability.AbilityType.DEFENSE_DEBUFF, "Fire a trap to lower an enemies " +
                "defense from afar");
        Ability flurry = new PhysicalAbility("Flurry", 7, 6, 2,
                Ability.AbilityType.DAMAGE,8, .95,
                "Hit multiple enemies from afar for light damage");
        jobAbilityList.add(snipe);
        jobAbilityList.add(hinder);
        jobAbilityList.add(flurry);
    }

    @Override
    public void setBaseStats(StatSheet statSheet) {
        statSheet.setMaxHealth(24);
        statSheet.setMaxMana(24);
        statSheet.setBaseStrength(10);
        statSheet.setBaseMagic(7);
        statSheet.setBaseArmour(4);
        statSheet.setBaseResistance(4);
        statSheet.setBaseSpeed(12);
        statSheet.setBaseDexterity(12);
        statSheet.setMovement(StatSheet.BASE_MOVEMENT);
    }

    @Override
    public XYChart.Series<Number, String> getJobStatData(StatSheet statSheet) {
        XYChart.Series<Number, String> series1 = new XYChart.Series<>();
        series1.getData().add(new XYChart.Data<>(statSheet.getHealth(), "Health"));
        series1.getData().add(new XYChart.Data<>(statSheet.getMana(), "Mana"));
        series1.getData().add(new XYChart.Data<>(statSheet.getStrength(), "Strength"));
        series1.getData().add(new XYChart.Data<>(statSheet.getMagic(), "Magic"));
        series1.getData().add(new XYChart.Data<>(statSheet.getArmour(), "Armour"));
        series1.getData().add(new XYChart.Data<>(statSheet.getResistance(), "Resistance"));
        series1.getData().add(new XYChart.Data<>(statSheet.getSpeed(), "Speed"));
        series1.getData().add(new XYChart.Data<>(statSheet.getDexterity(), "Dexterity"));
        series1.getData().add(new XYChart.Data<>(statSheet.getMovement(), "Movement"));
        return series1;
    }
}
