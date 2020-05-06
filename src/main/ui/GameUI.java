package main.ui;

import main.model.battleSystem.Battle;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.PlayableCharacterUnit;
import main.model.scenarioSystem.ScenarioOne;

import java.util.ArrayList;
import java.util.List;


public class GameUI {
    private List<CharacterUnit> playableCharacterList;

    public GameUI() {
        playableCharacterList = new ArrayList<>();
        initializeCharacters();
        new Battle(playableCharacterList, new ScenarioOne());
    }

    private void initializeCharacters() {
        CharacterUnit hero = new PlayableCharacterUnit("Hero");
        playableCharacterList.add(hero);
    }

    private void setJobs() {
        // for each character ask user to pick their job
    }
}
