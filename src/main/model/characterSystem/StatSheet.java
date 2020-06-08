package main.model.characterSystem;

import main.model.itemSystem.ResourceReplenishBonus;
import main.model.jobSystem.Job;

import java.util.List;

public class StatSheet implements ResourceReplenishBonus {
    public static int BASE_MOVEMENT = 4;
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


    public StatSheet(Job job) {
        job.setBaseStats(this);
    }

    public void updateStatSheetAccordingToJob(Job job) {
        job.setBaseStats(this);
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
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

    public void addStrength(int addedStrength) {
        this.strength += addedStrength;
    }

    public void addArmour(int addedArmour) {
        this.armour += addedArmour;
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

    @Override
    public int getHealingBonus() {
        return getMagic();
    }

    @Override
    public int getManaGainBonus() {
        return getMagic();
    }

    public double getSimpleHealth() {
        double value = SCALE_REFERENCE * ((maxHealth - LOWEST_HEALTH) / (HIGHEST_HEALTH - LOWEST_HEALTH));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleMana() {
        double value = SCALE_REFERENCE * ((maxMana - LOWEST_MANA) / (HIGHEST_MANA - LOWEST_MANA));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleStrength() {
        double value = SCALE_REFERENCE * ((baseStrength - LOWEST_STRENGTH) / (HIGHEST_STRENGTH - LOWEST_STRENGTH));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleMagic() {
        double value = SCALE_REFERENCE * ((baseMagic - LOWEST_MAGIC) / (HIGHEST_MAGIC - LOWEST_MAGIC));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleArmour() {
        double value = SCALE_REFERENCE * ((baseArmour - LOWEST_ARMOUR) / (HIGHEST_ARMOUR - LOWEST_ARMOUR));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleResistance() {
        double value = SCALE_REFERENCE * ((baseResistance - LOWEST_RESISTANCE) / (HIGHEST_RESISTANCE - LOWEST_RESISTANCE));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleSpeed() {
        double value =  SCALE_REFERENCE * ((baseSpeed - LOWEST_SPEED) / (HIGHEST_SPEED - LOWEST_SPEED));
        if (value < 1) return 1;
        return value;
    }

    public double getSimpleDexterity() {
        double value =  SCALE_REFERENCE * ((baseDexterity - LOWEST_DEXTERITY) / (HIGHEST_DEXTERITY - LOWEST_DEXTERITY));
        if (value < 1) return 1;
        return value;
    }

    public static void updateMaxStats(List<Job> availableJobs) {
        for (Job job : availableJobs) {
            job.updateMaxStats();
        }
    }
}
