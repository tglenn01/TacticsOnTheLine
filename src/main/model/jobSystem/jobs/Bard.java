package main.model.jobSystem.jobs;

import javafx.scene.chart.XYChart;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class Bard extends Job {

    public Bard() {
        this.jobTitle = "Bard";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability sing = new StatusEffectAbility("Sing", 10, 3, 4, 1,
                Ability.AbilityType.HEAL, "Calm your allies nerves with a song, healing them");
        Ability serenade = new StatusEffectAbility("Serenade", 10, 4, 4,
                3, Ability.AbilityType.DEFENSE_BUFF,
                "Serenade your allies, buffing their defense");
        Ability inspire = new StatusEffectAbility("Inspire", 10, 4, 4,
                3, Ability.AbilityType.ATTACK_BUFF,
                "Inspire your allies, boosting their attack");
        jobAbilityList.add(sing);
        jobAbilityList.add(serenade);
        jobAbilityList.add(inspire);
    }

    @Override
    public void setBaseStats(StatSheet statSheet) {
        statSheet.setMaxHealth(16);
        statSheet.setMaxMana(40);
        statSheet.setBaseStrength(2);
        statSheet.setBaseMagic(2);
        statSheet.setBaseArmour(2);
        statSheet.setBaseResistance(2);
        statSheet.setBaseSpeed(2);
        statSheet.setBaseDexterity(2);
        statSheet.setMovement(StatSheet.BASE_MOVEMENT);
    }

    @Override
    public XYChart.Series<Number, String> getJobStatData(StatSheet statSheet) {
        this.setBaseStats(statSheet);
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
