package definitions;

/**
 * Created by admin on 11/16/15.
 *
 * Type of activities kYOU does
 */
public enum kYouActivities {
    // DONT ADD NEW EVENTS BEYOND THE "NONE" ONE. IF YES, UPDATE suggestionLength method
    SUGGESTWALK(0), SUGGESTMUSIC(1), SUGGESTABOOK(2), SUGGESTSKYPE(3), NONE(100), CHECKMOOD(1000);

    private final int value;

    kYouActivities(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static int numberOfPotentialSuggestions(){
         return (values().length) - 2;
    }
}

