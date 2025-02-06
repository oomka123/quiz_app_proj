package enums;

public enum QuizCategory {
    GAMES("Games"),
    EDUCATION("Education"),
    GENERAL("General Knowledge"),
    MOVIES("Movies & TV Shows"),
    LANGUAGES("Languages");

    private final String displayName;

    QuizCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static QuizCategory fromInt(int option) {
        return switch (option) {
            case 1 -> GAMES;
            case 2 -> EDUCATION;
            case 3 -> GENERAL;
            case 4 -> MOVIES;
            case 5 -> LANGUAGES;
            default -> null;
        };
    }
}
