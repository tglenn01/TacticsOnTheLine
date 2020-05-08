package main.model.characterSystem;

import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.Noble;
import main.ui.Battle;

public abstract class CharacterUnit {
    protected String characterName;
    protected Job characterJob;
    protected StatSheet characterStatSheet;

    public CharacterUnit(String name) {
        this.characterName = name;
        this.characterJob = new Noble(); // Default job is noble
        this.characterStatSheet = new StatSheet(characterJob);
    }

    public void setJob(Job job) {
        this.characterJob = job;
    }

    public StatSheet getCharacterStatSheet() {
        return characterStatSheet;
    }

    public Job getCharacterJob() {
        return characterJob;
    }

    public String getCharacterName() {
        return characterName;
    }

    public abstract void takeAction(Battle battle);
}
