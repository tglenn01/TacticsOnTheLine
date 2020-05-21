package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class Thief extends Job {

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
        statSheet.setMaxHealth(20);
        statSheet.setMaxMana(16);
        statSheet.setBaseStrength(10);
        statSheet.setBaseMagic(10);
        statSheet.setBaseArmour(2);
        statSheet.setBaseResistance(2);
        statSheet.setBaseSpeed(16);
        statSheet.setBaseDexterity(18);
        statSheet.setMovement(StatSheet.BASE_MOVEMENT + 1);
    }
}
