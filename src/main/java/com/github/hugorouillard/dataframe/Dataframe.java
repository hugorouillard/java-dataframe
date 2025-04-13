package com.github.hugorouillard.dataframe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

/**
 * Represents a dataframe structure containing multiple series of data.
 */
public class Dataframe {
    private Series<?>[] data_tab;
    /**
     * Constructs a Dataframe from variable arguments where each argument represents a column of data.
     * Columns are labelled by the labels array.
     *
     * @param labels array of string, corresponding to each column label. If empty it uses default labels = 1,2,3...
     * @param input_series Set of multiple array that will be used as column in the Dataframe
     * @throws IllegalArgumentException if labels length does not correspond to the amount of series.
     */
    public Dataframe(String [] labels, Object ...input_series) {
        data_tab = new Series[input_series.length];
        if (labels.length != input_series.length && labels.length != 0) {
            throw new IllegalArgumentException("Input series and labels must have the same length or labels needs to be empty to use default labels");
        }

        for (int  i = 0; i < input_series.length; i++) {
            List<?> column_data_list = ConversionUtils.convertArrayToList(input_series[i]);
            data_tab[i] = new Series<>(column_data_list, String.valueOf(i));
            if (i > 0 && data_tab[i - 1].getData().size() != data_tab[i].getData().size()) {
                throw new IllegalArgumentException("Lists or arrays must be the same size");
            }
            if (labels.length == input_series.length) {
                data_tab[i].setName(labels[i]);
            }
        }
    }

