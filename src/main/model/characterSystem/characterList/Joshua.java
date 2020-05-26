package main.model.characterSystem.characterList;

import main.model.characterSystem.PlayableCharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.jobSystem.jobs.Thief;

import static main.model.characterSystem.CharacterPortrait.JOSHUA_PORTRAIT;
import static main.model.characterSystem.CharacterSprite.JOSHUA_SPRITE;

public class Joshua extends PlayableCharacterUnit {

    public Joshua() {
        this.characterName = "Joshua";
        this.characterJob = new Thief();
        this.characterStatSheet = new StatSheet(this.characterJob);
        this.setCharacterPortrait(JOSHUA_PORTRAIT);
        this.setCharacterSprite(JOSHUA_SPRITE);
    }
}
