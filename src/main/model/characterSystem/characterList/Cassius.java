package main.model.characterSystem.characterList;

import main.model.characterSystem.PlayableCharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.jobSystem.jobs.Noble;

import static main.model.characterSystem.CharacterPortrait.CASSIUS_PORTRAIT;
import static main.model.characterSystem.CharacterSprite.CASSIUS_SPRITE;

public class Cassius extends PlayableCharacterUnit {

    public Cassius() {
        this.characterName = "Cassius";
        this.characterJob = new Noble();
        this.characterStatSheet = new StatSheet(this.characterJob);
        this.setCharacterPortrait(CASSIUS_PORTRAIT);
        this.setCharacterSprite(CASSIUS_SPRITE);
    }
}
