package teammate;

public class Participant {

    private String id;
    private String name;
    private String email;
    private String gamePreference;
    private int skillLevel;
    private String role;
    private int personalityScore;
    private String personalityType;

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

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s | %d | %s (%d)",
                id, name, email, gamePreference, role, skillLevel,
                personalityType, personalityScore);
    }
}
