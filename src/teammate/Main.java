package teammate;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        FileManager fileManager = new FileManager();

        System.out.println("=== TeamMate: Intelligent Team Formation System ===");
        System.out.print("Enter path to participant CSV file: ");
        String path = input.nextLine();

        List<Participant> participants = fileManager.loadParticipants(path);
        System.out.println("Loaded " + participants.size() + " participants.");

        System.out.print("Enter desired team size: ");
        int teamSize = input.nextInt();

        TeamBuilder builder = new TeamBuilder(teamSize);
        DataProcessorThread thread = new DataProcessorThread(builder, participants);
        thread.start();

        try {
            thread.join(); // wait until thread finishes
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        List<Team> teams = thread.getFormedTeams();
        if (teams != null) {
            for (Team t : teams) {
                System.out.println(t);
            }

            fileManager.saveTeams("formed_teams.csv", teams);
        }

        System.out.println("All done! Results saved in formed_teams.csv");
    }
}
