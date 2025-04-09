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
        System.out.println(df);
    }
}