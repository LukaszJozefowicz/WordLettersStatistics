import java.io.IOException;

public class Main {



    public static void main(String[] args) throws IOException {
        IOoperations io = new IOoperations();
        String pattern = io.readPatternFromFile();
        String input = io.readInputFromFile();
        //CharsFrequencyInWordsCalculator calc = new CharsFrequencyInWordsCalculator(input, pattern);
        System.out.println("Input: " + input);
        System.out.println("Pattern: " + pattern +"\n");

        io.getCalc().calculate();
        io.displayOutput();
        io.generateOutputFile();
    }
}
