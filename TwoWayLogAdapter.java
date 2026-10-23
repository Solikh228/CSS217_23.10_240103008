interface ModernConsole {
    void logMessage(String tag, String body);
}
interface LegacyPrinter {
    void printLine(String combinedText);
}
public class TwoWayLogAdapter implements ModernConsole, LegacyPrinter {

    @Override
    public void logMessage(String tag, String body) {
        
        System.out.println("[" + tag + "] " + body);
    }
    @Override
    public void printLine(String combinedText) {
        
    if (combinedText != null && combinedText.startsWith("[") && combinedText.contains("] ")) {
            int closeBracketIndex = combinedText.indexOf("] ");
            String tag = combinedText.substring(1, closeBracketIndex);
            String body = combinedText.substring(closeBracketIndex + 2);

            logMessage(tag, body);
        } else {
            System.out.println(combinedText);
        }
    }
    public static void main(String[] args) {
        String studentId = "240103008";
        System.out.println("Student ID: " + studentId);

        TwoWayLogAdapter adapter = new TwoWayLogAdapter();
        ModernConsole modern = adapter;
        modern.logMessage(studentId, "Message sent via ModernConsole interface");

    
        LegacyPrinter legacy = adapter;
        legacy.printLine("[" + studentId + "] Message sent via LegacyPrinter interface");
    }
}
