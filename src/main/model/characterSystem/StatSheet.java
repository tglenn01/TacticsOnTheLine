package main.model.characterSystem;

import main.model.itemSystem.ResourceReplenishBonus;
import main.model.jobSystem.Job;

public class StatSheet implements ResourceReplenishBonus {
    public static int BASE_MOVEMENT = 4;
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private int strength;
    private int baseStrength;
    private int magic;
    private int baseMagic;
    private int armour;
    private int baseArmour;
    private int resistance;
    private int baseResistance;
    private int speed;
    private int baseSpeed;
    private int dexterity;
    private int baseDexterity;
    private int movement;


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
        setHealth(maxHealth);
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        setMana(maxMana);
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setBaseStrength(int baseStrength) {
        this.baseStrength = baseStrength;
        setStrength(baseStrength);
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void setBaseMagic(int baseMagic) {
        this.baseMagic = baseMagic;
        setMagic(baseMagic);
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public void setBaseArmour(int baseArmour) {
        this.baseArmour = baseArmour;
        setArmour(baseArmour);
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public void setBaseResistance(int baseResistance) {
        this.baseResistance = baseResistance;
        setResistance(baseResistance);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
        setSpeed(baseSpeed);
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setBaseDexterity(int baseDexterity) {
        this.baseDexterity = baseDexterity;
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
}
