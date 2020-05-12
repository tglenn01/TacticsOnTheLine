package main.model.characterSystem;

import main.model.jobSystem.Job;

public class StatSheet {
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


    public StatSheet(Job job) {
        initializeStats(job);
    }

    private void initializeStats(Job job) {
        job.setBaseStats(this);
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

    public void updateStatSheetAccordingToJob(Job job) {

    }
}
