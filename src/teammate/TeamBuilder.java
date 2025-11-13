package teammate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TeamBuilder {

    private int teamSize;

    public TeamBuilder(int teamSize) {
        this.teamSize = teamSize;
    }

    public List<Team> formTeams(List<Participant> participants) {
        List<Team> teams = new ArrayList<>();

        if (participants == null || participants.isEmpty()) {
            System.out.println("No participants available.");
            return teams;
        }

        // Shuffle list for randomization within fairness constraints
        Collections.shuffle(participants, new Random());

        int teamCount = (int) Math.ceil((double) participants.size() / teamSize);
        for (int i = 0; i < teamCount; i++) {
            Team team = new Team("Team-" + (i + 1));
            teams.add(team);
        }

        int index = 0;
        for (Participant p : participants) {
            teams.get(index % teamCount).addMember(p);
            index++;
        }

        return teams;
    }
}
