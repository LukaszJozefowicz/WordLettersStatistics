import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class CharsFrequencyInWordsCalculator {

    final public Locale locale = Locale.US;
    final public static String[] SPECIAL_CHARS = {"\"", "!", "#", "$", "%", "&", "'",
                                        "(", ")", "*", "+", ",", "-", ".", "/", ":",
                                        ";", "<", "=", ">", "?", "@", "[", "\\", "]",
                                        "^", "_", "`", "{", "|", "}", "~", ")"};
    private String pattern;
    private String input;
    private final String originalInput;
    private int totalOccurrences;
    private int totalNonSpecialChars;
    private Map<Integer, List<String>> wordsWithLengthsMap = new TreeMap<>();
    private List<OutputModel> outputList = new ArrayList<>();

    public CharsFrequencyInWordsCalculator(String input, String pattern) {
        this.input = input;
        this.originalInput = input;
        this.pattern = pattern;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getPattern() {
        return pattern;
    }

    public String getInput() {
        return input;
    }

    public String getOriginalInput() {
        return originalInput;
    }

    public Map<Integer, List<String>> getWordsWithLengthsMap() {
        return wordsWithLengthsMap;
    }

    public int getTotalOccurrences() {
        return totalOccurrences;
    }

    public int getTotalNonSpecialChars() {
        return totalNonSpecialChars;
    }

    public List<OutputModel> getOutputList() {
        return outputList;
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
        input = sb.toString().trim();
    }

    private void calculateTotalFrequency(){
        String[] allWords = input.split(" ");
        String inputNoSpecialChars = String.join("", allWords);
        int totalNonSpecialChars = inputNoSpecialChars.length();
        int totalOccurrences = 0;
        for(char c : inputNoSpecialChars.toCharArray()){
            if(pattern.toLowerCase(locale).contains(String.valueOf(c).toLowerCase(locale))){
                totalOccurrences++;
            }
        }
        this.totalOccurrences = totalOccurrences;
        this.totalNonSpecialChars = totalNonSpecialChars;
    }

    private void fillMap(){
        String[] allWords = input.trim().split(" ");
        List<String> allWordsList = Arrays.asList(allWords);
        wordsWithLengthsMap = allWordsList.stream()
                .collect(groupingBy(String::length, Collectors.mapping(word -> word, Collectors.toList())));

//    for(String word: allWords) {
//            if(wordsWithLengthsMap.containsKey(word.length())){
//                List<String> sameLengthWords = wordsWithLengthsMap.get(word.length());
//                sameLengthWords.add(word);
//            } else {
//                List<String> sameLengthWords = new ArrayList<>();
//                sameLengthWords.add(word);
//                wordsWithLengthsMap.put(word.length(), sameLengthWords);
//            }
//        }
    }

    private int getAmountOfOccurrences(String word, char c, int currentCount){
        for(int i = 0; i < word.length(); i++){
            if(word.toLowerCase(locale).charAt(i) == c){
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
                    if (word.toLowerCase(locale).contains(String.valueOf(c).toLowerCase(locale))){
                        matchingCombination += String.valueOf(c).toLowerCase(locale);
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
                .thenComparing(OutputModel::getFirstCharAsInt)
                .thenComparing(OutputModel::getSecondCharAsInt));

        List<Integer> indexesToDelete = new ArrayList<>();

        for(int i = 1; i < outputList.size(); i++){
            OutputModel current = outputList.get(i);
            OutputModel previous = outputList.get(i-1);
            //System.out.println("current: " + current + " previous: " + previous);
            if(current.equals(previous)){
                current.setOccurrencesCount(current.getOccurrencesCount() + previous.getOccurrencesCount());
                outputList.set(i, current);
                indexesToDelete.add(i-1);
                //System.out.println("to delete: " + previous);
            }
        }

        Collections.sort(indexesToDelete, Collections.reverseOrder());
        for(int i : indexesToDelete){
            outputList.remove(i);
        }
        outputList.sort(Comparator.comparing(OutputModel::getOccurrencesCount));
    }

    public void calculate(){
        removeSpecialChars();
        removeSuccessiveSpaces();
        calculateTotalFrequency();
        fillMap();
        buildOutputList();
        mergeOutputs();
    }

}
