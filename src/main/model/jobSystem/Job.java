package main.model.jobSystem;

import main.model.characterSystem.StatBonus;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.MovementAbility;
import main.model.itemSystem.ConsumableItemInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Job {
    public static Ability move = new MovementAbility();
    public static Ability defend = new DefendAbility();

    protected String jobTitle;
    protected List<Ability> jobAbilityList;
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
        initializeJobStats();
        initializeAbilities();
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

    public int getJobHealth() {
        return this.jobHealth;
    }

    public int getJobMana() {
        return this.jobMana;
    }

    public int getJobStrength() {
        return this.jobStrength;
    }

    public int getJobMagic() {
        return this.jobMagic;
    }

    public int getJobArmour() {
        return this.jobArmour;
    }

    public int getJobResistance() {
        return this.jobResistance;
    }

    public int getJobSpeed() {
        return this.jobSpeed;
    }

    public int getJobDexterity() {
        return this.jobDexterity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(jobTitle, job.jobTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobTitle);
    }
}
