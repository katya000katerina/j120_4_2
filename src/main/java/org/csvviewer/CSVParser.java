package org.csvviewer;

import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    private final String CSVLine;
    private final char delimiter;
    private final List<String> record = new ArrayList<>();
    private int startPosition = 0;
    private int numberOfDoubleQuotes = 0;
    private int currentPosition = 0;
    private boolean isLastChar;
    private boolean isParsed;

    public CSVParser(String CSVLine, char delimiter) {
        this.CSVLine = CSVLine;
        this.delimiter = delimiter;
    }

    public List<String> parse() {
        if (isParsed) {
            return new ArrayList<>(record);
        }
        for (; currentPosition < CSVLine.length(); currentPosition++) {
            isLastChar = currentPosition == CSVLine.length() - 1;
            if (CSVLine.charAt(currentPosition) == '"') {
                numberOfDoubleQuotes++;
            }
            if (CSVLine.charAt(currentPosition) == delimiter || isLastChar) {
                addValidToken();
            }
        }
        removeExtraDoubleQuotes();
        isParsed = true;
        return new ArrayList<>(record);
    }

    private void addValidToken() {
        if ((numberOfDoubleQuotes | 1) > numberOfDoubleQuotes) { //checking if a number of double quotes is even
            if (numberOfDoubleQuotes == 0) {
                record.add(CSVLine.substring(startPosition, isLastChar ? CSVLine.length() : currentPosition));
            } else {
                record.add(CSVLine.substring(startPosition + 1, isLastChar ? currentPosition : currentPosition - 1));
                numberOfDoubleQuotes = 0;
            }
            startPosition = currentPosition + 1;
        } else if (isLastChar) {
            throw new RuntimeException("Cannot parse line: " + CSVLine);
        }
    }

    private void removeExtraDoubleQuotes() {
        for (int i = 0; i < record.size(); i++) {
            String value = record.get(i);
            if (value.contains("\"")) {
                while (value.contains("\"\"")) {
                    value = value.replaceAll("\"\"", "\"");
                }
                record.set(i, value);
            }
        }
    }
}
