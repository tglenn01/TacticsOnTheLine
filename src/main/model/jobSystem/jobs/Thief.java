package main.model.jobSystem.jobs;

import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class Thief extends Job {

    public Thief() {
        jobTitle = "Thief";
    }


    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability crossSlash = new PhysicalAbility("Cross-Slash", 2, 2, 1,
                Ability.AbilityType.DAMAGE,12, .95,
                "Slash an enemy with high accuracy");
        Ability blind = new StatusEffectAbility("Blind", 10, 1, 1, 3,
                Ability.AbilityType.ATTACK_DEBUFF, 7, "Blind enemies lowering their attack potency");
        Ability dagger = new PhysicalAbility("Dagger Throw", 4, 8, 1,
                Ability.AbilityType.DAMAGE,10, .70,
                "Throw a dagger at an enemy from afar");
        jobAbilityList.add(crossSlash);
        jobAbilityList.add(blind);
        jobAbilityList.add(dagger);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 20;
        this.jobMana = 16;
        this.jobStrength = 10;
        this.jobMagic = 10;
        this.jobArmour = 2;
        this.jobResistance = 2;
        this.jobSpeed = 16;
        this.jobDexterity = 18;
    }
}
