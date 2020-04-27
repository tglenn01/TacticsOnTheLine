package test.model;

import main.model.characterSystem.Character;
import main.model.characterSystem.PlayableCharacter;
import main.model.jobSystem.jobs.Archer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class characterCreationTest {
    private Character testCharacter;

    @BeforeEach
    void runBefore() {
        this.testCharacter = new PlayableCharacter("test Character");
    }

    @Test
    void testDefaultJobIsNoble() {
        assertEquals("Noble", testCharacter.getCharacterJob().getJobTitle());
    }

    @Test
    void testChangeCharacterJob() {
        testCharacter.assignJobToCharacter(new Archer());
        assertEquals("Archer", testCharacter.getCharacterJob().getJobTitle());
    }
}
