package teammate;

import java.io.IOException;
import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final List<String> ALLOWED_GAMES = Arrays.asList(
            "Chess", "FIFA", "Basketball", "CS:GO", "DOTA 2", "Valorant"
    );

    private static final List<String> ALLOWED_ROLES = Arrays.asList(
            "strategist", "attacker", "defender", "supporter", "coordinator"
    );

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== TeamMate: Intelligent Team Formation System ===");
            System.out.println("1. Participant - Join and add your details");
            System.out.println("2. Organizer - Form teams");
            System.out.println("3. View Formed Teams");
            System.out.println("4. Exit");

            int choice = getValidatedInt("Select an option (1-4): ", 1, 4);

            if (choice == 4) {

                System.out.println("Exiting program. Goodbye!");
                break;
            }

            if (choice == 1) handleParticipantMode();
            else if (choice == 2) handleOrganizerMode();
            else if (choice == 3) handleViewTeams();

        }
    }

    private static void handleParticipantMode() {
        System.out.print("Enter CSV file path where your data should be saved: ");
        String filePath = scanner.nextLine();

        System.out.println("Enter your details:");

        String id = getValidatedString("ID: ");
        String name = getValidatedString("Name: ");
        String email = getValidatedEmail("Email: ");
        String game = chooseGame();
        int skill = getValidatedInt("Skill Level (1-10): ", 1, 10);
        String role = chooseRole();

        System.out.println("\nRate each statement 1–5 (1 = Strongly Disagree, 5 = Strongly Agree)");

        int q1 = getValidatedInt("Q1: I enjoy taking the lead: ", 1, 5);
        int q2 = getValidatedInt("Q2: I think strategically: ", 1, 5);
        int q3 = getValidatedInt("Q3: I work well in teams: ", 1, 5);
        int q4 = getValidatedInt("Q4: I stay calm under pressure: ", 1, 5);
        int q5 = getValidatedInt("Q5: I make quick decisions: ", 1, 5);

        int personalityScore = q1 + q2 + q3 + q4 + q5;
        String type = PersonalityClassifier.classify(personalityScore);

        // Create Participant object
        Participant participant = new Participant(
                id, name, email, game, skill, role, personalityScore, type
        );

        // Save via FileManager
        FileManager fileManager = new FileManager();
        fileManager.saveParticipant(filePath, participant);
    }



    private static void handleOrganizerMode() {

        FileManager fileManager = new FileManager();

        System.out.print("Enter path to participant CSV file: ");
        String path = scanner.nextLine();

        List<Participant> participants = fileManager.loadParticipants(path);

        if (participants.isEmpty()) {
            System.err.println("Error: No participants found! Cannot form teams.");
            return;
        }

        int teamSize = getValidatedInt("Enter desired team size: ", 1, 100);

        TeamBuilder builder = new TeamBuilder(teamSize);
        DataProcessorThread thread = new DataProcessorThread(builder, participants);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        List<Team> teams = thread.getFormedTeams();
        if (!teams.isEmpty()) {
            for (Team t : teams) System.out.println(t);
            fileManager.saveTeams("formed_teams.csv", teams);
        }

        System.out.println("All done! Results saved in formed_teams.csv");
    }

    private static String getValidatedString(String msg) {
        while (true) {
            System.out.print(msg);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Input cannot be empty. Try again.");
        }
    }

    private static String getValidatedEmail(String msg) {
        while (true) {
            System.out.print(msg);
            String email = scanner.nextLine().trim();
            if (email.contains("@") && email.contains(".")) return email;
            System.out.println("Invalid email format. Try again.");
        }
    }

    private static int getValidatedInt(String msg, int min, int max) {
        while (true) {
            try {
                System.out.print(msg);
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) return value;
                System.out.println("Value must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static String chooseGame() {
        System.out.println("\nSelect Game:");
        for (int i = 0; i < ALLOWED_GAMES.size(); i++)
            System.out.println((i + 1) + ". " + ALLOWED_GAMES.get(i));

        while (true) {
            System.out.print("Enter option (1-" + ALLOWED_GAMES.size() + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= ALLOWED_GAMES.size()) return ALLOWED_GAMES.get(choice - 1);
            } catch (Exception ignored) {}
            System.out.println("Invalid choice. Try again.");
        }
    }

    private static String chooseRole() {
        System.out.println("\nSelect Role:");
        for (int i = 0; i < ALLOWED_ROLES.size(); i++)
            System.out.println((i + 1) + ". " + ALLOWED_ROLES.get(i));

        while (true) {
            System.out.print("Enter option (1-" + ALLOWED_ROLES.size() + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= ALLOWED_ROLES.size()) return ALLOWED_ROLES.get(choice - 1);
            } catch (Exception ignored) {}
            System.out.println("Invalid choice. Try again.");
        }
    }

    private static void handleViewTeams() {
        System.out.print("Enter path to formed teams CSV file: ");
        String filePath = scanner.nextLine();

        try (Scanner fileScanner = new Scanner(new java.io.File(filePath))) {

            if (!fileScanner.hasNextLine()) {
                System.out.println("File is empty.");
                return;
            }

            System.out.println("\n=== VIEW FORMED TEAMS ===");

            String currentTeam = "";

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (data.length < 7) {
                    System.out.println("Invalid row: " + line);
                    continue;
                }

                String teamName = data[0].replace("\"", "");
                String member = data[1].replace("\"", "");
                String email = data[2].replace("\"", "");
                String game = data[3].replace("\"", "");
                String role = data[4].replace("\"", "");
                String skill = data[5].replace("\"", "");
                String personality = data[6].replace("\"", "");

                if (!teamName.equals(currentTeam)) {
                    currentTeam = teamName;
                    System.out.println("\n-----------------------------");
                    System.out.println("Team: " + currentTeam);
                    System.out.println("-----------------------------");
                }

                System.out.println("• " + member + " (" + email + ")");
                System.out.println("  Game: " + game);
                System.out.println("  Role: " + role);
                System.out.println("  Skill: " + skill);
                System.out.println("  Personality: " + personality);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

}
