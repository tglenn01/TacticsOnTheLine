package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.jobSystem.Job;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;

public class Archer extends Job {

    public Archer() {
        jobTitle = "Archer";
    }


    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability snipe = new PhysicalAbility("Snipe", 2, 6, 1,
                Ability.AbilityType.DAMAGE,8, .95,
                "Accurate attack from afar to damage a single foe");
        Ability hinder = new StatusEffectAbility("Hinder", 7, 6, 1,
                Ability.AbilityType.DEFENSE_DEBUFF, "Fire a trap to lower an enemies " +
                "defense from afar");
        Ability rainOfArrows = new PhysicalAbility("Rain Of Arrows", 7, 6, 2,
                Ability.AbilityType.DAMAGE,8, .95,
                "Hit multiple enemies from afar for light damage");
        jobAbilityList.add(snipe);
        jobAbilityList.add(hinder);
        jobAbilityList.add(rainOfArrows);
    }

    @Override
    public void setBaseStats(StatSheet statSheet) {
        statSheet.setHealth(20);
        statSheet.setMana(24);
        statSheet.setStrength(10);
        statSheet.setMagic(7);
        statSheet.setArmour(2);
        statSheet.setResistance(6);
        statSheet.setSpeed(10);
        statSheet.setDexterity(8);
        statSheet.setMovement(StatSheet.BASE_MOVEMENT);
    }
}
