public class Main {

    public static String input = "\"I love to work in global logic! Level the goddamn plate globally!!\"";
    public static String input2 = "\"l$$^^evel     plate  !!   sumthin  #$%^\"   ";
    public static String pattern = "LOGIC";

    public static void main(String[] args) {
        CharsFrequencyInWordsCalculator calc = new CharsFrequencyInWordsCalculator(input2, pattern);
        calc.calculate();
    }
}
