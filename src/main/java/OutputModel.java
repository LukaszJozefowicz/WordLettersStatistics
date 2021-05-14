import java.util.Arrays;
import java.util.Locale;

public class OutputModel {
    private char[] combinationOfChars;
    private int wordLength;
    private int occurrencesCount;
    private int totalOccurrences;

    public OutputModel(char[] combinationOfChars, int wordLength, int occurrencesCount, int totalOccurrences) {
        this.combinationOfChars = combinationOfChars;
        this.wordLength = wordLength;
        this.occurrencesCount = occurrencesCount;
        this.totalOccurrences = totalOccurrences;
    }

    public int getOccurrencesCount() {
        return occurrencesCount;
    }

    public void setOccurrencesCount(int occurrencesCount) {
        this.occurrencesCount = occurrencesCount;
    }

    public int getFirstCharAsInt() {
        return combinationOfChars[0];
    }

    public int getWordLength() {
        return wordLength;
    }

    @Override
    public String toString() {
        return "OutputModel{" +
                "combinationOfChars=" + Arrays.toString(combinationOfChars) +
                ", wordLength=" + wordLength +
                ", occurrencesCount=" + occurrencesCount +
                ", totalOccurrences=" + totalOccurrences +
                ", frequency=" + String.format(Locale.US, "%.2f", occurrencesCount/(float)totalOccurrences) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OutputModel)) return false;
        OutputModel that = (OutputModel) o;
        return wordLength == that.wordLength && Arrays.equals(combinationOfChars, that.combinationOfChars);
    }
}
