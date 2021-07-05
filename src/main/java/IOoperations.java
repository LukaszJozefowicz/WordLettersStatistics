import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class IOoperations{

    private String input2 = "\"I love to work in global logic!\"";
    private String input;
    private String pattern;
    private CharsFrequencyInWordsCalculator calc;

    public IOoperations() throws IOException {
        input = readInputFromFile();
        pattern = readPatternFromFile();
        calc = new CharsFrequencyInWordsCalculator(input, pattern);
    }

    public CharsFrequencyInWordsCalculator getCalc() {
        return calc;
    }

    public String getInput() {
        return input;
    }

    public String getPattern() {
        return pattern;
    }


    public String readPatternFromFile() throws IOException {
        File file = new File("src/main/resources/pattern.txt");
        String absolutePath = file.getAbsolutePath();
        pattern = Files.readString(Path.of(absolutePath));
        return Files.readString(Path.of(absolutePath));
    }

    public String readInputFromFile() throws IOException {
        File file = new File("src/main/resources/input.txt");
        String absolutePath = file.getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        Stream<String> stream = Files.lines( Paths.get(absolutePath), StandardCharsets.UTF_8);
        stream.forEach(s -> sb.append(s).append(" "));
        input = sb.toString();
        return sb.toString();
    }

    public String getMapAsString(Map<Integer, List<String>> map){
        StringBuilder sb = new StringBuilder("Map with (word length)|(list of words) as (key)|(value): \n");
        for(Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            int wordLength = entry.getKey();
            sb.append("Word Length: " + wordLength + "   |   list of words: ");
            List<String> sameLengthWords = entry.getValue();
            for (String word : sameLengthWords) {
                sb.append(word + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void generateOutputFile() throws IOException {
        File outputFile = new File("src/main/resources/output.txt");
        if(outputFile.exists()){
            outputFile.delete();
        }
        outputFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(outputFile);
        OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        writer.append("-------------REPORT OF ALGORITHM EXECUTION-------------\n\n");
        writer.append("Pattern: " + calc.getPattern() + "\n\n");
        writer.append("Input:\n" + calc.getOriginalInput() + "\n\n");
        writer.append("Input after removing special chars (but not spaces separating words):\n" + calc.getInput() + "\n\n");
        writer.append(new IOoperations().getMapAsString(calc.getWordsWithLengthsMap())+ "\n");
        writer.append("Final output after executing algorithm:\n");
        for(OutputModel output : calc.getOutputList()){
            writer.append(output.toString() + "\n");
        }
        writer.append("TOTAL occurrences: " + calc.getTotalOccurrences()
                + ", TOTAL non special chars: " + calc.getTotalNonSpecialChars()
                + ", TOTAL Frequency: "
                + String.format(calc.getLocale(), "%.2f", calc.getTotalOccurrences()/(float)calc.getTotalNonSpecialChars()));
        writer.close();
    }

    public void displayOutput(){
        for(OutputModel singleOutput: calc.getOutputList()){
            System.out.println(singleOutput);
        }
        System.out.println("TOTAL occurrences: " + calc.getTotalOccurrences()
                + ", TOTAL non special chars: " + calc.getTotalNonSpecialChars()
                + ", TOTAL Frequency: "
                + String.format(calc.getLocale(), "%.2f", calc.getTotalOccurrences()/(float)calc.getTotalNonSpecialChars()));
    }
}
