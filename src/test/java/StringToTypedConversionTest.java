import com.github.hugorouillard.dataframe.ConversionUtils;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class StringToTypedConversionTest {

    @Test
    public void testConvertStringListToTypedList_Integers() {
        List<String> input = List.of("123", "456", "789");
        List<?> result = ConversionUtils.convertStringListToTypedList(input);

        assertEquals(3, result.size());
        assertTrue(result.get(0) instanceof Integer);
        assertEquals(123, result.get(0));
        assertTrue(result.get(1) instanceof Integer);
        assertEquals(456, result.get(1));
        assertTrue(result.get(2) instanceof Integer);
        assertEquals(789, result.get(2));
    }

    @Test
    public void testConvertStringListToTypedList_Long() {
        List<String> input = List.of("6545489", "451006565489", "851050656899");
        List<?> result = ConversionUtils.convertStringListToTypedList(input);
        assertTrue(result.get(0) instanceof Long);
        assertTrue(result.get(1) instanceof Long);
        assertTrue(result.get(2) instanceof Long);
    }

    @Test
    public void testConvertStringListToTypedList_Doubles() {
        List<String> input = List.of("3.14", "2.718", "15.2");
        List<?> result = ConversionUtils.convertStringListToTypedList(input);

        assertEquals(3, result.size());
        assertTrue(result.get(0) instanceof Double);
        assertEquals(3.14, (Double)result.get(0), 0.001);
        assertTrue(result.get(1) instanceof Double);
        assertEquals(2.718, (Double)result.get(1), 0.001);
    }

    @Test
    public void testConvertStringListToTypedList_Booleans() {
        List<String> input = List.of("true", "false", "TRUE");
        List<?> result = ConversionUtils.convertStringListToTypedList(input);

        assertEquals(3, result.size());
        assertTrue(result.get(0) instanceof Boolean);
        assertEquals(true, result.get(0));
        assertTrue(result.get(1) instanceof Boolean);
        assertEquals(false, result.get(1));
    }

    @Test
    public void testConvertStringListToTypedList_Strings() {
        List<String> input = List.of("hello", "true", "123");
        List<?> result = ConversionUtils.convertStringListToTypedList(input);

        assertEquals(3, result.size());
        assertTrue(result.get(0) instanceof String);
        assertEquals("hello", result.get(0));
        assertTrue(result.get(1) instanceof String);
        assertEquals("true", result.get(1));
        assertTrue(result.get(2) instanceof String);
        assertEquals("123", result.get(2));
    }

    @Test
    public void testConvertStringListToTypedList_EmptyValue() {
        List<String> input = List.of("", "null", "NULL");
        List<?> result = ConversionUtils.convertStringListToTypedList(input);

        assertEquals(3, result.size());
        assertNull(result.get(0));
    }
}