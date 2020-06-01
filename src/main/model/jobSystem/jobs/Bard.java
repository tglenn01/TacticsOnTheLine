package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class Bard extends Job {
    private final int jobHealth = 16;
    private final int jobMana = 40;
    private final int jobStrength = 2;
    private final int jobMagic = 6;
    private final int jobArmour = 2;
    private final int jobResistance = 8;
    private final int jobSpeed = 6;
    private final int jobDexterity = 2;

    public Bard() {
        this.jobTitle = "Bard";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability sing = new StatusEffectAbility("Sing", 10, 3, 4, 1,
                Ability.AbilityType.HEAL, "Calm your allies nerves with a song, healing them");
        Ability serenade = new StatusEffectAbility("Serenade", 10, 4, 4,
                3, Ability.AbilityType.DEFENSE_BUFF,
                "Serenade your allies, buffing their defense");
        Ability inspire = new StatusEffectAbility("Inspire", 10, 4, 4,
                3, Ability.AbilityType.ATTACK_BUFF,
                "Inspire your allies, boosting their attack");
        jobAbilityList.add(sing);
        jobAbilityList.add(serenade);
        jobAbilityList.add(inspire);
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
