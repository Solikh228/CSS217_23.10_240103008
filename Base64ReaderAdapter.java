import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class Base64ReaderAdapter extends Reader {
    private final Reader decodedReader;

    public Base64ReaderAdapter(InputStream in) {
  
        InputStream decodingStream = Base64.getDecoder().wrap(in);
        this.decodedReader = new InputStreamReader(decodingStream, StandardCharsets.UTF_8);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return decodedReader.read(cbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        decodedReader.close();
    }

    public static void main(String[] args) throws IOException {
        String studentId = "240103008";
        System.out.println("Student ID: " + studentId);

        
        byte[] encodedBytes = Base64.getEncoder().encode(studentId.getBytes(StandardCharsets.UTF_8));
        ByteArrayInputStream bais = new ByteArrayInputStream(encodedBytes);

        try (BufferedReader reader = new BufferedReader(new Base64ReaderAdapter(bais))) {
            String decodedString = reader.readLine();
            System.out.println("Decoded String: " + decodedString);

            boolean isMatch = studentId.equals(decodedString);
            System.out.println("Assertion passed (decoded == Student ID): " + isMatch);

            assert isMatch : "Decoded string does not match original Student ID!";
        }
    }
}