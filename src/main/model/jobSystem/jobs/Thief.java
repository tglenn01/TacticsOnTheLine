package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class Thief extends Job {
    private final int jobHealth = 20;
    private final int jobMana = 16;
    private final int jobStrength = 10;
    private final int jobMagic = 10;
    private final int jobArmour = 2;
    private final int jobResistance = 2;
    private final int jobSpeed = 16;
    private final int jobDexterity = 18;

    public Thief() {
        jobTitle = "Thief";
    }


    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability crossSlash = new PhysicalAbility("Cross-Slash", 2, 2, 1,
                Ability.AbilityType.DAMAGE,12, .95,
                "Slash an enemy with high accuracy");
        Ability blind = new StatusEffectAbility("Blind", 10, 1, 2, 3,
                Ability.AbilityType.ATTACK_DEBUFF, "Blind enemies lowering their attack potency");
        Ability dagger = new PhysicalAbility("Dagger Throw", 4, 8, 1,
                Ability.AbilityType.DAMAGE,10, .70,
                "Throw a dagger at an enemy from afar");
        jobAbilityList.add(crossSlash);
        jobAbilityList.add(blind);
        jobAbilityList.add(dagger);
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
        statSheet.setMovement(StatSheet.BASE_MOVEMENT + 10);
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
