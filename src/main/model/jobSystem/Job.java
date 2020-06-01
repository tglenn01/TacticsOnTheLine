package main.model.jobSystem;

import javafx.scene.chart.XYChart;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.MovementAbility;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.itemSystem.ConsumableItemInventory;

import java.util.ArrayList;
import java.util.List;

public abstract class Job {
    public static Ability move = new MovementAbility("Move", 0, 0, 0,
            Ability.AbilityType.MOVEMENT, "Move your character");
    public static Ability attack = new PhysicalAbility("Attack", 0, 1, 1,
            Ability.AbilityType.DAMAGE,0, .90,
            "Attack an enemy");
    public static Ability defend = new StatusEffectAbility("Defend", 0, 0, 1, 1,
            Ability.AbilityType.DEFENSE_BUFF, "Strengthen one's own defenses");
    protected String jobTitle;
    protected List<Ability> jobAbilityList;
    protected int maxAbilityReach;

    public Job() {
        jobAbilityList = new ArrayList<>();
        maxAbilityReach = 0;
        initializeAbilities();
        updateMaxAbilityReach();
    }

    protected void initializeAbilities() {
        jobAbilityList.add(move);
        jobAbilityList.add(attack);
        jobAbilityList.add(defend);
        jobAbilityList.add(ConsumableItemInventory.getInstance().getItemAbility());
    }

    private void updateMaxAbilityReach() {
        for (Ability ability : jobAbilityList) {
            if (ability.getRange() > maxAbilityReach) maxAbilityReach = ability.getRange();
        }
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public List<Ability> getJobAbilityList() {
        return jobAbilityList;
    }

    public abstract void setBaseStats(StatSheet statSheet);

    public abstract void updateMaxStats();

    public XYChart.Series<Number, String> getJobStatData(StatSheet statSheet) {
        XYChart.Series<Number, String> newSeries = new XYChart.Series<>();
        newSeries.getData().add(new XYChart.Data<>(statSheet.getDexterity(), "Dexterity"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSpeed(), "Speed"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getResistance(), "Resistance"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getArmour(), "Armour"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getMagic(), "Magic"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getStrength(), "Strength"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getMana(), "Mana"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getHealth(), "Health"));
        return newSeries;
    }

    public XYChart.Series<Number, String> getJobStatSimpleData(StatSheet statSheet) {
        XYChart.Series<Number, String> newSeries = new XYChart.Series<>();
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleDexterity(), "Dexterity"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleSpeed(), "Speed"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleResistance(), "Resistance"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleArmour(), "Armour"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleMagic(), "Magic"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleStrength(), "Strength"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleMana(), "Mana"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleHealth(), "Health"));
        return newSeries;
    }

    public void getJobStatSimpleData(XYChart.Series<Number, String> oldSeries, StatSheet statSheet) {
        for (XYChart.Data<Number, String> data : oldSeries.getData()) {
            if (data.getYValue().equals("Health")) {
                data.setXValue(statSheet.getSimpleHealth());
            }
            if (data.getYValue().equals("Mana")) {
                data.setXValue(statSheet.getSimpleMana());
            }
            if (data.getYValue().equals("Strength")) {
                data.setXValue(statSheet.getSimpleStrength());
            }
            if (data.getYValue().equals("Magic")) {
                data.setXValue(statSheet.getSimpleMagic());
            }
            if (data.getYValue().equals("Armour")) {
                data.setXValue(statSheet.getSimpleArmour());
            }
            if (data.getYValue().equals("Resistance")) {
                data.setXValue(statSheet.getSimpleResistance());
            }
            if (data.getYValue().equals("Speed")) {
                data.setXValue(statSheet.getSimpleSpeed());
            }
            if (data.getYValue().equals("Dexterity")) {
                data.setXValue(statSheet.getSimpleDexterity());
            }
        }
    }

    public int getMaxAbilityRange() {
        return this.maxAbilityReach;
    }

    public boolean hasSupportingAbility() {
        for (Ability ability : jobAbilityList) {
            if (ability.targetsAlly() && !ability.getAbilityName().equals("Defend")
                    && !ability.getAbilityName().equals("Item")) return true;
        }
        return false;
    }
}
