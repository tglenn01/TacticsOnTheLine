package main.model.characterSystem.characterList;

import main.model.characterSystem.PlayableCharacterUnit;
import main.model.characterSystem.StatBonus;
import main.model.characterSystem.characterList.characterSprites.LiamSprite;
import main.model.combatSystem.abilities.personalAbilities.BarrierAbility;
import main.model.graphics.sceneElements.images.CharacterPortrait;
import main.model.jobSystem.jobs.clericJob.Cleric;

import static main.model.graphics.sceneElements.images.CharacterPortrait.ESTELLE_PORTRAIT;

public class Liam extends PlayableCharacterUnit {
    private final int personalHealthBoost = 4;
    private final int personalManaBoost = 4;
    private final int personalStrengthBoost = 0;
    private final int personalMagicBoost = 4;
    private final int personalArmourBoost = 4;
    private final int personalResistanceBoost = 6;
    private final int personalSpeedBoost = 0;
    private final int personalDexterityBoost = 0;

    public Liam() {
        this.characterName = "Liam Hudson";
    }

    @Override
    protected void setBaseJob() {
        this.setJob(new Cleric());
    }

    @Override
    protected void setPersonalAbility() {
        this.personalAbility = new BarrierAbility();
    }

    @Override
    protected void setGrowthRate() {
        characterStatSheet.setGrowthRates(20, 20, 5,
                20, 5, 20, 5, 5);
    }

    @Override
    protected void setPersonalStatBonuses() {
        this.personalStatBonus = new StatBonus(personalHealthBoost, personalManaBoost, personalStrengthBoost, personalMagicBoost,
                personalArmourBoost, personalResistanceBoost, personalSpeedBoost, personalDexterityBoost);
    }

    @Override
    protected void setCharacterSprite() {
        this.sprite = new LiamSprite(this);
    }

    @Override
    protected void setCharacterPortrait() {
        this.characterPortrait = new CharacterPortrait(ESTELLE_PORTRAIT);
    }
}
