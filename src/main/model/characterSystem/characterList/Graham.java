package main.model.characterSystem.characterList;

import main.model.characterSystem.PlayableCharacterUnit;
import main.model.characterSystem.StatBonus;
import main.model.characterSystem.characterList.characterSprites.GrahamSprite;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.personalAbilities.RescueAbility;
import main.model.graphics.sceneElements.images.CharacterPortrait;
import main.model.jobSystem.jobs.Archer;

import static main.model.graphics.sceneElements.images.CharacterPortrait.ESTELLE_PORTRAIT;

public class Graham extends PlayableCharacterUnit {
    private final int personalHealthBoost = 0;
    private final int personalManaBoost = 2;
    private final int personalStrengthBoost = 2;
    private final int personalMagicBoost = 4;
    private final int personalArmourBoost = 2;
    private final int personalResistanceBoost = 4;
    private final int personalSpeedBoost = 4;
    private final int personalDexterityBoost = 6;

    public Graham() {
        this.characterName = "Graham Lost";
    }

    @Override
    protected void setBaseJob() {
        this.setJob(new Archer());
    }

    protected void setPersonalAbility() {
        this.personalAbility = new RescueAbility("Rescue", 10, this.getCharacterStatSheet().getMagic(), 1,
                Ability.AbilityType.MOVEMENT, "Teleport a far away ally to your side", this);
    }



    protected void setPersonalStatBonuses() {
        this.personalStatBonus = new StatBonus(personalHealthBoost, personalManaBoost, personalStrengthBoost, personalMagicBoost,
                personalArmourBoost, personalResistanceBoost, personalSpeedBoost, personalDexterityBoost);
    }

    @Override
    protected void setCharacterSprite() {
        this.sprite = new GrahamSprite(this);
    }

    @Override
    protected void setCharacterPortrait() {
        this.characterPortrait = new CharacterPortrait(ESTELLE_PORTRAIT);
    }

    protected void setGrowthRate() {
        // stub
    }
}
