package teammate;

import java.util.*;

public class TeamBuilder {

    private final int teamSize;

    public TeamBuilder(int teamSize) {
        this.teamSize = teamSize;
    }

    public List<Team> buildBalancedTeams(List<Participant> participants) {

        List<Team> teams = new ArrayList<>();

        if (participants == null || participants.isEmpty()) {
            System.out.println("⚠ No participants available to form teams.");
            return teams;
        }

        int numTeams = Math.max(1, (int) Math.ceil((double) participants.size() / teamSize));

        for (int i = 1; i <= numTeams; i++) {
            teams.add(new Team("Team " + i));
        }

        participants.sort((p1, p2) ->
                Integer.compare(p2.getSkillLevel(), p1.getSkillLevel())
        );

        int index = 0;
        boolean reverse = false;

        for (Participant p : participants) {
            teams.get(index).addMember(p);

            if (!reverse) index++;
            else index--;

            if (index >= numTeams) { reverse = true; index = numTeams - 1; }
            else if (index < 0) { reverse = false; index = 0; }
        }

        return teams;
    }
}
