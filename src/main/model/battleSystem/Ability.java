package main.model.battleSystem;

public class Ability {
    private String abilityName;
    private int damage;
    private int manaCost;
    private double accuracy;
    private int range;
    private int areaOfEffect;

    public Ability(String abilityName, int damage, int manaCost, double accuracy, int range, int areaOfEffect) {
        this.abilityName = abilityName;
        this.damage = damage;
        this.manaCost = manaCost;
        this.accuracy = accuracy;
        this.range = range;
        this.areaOfEffect = areaOfEffect;
    }


}
