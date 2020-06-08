package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class Lancer extends Job {
    private final int jobHealth = 26;
    private final int jobMana = 30;
    private final int jobStrength = 14;
    private final int jobMagic = 2;
    private final int jobArmour = 8;
    private final int jobResistance = 3;
    private final int jobSpeed = 10;
    private final int jobDexterity = 10;

    public Lancer() { this.jobTitle = "Lancer"; }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability lunge = new PhysicalAbility("Lunge", 4, 2, 1,
                Ability.AbilityType.DAMAGE,12, .95,
                "Strike a nearby enemy with a powerful lunge");
        Ability overwhelm = new StatusEffectAbility("Overwhelm", 20, 2, 2,
                3, Ability.AbilityType.DEFENSE_DEBUFF, 2,
                "Scare all neighbouring enemies, weakening their defense");
        Ability weakPoint = new PhysicalAbility("Weakpoint", 6, 1, 1,
                Ability.AbilityType.DAMAGE,16, .80,
                "Strike an Enemies weakpoint with the blunt of your spear");
        jobAbilityList.add(lunge);
        jobAbilityList.add(overwhelm);
        jobAbilityList.add(weakPoint);
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
