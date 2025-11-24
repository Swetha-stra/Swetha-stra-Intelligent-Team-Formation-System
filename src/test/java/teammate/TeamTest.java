package teammate;

import org.junit.Test;
import static org.junit.Assert.*;

public class TeamTest {

    @Test
    public void testAddAndRemoveMember() {
        Team t = new Team("Team-1");
        Participant p = new Participant("1", "Test", "a@a.com", "Chess", 5, "Strategist", 80, "Balanced");

        t.addMember(p);
        assertEquals(1, t.getTeamSize());

        t.removeMember(p);
        assertEquals(0, t.getTeamSize());
    }

    @Test
    public void testGetMembers() {
        Team t = new Team("Team-1");
        assertNotNull(t.getMembers());
    }
}
