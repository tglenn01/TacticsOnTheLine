package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class Archer extends Job {
    private final int jobHealth = 24;
    private final int jobMana = 24;
    private final int jobStrength = 10;
    private final int jobMagic = 7;
    private final int jobArmour = 4;
    private final int jobResistance = 4;
    private final int jobSpeed = 12;
    private final int jobDexterity = 12;

    public Archer() {
        jobTitle = "Archer";
    }


    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability snipe = new PhysicalAbility("Snipe", 2, 6, 1,
                Ability.AbilityType.DAMAGE,8, .95,
                "Accurate attack from afar to damage a single foe");
        Ability hinder = new StatusEffectAbility("Hinder", 7, 6, 1, 3,
                Ability.AbilityType.DEFENSE_DEBUFF, 4, "Fire a trap to lower an enemies " +
                "defense from afar");
        Ability flurry = new PhysicalAbility("Flurry", 7, 6, 2,
                Ability.AbilityType.DAMAGE,8, .95,
                "Hit multiple enemies from afar for light damage");
        jobAbilityList.add(snipe);
        jobAbilityList.add(hinder);
        jobAbilityList.add(flurry);
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
