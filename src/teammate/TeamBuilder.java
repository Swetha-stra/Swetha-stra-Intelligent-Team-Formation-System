package teammate;

import java.util.List;
import java.util.ArrayList;

public class TeamBuilder {

    private int teamSize;

    public TeamBuilder(int teamSize) {
        this.teamSize = teamSize;
    }

    public List<Team> formTeams(List<Participant> participants) {

        List<Team> teams = new ArrayList<>();

        // Sort by skill (descending)
        participants.sort(
                Comparator.comparingInt(Participant::getSkillLevel).reversed()
        );

        int teamCount = (int) Math.ceil((double) participants.size() / teamSize);

        for (int i = 0; i < teamCount; i++) {
            teams.add(new Team("Team-" + (i + 1)));
        }

        // Round-robin distributes top skill evenly
        int index = 0;
        for (Participant p : participants) {
            teams.get(index % teamCount).addMember(p);
            index++;
        }

        return teams;
    }
}
