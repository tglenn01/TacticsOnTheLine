package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.jobSystem.Job;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;

public class Archer extends Job {

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
                Ability.AbilityType.DEFENSE_DEBUFF, "Fire a trap to lower an enemies " +
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
        statSheet.setMaxHealth(20);
        statSheet.setMaxMana(24);
        statSheet.setBaseStrength(10);
        statSheet.setBaseMagic(7);
        statSheet.setBaseArmour(2);
        statSheet.setBaseResistance(6);
        statSheet.setBaseSpeed(10);
        statSheet.setBaseDexterity(8);
        statSheet.setMovement(StatSheet.BASE_MOVEMENT);
    }
}
