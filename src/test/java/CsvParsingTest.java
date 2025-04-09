import org.example.ConversionUtils;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

public class CsvParsingTest {
    @Test
    public void testSimpleCommaSeparated() {
        ArrayList<String> result = ConversionUtils.parseCSVRow("one,two,three", ',');
        assertEquals(3, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));
        assertEquals("three", result.get(2));
    }

    @Test
    public void testQuotedFields() {
        ArrayList<String> result = ConversionUtils.parseCSVRow("\"name\",\"age\",\"city\"", ',');
        assertEquals(3, result.size());
        assertEquals("name", result.get(0));
        assertEquals("age", result.get(1));
        assertEquals("city", result.get(2));
    }

    @Test
    public void testDoubleQuotes() {
        ArrayList<String> result = ConversionUtils.parseCSVRow("\"test \"\"string\"\"\",\"another\"", ',');
        assertEquals(2, result.size());
        assertEquals("test \"string\"", result.get(0));
        assertEquals("another", result.get(1));
    }

    @Test
    public void quotedDelimiter() {
        ArrayList<String> result = ConversionUtils.parseCSVRow("\"test, \"\"string\"\"\",\"another\"", ',');
        assertEquals(2, result.size());
        assertEquals("test, \"string\"", result.get(0));
        assertEquals("another", result.get(1));
    }

    @Test
    public void testMixedQuotedAndUnquoted() {
        ArrayList<String> result = ConversionUtils.parseCSVRow("data,123,\"more \"\"data\"\"\",final", ',');
        assertEquals(4, result.size());
        assertEquals("data", result.get(0));
        assertEquals("123", result.get(1));
        assertEquals("more \"data\"", result.get(2));
        assertEquals("final", result.get(3));
    }

    @Test
    public void testEmptyFields() {
        ArrayList<String> result = ConversionUtils.parseCSVRow("first,,third,", ',');
        assertEquals(4, result.size());
        assertEquals("first", result.get(0));
        assertEquals("", result.get(1));
        assertEquals("third", result.get(2));
        assertEquals("", result.get(3));
    }
}
