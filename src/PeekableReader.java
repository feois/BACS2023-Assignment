import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

@SuppressWarnings("SpellCheckingInspection")
public class PeekableReader {
    private final BufferedReader reader;
    private String peeked = null;

    public PeekableReader(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    public String readLine() throws IOException {
        if (peeked != null) {
            var s = peeked;

            peeked = null;

            return s;
        }

        return reader.readLine();
    }

    public String peekLine() throws IOException {
        if (peeked == null) {
            peeked = reader.readLine();
        }

        return peeked;
    }

    public boolean hasLine() throws IOException {
        return peekLine() != null && !peekLine().isBlank();
    }

    public void close() throws IOException {
        reader.close();
    }
}
