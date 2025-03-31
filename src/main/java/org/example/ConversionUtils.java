package org.example;

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
            List<Float> list = new ArrayList<>(arr.length);
            for (float value : arr) {
                list.add(value);
            }
            return list;
        } else if (array instanceof Object[]) {
            return Arrays.asList((Object[]) array);
        }

        throw new IllegalArgumentException("Unsuported array type : " + array.getClass().getSimpleName());
    }

    private enum stringParsedType {
        UNKNOWN, EMPTY, INT, DOUBLE, BOOL, STRING
    }

    public static List<?> convertStringListToTypedList(List<String> list) {
        /**
         * Parse a list of String to get it converted into a typed list
         *
         * @param list the list to convert
         * @return typed array after parsing strings
         */
        List<Object> convertedList = new ArrayList<>();

        for (String s : list) {
            switch (parseStringType(s)) {
                case INT:
                    convertedList.add(Integer.parseInt(s));
                    break;
                case DOUBLE:
                    convertedList.add(Double.parseDouble(s));
                    break;
                case BOOL:
                    convertedList.add(Boolean.parseBoolean(s));
                    break;
                case STRING:
                    convertedList.add(s.substring(1, s.length() - 1));
                    break;
                case EMPTY:
                    convertedList.add(null);
                    break;
                default:
                    convertedList.add(s);
            }
        }
        return convertedList;
    }

    public static stringParsedType parseStringType(String s) {
        /**
         * Parse a string into whether int, double, bool or string
         *
         * @param s to parse
         * @return corresponding string type
         */
        if (s == null || s.isEmpty()) {
            return stringParsedType.EMPTY;
        }
        if (s.matches("^-?\\d+$")) {
            return stringParsedType.INT;
        }
        if (s.matches("^-?\\d*\\.\\d+$")) {
            return stringParsedType.DOUBLE;
        }
        if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
            return stringParsedType.BOOL;
        }
        if (s.matches("^\".*\"$") || s.matches("^'.*'$")) {
            return stringParsedType.STRING;
        }
        return stringParsedType.UNKNOWN;
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
            } else {
                sb.append(row.charAt(i));
            }
            i++;
        }
        return parsed_string;
    }
}
