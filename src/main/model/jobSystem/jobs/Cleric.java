package main.model.jobSystem.jobs;

import javafx.scene.chart.XYChart;
import main.model.characterSystem.StatSheet;
import main.model.jobSystem.Job;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.MagicAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;

public class Cleric extends Job {

    public Cleric() {
        jobTitle = "Cleric";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability heal = new StatusEffectAbility("Heal", 4, 1, 1, 1,
                Ability.AbilityType.HEAL, "Heal a neighbouring ally");
        Ability protect = new StatusEffectAbility("Protect", 8, 1, 1,
                3, Ability.AbilityType.DEFENSE_BUFF, "Buff a neighbouring allies defense");
        Ability holy = new MagicAbility("Holy", 10, 3, 1,
                Ability.AbilityType.DAMAGE, 20, .80,
                "Deal heavy light damage to a nearby enemy");
        jobAbilityList.add(heal);
        jobAbilityList.add(protect);
        jobAbilityList.add(holy);
    }

    @Override
    public void setBaseStats(StatSheet statSheet) {
        statSheet.setMaxHealth(34);
        statSheet.setMaxMana(30);
        statSheet.setBaseStrength(6);
        statSheet.setBaseMagic(10);
        statSheet.setBaseArmour(4);
        statSheet.setBaseResistance(12);
        statSheet.setBaseSpeed(4);
        statSheet.setBaseDexterity(6);
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
