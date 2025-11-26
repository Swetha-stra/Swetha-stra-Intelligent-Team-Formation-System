package teammate;

import java.io.*;
import java.util.*;

public class FileManager {

    public List<Participant> loadParticipants(String filePath) {

        List<Participant> participants = new ArrayList<>();
        Set<String> seenEmails = new HashSet<>();
        Set<String> seenIDs = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

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
                    int personalityScore = Integer.parseInt(data[6].trim());
                    String personalityType = data[7].trim();

                    // Skip duplicates
                    if (seenEmails.contains(email) || seenIDs.contains(id)) {
                        System.out.println("⚠ Duplicate participant skipped: " + name + " (" + email + ")");
                        continue;
                    }

                    seenEmails.add(email);
                    seenIDs.add(id);

                    // Validation
                    if (skill < 1 || skill > 10) {
                        System.out.println("⚠ Invalid skill level. Skipped: " + line);
                        continue;
                    }

                    if (personalityScore < 5 || personalityScore > 25) {
                        System.out.println("⚠ Invalid personality score. Skipped: " + line);
                        continue;
                    }

                    if (!email.contains("@")) {
                        System.out.println("⚠ Invalid email. Skipped: " + line);
                        continue;
                    }

                    if (role.isEmpty() || role.equals("-")) {
                        System.out.println("⚠ Invalid role. Skipped: " + line);
                        continue;
                    }

                    // Automatically classify personality if type is invalid
                    if (personalityType.equalsIgnoreCase("Invalid Score")) {
                        personalityType = PersonalityClassifier.classify(personalityScore);
                    }

                    Participant participant = new Participant(
                            id, name, email, game, skill, role, personalityScore, personalityType
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
