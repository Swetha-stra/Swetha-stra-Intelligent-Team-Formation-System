package teammate.teammate;

import java.util.List;

public class DataProcessorThread extends Thread {
    private TeamBuilder teamBuilder;
    private List<Participant> participants;
    private List<Team> formedTeams;

    public DataProcessorThread(TeamBuilder teamBuilder, List<Participant> participants) {
        this.teamBuilder = teamBuilder;
        this.participants = participants;
    }

    @Override
    public void run() {
        System.out.println("Thread started: Processing data...");
        formedTeams = teamBuilder.formTeams(participants);
        System.out.println("Thread finished: Team formation complete!");
    }

    public List<Team> getFormedTeams() {
        return formedTeams;
    }
}
