package teammate;

import java.util.*;

public class TeamBuilder {

    private final int teamSize;

    public TeamBuilder(int teamSize) {
        this.teamSize = teamSize;
    }

    public List<Team> formTeams(List<Participant> participants) {

        // Sort by skill (descending)
        participants.sort(Comparator.comparingInt(Participant::getSkillLevel).reversed());

        List<Team> teams = new ArrayList<>();
        int totalTeams = (int) Math.ceil((double) participants.size() / teamSize);

        // Initialize empty teams
        for (int i = 1; i <= totalTeams; i++) {
            teams.add(new Team("Team-" + i));
        }

        // Assign participants to teams with constraints
        for (Participant p : participants) {
            Team bestTeam = findBestTeam(teams, p);
            bestTeam.addMember(p);
        }

        return teams;
    }

    /** Finds the best team by:
     *  - preferring teams missing the participant’s role
     *  - preferring teams missing the participant’s personality
     *  - preferring teams with fewer matching game preferences
     */
    private Team findBestTeam(List<Team> teams, Participant p) {

        Team best = null;
        int bestScore = Integer.MIN_VALUE;

        for (Team t : teams) {

            if (t.getTeamSize() >= teamSize)
                continue; // skip full team

            int score = evaluateTeamFit(t, p);

            if (score > bestScore) {
                bestScore = score;
                best = t;
            }
        }
        return best;
    }

    /** Scoring system:
     *   +5 if role is not present (role diversity)
     *   +5 if personality type is not present (personality mix)
     *   -3 for repeated game type (game diversity)
     *   +1 for general balance
     */
    private int evaluateTeamFit(Team team, Participant p) {

        int score = 0;
        List<Participant> members = team.getMembers();

        boolean roleExists = false;
        boolean personalityExists = false;
        int sameGameCount = 0;

        for (Participant m : members) {

            if (m.getRole().equalsIgnoreCase(p.getRole())) {
                roleExists = true;
            }

            if (m.getPersonalityType().equalsIgnoreCase(p.getPersonalityType())) {
                personalityExists = true;
            }

            if (m.getGamePreference().equalsIgnoreCase(p.getGamePreference())) {
                sameGameCount++;
            }
        }

        // Encourage missing roles
        if (!roleExists) score += 5;

        // Encourage personality mixing
        if (!personalityExists) score += 5;

        // Penalize same game to enforce diversity
        score -= (sameGameCount * 3);

        // Small bonus for team balance
        score += (teamSize - members.size());

        return score;
    }
}
