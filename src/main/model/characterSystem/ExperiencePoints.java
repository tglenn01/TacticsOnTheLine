package main.model.characterSystem;

import javafx.scene.control.Button;
import main.model.graphics.menus.LevelUpMenu;

import java.util.*;

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
        if (currentExperience >= 100) {
            levelUp(activeUnit);
        }
    }

    // base experience plus the difference of the two level times 5
    private int calculateExperience(CharacterUnit activeUnit, Integer averageLevelOfTargetedUnits) {
        int activeUnitLevel = activeUnit.getLevel();
        int experienceGained = BASE_EXPERIENCE + ((averageLevelOfTargetedUnits - activeUnitLevel) * 5);
        if (experienceGained <= 0) experienceGained = 1; // experience must always be above 0;
        return experienceGained;
    }

    public void levelUp(CharacterUnit activeUnit) {
        level++;
        currentExperience -= 100;
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

    // create a button to level up each different stat. Then add them all to a map and roll a random number
    // using the experience increment search the map to see if the random number falls within the range of the
    // growth rate of that stat. If not then add the range of the growth to the experienceIncrement and try
    // the next entry
    private LevelUpButton getNextButton(StatSheet statSheet, List<Integer> randomNumberGenerator) {
        Random rand = new Random();
        int randomElement = randomNumberGenerator.get(rand.nextInt(randomNumberGenerator.size()));


        LevelUpButton healthButton = new LevelUpButton("Health", statSheet.getMaxHealth()) {
            @Override
            public void addStat(StatSheet statSheet) {
                statSheet.levelUpHealth();
            }
        };
        LevelUpButton manaButton = new LevelUpButton("Mana", statSheet.getMaxMana()) {
            @Override
            public void addStat(StatSheet statSheet) {
                statSheet.levelUpMana();
            }
        };
        LevelUpButton strengthButton = new LevelUpButton("Strength", statSheet.getBaseStrength()) {
            @Override
            public void addStat(StatSheet statSheet) {
                statSheet.levelUpStrength();
            }
        };
        LevelUpButton magicButton = new LevelUpButton("Magic", statSheet.getBaseMagic()) {
            @Override
            public void addStat(StatSheet statSheet) {
                statSheet.levelUpMagic();
            }
        };
        LevelUpButton armourButton = new LevelUpButton("Armour", statSheet.getBaseArmour()) {
            @Override
            public void addStat(StatSheet statSheet) {
                statSheet.levelUpArmour();
            }
        };
        LevelUpButton resistanceButton = new LevelUpButton("Resistance", statSheet.getBaseResistance()) {
            @Override
            public void addStat(StatSheet statSheet) {
                statSheet.levelUpResistance();
            }
        };
        LevelUpButton speedButton = new LevelUpButton("Speed", statSheet.getBaseSpeed()) {
            @Override
            public void addStat(StatSheet statSheet) {
                statSheet.levelUpSpeed();
            }
        };
        LevelUpButton dexterityButton = new LevelUpButton("Dexterity", statSheet.getBaseDexterity()) {
            @Override
            public void addStat(StatSheet statSheet) {
                statSheet.levelUpDexterity();
            }
        };


        Map<LevelUpButton, Integer> growthRateList = new LinkedHashMap<>();
        growthRateList.put(healthButton, statSheet.getHealthGrowthRate());
        growthRateList.put(manaButton, statSheet.getManaGrowthRate());
        growthRateList.put(strengthButton, statSheet.getStrengthGrowthRate());
        growthRateList.put(magicButton, statSheet.getMagicGrowthRate());
        growthRateList.put(armourButton, statSheet.getArmourGrowthRate());
        growthRateList.put(resistanceButton, statSheet.getResistanceGrowthRate());
        growthRateList.put(speedButton, statSheet.getSpeedGrowthRate());
        growthRateList.put(dexterityButton, statSheet.getDexterityGrowthRate());

        int experienceIncrement = 0;

        for (Map.Entry<LevelUpButton, Integer> entry : growthRateList.entrySet()) {
            if (randomElement >= experienceIncrement && experienceIncrement + entry.getValue() > randomElement) {
                removeRangeFrom(experienceIncrement, entry.getValue() + experienceIncrement, randomNumberGenerator);
                return entry.getKey();
            } else experienceIncrement += entry.getValue();
        }

        return null;
    }

    // Makes it so you cannot get the same button twice in one level Up
    private void removeRangeFrom(int initialRange, int maxRange, List<Integer> randomNumberGenerator) {
        List<Integer> toRemove = new ArrayList<>();
        for (int it = initialRange; it <= maxRange; it++) {
            toRemove.add(it);
        }
        randomNumberGenerator.removeAll(toRemove);
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

    public int getCurrentExperience() {
        return this.currentExperience;
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
