import org.example.Dataframe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class DataFrameDisplayTest {
    private Dataframe df;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        String[] labels = {"A", "B"};
        Integer[] col1 = {1, 2, 3, 4, 5};
        String[] col2 = {"a", "b", "c", "d", "e"};
        df = new Dataframe(labels, col1, col2);

        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testDisplayFirstLinesContent() {
        df.displayFirstLines(2);

        String output = outContent.toString();
        assertTrue(output.contains("1"));
        assertTrue(output.contains("2"));
        assertTrue(output.contains("a"));
        assertTrue(output.contains("b"));
    }

    @Test
    public void testDisplayLastLinesContent() {
        df.displayLastLines(2);

        String output = outContent.toString();
        assertTrue(output.contains("4"));
        assertTrue(output.contains("5"));
        assertTrue(output.contains("d"));
        assertTrue(output.contains("e"));
    }

    @Test
    public void testToStringWithData() {
        String output = df.toString();
        assertTrue(output.contains("1"));
        assertTrue(output.contains("2"));
        assertTrue(output.contains("a"));
        assertTrue(output.contains("b"));
        assertTrue(output.contains("4"));
        assertTrue(output.contains("5"));
        assertTrue(output.contains("d"));
        assertTrue(output.contains("e"));
        assertTrue(output.contains("c"));
        assertTrue(output.contains("3"));
        assertTrue(output.contains("A"));
        assertTrue(output.contains("B"));
    }
}
