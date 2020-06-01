package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.jobSystem.Job;

// default/base class
public class Noble extends Job {
    private final int jobHealth = 32;
    private final int jobMana = 15;
    private final int jobStrength = 10;
    private final int jobMagic = 4;
    private final int jobArmour = 8;
    private final int jobResistance = 2;
    private final int jobSpeed = 8;
    private final int jobDexterity = 8;

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
        statSheet.setMaxHealth(jobHealth);
        statSheet.setMaxMana(jobMana);
        statSheet.setBaseStrength(jobStrength);
        statSheet.setBaseMagic(jobMagic);
        statSheet.setBaseArmour(jobArmour);
        statSheet.setBaseResistance(jobResistance);
        statSheet.setBaseSpeed(jobSpeed);
        statSheet.setBaseDexterity(jobDexterity);
        statSheet.setMovement(StatSheet.BASE_MOVEMENT);
    }

    @Override
    public void updateMaxStats() {
        StatSheet.updateHighestLowestHealth(jobHealth);
        StatSheet.updateHighestLowestMana(jobMana);
        StatSheet.updateHighestLowestStrength(jobStrength);
        StatSheet.updateHighestLowestMagic(jobMagic);
        StatSheet.updateHighestLowestArmour(jobArmour);
        StatSheet.updateHighestLowestResistance(jobResistance);
        StatSheet.updateHighestLowestSpeed(jobSpeed);
        StatSheet.updateHighestLowestDexterity(jobDexterity);
    }
}
