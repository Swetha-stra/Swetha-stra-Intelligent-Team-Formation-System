package teammate;

import java.util.List;
import java.util.ArrayList;

public class DataProcessorThread extends Thread {

    private final List<Participant> participants;
    private final TeamBuilder teamBuilder;
    private List<Team> formedTeams;

    public DataProcessorThread(TeamBuilder teamBuilder, List<Participant> participants) {
        this.teamBuilder = teamBuilder;
        this.participants = participants;
    }

    @Override
    public void run() {
        System.out.println("Processing and forming teams in background thread...");
        formedTeams = teamBuilder.buildBalancedTeams(participants);
    }

    public List<Team> getFormedTeams() {
        return formedTeams != null ? formedTeams : new ArrayList<>();
    }
}
