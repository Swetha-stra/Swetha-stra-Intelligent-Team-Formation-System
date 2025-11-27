package teammate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public List<Participant> loadParticipants(String filePath) {

        List<Participant> participants = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                // --- FIXED: Parse CSV safely (supports quotes, commas, etc) ---
                List<String> cells = parseCSVLine(line);

                if (cells.size() < 8) {
                    System.err.println("⚠ Skipping incomplete row: " + line);
                    continue;
                }

                try {
                    String id = cells.get(0).trim();
                    String name = cells.get(1).trim();
                    String email = cells.get(2).trim();

                    String game = normalizeGame(cells.get(3).trim());
                    int skill = Integer.parseInt(cells.get(4).trim());

                    String role = normalizeRole(cells.get(5).trim());
                    int score = Integer.parseInt(cells.get(6).trim());

                    String personalityType = PersonalityClassifier.classify(score);

                    Participant p = new Participant(
                            id, name, email, game, skill, role, score, personalityType
                    );

                    participants.add(p);

                } catch (Exception e) {
                    System.err.println("⚠ Error parsing: " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return participants;
    }

    // ===========================================
    // SAFE CSV PARSER
    // ===========================================
    private List<String> parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        result.add(sb.toString());
        return result;
    }

    // ===========================================
    // NORMALIZATION FUNCTIONS
    // ===========================================
    private String normalizeGame(String g) {
        g = g.replace("\"", "").trim().toLowerCase();
        switch (g) {
            case "chess": return "Chess";
            case "fifa": return "FIFA";
            case "basketball": return "Basketball";
            case "cs:go":
            case "csgo": return "CS:GO";
            case "dota":
            case "dota2":
            case "dota 2": return "DOTA 2";
            case "valorant": return "Valorant";
            default: return "Unknown"; // Never break team formation
        }
    }

    private String normalizeRole(String r) {
        r = r.replace("\"", "").trim().toLowerCase();
        switch (r) {
            case "strategist": return "Strategist";
            case "attacker": return "Attacker";
            case "defender": return "Defender";
            case "supporter": return "Supporter";
            case "coordinator": return "Coordinator";
            default: return "Supporter";
        }
    }

    // ===========================================
    // SAVE TEAMS
    // ===========================================
    public void saveTeams(String filePath, List<Team> teams) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("Team Name,Member Name,Email,Game,Role,Skill,Personality\n");

            for (Team t : teams) {
                for (Participant p : t.getMembers()) {
                    writer.write(String.format("%s,%s,%s,%s,%s,%d,%s\n",
                            t.getTeamName(),
                            p.getName(),
                            p.getEmail(),
                            p.getGamePreference(),
                            p.getRole(),
                            p.getSkillLevel(),
                            p.getPersonalityType()));
                }
            }

            System.out.println("✔ Teams saved to: " + filePath);

        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
