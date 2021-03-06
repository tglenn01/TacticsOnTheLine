package main.model.characterSystem.characterList;

import main.model.characterSystem.PlayableCharacterUnit;
import main.model.characterSystem.StatBonus;
import main.model.characterSystem.characterList.characterSprites.No1Sprite;
import main.model.combatSystem.abilities.personalAbilities.DeactivateAbility;
import main.model.graphics.sceneElements.images.CharacterPortrait;
import main.model.jobSystem.jobs.thiefJob.Thief;

public class No1 extends PlayableCharacterUnit {
    private final int personalHealthBoost = 0;
    private final int personalManaBoost = 0;
    private final int personalStrengthBoost = 4;
    private final int personalMagicBoost = 6;
    private final int personalArmourBoost = 0;
    private final int personalResistanceBoost = 2;
    private final int personalSpeedBoost = 6;
    private final int personalDexterityBoost = 6;

    public No1() {
        this.characterName = "No.1";
    }

    @Override
    protected void setBaseJob() {
        this.setJob(new Thief());
    }

    @Override
    protected void setPersonalAbility() {
        this.personalAbility = new DeactivateAbility();
    }

    @Override
    protected void setGrowthRate() {
        characterStatSheet.setGrowthRates(5, 5, 20, 10,
                5, 5, 30, 20);
    }

    @Override
    protected void setPersonalStatBonuses() {
        this.personalStatBonus = new StatBonus(personalHealthBoost, personalManaBoost, personalStrengthBoost, personalMagicBoost,
                personalArmourBoost, personalResistanceBoost, personalSpeedBoost, personalDexterityBoost);
    }

    @Override
    protected void setCharacterSprite() {
        this.sprite = new No1Sprite(this);
    }

    @Override
    protected void setCharacterPortrait() {
        this.characterPortrait = new CharacterPortrait("resources/no1CharacterSprites/no1_portrait.jpg");
    }
}
