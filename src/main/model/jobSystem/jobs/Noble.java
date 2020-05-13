package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.jobSystem.Job;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;

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
    public void setBaseStats(StatSheet statSheet) {
        statSheet.setHealth(32);
        statSheet.setMana(15);
        statSheet.setStrength(10);
        statSheet.setMagic(4);
        statSheet.setArmour(4);
        statSheet.setResistance(2);
        statSheet.setSpeed(8);
        statSheet.setDexterity(2);
        statSheet.setMovement(StatSheet.BASE_MOVEMENT);
    }
}
