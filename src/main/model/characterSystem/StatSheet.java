package main.model.characterSystem;

import main.model.jobSystem.Job;

import java.util.List;

public class StatSheet {
    public static int BASE_MOVEMENT = 10;
    private int health;
    private int mana;
    private int strength;
    private int magic;
    private int armour;
    private int resistance;
    private int speed;
    private int dexterity;
    private int movement;
    private int maxHealth;
    private int maxMana;
    private int baseStrength;
    private int baseMagic;
    private int baseArmour;
    private int baseResistance;
    private int baseSpeed;
    private int baseDexterity;
    private int healthGrowthRate;
    private int manaGrowthRate;
    private int strengthGrowthRate;
    private int magicGrowthRate;
    private int armourGrowthRate;
    private int resistanceGrowthRate;
    private int speedGrowthRate;
    private int dexterityGrowthRate;
    private static double HIGHEST_HEALTH;
    private static double HIGHEST_MANA;
    private static double HIGHEST_STRENGTH;
    private static double HIGHEST_MAGIC;
    private static double HIGHEST_ARMOUR;
    private static double HIGHEST_RESISTANCE;
    private static double HIGHEST_SPEED;
    private static double HIGHEST_DEXTERITY;
    private static double LOWEST_HEALTH;
    private static double LOWEST_MANA;
    private static double LOWEST_STRENGTH;
    private static double LOWEST_MAGIC;
    private static double LOWEST_ARMOUR;
    private static double LOWEST_RESISTANCE;
    private static double LOWEST_SPEED;
    private static double LOWEST_DEXTERITY;
    public static double SCALE_REFERENCE = 10.00;

    // For generic enemies
    public StatSheet(Job job) {
        job.loadBaseStats(this);
    }

    public void updateStatSheetAccordingToJob(Job job) {
        job.loadBaseStats(this);
    }

    public void updateStatSheetAccordingToJob(Job job, StatBonus statBonus) {
        job.loadBaseStats(this, statBonus);
    }

