import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class FileEnded extends Exception {
    public FileEnded() { super("Файл закончился"); }
}


public class ParseMeteoriteFile {
    final Scanner scanner;
    final int countLineNumber = 10;
    int countLine = 0;
    int errorLineNumber = 0;

    public ParseMeteoriteFile(String filePath) throws FileNotFoundException {
        this.scanner = new Scanner(new File(filePath));
    }

    public Meteorite getMeteorite() throws FileEnded {
        return new Meteorite(
                getString(),
                Long.parseLong(getString()),
                MeteoriteType.valueOf(getString().toLowerCase()),
                getString(),
                Long.parseLong(getString()),
                MeteoriteFallingState.valueOf(getString().toLowerCase()),
                Integer.parseInt(getString()),
                Double.parseDouble(getString()),
                Double.parseDouble(getString()),
                Arrays.stream(getString().replace(")", "")
                                         .replace("(", "")
                                         .replace(",", "")
                                         .split(" +"))
                      .mapToDouble(Double::parseDouble)
                      .toArray()
        );
    }

    public List<Meteorite> getAllMeteorite() {
        List<Meteorite> list = new ArrayList<>();
        while (true) try {
            list.add(getMeteorite());
            //System.out.println(list.get(list.size() - 1).getPartOfTheWorld());
        } catch (IllegalArgumentException ignored) {
            //System.out.println("Ошибка в строке: " + errorLineNumber);
        } catch (FileEnded fileEnded) {
            break;
        }

        return list;
    }

    private String getString() throws FileEnded {
        String str;
        ++errorLineNumber; ++countLine;
        while (scanner.hasNext())
            if (!(str = scanner.nextLine().trim()).isEmpty()) return str;
        throw new FileEnded();
    }
}
