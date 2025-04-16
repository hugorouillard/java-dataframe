import com.github.hugorouillard.dataframe.Dataframe;
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

    @Test
    public void testSeriesDisplay() {
        String output = df.getDataTab()[0].toString();
        assertTrue(output.contains("A"));
        assertTrue(output.contains("1"));
        assertTrue(output.contains("2"));
        assertTrue(output.contains("3"));
        assertTrue(output.contains("4"));
        assertTrue(output.contains("5"));
    }

    @Test
    public void testDescribeDisplaySeries() {
        df.getDataTab()[0].describe();
        String output = outContent.toString();

        assertTrue(output.contains("5")); // count / max
        assertTrue(output.contains("3.0")); // mean
        assertTrue(output.contains("1.41")); // std
        assertTrue(output.contains("1")); // min
    }

    @Test
    public void testDescribeDisplayDataframe() {
        df.describe();
        String output = outContent.toString();
        assertTrue(output.contains("A"));
        assertTrue(output.contains("5")); // count / max
        assertTrue(output.contains("3")); // mean
        assertTrue(output.contains("1.4")); // std
        assertTrue(output.contains("1")); // min
    }
}
