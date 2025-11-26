package teammate;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamName;
    private List<Participant> members;

    public Team(String teamName) {
        this.teamName = teamName;
        this.members = new ArrayList<>();
    }

    public String getTeamName() { return teamName; }
    public List<Participant> getMembers() { return members; }

    public void addMember(Participant participant) { members.add(participant); }
    public void removeMember(Participant participant) { members.remove(participant); }
    public int getTeamSize() { return members.size(); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Team: " + teamName + "\nMembers:\n");
        for (Participant p : members) {
            sb.append(" - ").append(p.toString()).append("\n");
        }
        return sb.toString();
    }
}
