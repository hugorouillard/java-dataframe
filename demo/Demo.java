import com.github.hugorouillard.dataframe.Dataframe;

import java.util.HashSet;
import java.util.Set;

public class Demo {
    public static void main(String[] args) throws Exception {
        Dataframe df = new Dataframe("league_champion_stats_13.13.csv", ';');

        System.out.println("\nChampions with Win % > 52:");
        Dataframe highWinrate = df.selectColumns("Name", "Role", "Win %").filterRows("Win %", v ->
                v instanceof Number && ((Number) v).doubleValue() > 52.0
        );
        System.out.println(highWinrate);

        System.out.println("\nChampions with Ban % > 10:");
        Dataframe highBanRaw = df.selectColumns("Name", "Ban %").filterRows("Ban %", v ->
                v instanceof Number && ((Number) v).doubleValue() > 10.0
        );
        Dataframe highBan = filterFirstOccurrence(highBanRaw, "Name");
        System.out.println(highBan);


        System.out.println("\nMage champions in the Mid role:");
        Dataframe mageMid = df
                .filterRows("Class", v -> v != null && v.toString().equalsIgnoreCase("Mage"))
                .filterRows("Role", v -> v != null && v.toString().equalsIgnoreCase("Mid"));
        System.out.println(mageMid);

        System.out.println("\nStatistical Summary:");
        df.selectColumns("Win %", "Pick %", "Ban %", "KDA").describe();
    }

    private static Dataframe filterFirstOccurrence(Dataframe df, String columnLabel) {
        Set<Object> seen = new HashSet<>();
        return df.filterRows(columnLabel, value -> {
            if (seen.contains(value)) return false;
            seen.add(value);
            return true;
        });
    }
}
