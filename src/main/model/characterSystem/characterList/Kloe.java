package main.model.characterSystem.characterList;

import main.model.characterSystem.PlayableCharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.jobSystem.jobs.Cleric;

import static main.model.characterSystem.CharacterPortrait.KLOE_PORTRAIT;
import static main.model.characterSystem.CharacterSprite.KLOE_SPRITE;

public class Kloe extends PlayableCharacterUnit {

    public Kloe() {
        this.characterName = "Kloe";
        this.characterJob = new Cleric();
        this.characterStatSheet = new StatSheet(this.characterJob);
        this.setCharacterPortrait(KLOE_PORTRAIT);
        this.setCharacterSprite(KLOE_SPRITE);
    }
}
