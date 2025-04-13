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

    public double std() {
        if (!isNumerical()) {
            throw new IllegalArgumentException("Series must contain numerical values");
        }
        double mean = mean();
        double sumSquaredDiffs = 0;
        for (V value : data) {
            double val = ((Number) value).doubleValue();
            sumSquaredDiffs += Math.pow(val - mean, 2);
        }
        return Math.sqrt(sumSquaredDiffs / data.size());
    }

    public double min() {
        if (!isNumerical()) {
            throw new IllegalArgumentException("Series must contain numerical values");
        }
        double min = ((Number) data.get(0)).doubleValue();
        for (V value : data) {
            double val = ((Number) value).doubleValue();
            if (val < min) {
                min = val;
            }
        }
        return min;
    }

    public double max() {
        if (!isNumerical()) {
            throw new IllegalArgumentException("Series must contain numerical values");
        }
        double max = ((Number) data.get(0)).doubleValue();
        for (V value : data) {
            double val = ((Number) value).doubleValue();
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    private boolean isNumerical() {
        return data.get(0) instanceof Double || data.get(0) instanceof Float || data.get(0) instanceof Integer || data.get(0) instanceof Long;
    }

    public void setData(List<V> data) {
        this.data = data;
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
