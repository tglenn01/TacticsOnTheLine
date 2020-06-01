package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class Warrior extends Job {
    private final int jobHealth = 40;
    private final int jobMana = 8;
    private final int jobStrength = 13;
    private final int jobMagic = 2;
    private final int jobArmour = 8;
    private final int jobResistance = 0;
    private final int jobSpeed = 6;
    private final int jobDexterity = 6;

    public Warrior() {
        jobTitle = "Warrior";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability club = new PhysicalAbility("Club", 2, 1, 1,
                Ability.AbilityType.DAMAGE,18, .70,
                "Club a neighbouring enemy on the head with low accuracy");
        Ability focus = new StatusEffectAbility("Focus", 4, 1, 1, 3,
                Ability.AbilityType.ATTACK_BUFF, "Buff the attack of a neighbouring ally");
        Ability stone = new PhysicalAbility("Stone", 1, 4, 1,
                Ability.AbilityType.DAMAGE,8, .60,
                "A low chance to damage an enemy from afar");
        jobAbilityList.add(club);
        jobAbilityList.add(focus);
        jobAbilityList.add(stone);
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
