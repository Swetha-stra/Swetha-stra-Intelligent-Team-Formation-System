package teammate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public List<Participant> loadParticipants(String filePath) {

        List<Participant> participants = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine(); // Skip header row

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                // Split correctly even if commas appear inside quotes
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (data.length < 8) {
                    System.err.println("⚠ Skipping incomplete row: " + line);
                    continue;
                }

                try {
                    String id = data[0].trim();
                    String name = data[1].trim();
                    String email = data[2].trim();
                    String game = data[3].trim();

                    int skill = Integer.parseInt(data[4].trim());
                    String role = data[5].trim();
                    int score = Integer.parseInt(data[6].trim());

                    // -------------------------------
                    // VALIDATION LOGIC
                    // -------------------------------
                    if (skill < 1 || skill > 10) {
                        System.out.println("⚠ Invalid skill level. Adjusted to nearest valid: " + line);
                        skill = Math.max(1, Math.min(skill, 10));
                    }

                    if (!email.contains("@")) {
                        System.out.println("⚠ Invalid email. Skipped: " + line);
                        continue;
                    }

                    if (role.isEmpty() || role.equals("-")) {
                        role = "Supporter"; // default role
                    }

                    // Classify personality even if score > 25
                    String personalityType = PersonalityClassifier.classify(score);

                    Participant participant = new Participant(
                            id, name, email, game, skill, role, score, personalityType
                    );

                    participants.add(participant);

                } catch (NumberFormatException e) {
                    System.err.println("⚠ Number format error. Skipped: " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("❌ Error reading file: " + e.getMessage());
        }

        return participants;
    }

    // ----------------------------------------------------
    // SAVE TEAMS INTO CSV
    // ----------------------------------------------------
    public void saveTeams(String filePath, List<Team> teams) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("Team Name,Member Name,Email,Game,Role,Skill,Personality\n");

            for (Team team : teams) {
                for (Participant p : team.getMembers()) {
                    writer.write(String.format("%s,%s,%s,%s,%s,%d,%s\n",
                            team.getTeamName(),
                            p.getName(),
                            p.getEmail(),
                            p.getGamePreference(),
                            p.getRole(),
                            p.getSkillLevel(),
                            p.getPersonalityType()));
                }
            }

            System.out.println("✔ Teams successfully saved to: " + filePath);

        } catch (IOException e) {
            System.err.println("❌ Error writing file: " + e.getMessage());
        }
    }
}
