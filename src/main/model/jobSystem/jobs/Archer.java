package main.model.jobSystem.jobs;

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
                8, .95, "Accurate attack from afar to damage a single foe");
        Ability hinder = new StatusEffectAbility("Hinder", 7, 6, 1,
                StatusEffectAbility.StatusType.DEFENSE_DEBUFF, "Fire a trap to lower an enemies " +
                "defense from afar");
        Ability rainOfArrows = new PhysicalAbility("Rain Of Arrows", 7, 6, 2,
                8, .95, "Hit multiple enemies from afar for light damage");
        jobAbilityList.add(snipe);
        jobAbilityList.add(hinder);
        jobAbilityList.add(rainOfArrows);
    }
}
