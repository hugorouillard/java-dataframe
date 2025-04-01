import org.example.Dataframe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class DataFrameCreationTest {
    private Dataframe df;
    private Path tempFile;

    @Before
    public void setUp() throws IOException {
        tempFile = Files.createTempFile("test", ".csv");
        Files.write(tempFile, Arrays.asList(
                "Name,Age,City",
                "Alice,30,Paris",
                "Bob,25,London",
                "Charlie,35,New York"
        ));
    }

    @After
    public void deleteSetup() throws IOException {
        if (tempFile != null) {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    public void testConstructorWithArrays() {
        df = new Dataframe(
                new String[]{"Alice", "Bob", "Charlie"},
                new int[]{30, 25, 35},
                new String[]{"Paris", "London", "New York"}
        );
        assertNotNull(df);
        assertEquals(3, df.data_tab.length);
        assertEquals("0", df.data_tab[0].getName());
        assertEquals("Alice", df.data_tab[0].getData().get(0));
        assertEquals(25, df.data_tab[1].getData().get(1));
        assertEquals("New York", df.data_tab[2].getData().get(2));
    }

    @Test
    public void testConstructorWithCSV() throws IOException {
        df = new Dataframe(tempFile.toString(), ',');
        assertNotNull(df);
        assertEquals(3, df.data_tab.length);
        assertEquals("Name", df.data_tab[0].getName());
        assertEquals("Alice", df.data_tab[0].getData().get(0));
        assertEquals(25, df.data_tab[1].getData().get(1));
        assertEquals("New York", df.data_tab[2].getData().get(2));
    }

    @Test(expected = IOException.class)
    public void testCSVConstructorWithInvalidFile() throws IOException {
        new Dataframe("missing.csv", ',');
    }

    @Test
    public void testToStringWithData() {
        df = new Dataframe(
                new String[]{"Alice", "Bob"},
                new Integer[]{30, 25}
        );
        String result = df.toString();

        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("30"));
        assertTrue(result.contains("Bob"));
        assertTrue(result.contains("25"));
    }
}
