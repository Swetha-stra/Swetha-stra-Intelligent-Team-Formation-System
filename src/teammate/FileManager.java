package teammate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public List<Participant> loadParticipants(String filePath) {
        List<Participant> participants = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                if (data.length < 8) {
                    System.err.println("Skipping incomplete line: " + line);
                    continue;
                }

                String id = data[0].trim();
                String name = data[1].trim();
                String email = data[2].trim();
                String game = data[3].trim();
                int skill = Integer.parseInt(data[4].trim());
                String role = data[5].trim();
                int score = Integer.parseInt(data[6].trim());
                String personalityType = data[7].trim();

                Participant p = new Participant(
                        id,
                        name,
                        email,
                        game,
                        skill,
                        role,
                        score,
                        personalityType
                );

                participants.add(p);
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid number in file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
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
