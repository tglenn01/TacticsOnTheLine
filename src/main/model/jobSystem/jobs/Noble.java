package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.jobSystem.Job;

// default/base class
public class Noble extends Job {

    public Noble() {
        jobTitle = "Noble";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability strike = new PhysicalAbility("Strike", 3, 1, 1,
                Ability.AbilityType.DAMAGE, 15, .80,
                "Strike an enemy with increased force");
        Ability dagger = new PhysicalAbility("Dagger", 3, 3, 1,
                Ability.AbilityType.DAMAGE,8, .70,
                "Strike an nearby enemy with an light dagger");
        Ability desperation = new PhysicalAbility("Desperation", 10, 1, 2,
                Ability.AbilityType.DAMAGE,30, .50,
                "Spend a lot of mana for a low chance to deal a lot " +
                "of damage to multiple neighbouring enemies");
        jobAbilityList.add(strike);
        jobAbilityList.add(dagger);
        jobAbilityList.add(desperation);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 32;
        this.jobMana = 15;
        this.jobStrength = 10;
        this.jobMagic = 4;
        this.jobArmour = 8;
        this.jobResistance = 2;
        this.jobSpeed = 8;
        this.jobDexterity = 8;
        this.jobMovement = StatSheet.BASE_MOVEMENT;
    }
}
