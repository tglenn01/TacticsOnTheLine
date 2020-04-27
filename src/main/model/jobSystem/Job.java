package main.model.jobSystem;

import main.model.jobSystem.jobAbilities.Ability;

import java.util.ArrayList;
import java.util.List;

public abstract class Job {
    protected String jobTitle;
    protected List<Ability> jobAbilityList;

    public Job() {
        jobAbilityList = new ArrayList<>();
    }

    protected abstract void initializeAbilities();

    public String getJobTitle() {
        return jobTitle;
    }
}
