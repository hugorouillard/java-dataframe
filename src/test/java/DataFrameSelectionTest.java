import com.github.hugorouillard.dataframe.Dataframe;
import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.*;

public class DataFrameSelectionTest {

    @Test
    public void testSelectRowsRange() {
        Dataframe df = new Dataframe(new String[]{"Name", "Age"},
                new String[]{"Alice", "Bob", "Charlie", "Dora"},
                new int[]{22, 25, 30, 27});

        Dataframe result = df.selectRowsRange(1, 3); // Bob & Charlie

        assertEquals(2, result.getDataTab()[0].getData().size());
        assertEquals("Bob", result.getDataTab()[0].getData().get(0));
        assertEquals("Charlie", result.getDataTab()[0].getData().get(1));
        assertEquals(25, result.getDataTab()[1].getData().get(0));
        assertEquals(30, result.getDataTab()[1].getData().get(1));
    }

    @Test
    public void testSelectRowsIndices() {
        Dataframe df = new Dataframe(new String[]{"Name", "Age"},
                new String[]{"Alice", "Bob", "Charlie", "Dora"},
                new int[]{22, 25, 30, 27});

        Dataframe result = df.selectRows(0, 3); // Alice & Dora

        assertEquals(2, result.getDataTab()[0].getData().size());
        assertEquals("Alice", result.getDataTab()[0].getData().get(0));
        assertEquals("Dora", result.getDataTab()[0].getData().get(1));
    }

    @Test
    public void testSelectColumns() {
        Dataframe df = new Dataframe(new String[]{"Name", "Age", "Score"},
                new String[]{"Alice", "Bob", "Charlie"},
                new int[]{22, 25, 30},
                new double[]{10.5, 9.8, 12.0});

        Dataframe result = df.selectColumns("Name", "Score");

        assertEquals(2, result.getDataTab().length);
        assertEquals("Name", result.getDataTab()[0].getName());
        assertEquals("Score", result.getDataTab()[1].getName());
        assertEquals("Alice", result.getDataTab()[0].getData().get(0));
        assertEquals(12.0, (Double) result.getDataTab()[1].getData().get(2), 0.001);
    }

    @Test
    public void testFilterRowsWithCast() {
        Dataframe df = new Dataframe(new String[]{"Name", "Age"},
                new String[]{"Alice", "Bob", "Charlie", "Dora"},
                new Integer[]{22, 25, 30, 27});

        Predicate<Object> olderThan25 = val -> ((Integer) val) > 25;

        Dataframe result = df.filterRows("Age", olderThan25);

        assertEquals(2, result.getDataTab()[0].getData().size());
        assertEquals("Charlie", result.getDataTab()[0].getData().get(0));
        assertEquals("Dora", result.getDataTab()[0].getData().get(1));
        assertEquals(30, result.getDataTab()[1].getData().get(0));
        assertEquals(27, result.getDataTab()[1].getData().get(1));
    }

    @Test
    public void testFilterRowsByNameStartsWith() {
        Dataframe df = new Dataframe(new String[]{"Name", "Age"},
                new String[]{"Alice", "Bob", "Anna", "Dora"},
                new Integer[]{22, 25, 23, 27});

        Predicate<Object> startsWithA = val -> ((String) val).startsWith("A");

        Dataframe result = df.filterRows("Name", startsWithA);

        assertEquals(2, result.getDataTab()[0].getData().size());
        assertEquals("Alice", result.getDataTab()[0].getData().get(0));
        assertEquals("Anna", result.getDataTab()[0].getData().get(1));
        assertEquals(22, result.getDataTab()[1].getData().get(0));
        assertEquals(23, result.getDataTab()[1].getData().get(1));
    }
}
