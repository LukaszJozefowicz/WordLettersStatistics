import java.util.*;
import java.util.regex.Pattern;

public class CharsFrequencyInWordsCalculator {

    final public static String[] SPECIAL_CHARS = {"\"", "!", "#", "$", "%", "&", "'",
                                        "(", ")", "*", "+", ",", "-", ".", "/", ":",
                                        ";", "<", "=", ">", "?", "@", "[", "\\", "]",
                                        "^", "_", "`", "{", "|", "}", "~", ")"};
    private final String pattern;
    private String input;
    private int totalOccurrences;
    private int totalNonSpecialChars;
    private TreeMap<Integer, List<String>> wordsWithLengthsMap = new TreeMap<>();
    private ArrayList<OutputModel> outputList = new ArrayList<>();

    public CharsFrequencyInWordsCalculator(String input, String pattern) {
        this.input = input;
        this.pattern = pattern;
    }

    private void removeSpecialChars(){
        input = input.trim();
        for(String specialChar : SPECIAL_CHARS){
            if(input.contains(specialChar)){
                specialChar = Pattern.quote(specialChar);
                input = input.replaceAll(specialChar, "");
            }
        }
    }

    private void removeSuccessiveSpaces(){
        StringBuilder sb = new StringBuilder(input);
        int deletedSpacesCount = 0;
        for(int i = 1; i < input.length(); i++){
            if(input.charAt(i) == ' ' && input.charAt(i) == input.charAt(i-1)){
                sb.deleteCharAt(i - 1 - deletedSpacesCount);
                deletedSpacesCount++;
            }
        }
        input = sb.toString();
    }

    private void calculateTotalFrequency(){
        String[] allWords = input.trim().split(" ");
        String inputNoSpecialChars = String.join("", allWords);
        int totalNonSpecialChars = inputNoSpecialChars.length();
        int totalOccurrences = 0;
        for(char c : inputNoSpecialChars.toCharArray()){
            if(pattern.toLowerCase().contains(String.valueOf(c).toLowerCase())){
                totalOccurrences++;
            }
        }
        this.totalOccurrences = totalOccurrences;
        this.totalNonSpecialChars = totalNonSpecialChars;
    }

    private void fillMap(){
        String[] allWords = input.trim().split(" ");
        for(String word: allWords) {
            if(wordsWithLengthsMap.containsKey(word.length())){
                List<String> sameLengthWords = wordsWithLengthsMap.get(word.length());
                sameLengthWords.add(word);
            } else {
                List<String> sameLengthWords = new ArrayList<>();
                sameLengthWords.add(word);
                wordsWithLengthsMap.put(word.length(), sameLengthWords);
            }
        }
    }

    private int getAmountOfOccurrences(String word, char c, int currentCount){
        for(int i = 0; i < word.length(); i++){
            if(word.toLowerCase(Locale.ROOT).charAt(i) == c){
                currentCount++;
            }
        }
        return currentCount;
    }

    private void buildOutputList(){
        for(Map.Entry<Integer, List<String>> entry : wordsWithLengthsMap.entrySet()){
            int wordLength = entry.getKey();
            List<String> sameLengthWords = entry.getValue();
            for (String word: sameLengthWords){
                String matchingCombination = "";
                int numberOfOccurrences = 0;
                for(char c : pattern.toCharArray()){
                    if (word.toLowerCase(Locale.ROOT).contains(String.valueOf(c).toLowerCase(Locale.ROOT))){
                        matchingCombination += String.valueOf(c).toLowerCase(Locale.ROOT);
                        numberOfOccurrences = getAmountOfOccurrences(word, Character.toLowerCase(c), numberOfOccurrences);
                    }
                }
                char[] combination = matchingCombination.toCharArray();

                if (combination.length > 0){
                    outputList.add(new OutputModel(combination, wordLength, numberOfOccurrences, totalOccurrences));
                }
            }
        }
    }

    private void mergeOutputs(){
        outputList.sort(Comparator.comparing(OutputModel::getWordLength)
                .thenComparing(OutputModel::getFirstCharAsInt));
        ArrayList<OutputModel> sortedOutputList = outputList;

        List<Integer> indexesToDelete = new ArrayList<>();

        for(int i = 1; i < sortedOutputList.size(); i++){
            OutputModel current = sortedOutputList.get(i);
            OutputModel previous = sortedOutputList.get(i-1);
            if(current.equals(previous)){
                current.setOccurrencesCount(current.getOccurrencesCount() + previous.getOccurrencesCount());
                sortedOutputList.set(i, current);
                indexesToDelete.add(i-1);
            }
        }

        for(int i : indexesToDelete){
            sortedOutputList.remove(i);
        }
        outputList = sortedOutputList;
        outputList.sort(Comparator.comparing(OutputModel::getOccurrencesCount));
    }

    private void display(){
        for(OutputModel singleOutput: outputList){
            System.out.println(singleOutput);
        }
        System.out.println("TOTAL occurrences: " + totalOccurrences
                + ", TOTAL non special chars: " + totalNonSpecialChars
                + ", TOTAL Frequency: "+ String.format(Locale.US, "%.2f", totalOccurrences/(float)totalNonSpecialChars));
    }

    public void calculate(){
        removeSpecialChars();
        removeSuccessiveSpaces();
        calculateTotalFrequency();
        fillMap();
        buildOutputList();
        mergeOutputs();
        display();
    }

}
