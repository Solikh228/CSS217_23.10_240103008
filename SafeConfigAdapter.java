import java.io.IOException;
import java.sql.SQLException;


interface ConfigService {
    String readKey(String key);
}

class ConfigNotFoundException extends RuntimeException {
    public ConfigNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}


class FlakyConfig {
    public String getRaw(String k) throws IOException, SQLException {
        if ("db.error".equals(k)) {
            throw new SQLException("Connection timeout while reading key: " + k);
        } else if ("io.error".equals(k)) {
            throw new IOException("Failed to access configuration file for key: " + k);
        }
        return "ValueFor_" + k;
    }
}


public class SafeConfigAdapter implements ConfigService {
    private final FlakyConfig legacyConfig;

    public SafeConfigAdapter(FlakyConfig legacyConfig) {
        this.legacyConfig = legacyConfig;
    }

    @Override
    public String readKey(String key) {
        try {
            return legacyConfig.getRaw(key);
        } catch (IOException | SQLException e) {
            throw new ConfigNotFoundException("Unable to read config key: " + key, e);
        }
    }

    public static void main(String[] args) {
        String studentId = "240103008";
        System.out.println("Student ID: " + studentId);

        FlakyConfig flakyConfig = new FlakyConfig();
        ConfigService configService = new SafeConfigAdapter(flakyConfig);

        
        String workingKey = "app.env";
        String value = configService.readKey(workingKey);
        System.out.println("Successfully read [" + workingKey + "]: " + value);

        
        String failingKey = "db.error";
        try {
            configService.readKey(failingKey);
        } catch (ConfigNotFoundException e) {
            System.out.println("Caught ConfigNotFoundException: " + e.getMessage());
            System.out.println("Underlying cause: " + e.getCause().getClass().getName());
        }
    }
}