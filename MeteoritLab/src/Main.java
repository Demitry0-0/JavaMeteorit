import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;

class StatisticMeteorite {
    int count = 0;
    int countIsFell = 0;
    long massAll = 0;
    Meteorite easiest = null, heaviest = null;

    void add(Meteorite meteorite) {
        ++count;
        if (meteorite.fallingState.isFell()) ++countIsFell;
        massAll += meteorite.massInGram;
        if (easiest == null || easiest.massInGram > meteorite.massInGram)
            easiest = meteorite;
        if (heaviest == null || heaviest.massInGram > meteorite.massInGram)
            heaviest = meteorite;
    }

    double averageWeight() {
        return (count == 0 ? 0 : massAll * 1.0 / count);
    }
}

public class Main {

    public static void main(String... args) throws FileNotFoundException {
        ParseMeteoriteFile parse = new ParseMeteoriteFile("data_meteorite.txt");
        List<Meteorite> meteorites = parse.getAllMeteorite();
        TreeMap<PartOfTheWorld, StatisticMeteorite> map = new TreeMap<>();
        for (PartOfTheWorld part: PartOfTheWorld.values())
            map.put(part, new StatisticMeteorite());
        for (Meteorite meteorite: meteorites)
            map.get(meteorite.getPartOfTheWorld())
               .add(meteorite);
        Iterator<Map.Entry<PartOfTheWorld, StatisticMeteorite>> it = map.entrySet()
                                                                        .stream()
                                                                        .sorted(
                                                                                (o1, o2) -> o2.getValue().count - o1.getValue().count
                                                                        )
                                                                        .iterator();
        for (; it.hasNext(); ) {
            Map.Entry<PartOfTheWorld, StatisticMeteorite> entry = it.next();
            System.out.printf("%s:\n\tобщее количество: %d\n\tколичество упавших: %d\n\tсредняя масса: %.3fg\n",
                              entry.getKey().translate, entry.getValue().count, entry.getValue().countIsFell, entry.getValue()
                                                                                                                   .averageWeight()
            );
            if (entry.getValue().count > 0)
            System.out.printf("\tсамый легкий:\n\t\tназвание: %s\n\t\tгод: %d\n\t\tмасса: %dg\n\tсамый тяжелый:\n\t\tназвание: %s\n\t\tгод: %d\n\t\tмасса: %d\n",
                              entry.getValue().easiest.name, entry.getValue().easiest.yearOfDiscovery, entry.getValue().easiest.massInGram, entry.getValue().heaviest.name, entry.getValue().heaviest.yearOfDiscovery, entry.getValue().heaviest.massInGram
            );
        }

    }
}
