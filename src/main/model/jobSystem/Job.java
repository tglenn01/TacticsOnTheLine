package main.model.jobSystem;

import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;

import java.util.ArrayList;
import java.util.List;

public abstract class Job {
    protected String jobTitle;
    protected List<Ability> jobAbilityList;

    public Job() {
        jobAbilityList = new ArrayList<>();
        initializeAbilities();
    }

    protected void initializeAbilities() {
        Ability attack = new PhysicalAbility("Attack", 0, 1, 1,
                0, .90, "Attack an enemy");
        Ability defend = new StatusEffectAbility("Defend", 0, 0, 1,
                StatusEffectAbility.StatusType.DEFENSE_BUFF, "Strengthen one's own defenses");
        jobAbilityList.add(attack);
        jobAbilityList.add(defend);
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public List<Ability> getJobAbilityList() {
        return jobAbilityList;
    }
}
