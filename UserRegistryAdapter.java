record UserProfile(String studentId, String email, int accessLevel) {}


interface UserRegistry {
    UserProfile fetchUser();
}

class LegacyUserFeed {
    private String rawData;

    public LegacyUserFeed(String rawData) {
        this.rawData = rawData;
    }

    public String getRawRecord() {
        return rawData;
    }
}

public class UserRegistryAdapter implements UserRegistry {
    private LegacyUserFeed feed;

    public UserRegistryAdapter(LegacyUserFeed feed) {
        this.feed = feed;
    }

    @Override
    public UserProfile fetchUser() {
        String raw = feed.getRawRecord();
        if (raw == null) {
            throw new IllegalArgumentException("Raw record cannot be null");
        }

        String[] parts = raw.split("#");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid format. Expected 3 fields separated by '#', got: " + raw);
        }

        String studentId = parts[0].trim();
        String email = parts[1].trim();

        int accessLevel;
        try {
            accessLevel = Integer.parseInt(parts[2].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Access level must be an integer: " + parts[2], e);
        }

        return new UserProfile(studentId, email, accessLevel);
    }

    public static void main(String[] args) {
        String studentId = "240103008";
        System.out.println("Student ID: " + studentId);

        String rawRecord = studentId + "#user@college.edu#4";
        LegacyUserFeed feed = new LegacyUserFeed(rawRecord);
        UserRegistry registry = new UserRegistryAdapter(feed);

        UserProfile profile = registry.fetchUser();
        System.out.println("Resulting Profile: " + profile);
    }
}