    /**
     * Constructs a Dataframe by reading data from a CSV file.
     * The first row of the CSV file is treated as column labels.
     * Subsequent rows are treated as data values for each column.
     *
     * @param csv_file Path to the CSV file to read
     * @param delimiter Character used as field delimiter in the CSV file
     * @throws IOException If there's an error reading the file
     * @throws IllegalArgumentException If the CSV file is empty or improperly formatted
     */
    public Dataframe(String csv_file, char delimiter) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csv_file))) {
            String line;
            ArrayList<String> values;

            ArrayList<String>[] data_columns = null;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                values = ConversionUtils.parseCSVRow(line, delimiter);
                if (data_columns == null) {
                    data_columns = new ArrayList[values.size()];
                }

                for (int i = 0; i < values.size(); i++) {
                    if (data_columns[i] == null) {
                        data_columns[i] = new ArrayList<>();
                    }
                    data_columns[i].add(values.get(i));
                }
            }
            data_tab = new Series[data_columns.length];
            for (int i = 0; i < data_columns.length; i++) {
                data_tab[i] = new Series<>(ConversionUtils.convertStringListToTypedList(data_columns[i].subList(1, data_columns[i].size())),data_columns[i].get(0));
            }
        }
    }

    public void displayFirstLines(int linesAmount) {
        Object[] subArrays = new Object[data_tab.length];
        for (int i = 0; i < data_tab.length; i++) {
            Series<?> s = data_tab[i];
            subArrays[i] = s.getData().subList(0, Math.min(linesAmount, s.getData().size())).toArray();
        }
        System.out.println(new Dataframe(getLabels(), subArrays));
    }

    public void displayLastLines(int linesAmount) {
        Object[] subArrays = new Object[data_tab.length];
        for (int i = 0; i < data_tab.length; i++) {
            Series<?> s = data_tab[i];
            subArrays[i] = s.getData().subList(Math.max(0, s.getData().size() - linesAmount), s.getData().size()).toArray();
        }
        System.out.println(new Dataframe(getLabels(), subArrays));
    }

    public String[] getLabels() {
        String[] labels = new String[data_tab.length];
        for (int i = 0; i < data_tab.length; i++) {
            labels[i] = data_tab[i].getName();
        }
        return labels;
    }

    public Series<?>[] getDataTab() {
        return data_tab;
    }

    /**
     * Generates descriptive statistics for the dataframe.
     * Similar to pandas describe() function, this method computes various statistics
     * for each numerical column in the dataframe.
     *
     * @return A string representation of descriptive statistics in tabular format
     */
    public void describe() {
        List<Series<?>> numericalSeries = new ArrayList<>();
        for (Series<?> series : data_tab) {
            if (!series.getData().isEmpty() && series.getData().get(0) instanceof Number) {
                numericalSeries.add(series);
            }
        }

        if (numericalSeries.isEmpty()) {
            System.out.println("No numerical columns to describe");
            return;
        }

        String[] stats = {"count", "mean", "std", "median", "min", "max"};
        StringBuilder sb = new StringBuilder();

        int[] columnWidths = new int[numericalSeries.size()];
        for (int i = 0; i < numericalSeries.size(); i++) {
            columnWidths[i] = numericalSeries.get(i).getName().length();

            double count = numericalSeries.get(i).getData().size();
            double mean = numericalSeries.get(i).mean();
            double std = numericalSeries.get(i).std();
            double median = numericalSeries.get(i).median();
            Object min = ((Series<? extends Number>) numericalSeries.get(i)).min();
            Object max = ((Series<? extends Number>) numericalSeries.get(i)).max();

            columnWidths[i] = Math.max(columnWidths[i], String.valueOf(count).length());
            columnWidths[i] = Math.max(columnWidths[i], String.format("%.6f", mean).trim().length());
            columnWidths[i] = Math.max(columnWidths[i], String.format("%.6f", std).trim().length());
            columnWidths[i] = Math.max(columnWidths[i], min.toString().length());
            columnWidths[i] = Math.max(columnWidths[i], String.format("%.6f", median).trim().length());
            columnWidths[i] = Math.max(columnWidths[i], max.toString().length());

            columnWidths[i] += 4;
        }

        int statsWidth = 8;

        sb.append(" ".repeat(statsWidth + 1)).append("╔");
        for (int i = 0; i < numericalSeries.size(); i++) {
            sb.append("═".repeat(columnWidths[i])).append(i == numericalSeries.size() - 1 ? "╗\n" : "╦");
        }

        sb.append(" ".repeat(statsWidth + 1)).append("║");
        for (int i = 0; i < numericalSeries.size(); i++) {
            sb.append(String.format(" %-" + (columnWidths[i] - 2) + "s ║", numericalSeries.get(i).getName()));
        }
        sb.append("\n");

        sb.append("╔").append("═".repeat(statsWidth)).append("╬");
        for (int i = 0; i < numericalSeries.size(); i++) {
            sb.append("═".repeat(columnWidths[i])).append(i == numericalSeries.size() - 1 ? "╣\n" : "╬");
        }

        for (String stat : stats) {
            sb.append(String.format("║ %-6s ║", stat));

            for (int i = 0; i < numericalSeries.size(); i++) {
                Series<? extends Number> series = (Series<? extends Number>) numericalSeries.get(i);
                String value;

                switch (stat) {
                    case "count":
                        value = String.valueOf(series.getData().size());
                        break;
                    case "mean":
                        value = String.format("%.6f", series.mean()).trim();
                        break;
                    case "std":
                        value = String.format("%.6f", series.std()).trim();
                        break;
                    case "min":
                        value = series.min().toString();
                        break;
                    case "median":
                        value = String.format("%.6f", series.median()).trim();
                        break;
                    case "max":
                        value = series.max().toString();
                        break;
                    default:
                        value = "N/A";
                }

                sb.append(String.format(" %-" + (columnWidths[i] - 2) + "s ║", value));
            }
            sb.append("\n");
        }

        sb.append("╚").append("═".repeat(statsWidth)).append("╩");
        for (int i = 0; i < numericalSeries.size(); i++) {
            sb.append("═".repeat(columnWidths[i])).append(i == numericalSeries.size() - 1 ? "╝\n" : "╩");
        }

        System.out.println(sb);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (data_tab == null || data_tab.length == 0) {
            return "Empty dataframe";
        }

        int[] columnWidths = new int[data_tab.length];
        for (int i = 0; i < data_tab.length; i++) {
            columnWidths[i] = data_tab[i].getName().length();
            for (Object value : data_tab[i].getData()) {
                if (value == null) {
                    columnWidths[i] = Math.max(columnWidths[i], 4);
                } else {
                    columnWidths[i] = Math.max(columnWidths[i], value.toString().length());
                }
            }
            columnWidths[i] += 4;
        }

        int indexWidth = 5;

        sb.append(" ".repeat(indexWidth+ 1)).append("╔");
        for (int i = 0; i < data_tab.length; i++) {
            sb.append("═".repeat(columnWidths[i])).append(i == data_tab.length - 1 ? "╗\n" : "╦");
        }

        sb.append(" ".repeat(indexWidth+ 1)).append("║");
        for (int i = 0; i < data_tab.length; i++) {
            sb.append(String.format(" %-" + (columnWidths[i] - 2) + "s ║", data_tab[i].getName()));
        }
        sb.append("\n");

        sb.append("╔").append("═".repeat(indexWidth)).append("╬");
        for (int i = 0; i < data_tab.length; i++) {
            sb.append("═".repeat(columnWidths[i])).append(i == data_tab.length - 1 ? "╣\n" : "╬");
        }

        int maxRows = 0;
        for (Series<?> serie : data_tab) {
            maxRows = Math.max(maxRows, serie.getData().size());
        }

        for (int i = 0; i < maxRows; i++) {
            sb.append(String.format("║ %3d ║", i));
            for (int j = 0; j < data_tab.length; j++) {
                if (i < data_tab[j].getData().size()) {
                    sb.append(String.format(" %-" + (columnWidths[j] - 2) + "s ║", data_tab[j].getData().get(i)));
                } else {
                    sb.append(String.format(" %-" + (columnWidths[j] - 2) + "s ║", ""));
                }
            }
            sb.append("\n");
        }

        sb.append("╚").append("═".repeat(indexWidth)).append("╩");
        for (int i = 0; i < data_tab.length; i++) {
            sb.append("═".repeat(columnWidths[i])).append(i == data_tab.length - 1 ? "╝\n" : "╩");
        }

        return sb.toString();
    }
}
