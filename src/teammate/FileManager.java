package teammate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public List<Participant> loadParticipants(String filePath) {
        List<Participant> participants = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                // Proper CSV split handling commas inside quotes
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (data.length < 8) {
                    System.err.println("Skipping incomplete line: " + line);
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

                    String personalityType = PersonalityClassifier.classify(score);

                    Participant p = new Participant(
                            id, name, email, game, skill, role, score, personalityType
                    );

                    participants.add(p);

                } catch (NumberFormatException e) {
                    System.err.println("Skipping line (invalid number): " + line);
                }
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid number in file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return participants;
    }
}