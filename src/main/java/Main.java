public class Main {

    public static String input = "\"I love to work in global logic! Level the goddamn plate globally!!\"";
    public static String pattern = "LOGIC";

    public static void main(String[] args) {
        CharsFrequencyInWordsCalculator calc = new CharsFrequencyInWordsCalculator(input, pattern);
        calc.calculate();
    }
}
