import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class OutputValidation {

    private String pattern = "LOGIC";
    private String input = "\"I love to work in global logic!\"";
    private CharsFrequencyInWordsCalculator calc;

    @Test
    public void exampleOutputTotalValuesValidation(){
        int expectedTotalChars = 24;
        int expectedTotalOccurrences = 15;
        float expectedTotalFrequency = 0.63f;

        calc = new CharsFrequencyInWordsCalculator(input, pattern);
        calc.calculate();

        Assert.assertEquals(expectedTotalChars, calc.getTotalNonSpecialChars());
        Assert.assertEquals(expectedTotalOccurrences, calc.getTotalOccurrences());
        Assert.assertEquals(expectedTotalFrequency, calc.getTotalOccurrences()/(float)calc.getTotalNonSpecialChars(), 0.05);
    }

    @Test
    public void exampleOutputModelValidation(){
        OutputModel expectedOutputModel1 = new OutputModel(new char[]{'i'}, 1, 1, 15);
        OutputModel expectedOutputModel2 = new OutputModel(new char[]{'i'}, 2, 1, 15);
        OutputModel expectedOutputModel3 = new OutputModel(new char[]{'o'}, 2, 1, 15);
        OutputModel expectedOutputModel4 = new OutputModel(new char[]{'o'}, 4, 1, 15);
        OutputModel expectedOutputModel5 = new OutputModel(new char[]{'l', 'o'}, 4, 2, 15);
        OutputModel expectedOutputModel6 = new OutputModel(new char[]{'l', 'o', 'g'}, 6, 4, 15);
        OutputModel expectedOutputModel7 = new OutputModel(new char[]{'l', 'o', 'g', 'i', 'c'}, 5, 5, 15);

        calc = new CharsFrequencyInWordsCalculator(input, pattern);
        calc.calculate();

        Assert.assertEquals(expectedOutputModel1, calc.getOutputList().get(0));
        Assert.assertEquals(expectedOutputModel2, calc.getOutputList().get(1));
        Assert.assertEquals(expectedOutputModel3, calc.getOutputList().get(2));
        Assert.assertEquals(expectedOutputModel4, calc.getOutputList().get(3));
        Assert.assertEquals(expectedOutputModel5, calc.getOutputList().get(4));
        Assert.assertEquals(expectedOutputModel6, calc.getOutputList().get(5));
        Assert.assertEquals(expectedOutputModel7, calc.getOutputList().get(6));

        Assert.assertEquals(0.07f, calc.getOutputList().get(0).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.07f, calc.getOutputList().get(1).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.07f, calc.getOutputList().get(2).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.07f, calc.getOutputList().get(3).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.13f, calc.getOutputList().get(4).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.27f, calc.getOutputList().get(5).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.33f, calc.getOutputList().get(6).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
    }

    @Test
    public void multiOccurrencesInSameCharsetAndWordLengthValidation() {
        input = "Level plate level in it is logically lylogical";
        OutputModel expectedOutputModel1 = new OutputModel(new char[]{'i'}, 2, 3, 22);
        OutputModel expectedOutputModel2 = new OutputModel(new char[]{'l'}, 5, 5, 22);
        OutputModel expectedOutputModel3 = new OutputModel(new char[]{'l','o','g','i','c'}, 9, 14, 22);

        calc = new CharsFrequencyInWordsCalculator(input, pattern);
        calc.calculate();

        Assert.assertEquals(expectedOutputModel1, calc.getOutputList().get(0));
        Assert.assertEquals(expectedOutputModel2, calc.getOutputList().get(1));
        Assert.assertEquals(expectedOutputModel3, calc.getOutputList().get(2));

        Assert.assertEquals(0.14f, calc.getOutputList().get(0).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.23f, calc.getOutputList().get(1).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.64f, calc.getOutputList().get(2).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
    }

    @Test
    public void specialCharsRemovalValidation() {
        input = "   :''/.,?>< \"I   l!@#$ove  %^&* t()_o  -  w=o+r}k{  [ ]    |in\\    g';l?>obal\" :   l/,ogic,. !!!!!!! !@#$%^&*()?><\" \"    ";
        int expectedTotalChars = 24;
        int expectedTotalOccurrences = 15;
        float expectedTotalFrequency = 0.63f;

        calc = new CharsFrequencyInWordsCalculator(input, pattern);
        calc.calculate();

        Assert.assertEquals(expectedTotalChars, calc.getTotalNonSpecialChars());
        Assert.assertEquals(expectedTotalOccurrences, calc.getTotalOccurrences());
        Assert.assertEquals(expectedTotalFrequency, calc.getTotalOccurrences()/(float)calc.getTotalNonSpecialChars(), 0.05);

        OutputModel expectedOutputModel1 = new OutputModel(new char[]{'i'}, 1, 1, 15);
        OutputModel expectedOutputModel2 = new OutputModel(new char[]{'i'}, 2, 1, 15);
        OutputModel expectedOutputModel3 = new OutputModel(new char[]{'o'}, 2, 1, 15);
        OutputModel expectedOutputModel4 = new OutputModel(new char[]{'o'}, 4, 1, 15);
        OutputModel expectedOutputModel5 = new OutputModel(new char[]{'l', 'o'}, 4, 2, 15);
        OutputModel expectedOutputModel6 = new OutputModel(new char[]{'l', 'o', 'g'}, 6, 4, 15);
        OutputModel expectedOutputModel7 = new OutputModel(new char[]{'l', 'o', 'g', 'i', 'c'}, 5, 5, 15);

        Assert.assertEquals(expectedOutputModel1, calc.getOutputList().get(0));
        Assert.assertEquals(expectedOutputModel2, calc.getOutputList().get(1));
        Assert.assertEquals(expectedOutputModel3, calc.getOutputList().get(2));
        Assert.assertEquals(expectedOutputModel4, calc.getOutputList().get(3));
        Assert.assertEquals(expectedOutputModel5, calc.getOutputList().get(4));
        Assert.assertEquals(expectedOutputModel6, calc.getOutputList().get(5));
        Assert.assertEquals(expectedOutputModel7, calc.getOutputList().get(6));

        Assert.assertEquals(0.07f, calc.getOutputList().get(0).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.07f, calc.getOutputList().get(1).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.07f, calc.getOutputList().get(2).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.07f, calc.getOutputList().get(3).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.13f, calc.getOutputList().get(4).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.27f, calc.getOutputList().get(5).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
        Assert.assertEquals(0.33f, calc.getOutputList().get(6).getOccurrencesCount()/(float)calc.getTotalOccurrences(), 0.005);
    }

    @Test
    public void emptyInputValidation() {
        input = "";
        int expectedTotalChars = 0;
        int expectedTotalOccurrences = 0;

        calc = new CharsFrequencyInWordsCalculator(input, pattern);
        calc.calculate();

        Assert.assertEquals(expectedTotalChars, calc.getTotalNonSpecialChars());
        Assert.assertEquals(expectedTotalOccurrences, calc.getTotalOccurrences());
        Assert.assertTrue(Float.isNaN(calc.getTotalOccurrences()/(float)calc.getTotalNonSpecialChars()));
        Assert.assertEquals(calc.getOutputList().size(), 0);
    }

    @Test
    public void onlySpecialCharsValidation() {
        input = "  !@##$%^&    *()_+=-  ][{}   ';:\"  /.,<>? \\ |       ";
        int expectedTotalChars = 0;
        int expectedTotalOccurrences = 0;

        calc = new CharsFrequencyInWordsCalculator(input, pattern);
        calc.calculate();

        Assert.assertEquals(expectedTotalChars, calc.getTotalNonSpecialChars());
        Assert.assertEquals(expectedTotalOccurrences, calc.getTotalOccurrences());
        Assert.assertTrue(Float.isNaN(calc.getTotalOccurrences()/(float)calc.getTotalNonSpecialChars()));
        Assert.assertEquals(calc.getOutputList().size(), 0);
    }
}
