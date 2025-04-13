package com.github.hugorouillard.dataframe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Series<V> {
    private List<V> data;
    private String name;

    public Series(List<V> data, String name) {
        this.data = data;
        this.name = name;
    }

    /**
     * Return the mean of the series, only works with numerical series (int, double, long...)
     *
     * @throws IllegalArgumentException if the series is not numerical.
     * @return double mean of the series
     */
    public double mean() {
        if (!isNumerical()) {
            throw new IllegalArgumentException("Series must contain numerical values");
        }
        double sum = 0;
        for (V value : data) {
            sum += ((Number) value).doubleValue();
        }
        return sum / data.size();
    }

    /**
     * Return the median of the series, only works with numerical series (int, double, long...)
     *
     * @throws IllegalArgumentException if the series is not numerical.
     * @return double median of the series
     */
    public double median() {
        if (!isNumerical()) {
            throw new IllegalArgumentException("Series must contain numerical values");
        }

        List<Double> sorted = new ArrayList<>();
        for (V value : data) {
            sorted.add(((Number) value).doubleValue());
        }
        Collections.sort(sorted);
        int size = sorted.size();
        if (size % 2 == 0) {
            return (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0;
        } else {
            return sorted.get(size / 2);
        }
    }

    /**
     * Return the median of the series, only works with numerical series (int, double, long...)
     *
     * @throws IllegalArgumentException if the series is not numerical.
     * @return double median of the series
     */
    public double std() {
        if (!isNumerical()) {
            throw new IllegalArgumentException("Series must contain numerical values");
        }
        double mean = mean();
        double sum_square_diff = 0;
        for (V value : data) {
            double val = ((Number) value).doubleValue();
            sum_square_diff += Math.pow(val - mean, 2);
        }
        return Math.sqrt(sum_square_diff / data.size());
    }

    /**
     * Return the min of the series, only works with numerical series (int, double, long...)
     *
     * @throws IllegalArgumentException if the series is not numerical.
     * @return V the minimum of the list
     */
    public V min() {
        if (!isNumerical()) {
            throw new IllegalArgumentException("Series must contain numerical values");
        }
        double min_value = ((Number) data.get(0)).doubleValue();
        V element = data.get(0);
        for (V value : data) {
            double val = ((Number) value).doubleValue();
            if (val < min_value) {
                min_value = val;
                element = value;
            }
        }
        return element;
    }

    /**
     * Return the min of the series, only works with numerical series (int, double, long...)
     *
     * @throws IllegalArgumentException if the series is not numerical.
     * @return V the maximum of the list
     */
    public V max() {
        if (!isNumerical()) {
            throw new IllegalArgumentException("Series must contain numerical values");
        }
        double min_value = ((Number) data.get(0)).doubleValue();
        V element = data.get(0);
        for (V value : data) {
            double val = ((Number) value).doubleValue();
            if (val > min_value) {
                min_value = val;
                element = value;
            }
        }
        return element;
    }

    private boolean isNumerical() {
        return data.get(0) instanceof Number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public List<V> getData() {
        return this.data;
    }

    public void describe() {
        double count = data.size();
        double mean = mean();
        double std_ = std();
        V min = min();
        V max = max();
        double median = median();

        System.out.println("count \t" + count);
        System.out.println("mean \t" + mean);
        System.out.println("median \t" + median);
        System.out.println("std \t" + std_);
        System.out.println("min \t" + min);
        System.out.println("max \t" + max);

        System.out.println("Name = " + getName() + ", type = " + data.get(0).getClass());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            V value = data.get(i);
            sb.append(i).append("\t").append(value.toString()).append("\n");
        }
        sb.append("Name: ").append(name).append(", type: ").append(data.get(0).getClass()).append("\n");
        return sb.toString();
    }
}
