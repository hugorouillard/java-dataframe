package com.github.hugorouillard.dataframe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConversionUtils {
    public static List<?> convertArrayToList(Object array) {
        /**
         * Convert a primitive typed array into a java object type List
         *
         * @param array the tab to convert
         * @return the same array without primitive typing
         * @throws IllegalArgumentException if the input tab is not in a supported type
         */
        if (array instanceof int[]) {
            int[] arr = (int[]) array;
            List<Integer> list = new ArrayList<>(arr.length);
            for (int value : arr) {
                list.add(value);
            }
            return list;
        } else if (array instanceof double[]) {
            double[] arr = (double[]) array;
            List<Double> list = new ArrayList<>(arr.length);
            for (double value : arr) {
                list.add(value);
            }
            return list;
        } else if (array instanceof boolean[]) {
            boolean[] arr = (boolean[]) array;
            List<Boolean> list = new ArrayList<>(arr.length);
            for (boolean value : arr) {
                list.add(value);
            }
            return list;
        } else if (array instanceof float[]) {
            float[] arr = (float[]) array;
            List<Double> list = new ArrayList<>(arr.length);
            for (float value : arr) {
                list.add((double)value);
            }
            return list;
        } else if (array instanceof long[]) {
            long[] arr = (long[]) array;
            List<Long> list = new ArrayList<>(arr.length);
            for (long value : arr) {
                list.add(value);
            }
            return list;
        } else if (array instanceof Object[]) {
            return Arrays.asList((Object[]) array);
        }
        throw new IllegalArgumentException("Unsuported array type : " + array.getClass().getSimpleName());
    }

    public static <T> List<T> convertStringListToTypedList(List<String> list) {
        /**
         * Parse a list of String to get it converted into a single type list
         *
         * @param list the list to convert
         * @return typed array after parsing strings
         */
        // first let's determine the common type of the list
        Class<?> type = parseStringType(list.get(0));
        for (String s : list) {
            if (type != null && !type.equals(parseStringType(s))) {
                if ((type == Integer.class && parseStringType(s).equals(Long.class)) || (type == Long.class && parseStringType(s).equals(Integer.class))) {
                    // if there's a mix between long and integer, the type is long
                    type = Long.class;
                    continue;
                }
                type = String.class;
                break;
            }
        }
        List<T> convertedList = new ArrayList<>();
        for (String s : list) {
            Object value = parseValue(s, type);
            convertedList.add((T) value);
        }
        return convertedList;
    }

    private static Object parseValue(String s, Class<?> type) {
        if (s.isEmpty()) {
            return null;
        }
        if (type == Integer.class) return Integer.parseInt(s);
        if (type == Long.class) return Long.parseLong(s);
        if (type == Double.class) return Double.parseDouble(s);
        if (type == Boolean.class) return Boolean.parseBoolean(s);
        if (type == String.class) return s;
        return null;
    }

    public static Class<?> parseStringType(String s) {
        /**
         * Parse a string to determine the most appropriate Java type.
         *
         * @param s the string to parse
         * @return Class<?> representing the detected type
         */
        s = s.trim();
        try {
            Integer.parseInt(s);
            return Integer.class;
        } catch (NumberFormatException ignored) {}

        try {
            Long.parseLong(s);
            return Long.class;
        } catch (NumberFormatException ignored) {}

        try {
            Double.parseDouble(s);
            return Double.class;
        } catch (NumberFormatException ignored) {}

        if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
            return Boolean.class;
        }
        return String.class;
    }

    public static ArrayList<String> parseCSVRow(String row, char delimiter) {
        /**
         * Parse a csv row and convert it into a string List of elements, double "" are parsed as a single "
         *
         * @param row the csv row to parse
         * @return string array of row elements
         */
        int i = 0;
        ArrayList<String> parsed_string = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean opened_quote = false;
        while (i < row.length()) {
            if (i + 1 < row.length() && row.charAt(i) == '\"' && row.charAt(i + 1) != '\"') {
                opened_quote = !opened_quote;
            } else if (i + 1 < row.length() && row.charAt(i) == '\"' && row.charAt(i + 1) == '\"') {
                sb.append('\"');
                i++;
            } else if (row.charAt(i) == delimiter && !opened_quote) {
                parsed_string.add(sb.toString().trim());
                sb.setLength(0);
            } else if (!(i == row.length() - 1 && row.charAt(i) == '\"')) {
                sb.append(row.charAt(i));
            }
            i++;
        }
        parsed_string.add(sb.toString().trim());
        return parsed_string;
    }
}
