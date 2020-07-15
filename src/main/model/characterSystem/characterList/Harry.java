package main.model.characterSystem.characterList;

import main.model.characterSystem.PlayableCharacterUnit;
import main.model.characterSystem.StatBonus;
import main.model.characterSystem.characterList.characterSprites.HarrySprite;
import main.model.combatSystem.abilities.personalAbilities.TremorAbility;
import main.model.graphics.sceneElements.images.CharacterPortrait;
import main.model.jobSystem.jobs.warriorJob.Warrior;

import static main.model.graphics.sceneElements.images.CharacterPortrait.ESTELLE_PORTRAIT;

public class Harry extends PlayableCharacterUnit {
    private final int personalHealthBoost = 8;
    private final int personalManaBoost = 0;
    private final int personalStrengthBoost = 6;
    private final int personalMagicBoost = 0;
    private final int personalArmourBoost = 4;
    private final int personalResistanceBoost = 0;
    private final int personalSpeedBoost = 2;
    private final int personalDexterityBoost = 2;

    public Harry() {
        this.characterName = "Harry North";
    }

    @Override
    protected void setBaseJob() {
        this.setJob(new Warrior());
    }

    @Override
    protected void setPersonalAbility() {
        this.personalAbility = new TremorAbility();
    }

    @Override
    protected void setPersonalStatBonuses() {
        this.personalStatBonus = new StatBonus(personalHealthBoost, personalManaBoost, personalStrengthBoost, personalMagicBoost,
                personalArmourBoost, personalResistanceBoost, personalSpeedBoost, personalDexterityBoost);
    }

    @Override
    protected void setCharacterSprite() {
        this.sprite = new HarrySprite(this);
    }

    @Override
    protected void setCharacterPortrait() {
        this.characterPortrait = new CharacterPortrait(ESTELLE_PORTRAIT);
    }
}
