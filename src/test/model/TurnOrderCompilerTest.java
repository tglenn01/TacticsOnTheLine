package test.model;

import main.model.battleSystem.TurnOrderCompiler;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.NPC;
import main.model.characterSystem.PlayableCharacterUnit;
import main.model.jobSystem.jobs.Archer;
import main.model.jobSystem.jobs.BattleMage;
import main.model.jobSystem.jobs.Warrior;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TurnOrderCompilerTest {
    private TurnOrderCompiler testTurnOrder;
    private List<CharacterUnit> testPlayableCharacters;

    @BeforeEach
    void runBefore() {
        CharacterUnit fastCharacter = new PlayableCharacterUnit("Fast Character");
        fastCharacter.setJob(new Archer()); // Archer is the fastest class
        CharacterUnit slowCharacter = new PlayableCharacterUnit("Slow Character");
        slowCharacter.setJob(new BattleMage()); // Battle mage is the slowest job
        testPlayableCharacters = new ArrayList<>();

        CharacterUnit mediumSpeedEnemy = new NPC("Medium Speed Enemy");
        mediumSpeedEnemy.setJob(new Warrior()); // Warrior is a medium speed job
    }

    @Test
    void testTurnOrder() {
    }
}
