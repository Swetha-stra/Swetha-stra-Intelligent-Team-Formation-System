package teammate;

import java.util.Arrays;

public class Participant {
    // Core fields (match what FileManager expects)
    private String name;
    private String email;
    private String gamePreference;   // getGamePreference()
    private String role;             // getRole()
    private int skillLevel;          // getSkillLevel()
    private int personalityScore;    // raw score (e.g. 20..100)
    private String personalityType;  // getPersonalityType()

    // Constructor used by FileManager: (name, email, game, role, skill, score, personalityType)
    public Participant(String name, String email, String gamePreference,
                       String role, int skillLevel, int personalityScore, String personalityType) {
        this.name = name;
        this.email = email;
        this.gamePreference = gamePreference;
        this.role = role;
        this.skillLevel = skillLevel;
        this.personalityScore = personalityScore;
        this.personalityType = personalityType;
    }

    // Alternate simpler constructor if you need it later
    public Participant(String name, String email, String gamePreference, String role) {
        this(name, email, gamePreference, role, 3, 60, "Balanced");
    }

    // Getters (these are the ones your other classes call)
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGamePreference() {
        return gamePreference;
    }

    public String getRole() {
        return role;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getPersonalityScore() {
        return personalityScore;
    }

    public String getPersonalityType() {
        return personalityType;
    }

    // Setters (useful for later changes)
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGamePreference(String gamePreference) {
        this.gamePreference = gamePreference;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public void setPersonalityScore(int personalityScore) {
        this.personalityScore = personalityScore;
    }

    public void setPersonalityType(String personalityType) {
        this.personalityType = personalityType;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s | Role: %s | Skill: %d | Personality: %s (%d)",
                name, email, gamePreference, role, skillLevel, personalityType, personalityScore);
    }
}

