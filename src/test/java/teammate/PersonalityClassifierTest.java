package teammate;

import org.junit.Test;
import static org.junit.Assert.*;

public class PersonalityClassifierTest {

    @Test
    public void testLeader() {
        assertEquals("Leader", PersonalityClassifier.classify(95));
    }

    @Test
    public void testBalanced() {
        assertEquals("Balanced", PersonalityClassifier.classify(75));
    }

    @Test
    public void testThinker() {
        assertEquals("Thinker", PersonalityClassifier.classify(55));
    }

    @Test
    public void testInvalidScore() {
        assertEquals("Invalid Score", PersonalityClassifier.classify(20));
    }
}
