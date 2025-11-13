package teammate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public List<Participant> loadParticipants(String filePath) {
        List<Participant> participants = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) continue; // skip incomplete lines

                String name = data[0].trim();
                String email = data[1].trim();
                String game = data[2].trim();
                String role = data[3].trim();
                int skill = Integer.parseInt(data[4].trim());
                int score = Integer.parseInt(data[5].trim());

                String personalityType = PersonalityClassifier.classify(score);
                Participant p = new Participant(name, email, game, role, skill, score, personalityType);
                participants.add(p);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number in file: " + e.getMessage());
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

            System.out.println("Teams successfully saved to " + filePath);

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
