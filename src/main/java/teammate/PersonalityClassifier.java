package teammate;

public class PersonalityClassifier {

    /**
     * Classify a personality score that may be on different scales.
     * Accepts scores from 0 up to 100 and returns one of:
     *   Leader, Balanced, Thinker, Reserved
     *
     * Keeps behaviour sensible if someone still provides 5-25.
     */
    public static String classify(int score) {

        // clamp to 0-100 just in case
        if (score < 0) score = 0;
        if (score > 100) score = 100;

        // Map to buckets (0-100 scale)
        if (score >= 80) {
            return "Leader";
        } else if (score >= 60) {
            return "Balanced";
        } else if (score >= 40) {
            return "Thinker";
        } else {
            return "Reserved";
        }
    }
}
