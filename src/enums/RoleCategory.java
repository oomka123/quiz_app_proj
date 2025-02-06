package enums;

public enum RoleCategory {
    ADMIN("Admin"),
    EDITOR("Editor"),
    USER("User");

    private final String displayName;

    RoleCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static RoleCategory fromInt(int option) {
        return switch (option) {
            case 1 -> ADMIN;
            case 2 -> EDITOR;
            case 3 -> USER;
            default -> USER;
        };
    }
}

