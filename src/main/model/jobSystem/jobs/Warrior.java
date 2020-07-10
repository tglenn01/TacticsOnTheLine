package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class Warrior extends Job {

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
                Ability.AbilityType.ATTACK_BUFF, 4, "Buff the attack of a neighbouring ally");
        Ability stone = new PhysicalAbility("Stone", 1, 4, 1,
                Ability.AbilityType.DAMAGE,8, .60,
                "A low chance to damage an enemy from afar");
        jobAbilityList.add(club);
        jobAbilityList.add(focus);
        jobAbilityList.add(stone);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 40;
        this.jobMana = 8;
        this.jobStrength = 13;
        this.jobMagic = 2;
        this.jobArmour = 8;
        this.jobResistance = 0;
        this.jobSpeed = 6;
        this.jobDexterity = 6;
        this.jobMovement = StatSheet.BASE_MOVEMENT;
    }
}
