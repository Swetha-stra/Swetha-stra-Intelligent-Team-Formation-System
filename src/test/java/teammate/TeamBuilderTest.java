package teammate;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class TeamBuilderTest {

    @Test
    public void testTeamFormation() {
        List<Participant> list = new ArrayList<>();
        list.add(new Participant("1", "A", "a@a.com", "Chess", 9, "Strategist", 90, "Leader"));
        list.add(new Participant("2", "B", "b@b.com", "Chess", 5, "Defender", 70, "Balanced"));

        TeamBuilder tb = new TeamBuilder(1);
        List<Team> teams = tb.formTeams(list);

        assertEquals(2, teams.size());
    }

    @Test
    public void testSkillSorting() {
        List<Participant> list = new ArrayList<>();
        list.add(new Participant("1", "Low", "a@a.com", "Chess", 1, "Strategist", 90, "Leader"));
        list.add(new Participant("2", "High", "b@b.com", "Chess", 10, "Defender", 70, "Balanced"));

        TeamBuilder tb = new TeamBuilder(2);
        List<Team> teams = tb.formTeams(list);

        assertEquals("High", teams.get(0).getMembers().get(0).getName());
    }
}
