package teammate;

import org.junit.Test;
import static org.junit.Assert.*;

public class ParticipantTest {

    @Test
    public void testGettersAndSetters() {
        Participant p = new Participant("P001", "Alice", "alice@test.com",
                "Chess", 8, "Strategist", 90, "Leader");

        assertEquals("P001", p.getId());
        assertEquals("Alice", p.getName());
        assertEquals("alice@test.com", p.getEmail());
        assertEquals("Chess", p.getGamePreference());
        assertEquals(8, p.getSkillLevel());
        assertEquals("Strategist", p.getRole());
        assertEquals(90, p.getPersonalityScore());
        assertEquals("Leader", p.getPersonalityType());

        p.setName("Bob");
        assertEquals("Bob", p.getName());
    }

    @Test
    public void testToStringNotNull() {
        Participant p = new Participant("P001", "Alice", "alice@test.com",
                "Chess", 8, "Strategist", 90, "Leader");
        assertNotNull(p.toString());
    }
}
