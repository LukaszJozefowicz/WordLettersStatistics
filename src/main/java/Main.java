import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    private static String input;
    private static String pattern;

    private static void readPatternFromFile() throws IOException {
        File file = new File("src/main/resources/pattern.txt");
        String absolutePath = file.getAbsolutePath();
        pattern = Files.readString(Path.of(absolutePath));
    }

    private static void readInputFromFile() throws IOException {
        File file = new File("src/main/resources/input.txt");
        String absolutePath = file.getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        Stream<String> stream = Files.lines( Paths.get(absolutePath), StandardCharsets.UTF_8);
        stream.forEach(s -> sb.append(s).append(" "));
        input = sb.toString();
    }

    public static void main(String[] args) throws IOException {
        readPatternFromFile();
        readInputFromFile();
        System.out.println("Input: " + input);
        System.out.println("Pattern: " + pattern +"\n");
        CharsFrequencyInWordsCalculator calc = new CharsFrequencyInWordsCalculator(input, pattern);
        calc.calculate();
        calc.displayOutput();
        calc.generateOutputFile();
    }
}
