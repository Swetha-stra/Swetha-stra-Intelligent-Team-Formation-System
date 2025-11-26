package teammate;

import org.junit.Test;
import static org.junit.Assert.*;

public class PersonalityClassifierTest {

    @Test
    public void testLeaderRange() {
        assertEquals("Leader", PersonalityClassifier.classify(22));
    }

    @Test
    public void testBalancedRange() {
        assertEquals("Balanced", PersonalityClassifier.classify(16));
    }

    @Test
    public void testThinkerRange() {
        assertEquals("Thinker", PersonalityClassifier.classify(12));
    }

    @Test
    public void testReservedRange() {
        assertEquals("Reserved", PersonalityClassifier.classify(7));
    }

    @Test
    public void testInvalidScore() {
        assertEquals("Invalid Score", PersonalityClassifier.classify(45));
    }

}
