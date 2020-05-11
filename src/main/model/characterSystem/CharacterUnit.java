package main.model.characterSystem;

import main.exception.AttackMissedException;
import main.exception.OutOfManaException;
import main.exception.UnitIsDeadException;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.Noble;
import main.ui.Battle;

public abstract class CharacterUnit {
    protected String characterName;
    protected Job characterJob;
    protected StatSheet characterStatSheet;
    protected boolean isAlive;

    public CharacterUnit(String name) {
        this.characterName = name;
        this.characterJob = new Noble(); // Default job is noble
        this.characterStatSheet = new StatSheet(characterJob);
        this.isAlive = true;
    }

    public void setJob(Job job) {
        this.characterJob = job;
    }

    public StatSheet getCharacterStatSheet() {
        return characterStatSheet;
    }

    public Job getCharacterJob() {
        return characterJob;
    }

    public String getCharacterName() {
        return characterName;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setAlive(boolean deathStatus) {
        this.isAlive = deathStatus;
    }

    public abstract void takeAction(Battle battle);

    protected abstract CharacterUnit getDefendingEnemy(Battle battle);

    protected abstract void getChoosenAbility(Battle battle);

    protected void abilityTakeAction(Battle battle, Ability ability) {
        CharacterUnit targetedEnemy = getDefendingEnemy(battle);
        try {
            ability.takeAction(this, targetedEnemy);
        } catch (OutOfManaException e) {
            System.out.println("Insufficent Mana: you have: " + this.characterStatSheet.getMana() + " left");
            System.out.println("Choose a different ability");
            getChoosenAbility(battle);
        } catch (AttackMissedException e) {
            System.out.println("Their attack missed :(");
        } catch (UnitIsDeadException e) {
            System.out.println(e.getDeadUnit().getCharacterName() + " has died");
            battle.removeDeadCharacter(e.getDeadUnit());
        }
    }
}
