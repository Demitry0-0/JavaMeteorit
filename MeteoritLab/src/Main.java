import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONObject;

class StatisticMeteorite {
    private int count = 0;
    private int countIsFell = 0;
    private long massAll = 0;
    private Meteorite easiest = null, heaviest = null;

    void add(Meteorite meteorite) {
        ++count; if (meteorite.fallingState.isFell()) ++countIsFell; massAll += meteorite.massInGram;
        if (easiest == null || easiest.massInGram > meteorite.massInGram) easiest = meteorite;
        if (heaviest == null || heaviest.massInGram < meteorite.massInGram) heaviest = meteorite;
    }

    double averageWeight() {
        return (count == 0 ? 0 : massAll * 1.0 / count);
    }

    public int getCount() {
        return count;
    }

    public int getCountIsFell() {
        return countIsFell;
    }

    public long getMassAll() {
        return massAll;
    }

    public Meteorite getEasiest() {
        return easiest;
    }

    public Meteorite getHeaviest() {
        return heaviest;
    }
}

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String... args) throws IOException {
        ParseMeteoriteFile parse = new ParseMeteoriteFile("data_meteorite.txt");
        List<Meteorite> meteorites = parse.getAllMeteorite();
        TreeMap<PartOfTheWorld, StatisticMeteorite> map = new TreeMap<>();
        for (PartOfTheWorld part: PartOfTheWorld.values())
            map.put(part, new StatisticMeteorite());

        for (Meteorite meteorite: meteorites)
            map.get(meteorite.getPartOfTheWorld()).add(meteorite);
        Iterator<Map.Entry<PartOfTheWorld, StatisticMeteorite>> it;
        it = map.entrySet().stream().sorted((o1, o2) -> o2.getValue().getCount() - o1.getValue().getCount()).iterator();

        Meteorite easiest, heaviest;
        JSONObject mainJSONObject = new JSONObject(), jsonObject, easiestJSONObject = null, heaviestJSONObject = null;

        for (Map.Entry<PartOfTheWorld, StatisticMeteorite> entry; it.hasNext(); ) {
            entry = it.next();

            jsonObject = new JSONObject();

            jsonObject.put("count", entry.getValue().getCount());
            jsonObject.put("countIsFell", entry.getValue().getCountIsFell());
            jsonObject.put("averageWeight", entry.getValue().averageWeight());

            System.out.printf("%s:\n\tобщее количество: %d\n\tколичество упавших: %d\n\tсредняя масса: %.3fg\n",
                              entry.getKey().translate, entry.getValue().getCount(),
                              entry.getValue().getCountIsFell(), entry.getValue().averageWeight());
            if (entry.getValue().getCount() > 0) {
                easiestJSONObject = new JSONObject(); heaviestJSONObject = new JSONObject();
                easiest           = entry.getValue().getEasiest(); heaviest = entry.getValue().getHeaviest();
                System.out.printf("\tсамый легкий:\n\t\tназвание: %s\n\t\tгод: %d\n\t\tмасса: %dg\n\tсамый " +
                                          "тяжелый:\n\t\tназвание: %s\n\t\tгод: %d\n\t\tмасса: %dg\n", easiest.name,
                                  easiest.yearOfDiscovery, easiest.massInGram, heaviest.name,
                                  heaviest.yearOfDiscovery, heaviest.massInGram);

                easiestJSONObject.put("name", easiest.name);
                easiestJSONObject.put("yearOfDiscovery", easiest.yearOfDiscovery);
                easiestJSONObject.put("massInGram", easiest.massInGram);

                heaviestJSONObject.put("name", heaviest.name);
                heaviestJSONObject.put("yearOfDiscovery", heaviest.yearOfDiscovery);
                heaviestJSONObject.put("massInGram", heaviest.massInGram);
            } else easiestJSONObject = heaviestJSONObject = null;

            jsonObject.put("easiest", easiestJSONObject); jsonObject.put("heaviest", heaviestJSONObject);

            mainJSONObject.put(entry.getKey(), jsonObject);
        } FileWriter file = new FileWriter("answer.json"); file.write(mainJSONObject.toString()); file.close();
    }
}
