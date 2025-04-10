package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        Path tempFile = Files.createTempFile("example", ".csv");
        Files.write(tempFile, Arrays.asList(
                "Name,Age,City",
                "Alice,30,Paris",
                "Bob,25,London",
                "Charlie,35,New York"
        ));

        Dataframe df = new Dataframe(tempFile.toString(), ',');
        df.displayLastLines(2);

        String[] labels = {"A", "B"};
        Integer[] col1 = {1, 2, 3, 4, 5};
        String[] col2 = {"a", "b", "c", "d", "e"};
        df = new Dataframe(labels, col1, col2);
        df.displayLastLines(2);
        System.out.println(df);
    }
}