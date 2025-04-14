package com.github.hugorouillard.dataframe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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
     * Returns a new Dataframe containing only the specified rows (by index).
     * Mimics Pandas' df.iloc[[i1, i2, ...]].
     *
     * @param indices the exact row indices to include
     * @return a new Dataframe with those rows
     * @throws IllegalArgumentException if any index is out of bounds
     */
    public Dataframe selectRows(int... indices) {
        int rowCount = data_tab[0].getData().size();
        Object[] newData = new Object[data_tab.length];

        for (int col = 0; col < data_tab.length; col++) {
            List<?> originalCol = data_tab[col].getData();
            List<Object> selectedCol = new ArrayList<>();

            for (int idx : indices) {
                if (idx < 0 || idx >= rowCount) {
                    throw new IllegalArgumentException("Row index out of bounds: " + idx);
                }
                selectedCol.add(originalCol.get(idx));
            }

            newData[col] = selectedCol.toArray();
        }

        return new Dataframe(getLabels(), newData);
    }

    /**
     * Returns a new Dataframe containing rows in the range [from, to).
     * Mimics Pandas' df.iloc[from:to].
     *
     * @param from the start index (inclusive)
     * @param to the end index (exclusive)
     * @return a new Dataframe with selected rows
     */
    public Dataframe selectRows(int from, int to) {
        if (from < 0 || to > data_tab[0].getData().size() || from >= to) {
            throw new IllegalArgumentException("Invalid row range");
        }

        int[] indices = new int[to - from];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = from + i;
        }

        return selectRows(indices);
    }

    public Dataframe selectColumn (String... labels) {
        List<Series<?>> selectedSeries = new ArrayList<>();
        List<String> selectedLabels = new ArrayList<>();

        for (String label : labels) {
            boolean found = false;
            for (Series<?> series : data_tab) {
                if (series.getName().equals(label)) {
                    selectedSeries.add(series);
                    selectedLabels.add(label);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("Column not found: " + label);
            }
        }

        Object[] newData = new Object[selectedSeries.size()];
        for (int i = 0; i < selectedSeries.size(); i++) {
            newData[i] = selectedSeries.get(i).getData().toArray();
        }

        return new Dataframe(selectedLabels.toArray(new String[0]), newData);
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
