package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.MagicAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class Cleric extends Job {
    private final int jobHealth = 34;
    private final int jobMana = 30;
    private final int jobStrength = 6;
    private final int jobMagic = 10;
    private final int jobArmour = 4;
    private final int jobResistance = 12;
    private final int jobSpeed = 4;
    private final int jobDexterity = 6;

    public Cleric() {
        jobTitle = "Cleric";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability heal = new StatusEffectAbility("Heal", 4, 1, 1, 1,
                Ability.AbilityType.HEAL, "Heal a neighbouring ally");
        Ability protect = new StatusEffectAbility("Protect", 8, 1, 1,
                3, Ability.AbilityType.DEFENSE_BUFF, "Buff a neighbouring allies defense");
        Ability holy = new MagicAbility("Holy", 10, 3, 1,
                Ability.AbilityType.DAMAGE, 20, .80,
                "Deal heavy light damage to a nearby enemy");
        jobAbilityList.add(heal);
        jobAbilityList.add(protect);
        jobAbilityList.add(holy);
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
