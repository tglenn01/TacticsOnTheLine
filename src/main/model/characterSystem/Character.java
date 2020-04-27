package main.model.characterSystem;

import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.Noble;

public class Character {
    private String characterName;
    private Job characterJob;
    private StatSheet characterStatSheet;

    public Character(String name) {
        this.characterName = name;
        this.characterJob = new Noble(); // Default job is noble
        this.characterStatSheet = new StatSheet(characterJob);
    }

    public void assignJobToCharacter(Job job) {
        this.characterJob = job;
    }

    public StatSheet getCharacterStatSheet() {
        return characterStatSheet;
    }

    public Job getCharacterJob() {
        return characterJob;
    }
}
