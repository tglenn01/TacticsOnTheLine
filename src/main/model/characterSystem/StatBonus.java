package main.model.characterSystem;

public class StatBonus {
    private int personalHealthBoost;
    private int personalManaBoost;
    private int personalStrengthBoost;
    private int personalMagicBoost;
    private int personalArmourBoost;
    private int personalResistanceBoost;
    private int personalSpeedBoost;
    private int personalDexterityBoost;

    public StatBonus(int health, int mana, int strength, int magic, int armour,
                     int resistance, int speed, int dexterity) {
        this.personalHealthBoost = health;
        this.personalManaBoost = mana;
        this.personalStrengthBoost = strength;
        this.personalMagicBoost = magic;
        this.personalArmourBoost = armour;
        this.personalResistanceBoost = resistance;
        this.personalSpeedBoost = speed;
        this.personalDexterityBoost = dexterity;
    }

    public int getHealthBonus() {
        return this.personalHealthBoost;
    }

    public int getManaBonus() {
        return this.personalManaBoost;
    }

    public int getStrengthBonus() {
        return this.personalStrengthBoost;
    }

    public int getMagicBonus() {
        return this.personalMagicBoost;
    }

    public int getArmourBonus() {
        return this.personalArmourBoost;
    }

    public int getResistanceBonus() {
        return this.personalResistanceBoost;
    }

    public int getSpeedBonus() {
        return this.personalSpeedBoost;
    }

    public int getDexterityBonus() {
        return this.personalDexterityBoost;
    }
}
