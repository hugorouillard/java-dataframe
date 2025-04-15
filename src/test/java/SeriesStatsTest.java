import com.github.hugorouillard.dataframe.Series;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class SeriesStatsTest {

    @Test
    public void testMean() {
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        Series<Integer> series = new Series<>(data, "TestSeries");
        assertEquals(3.0, series.mean(), 0.0001);
    }

    @Test
    public void testMedian1() {
        List<Integer> data = Arrays.asList(5, 1, 3);
        Series<Integer> series = new Series<>(data, "MedianOdd");
        assertEquals(3.0, series.median(), 0.0001);
    }

    @Test
    public void testMedian2() {
        List<Integer> data = Arrays.asList(1, 4, 5, 10);
        Series<Integer> series = new Series<>(data, "MedianOdd");
        assertEquals(4.5, series.median(), 0.0001);
    }

    @Test
    public void testMedianEven() {
        List<Integer> data = Arrays.asList(4, 2, 1, 3);
        Series<Integer> series = new Series<>(data, "MedianEven");
        assertEquals(2.5, series.median(), 0.0001);
    }

    @Test
    public void testStd() {
        List<Integer> data = Arrays.asList(2, 4, 4, 4, 5, 5, 7, 9);
        Series<Integer> series = new Series<>(data, "StdTest");
        assertEquals(2.0, series.std(), 0.0001);
    }

    @Test
    public void testMin() {
        List<Double> data = Arrays.asList(1.1, 2.2, -3.3, 4.4);
        Series<Double> series = new Series<>(data, "MinTest");
        assertEquals(-3.3, series.min(), 0.0001);
    }

    @Test
    public void testMax() {
        List<Long> data = Arrays.asList(10L, 20L, 30L, 5L);
        Series<Long> series = new Series<>(data, "MaxTest");
        assertEquals(30.0, series.max(), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMeanNonNumerical() {
        List<String> data = Arrays.asList("a", "b", "c");
        Series<String> series = new Series<>(data, "NonNumerical");
        series.mean();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMedianNonNumerical() {
        List<String> data = Arrays.asList("a", "b", "c");
        Series<String> series = new Series<>(data, "NonNumerical");
        series.median();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStdNonNumerical() {
        List<String> data = Arrays.asList("a", "b", "c");
        Series<String> series = new Series<>(data, "NonNumerical");
        series.std();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinNonNumerical() {
        List<String> data = Arrays.asList("a", "b", "c");
        Series<String> series = new Series<>(data, "NonNumerical");
        series.min();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxNonNumerical() {
        List<String> data = Arrays.asList("a", "b", "c");
        Series<String> series = new Series<>(data, "NonNumerical");
        series.max();
    }
}