    public void addStatBonus(StatBonus statBonus) {
        this.maxHealth += statBonus.getHealthBonus();
        this.maxMana += statBonus.getManaBonus();
        this.baseStrength += statBonus.getStrengthBonus();
        this.baseMagic += statBonus.getMagicBonus();
        this.baseArmour += statBonus.getArmourBonus();
        this.baseResistance += statBonus.getResistanceBonus();
        this.baseDexterity += statBonus.getDexterityBonus();
        this.baseSpeed += statBonus.getSpeedBonus();
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getBaseStrength() {
        return baseStrength;
    }

    public int getBaseMagic() {
        return baseMagic;
    }

    public int getBaseArmour() {
        return baseArmour;
    }

    public int getBaseResistance() {
        return baseResistance;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public int getBaseDexterity() {
        return baseDexterity;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxMana() { return maxMana; }

    public int getStrength() {
        return strength;
    }

    public int getMagic() {
        return magic;
    }

    public int getArmour() {
        return armour;
    }

    public int getResistance() {
        return resistance;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getMovement() {
        return movement;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        setHealth(maxHealth);
    }

    public void removeStrength(int removedStrength) {
        this.strength -= removedStrength;
    }

    public void removeArmour(int removedArmour) {
        this.armour -= removedArmour;
    }

    public void removeDexterity(int removedDexterity) {
        this.dexterity -= removedDexterity;
    }

    public void addStrength(int addedStrength) {
        this.strength += addedStrength;
    }

    public void addArmour(int addedArmour) {
        this.armour += addedArmour;
    }

    public void addDexterity(int addedDexterity) {
        this.dexterity += addedDexterity;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        setMana(maxMana);
    }

    public void setBaseStrength(int baseStrength) {
        this.baseStrength = baseStrength;
        setStrength(baseStrength);
    }

    public void setBaseMagic(int baseMagic) {
        this.baseMagic = baseMagic;
        setMagic(baseMagic);
    }

    public void setBaseArmour(int baseArmour) {
        this.baseArmour = baseArmour;
        setArmour(baseArmour);
    }

    public void setBaseResistance(int baseResistance) {
        this.baseResistance = baseResistance;
        setResistance(baseResistance);
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
        setSpeed(baseSpeed);
    }

    public void setBaseDexterity(int baseDexterity) {
        this.baseDexterity = baseDexterity;
        setDexterity(baseDexterity);
    }

    public static void updateHighestLowestHealth(int maxHealth) {
        if (maxHealth > HIGHEST_HEALTH) HIGHEST_HEALTH = maxHealth;
        if (maxHealth < LOWEST_HEALTH || LOWEST_HEALTH == 0) LOWEST_HEALTH = maxHealth;
    }

    public static void updateHighestLowestMana(int maxMana) {
        if (maxMana > HIGHEST_MANA) HIGHEST_MANA = maxMana;
        if (maxMana < LOWEST_MANA || LOWEST_MANA == 0) LOWEST_MANA = maxMana;
    }

    public static void updateHighestLowestStrength(int baseStrength) {
        if (baseStrength > HIGHEST_STRENGTH) HIGHEST_STRENGTH = baseStrength;
        if (baseStrength < LOWEST_STRENGTH || LOWEST_STRENGTH == 0) LOWEST_STRENGTH = baseStrength;
    }

    public static void updateHighestLowestMagic(int baseMagic) {
        if (baseMagic > HIGHEST_MAGIC) HIGHEST_MAGIC = baseMagic;
        if (baseMagic < LOWEST_MAGIC || LOWEST_MAGIC == 0) LOWEST_MAGIC = baseMagic;
    }

    public static void updateHighestLowestArmour(int baseArmour) {
        if (baseArmour > HIGHEST_ARMOUR) HIGHEST_ARMOUR = baseArmour;
        if (baseArmour < LOWEST_ARMOUR || LOWEST_ARMOUR == 0) LOWEST_ARMOUR = baseArmour;
    }

    public static void updateHighestLowestResistance(int baseResistance) {
        if (baseResistance > HIGHEST_RESISTANCE) HIGHEST_RESISTANCE = baseResistance;
        if (baseResistance < LOWEST_RESISTANCE || LOWEST_RESISTANCE == 0) LOWEST_RESISTANCE = baseResistance;
    }

    public static void updateHighestLowestSpeed(int baseSpeed) {
        if (baseSpeed > HIGHEST_SPEED) HIGHEST_SPEED = baseSpeed;
        if (baseSpeed < LOWEST_SPEED || LOWEST_SPEED == 0) LOWEST_SPEED = baseSpeed;
    }

    public static void updateHighestLowestDexterity(int baseDexterity) {
        if (baseDexterity > HIGHEST_DEXTERITY) HIGHEST_DEXTERITY = baseDexterity;
        if (baseDexterity < LOWEST_DEXTERITY || LOWEST_DEXTERITY == 0) LOWEST_DEXTERITY = baseDexterity;
    }

    public void revertStrength() {
        this.strength = this.baseStrength;
    }

    public void revertMagic() {
        this.magic = this.baseMagic;
    }

    public void revertArmour() {
        this.armour = this.baseArmour;
    }

    public void revertResistance() {
        this.resistance = this.baseResistance;
    }

    public void revertSpeed() {
        this.speed = this.baseSpeed;
    }


    public double getSimpleHealth(int jobHealth) {
        double value = SCALE_REFERENCE * ((jobHealth - LOWEST_HEALTH) / (HIGHEST_HEALTH - LOWEST_HEALTH));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleMana(int jobMana) {
        double value = SCALE_REFERENCE * ((jobMana - LOWEST_MANA) / (HIGHEST_MANA - LOWEST_MANA));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleStrength(int jobStrength) {
        double value = SCALE_REFERENCE * ((jobStrength - LOWEST_STRENGTH) / (HIGHEST_STRENGTH - LOWEST_STRENGTH));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleMagic(int jobMagic) {
        double value = SCALE_REFERENCE * ((jobMagic - LOWEST_MAGIC) / (HIGHEST_MAGIC - LOWEST_MAGIC));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleArmour(int jobArmour) {
        double value = SCALE_REFERENCE * ((jobArmour - LOWEST_ARMOUR) / (HIGHEST_ARMOUR - LOWEST_ARMOUR));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleResistance(int jobResistance) {
        double value = SCALE_REFERENCE * ((jobResistance - LOWEST_RESISTANCE) / (HIGHEST_RESISTANCE - LOWEST_RESISTANCE));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleSpeed(int jobSpeed) {
        double value =  SCALE_REFERENCE * ((jobSpeed - LOWEST_SPEED) / (HIGHEST_SPEED - LOWEST_SPEED));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleDexterity(int jobDexterity) {
        double value =  SCALE_REFERENCE * ((jobDexterity - LOWEST_DEXTERITY) / (HIGHEST_DEXTERITY - LOWEST_DEXTERITY));
        if (value < 1) return 1;
        return value;
    }

    public void levelUpHealth() {
        this.maxHealth++;
    }

    public void levelUpMana() {
        this.maxMana++;
    }

    public void levelUpStrength() {
        this.baseStrength++;
        this.strength++;
    }

    public void levelUpMagic() {
        this.baseMagic++;
        this.magic++;
    }

    public void levelUpArmour() {
        this.baseArmour++;
        this.armour++;
    }

    public void levelUpResistance() {
        this.baseResistance++;
        this.resistance++;
    }

    public void levelUpSpeed() {
        this.baseSpeed++;
        this.speed++;
    }

    public void levelUpDexterity() {
        this.baseDexterity++;
        this.dexterity++;
    }

    public void setGrowthRates(int healthGrowthRate, int manaGrowthRate, int strengthGrowthRate, int magicGrowthRate,
                               int armourGrowthRate, int resistanceGrowthRate, int speedGrowthRate, int dexterityGrowthRate) {
        this.healthGrowthRate = healthGrowthRate;
        this.manaGrowthRate = manaGrowthRate;
        this.strengthGrowthRate = strengthGrowthRate;
        this.magicGrowthRate = magicGrowthRate;
        this.armourGrowthRate = armourGrowthRate;
        this.resistanceGrowthRate = resistanceGrowthRate;
        this.speedGrowthRate = speedGrowthRate;
        this.dexterityGrowthRate = dexterityGrowthRate;
        if (healthGrowthRate + manaGrowthRate + strengthGrowthRate + magicGrowthRate +
                armourGrowthRate + resistanceGrowthRate + speedGrowthRate + dexterityGrowthRate != 100)
            throw new RuntimeException();
    }

    public int getHealthGrowthRate() {
        return this.healthGrowthRate;
    }

    public int getManaGrowthRate() {
        return this.manaGrowthRate;
    }

    public int getStrengthGrowthRate() {
        return this.strengthGrowthRate;
    }

    public int getMagicGrowthRate() {
        return this.magicGrowthRate;
    }

    public int getArmourGrowthRate() {
        return this.armourGrowthRate;
    }

    public int getResistanceGrowthRate() {
        return this.resistanceGrowthRate;
    }

    public int getSpeedGrowthRate() {
        return this.speedGrowthRate;
    }

    public int getDexterityGrowthRate() {
        return this.dexterityGrowthRate;
    }

    public static void updateMaxStats(List<Job> availableJobs) {
        for (Job job : availableJobs) {
            job.updateMaxStats();
        }
    }

    public void setAllStatsToMax() {
        this.health = maxHealth;
        this.mana = maxMana;
        this.strength = baseStrength;
        this.magic = baseMagic;
        this.armour = baseArmour;
        this.resistance = baseResistance;
        this.speed = baseSpeed;
        this.dexterity = baseDexterity;
    }

}
