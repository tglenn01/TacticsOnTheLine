package main.ui;

import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.PlayableCharacterUnit;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.*;
import main.model.scenarioSystem.ScenarioOne;

import java.util.ArrayList;
import java.util.List;


public class TurnBaseBattle {
    private static int PARTY_SIZE = 4;
    private List<CharacterUnit> partyMemberList;
    private List<Job> availableJobs;

    public TurnBaseBattle() {
        partyMemberList = new ArrayList<>();
        availableJobs = new ArrayList<>();
        initializeJobs();
        initializeCharacters();
        new Battle(partyMemberList, new ScenarioOne());
    }

    private void initializeJobs() {
        availableJobs.add(new Archer());
        availableJobs.add(new BattleMage());
        availableJobs.add(new Cleric());
        availableJobs.add(new Noble());
        availableJobs.add(new Warrior());
    }

    private void initializeCharacters() {
        System.out.println("Choose your party members!");
        for (int i = 1; i <= PARTY_SIZE; i++) {
            createNewPartyMember(i);
        }
    }

    private void createNewPartyMember(int characterPosition) {
        System.out.println("Party Member " + characterPosition + "!");
        System.out.println("Choose One:");
        for (Job job : availableJobs) {
            System.out.println(job.getJobTitle());
        }

        Job characterJob = askUserForJob();

        CharacterUnit partyMember = new PlayableCharacterUnit(characterJob, characterJob.getJobTitle());
        partyMemberList.add(partyMember);
        System.out.println("You have choosen a " + characterJob.getJobTitle() + "!");
    }

    private Job askUserForJob() {
        UserInput input = new UserInput();
        String command = input.getInput();
        Job characterJob = new Noble(); // noble is default job
        for (Job job : availableJobs) {
            if (command.equals(job.getJobTitle())) {
                characterJob = job;
            }
        }
        return characterJob;
    }
}
