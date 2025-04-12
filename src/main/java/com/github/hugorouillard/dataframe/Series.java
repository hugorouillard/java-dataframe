package com.github.hugorouillard.dataframe;
import java.util.List;

public class Series<V> {
    private List<V> data;
    private String name;

    public Series(List<V> data, String name) {
        this.data = data;
        this.name = name;
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
}
