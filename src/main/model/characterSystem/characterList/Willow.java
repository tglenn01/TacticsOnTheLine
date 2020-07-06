package main.model.characterSystem.characterList;

import main.model.characterSystem.PlayableCharacterUnit;
import main.model.characterSystem.StatBonus;
import main.model.combatSystem.abilities.personalAbilities.TowerAbility;
import main.model.jobSystem.jobs.BattleMage;

import static main.model.graphics.sceneElements.images.CharacterPortrait.ESTELLE_PORTRAIT;
import static main.model.graphics.sceneElements.images.CharacterSprite.ESTELLE_SPRITE;

public class Willow extends PlayableCharacterUnit {
    private final int personalHealthBoost = 0;
    private final int personalManaBoost = 10;
    private final int personalStrengthBoost = 0;
    private final int personalMagicBoost = 6;
    private final int personalArmourBoost = 0;
    private final int personalResistanceBoost = 3;
    private final int personalSpeedBoost = 3;
    private final int personalDexterityBoost = 0;

    public Willow() {
        this.characterName = "Willow Summers";
        this.setCharacterPortrait(ESTELLE_PORTRAIT);
        this.setCharacterSprite(ESTELLE_SPRITE);
    }

    @Override
    protected void setBaseJob() {
        this.setJob(new BattleMage());
    }

    @Override
    protected void setPersonalAbility() {
        this.personalAbility = new TowerAbility("Tower", 20, 0, 1, null,
                "Root yourself increasing the range of magical abilities");
        addPersonalAbilityToAbilityList();
    }

    @Override
    protected void setPersonalStatBonuses() {
        this.personalStatBonus = new StatBonus(personalHealthBoost, personalManaBoost, personalStrengthBoost, personalMagicBoost,
                personalArmourBoost, personalResistanceBoost, personalSpeedBoost, personalDexterityBoost);
    }
}
