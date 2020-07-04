package main.model.characterSystem.characterList;

import main.model.characterSystem.PlayableCharacterUnit;
import main.model.characterSystem.StatBonus;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.personalAbilities.Barrier;
import main.model.jobSystem.jobs.Cleric;

import static main.model.graphics.sceneElements.images.CharacterPortrait.ESTELLE_PORTRAIT;
import static main.model.graphics.sceneElements.images.CharacterSprite.ESTELLE_SPRITE;

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
        this.setCharacterPortrait(ESTELLE_PORTRAIT);
        this.setCharacterSprite(ESTELLE_SPRITE);
    }

    @Override
    protected void setBaseJob() {
        this.setJob(new Cleric());
    }

    @Override
    protected void setPersonalAbility() {
        this.personalAbility = new Barrier("Barrier", 14, 1, 1, Ability.AbilityType.DEFENSE_BUFF,
                "Protect an ally from damage one time");
        addPersonalAbilityToAbilityList();
    }

    @Override
    protected void setPersonalStatBonuses() {
        this.personalStatBonus = new StatBonus(personalHealthBoost, personalManaBoost, personalStrengthBoost, personalMagicBoost,
                personalArmourBoost, personalResistanceBoost, personalSpeedBoost, personalDexterityBoost);
    }
}
