package main.model.characterSystem;

import javafx.scene.control.Button;
import main.model.graphics.menus.LevelUpMenu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ExperiencePoints {
    private final int BASE_EXPERIENCE = 25;
    private int currentExperience;
    private int level;


    public ExperiencePoints() {
        currentExperience = 0;
        level = 1;
    }

    public void addExperiencePoints(CharacterUnit activeUnit, Integer averageLevelOfTargetedUnits) {
        int experienceGained = calculateExperience(activeUnit, averageLevelOfTargetedUnits);
        currentExperience = currentExperience + experienceGained;
        while (currentExperience >= 100) {
            levelUp(activeUnit);
            level++;
            currentExperience =- 100;
        }
    }

    // base experience plus the difference of the two level times 5
    private int calculateExperience(CharacterUnit activeUnit, Integer averageLevelOfTargetedUnits) {
        int activeUnitLevel = activeUnit.getLevel();
        int experienceGained = BASE_EXPERIENCE + ((averageLevelOfTargetedUnits - activeUnitLevel) * 5);
        if (experienceGained <= 0) experienceGained = 1; // experience must always be above 0;
        return experienceGained;
    }

    private void levelUp(CharacterUnit activeUnit) {
        List<LevelUpButton> levelUpButtons = initializeLevelUpButtons(activeUnit);
        new LevelUpMenu(activeUnit, levelUpButtons);
    }


    public List<LevelUpButton> initializeLevelUpButtons(CharacterUnit activeUnit) {
        List<LevelUpButton> levelUpButtons = new ArrayList<>();
        StatSheet statSheet = activeUnit.getCharacterStatSheet();
        List<Integer> randomNumberGenerator = new LinkedList<>();
        for (int i = 0; i <= 99; i++) randomNumberGenerator.add(i);

        for (int i = 0; i < 5; i++) {
            levelUpButtons.add(getNextButton(statSheet, randomNumberGenerator));
        }

        return levelUpButtons;
    }

    private LevelUpButton getNextButton(StatSheet statSheet, List<Integer> randomNumberGenerator) {
        Random rand = new Random();
        int randomElement = randomNumberGenerator.get(rand.nextInt(randomNumberGenerator.size()));

        LevelUpButton levelUpButton;
        int maxRange;
        int initialRange;


        if (randomElement >= 0 && 19 >= randomElement) {
            LevelUpButton healthButton = new LevelUpButton("Health", statSheet.getMaxHealth()) {
                @Override
                public void addStat(StatSheet statSheet) {
                    statSheet.setMaxHealth(this.maxStat + 1);
                }
            };
            levelUpButton = healthButton;
            maxRange = 19;
            initialRange = 0;
        } else if (randomElement >= 20 && 25 >= randomElement) {
            LevelUpButton manaButton = new LevelUpButton("Mana" , statSheet.getMaxMana()) {
                @Override
                public void addStat(StatSheet statSheet) {
                    statSheet.setMaxMana(this.maxStat + 1);
                }
            };
            levelUpButton = manaButton;
            maxRange = 25;
            initialRange = 20;
        } else if (randomElement >= 26 && 40 >= randomElement) {
            LevelUpButton strengthButton = new LevelUpButton("Strength", statSheet.getBaseStrength()) {
                @Override
                public void addStat(StatSheet statSheet) {
                    statSheet.setBaseStrength(this.maxStat + 1);
                }
            };
            levelUpButton = strengthButton;
            maxRange = 40;
            initialRange = 26;
        } else if (randomElement >= 41 && 60 >= randomElement) {
            LevelUpButton magicButton = new LevelUpButton("Magic" , statSheet.getBaseMagic()) {
                @Override
                public void addStat(StatSheet statSheet) {
                    statSheet.setBaseMagic(this.maxStat + 1);
                }
            };
            levelUpButton = magicButton;
            maxRange = 60;
            initialRange = 41;
        } else if (randomElement >= 61 && 67 >= randomElement) {
            LevelUpButton armourButton = new LevelUpButton("Armour" , statSheet.getBaseArmour()) {
                @Override
                public void addStat(StatSheet statSheet) {
                    statSheet.setBaseArmour(this.maxStat + 1);
                }
            };
            levelUpButton = armourButton;
            maxRange = 67;
            initialRange = 61;
        } else if (randomElement >= 68 && 73 >= randomElement) {
            LevelUpButton resistanceButton = new LevelUpButton("Resistance" , statSheet.getBaseResistance()) {
                @Override
                public void addStat(StatSheet statSheet) {
                    statSheet.setBaseResistance(this.maxStat + 1);
                }
            };
            levelUpButton = resistanceButton;
            maxRange = 73;
            initialRange = 68;
        } else if (randomElement >= 74 && 84 >= randomElement) {
            LevelUpButton speedButton = new LevelUpButton("Speed" , statSheet.getBaseSpeed()) {
                @Override
                public void addStat(StatSheet statSheet) {
                    statSheet.setBaseSpeed(this.maxStat + 1);
                }
            };
            levelUpButton = speedButton;
            maxRange = 84;
            initialRange = 74;
        } else  {
            LevelUpButton dexterityButton = new LevelUpButton("Dexterity", statSheet.getBaseDexterity()) {
                @Override
                public void addStat(StatSheet statSheet) {
                    statSheet.setBaseDexterity(this.maxStat + 1);
                }
            };
            levelUpButton = dexterityButton;
            maxRange = 99;
            initialRange = 85;
        }


        List<Integer> toRemove = new ArrayList<>();
        for (int it = initialRange; it <= maxRange; it++) {
            toRemove.add(it);
        }
        randomNumberGenerator.removeAll(toRemove);

        return levelUpButton;
    }

    public int getLevel() {
        return this.level;
    }

    public void setCurrentExperience(int experience) {
        this.currentExperience = experience;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public abstract class LevelUpButton extends Button {
        protected String statName;
        protected int maxStat;

        public LevelUpButton(String statName, int maxStat) {
            this.statName = statName;
            this.maxStat = maxStat;
            this.setText(statName + "\n" + maxStat + " -> " + (maxStat + 1));
        }

        public abstract void addStat(StatSheet statSheet);
    }
}
