package teammate;

import java.util.*;

public class TeamBuilder {

    private int teamSize;

    public TeamBuilder(int teamSize) {
        this.teamSize = teamSize;
    }

    public List<Team> formTeams(List<Participant> participants) {

        List<Team> teams = new ArrayList<>();

        // Sort by skill (high → low)
        participants.sort((a, b) -> Integer.compare(b.getSkillLevel(), a.getSkillLevel()));

        int teamCount = (int) Math.ceil((double) participants.size() / teamSize);

        // Create empty teams
        for (int i = 0; i < teamCount; i++) {
            teams.add(new Team("Team-" + (i + 1)));
        }

        // Round-robin assignment
        int index = 0;
        for (Participant p : participants) {
            teams.get(index % teamCount).addMember(p);
            index++;
        }

        return teams;
    }
}
