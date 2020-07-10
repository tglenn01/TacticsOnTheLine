package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

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
    protected void initializeJobStats() {
        this.jobHealth = 24;
        this.jobMana = 24;
        this.jobStrength = 10;
        this.jobMagic = 7;
        this.jobArmour = 4;
        this.jobResistance = 4;
        this.jobSpeed = 12;
        this.jobDexterity = 12;
        this.jobMovement = StatSheet.BASE_MOVEMENT;
    }
}
