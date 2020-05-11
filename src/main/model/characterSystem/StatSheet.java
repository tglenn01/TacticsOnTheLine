package main.model.characterSystem;

import main.model.jobSystem.Job;

public class StatSheet {
    private static int BASE_MOVEMENT = 4;
    private int health;
    private int mana;
    private int strength;
    private int magic;
    private int armour;
    private int resistance;
    private int speed;
    private int dexterity;
    private int movement;
    private Job characterJob;


    public StatSheet(Job characterJob) {
        this.characterJob = characterJob;
        initializeStats();
    }

    private void initializeStats() {
        health = baseHealth();
        mana = baseMana();
        strength = baseStrength();
        magic = baseMagic();
        armour = baseArmour();
        resistance = baseResistance();
        speed = baseSpeed();
        dexterity = baseDexterity();
        movement = baseMovement();
    }

    private int baseHealth() {
        return 32;
    }

    private int baseMana() {
        return 16;
    }

    private int baseStrength() {
        return 10;
    }

    private int baseMagic() {
        return 8;
    }

    private int baseArmour() {
        return 4;
    }

    private int baseResistance() {
        return 2;
    }

    private int baseSpeed() {
        return 8;
    }

    private int baseDexterity() {
        return 2;
    }

    private int baseMovement() {
        return BASE_MOVEMENT;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

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

}
