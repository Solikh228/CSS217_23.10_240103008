interface TextMessage {
    String getContent();
}

public class SimpleTextMessage implements TextMessage {
    private final String content;

    public SimpleTextMessage(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    public static void main(String[] args) {
        String studentId = "240103008";
        System.out.println("Student ID: " + studentId);


        String rawText = "Alert: 5 < 10 & 2 > 1";
        TextMessage baseMessage = new SimpleTextMessage(rawText);

        
        TextMessage decoratedMessage = new WatermarkDecorator(
                new HtmlEscapingDecorator(baseMessage),
                studentId
        );

        System.out.println("Original:  " + baseMessage.getContent());
        System.out.println("Decorated: " + decoratedMessage.getContent());
    }
}


abstract class TextMessageDecorator implements TextMessage {
    protected final TextMessage wrapped;

    public TextMessageDecorator(TextMessage wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getContent() {
        return wrapped.getContent();
    }
}


class HtmlEscapingDecorator extends TextMessageDecorator {

    public HtmlEscapingDecorator(TextMessage wrapped) {
        super(wrapped);
    }

    @Override
    public String getContent() {
        String text = super.getContent();
        if (text == null) {
            return null;
        }

        
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;");
    }
}


class WatermarkDecorator extends TextMessageDecorator {
    private final String studentId;

    public WatermarkDecorator(TextMessage wrapped, String studentId) {
        super(wrapped);
        this.studentId = studentId;
    }

    @Override
    public String getContent() {
        return super.getContent() + " [Author: " + studentId + "]";
    }
}