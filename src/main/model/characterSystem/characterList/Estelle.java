package main.model.characterSystem.characterList;

import main.model.characterSystem.PlayableCharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.jobSystem.jobs.Lancer;

import static main.model.graphics.sceneElements.images.CharacterPortrait.ESTELLE_PORTRAIT;
import static main.model.graphics.sceneElements.images.CharacterSprite.*;

public class Estelle extends PlayableCharacterUnit {

    public Estelle() {
        this.characterName = "Estelle";
        this.characterJob = new Lancer();
        this.characterStatSheet = new StatSheet(this.characterJob);
        this.setCharacterPortrait(ESTELLE_PORTRAIT);
        this.setCharacterSprite(ESTELLE_SPRITE);
    }
}
