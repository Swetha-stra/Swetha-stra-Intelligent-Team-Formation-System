package teammate.teammate;

public class Participant {

    private String id;                // P001, P002...
    private String name;              // Participant_1
    private String email;             // user1@university.edu
    private String gamePreference;    // Chess, FIFA, etc.
    private int skillLevel;           // 1–10
    private String role;              // Strategist, Attacker, Defender...
    private int personalityScore;     // 0–100
    private String personalityType;   // Leader, Balanced, Thinker...

    // Constructor for FileManager
    public Participant(String id, String name, String email,
                       String gamePreference, int skillLevel,
                       String role, int personalityScore,
                       String personalityType) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.gamePreference = gamePreference;
        this.skillLevel = skillLevel;
        this.role = role;
        this.personalityScore = personalityScore;
        this.personalityType = personalityType;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getGamePreference() { return gamePreference; }
    public int getSkillLevel() { return skillLevel; }
    public String getRole() { return role; }
    public int getPersonalityScore() { return personalityScore; }
    public String getPersonalityType() { return personalityType; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setGamePreference(String gamePreference) { this.gamePreference = gamePreference; }
    public void setSkillLevel(int skillLevel) { this.skillLevel = skillLevel; }
    public void setRole(String role) { this.role = role; }
    public void setPersonalityScore(int personalityScore) { this.personalityScore = personalityScore; }
    public void setPersonalityType(String personalityType) { this.personalityType = personalityType; }

    @Override
    public String toString() {
        return String.format(
                "%s | %s (%s)\nGame: %s | Role: %s | Skill: %d\nPersonality: %s (%d)\n",
                id, name, email,
                gamePreference, role, skillLevel,
                personalityType, personalityScore
        );
    }
}
