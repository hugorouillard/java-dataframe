import com.github.hugorouillard.dataframe.Dataframe;
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
                "", // testing an empty line
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
    public void testConstructorWithArrays1() {
        df = new Dataframe(
                new String[]{"Nom", "Age", "Ville"}, // Specific labels
                new String[]{"Alice", "Bob", "Charlie"},
                new int[]{30, 25, 35},
                new String[]{"Paris", "London", "New York"}
        );
        assertNotNull(df);
        assertEquals(3, df.getDataTab().length);
        assertEquals("Alice", df.getDataTab()[0].getData().get(0));
        assertEquals(25, df.getDataTab()[1].getData().get(1));
        assertEquals("New York", df.getDataTab()[2].getData().get(2));
        assertEquals("Ville", df.getDataTab()[2].getName());
        assertEquals("Age", df.getDataTab()[1].getName());
    }

    @Test
    public void testConstructorWithArrays2() {
        df = new Dataframe(
                new String[]{}, // Defaults labels
                new float[]{1.25F, 1.2F, 15.5F},
                new long[]{1524856122, 1235478915, 458932346},
                new int[]{30, 25, 35},
                new double[]{1.2556, 1.24548, 1.65478},
                new boolean[]{true, false, true}
        );
        assertNotNull(df);
        assertEquals(5, df.getDataTab().length);
        assertEquals(1.25, (double)df.getDataTab()[0].getData().get(0), 0.0001);
        assertEquals(1.2, (double)df.getDataTab()[0].getData().get(1), 0.0001);
        assertEquals(15.5, (double)df.getDataTab()[0].getData().get(2), 0.0001);
        assertEquals(1524856122L, df.getDataTab()[1].getData().get(0));
        assertEquals(1235478915L, df.getDataTab()[1].getData().get(1));
        assertEquals(458932346L, df.getDataTab()[1].getData().get(2));
        assertEquals(30, df.getDataTab()[2].getData().get(0));
        assertEquals(25, df.getDataTab()[2].getData().get(1));
        assertEquals(35, df.getDataTab()[2].getData().get(2));
        assertEquals(1.2556, (double) df.getDataTab()[3].getData().get(0), 0.0001);
        assertEquals(1.24548, (double) df.getDataTab()[3].getData().get(1), 0.0001);
        assertEquals(1.65478, (double) df.getDataTab()[3].getData().get(2), 0.0001);
        assertEquals(true, df.getDataTab()[4].getData().get(0));
        assertEquals(false, df.getDataTab()[4].getData().get(1));
        assertEquals(true, df.getDataTab()[4].getData().get(2));
        assertEquals("0", df.getDataTab()[0].getName());
        assertEquals("1", df.getDataTab()[1].getName());
        assertEquals("2", df.getDataTab()[2].getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArrayConstructorInvalidArgs1() throws IOException {
        new Dataframe(new String[]{"A", "B"}, // not enough labels for 3 columns
                new float[]{1.25F, 1.2F, 15.5F},
                new long[]{1524856122, 1235478915, 458932346},
                new int[]{30, 25, 35});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArrayConstructorInvalidArgs2() throws IOException {
        new Dataframe(new String[]{"A", "B", "C"},
                new float[]{1.25F, 1.2F, 15.5F},
                new long[]{1524856122, 1235478915}, // not the same size as the others arrays
                new int[]{30, 25, 35});
    }

    @Test
    public void testConstructorWithCSV() throws IOException {
        df = new Dataframe(tempFile.toString(), ',');
        assertNotNull(df);
        assertEquals(3, df.getDataTab().length);
        assertEquals("Name", df.getDataTab()[0].getName());
        assertEquals("Alice", df.getDataTab()[0].getData().get(0));
        assertEquals(25, df.getDataTab()[1].getData().get(1));
        assertEquals("New York", df.getDataTab()[2].getData().get(2));
    }

    @Test(expected = IOException.class)
    public void testCSVConstructorWithInvalidFile() throws IOException {
        new Dataframe("missing.csv", ',');
    }
}
