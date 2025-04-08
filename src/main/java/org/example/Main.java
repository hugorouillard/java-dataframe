package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Dataframe d = new Dataframe("sa3.csv", ',' );
        System.out.println(d.data_tab[2]);
        System.out.println(d);
    }
}