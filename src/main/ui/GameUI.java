package main.ui;

import main.model.characterSystem.PlayableCharacterUnit;

import java.util.ArrayList;
import java.util.List;


public class GameUI {
    private List<PlayableCharacterUnit> playableCharacterList;

    public GameUI() {
        initializeCharacters();
        playableCharacterList = new ArrayList<>();
    }

    private void initializeCharacters() {
        PlayableCharacterUnit hero = new PlayableCharacterUnit("hero");
        playableCharacterList.add(hero);
    }

    private void setJobs() {
        // for each character ask user to pick their job
    }
}
