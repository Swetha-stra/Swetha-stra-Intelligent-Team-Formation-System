package teammate;
public class PersonalityClassifier {

    public static String classify(int score) {

        if (score >= 20 && score <= 25) {
            return "Leader";
        }
        else if (score >= 15 && score <= 19) {
            return "Balanced";
        }
        else if (score >= 10 && score <= 14) {
            return "Thinker";
        }
        else if (score >= 5 && score <= 9) {
            return "Reserved";
        }
        else {
            return "Invalid Score";
        }
    }
}
