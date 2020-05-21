package main.model.characterSystem;

import main.model.itemSystem.ResourceReplenishBonus;
import main.model.jobSystem.Job;

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

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        if (maxHealth > HIGHEST_HEALTH) HIGHEST_HEALTH = maxHealth;
        setHealth(maxHealth);
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        if (maxMana > HIGHEST_MANA) HIGHEST_MANA = maxMana;
        setMana(maxMana);
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setBaseStrength(int baseStrength) {
        this.baseStrength = baseStrength;
        if (baseStrength > HIGHEST_STRENGTH) HIGHEST_STRENGTH = baseStrength;
        setStrength(baseStrength);
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void setBaseMagic(int baseMagic) {
        this.baseMagic = baseMagic;
        if (baseMagic > HIGHEST_MAGIC) HIGHEST_MAGIC = baseMagic;
        setMagic(baseMagic);
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public void setBaseArmour(int baseArmour) {
        this.baseArmour = baseArmour;
        if (baseArmour > HIGHEST_ARMOUR) HIGHEST_ARMOUR = baseArmour;
        setArmour(baseArmour);
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public void setBaseResistance(int baseResistance) {
        this.baseResistance = baseResistance;
        if (baseResistance > HIGHEST_RESISTANCE) HIGHEST_RESISTANCE = baseResistance;
        setResistance(baseResistance);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
        if (baseSpeed > HIGHEST_SPEED) HIGHEST_SPEED = baseSpeed;
        setSpeed(baseSpeed);
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setBaseDexterity(int baseDexterity) {
        this.baseDexterity = baseDexterity;
        if (baseDexterity > HIGHEST_DEXTERITY) HIGHEST_DEXTERITY = baseDexterity;
        setDexterity(baseDexterity);
    }

    public void setMovement(int movement) {
        this.movement = movement;
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

    // SCALE_REFERENCE * (current / max)
    public double getSimpleHealth() {
        return SCALE_REFERENCE * (maxHealth / HIGHEST_HEALTH);
    }

    public double getSimpleMana() {
        return SCALE_REFERENCE * (maxMana / HIGHEST_MANA);
    }

    public double getSimpleStrength() {
        return SCALE_REFERENCE * (baseStrength / HIGHEST_STRENGTH);
    }

    public double getSimpleMagic() {
        return SCALE_REFERENCE * (baseMagic / HIGHEST_MAGIC);
    }

    public double getSimpleArmour() {
        return SCALE_REFERENCE * (baseArmour / HIGHEST_ARMOUR);
    }

    public double getSimpleResistance() {
        return SCALE_REFERENCE * (baseResistance / HIGHEST_RESISTANCE);
    }

    public double getSimpleSpeed() {
        return SCALE_REFERENCE * (baseSpeed / HIGHEST_SPEED);
    }

    public double getSimpleDexterity() {
        return SCALE_REFERENCE * (baseDexterity / HIGHEST_DEXTERITY);
    }
}
