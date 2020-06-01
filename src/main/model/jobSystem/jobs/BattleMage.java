package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.MagicAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class BattleMage extends Job {
    private final int jobHealth = 20;
    private final int jobMana = 36;
    private final int jobStrength = 6;
    private final int jobMagic = 12;
    private final int jobArmour = 2;
    private final int jobResistance = 8;
    private final int jobSpeed = 4;
    private final int jobDexterity = 2;

    public BattleMage() {
        jobTitle = "BattleMage";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability zap = new MagicAbility("Zap", 2, 4, 1,
                Ability.AbilityType.DAMAGE,16, .90,
                "deal lightning damage to an enemy from afar");
        Ability scorch = new MagicAbility("Scorch", 6, 3, 2,
                Ability.AbilityType.DAMAGE,10, .70,
                "deal light fire damage to multiple enemies from afar");
        Ability cripple = new StatusEffectAbility("Cripple", 6, 2, 1,
                3, Ability.AbilityType.ATTACK_DEBUFF, "Lower a nearby enemies attack");
        jobAbilityList.add(zap);
        jobAbilityList.add(scorch);
        jobAbilityList.add(cripple);
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
