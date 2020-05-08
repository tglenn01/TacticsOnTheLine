package test.model;

import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.PlayableCharacterUnit;
import main.model.jobSystem.jobs.Archer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharacterUnitCreationTest {
    private CharacterUnit testCharacterUnit;

    @BeforeEach
    void runBefore() {
        this.testCharacterUnit = new PlayableCharacterUnit("test Character");
    }

    @Test
    void testDefaultJobIsNoble() {
        assertEquals("Noble", testCharacterUnit.getCharacterJob().getJobTitle());
    }

    @Test
    void testChangeCharacterJob() {
        testCharacterUnit.setJob(new Archer());
        assertEquals("Archer", testCharacterUnit.getCharacterJob().getJobTitle());
    }
}
