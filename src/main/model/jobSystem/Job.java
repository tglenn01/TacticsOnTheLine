package main.model.jobSystem;

import javafx.scene.chart.XYChart;
import main.model.characterSystem.StatBonus;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.combatSystem.abilities.MovementAbility;
import main.model.itemSystem.ConsumableItemInventory;
import main.model.jobSystem.jobs.bardJob.Bard;
import main.model.jobSystem.jobs.clericJob.Cleric;

import java.util.ArrayList;
import java.util.List;

public abstract class Job {
    public static Ability move = new MovementAbility();
    public static Ability defend = new DefendAbility();


    protected String jobTitle;
    protected List<Ability> jobAbilityList;
    protected int maxDamageAbilityReach;
    protected int maxSupportingAbilityReach;
    protected int jobHealth;
    protected int jobMana;
    protected int jobStrength;
    protected int jobMagic;
    protected int jobArmour;
    protected int jobResistance;
    protected int jobSpeed;
    protected int jobDexterity;
    protected int jobMovement;
    protected int attackRange;

    public Job() {
        jobAbilityList = new ArrayList<>();
        maxDamageAbilityReach = 0;
        maxSupportingAbilityReach = 0;
        initializeJobStats();
        initializeAbilities();
        updateMaxAbilityReach();
    }

    protected abstract void initializeJobStats();

    // All classes have these at their disposal
    protected void initializeAbilities() {
        Ability attack = new BasicAttackAbility(this.attackRange);
        jobAbilityList.add(move);
        jobAbilityList.add(attack);
        jobAbilityList.add(defend);
        jobAbilityList.add(ConsumableItemInventory.getInstance().getItemAbility());
    }

    // Used for the AI to figure out it's range of movement
    private void updateMaxAbilityReach() {
        for (Ability ability : jobAbilityList) {
            if (ability.targetsAlly()) {
                if (ability.getRange() > maxSupportingAbilityReach && ability.getClass() != ConsumableAbility.class) maxSupportingAbilityReach = ability.getRange();
            } else if (ability.getRange() > maxDamageAbilityReach) maxDamageAbilityReach = ability.getRange();
        }
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public List<Ability> getJobAbilityList() { return jobAbilityList; }


    // for generic units with no stat bonuses
    public void loadBaseStats(StatSheet statSheet) {
        statSheet.setMaxHealth(jobHealth);
        statSheet.setMaxMana(jobMana);
        statSheet.setBaseStrength(jobStrength);
        statSheet.setBaseMagic(jobMagic);
        statSheet.setBaseArmour(jobArmour);
        statSheet.setBaseResistance(jobResistance);
        statSheet.setBaseSpeed(jobSpeed);
        statSheet.setBaseDexterity(jobDexterity);
        statSheet.setMovement(jobMovement);
    }

    // For named characters that have stat bonuses
    public void loadBaseStats(StatSheet statSheet, StatBonus statBonus) {
        statSheet.setMaxHealth(jobHealth + statBonus.getHealthBonus());
        statSheet.setMaxMana(jobMana + statBonus.getManaBonus());
        statSheet.setBaseStrength(jobStrength + statBonus.getStrengthBonus());
        statSheet.setBaseMagic(jobMagic + statBonus.getMagicBonus());
        statSheet.setBaseArmour(jobArmour + statBonus.getArmourBonus());
        statSheet.setBaseResistance(jobResistance + statBonus.getResistanceBonus());
        statSheet.setBaseSpeed(jobSpeed + statBonus.getSpeedBonus());
        statSheet.setBaseDexterity(jobDexterity + statBonus.getDexterityBonus());
        statSheet.setMovement(jobMovement);
    }

    public void updateMaxStats() {
        StatSheet.updateHighestLowestHealth(jobHealth);
        StatSheet.updateHighestLowestMana(jobMana);
        StatSheet.updateHighestLowestStrength(jobStrength);
        StatSheet.updateHighestLowestMagic(jobMagic);
        StatSheet.updateHighestLowestArmour(jobArmour);
        StatSheet.updateHighestLowestResistance(jobResistance);
        StatSheet.updateHighestLowestSpeed(jobSpeed);
        StatSheet.updateHighestLowestDexterity(jobDexterity);
    }

    // Used for Character Menu's and won't include health and mana
    public XYChart.Series<Number, String> getRawStatData(StatSheet statSheet) {
        XYChart.Series<Number, String> newSeries = new XYChart.Series<>();
        newSeries.getData().add(new XYChart.Data<>(statSheet.getDexterity(), "Dexterity"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSpeed(), "Speed"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getResistance(), "Resistance"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getArmour(), "Armour"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getMagic(), "Magic"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getStrength(), "Strength"));
        return newSeries;
    }

    public XYChart.Series<Number, String> getSimpleStatData(StatSheet statSheet) {
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

    public void getSimpleStatData(XYChart.Series<Number, String> oldSeries, StatSheet statSheet) {
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

    public int getMaxDamageAbilityReach() {
        return this.maxDamageAbilityReach;
    }

    public int getMaxSupportingAbilityReach() {
        return this.maxSupportingAbilityReach;
    }

    public int getMaxTotalAbilityReach() {
        return Math.max(this.maxDamageAbilityReach, this.maxSupportingAbilityReach);
    }


    public boolean hasSupportingAbility() {
        for (Ability ability : jobAbilityList) {
            if (ability.targetsAlly() && !ability.getAbilityName().equals("Defend")
                    && !ability.getAbilityName().equals("Item")) return true;
        }
        return false;
    }

    public boolean isSupportJob() {
        return this.getClass() == Bard.class || this.getClass() == Cleric.class;
    }
}
