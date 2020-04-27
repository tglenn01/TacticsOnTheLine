package main.ui;

import main.model.characterSystem.PlayableCharacter;

import java.util.ArrayList;
import java.util.List;


public class GameUI {
    private List<PlayableCharacter> playableCharacterList;

    public GameUI() {
        initializeCharacters();
        playableCharacterList = new ArrayList<>();
    }

    private void initializeCharacters() {
        PlayableCharacter hero = new PlayableCharacter("hero");
        playableCharacterList.add(hero);
    }

    private void setJobs() {
        // for each character ask user to pick their job
    }
}
