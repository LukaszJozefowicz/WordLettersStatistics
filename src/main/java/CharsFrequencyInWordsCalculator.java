import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

public class CharsFrequencyInWordsCalculator {

    final public static String[] SPECIAL_CHARS = {"\"", "!", "#", "$", "%", "&", "'",
                                        "(", ")", "*", "+", ",", "-", ".", "/", ":",
                                        ";", "<", "=", ">", "?", "@", "[", "\\", "]",
                                        "^", "_", "`", "{", "|", "}", "~", ")", "\n"};
    private final String pattern;
    private String input;
    private final String originalInput;
    private int totalOccurrences;
    private int totalNonSpecialChars;
    private final TreeMap<Integer, List<String>> wordsWithLengthsMap = new TreeMap<>();
    private ArrayList<OutputModel> outputList = new ArrayList<>();

    public CharsFrequencyInWordsCalculator(String input, String pattern) {
        this.input = input;
        this.originalInput = input;
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
        input = sb.toString().trim();
    }

    private void calculateTotalFrequency(){
        String[] allWords = input.split(" ");
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

    private String getMapAsString(){
        String mapOutput = "Map with (word length)|(list of words) as (key)|(value): \n";
        for(Map.Entry<Integer, List<String>> entry : wordsWithLengthsMap.entrySet()) {
            int wordLength = entry.getKey();
            mapOutput += "Word Length: " + wordLength + "   |   list of words: ";
            List<String> sameLengthWords = entry.getValue();
            for (String word : sameLengthWords) {
                mapOutput += word + " ";
            }
            mapOutput += "\n";
        }
        return mapOutput;
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
                    //System.out.println("word: "+word+" added with combination "+Arrays.toString(combination));
                }
            }
        }
    }

    private void mergeOutputs(){
        outputList.sort(Comparator.comparing(OutputModel::getWordLength)
                .thenComparing(OutputModel::getFirstCharAsInt));

        List<Integer> indexesToDelete = new ArrayList<>();

        for(int i = 1; i < outputList.size(); i++){
            OutputModel current = outputList.get(i);
            OutputModel previous = outputList.get(i-1);
            if(current.equals(previous)){
                //System.out.println("equality on "+i);
                current.setOccurrencesCount(current.getOccurrencesCount() + previous.getOccurrencesCount());
                outputList.set(i, current);
                indexesToDelete.add(i-1);
            }
        }

        Collections.sort(indexesToDelete, Collections.reverseOrder());
        for(int i : indexesToDelete){
            //System.out.println("removing "+i + "th output: "+ outputList.get(i));
            outputList.remove(i);
        }
        outputList.sort(Comparator.comparing(OutputModel::getOccurrencesCount));
    }

    public void displayOutput(){
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
        //System.out.println(getMap());
        buildOutputList();
        mergeOutputs();
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
        writer.append("Pattern: " + pattern + "\n\n");
        writer.append("Input:\n" + originalInput + "\n\n");
        writer.append("Input after removing special chars (but not spaces separating words):\n" + input + "\n\n");
        writer.append(getMapAsString()+ "\n");
        writer.append("Final output after executing algorithm:\n");
        for(OutputModel output : outputList){
            writer.append(output.toString() + "\n");
        }
        writer.append("TOTAL occurrences: " + totalOccurrences
                + ", TOTAL non special chars: " + totalNonSpecialChars
                + ", TOTAL Frequency: "
                + String.format(Locale.US, "%.2f", totalOccurrences/(float)totalNonSpecialChars));
        writer.close();
    }

}
