package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Dataframe d = new Dataframe("cities.csv", ',');
        System.out.println(d);
    }
}