package com.github.hugorouillard.dataframe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a dataframe structure containing multiple series of data.
 */
public class Dataframe {
    private Series<?>[] data_tab;

    /**
     * Constructs a Dataframe from variable arguments where each argument represents a column of data.
     *
     * @param input_series Set of multiple array that will be used as column in the Dataframe
     * @throws IllegalArgumentException If any input series cannot be properly converted.
     */
    public Dataframe(Object ...input_series) {
        data_tab = new Series[input_series.length];
        for (int  i = 0; i < input_series.length; i++) {
            List<?> column_data_list = ConversionUtils.convertArrayToList(input_series[i]);
            data_tab[i] = new Series<>(column_data_list, String.valueOf(i));
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

    public Series<?>[] getDataTab() {
        return data_tab;
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
