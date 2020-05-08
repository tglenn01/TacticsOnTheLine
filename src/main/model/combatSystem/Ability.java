package main.model.combatSystem;

public abstract class Ability {
    protected String abilityName;
    protected int manaCost;
    protected int range;
    protected int areaOfEffect;


    public Ability(String abilityName, int manaCost, int range, int areaOfEffect) {

    }

    public String getAbilityName() {
        return abilityName;
    }

}
