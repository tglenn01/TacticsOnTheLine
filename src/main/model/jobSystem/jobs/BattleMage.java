package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.MagicAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class BattleMage extends Job {

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
        statSheet.setMaxHealth(20);
        statSheet.setMaxMana(36);
        statSheet.setBaseStrength(6);
        statSheet.setBaseMagic(12);
        statSheet.setBaseArmour(2);
        statSheet.setBaseResistance(8);
        statSheet.setBaseSpeed(4);
        statSheet.setBaseDexterity(2);
        statSheet.setMovement(StatSheet.BASE_MOVEMENT);
    }
}
