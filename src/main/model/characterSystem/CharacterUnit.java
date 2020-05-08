package main.model.characterSystem;

import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.Noble;

public class CharacterUnit {
    private String characterName;
    private Job characterJob;
    private StatSheet characterStatSheet;

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
}
