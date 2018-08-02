package com.kskrueger.college.util.csv;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by kskrueger on 11/12/17.
 */

public class CSVWrite {

    String[][] test = new String[1][2];

    public void logArray () throws IOException {
        saveDegree(test,"swerveLog");
    }

    public void saveDegree(String[][] array, String fileName) throws IOException {
        String csvName = "src/com/kskrueger/college/data/degress/"+fileName+".csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csvName));
        writer.writeAll(Arrays.asList(array));
        writer.close();
    }

    public void saveRaw(String[][] array, String fileName) throws IOException {
        if (!fileName.contains(".csv"))
            fileName += ".csv";
        CSVWriter writer = new CSVWriter(new FileWriter(fileName));
        writer.writeAll(Arrays.asList(array));
        writer.close();
    }

    public void updateValue(String fileName) {

    }
}