package com.kskrueger.college.util.csv;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVRead {
    public String[][] readArray;

    public CSVRead (String fileName) {
        try {
            readArray = readCSV(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[][] readCSV (String fileName) throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(fileName));
        List<String[]> list = csvReader.readAll();
        String[][] dataArr = new String[list.size()][];
        dataArr = list.toArray(dataArr);
        return dataArr;
    }

    private static String[] readCSVSingle (String fileName) throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(fileName));
        List<String[]> list = csvReader.readAll();
        String[] dataArr = new String[list.size()];
        dataArr = list.toArray(dataArr);
        return dataArr;
    }

    public double readDouble(int y, int x) {
        return Double.parseDouble(readArray[y][x]);
    }

    public int readInt(int y, int x) {
        return (int)Double.parseDouble(readArray[y][x]);
    }

    public String readString(int y, int x) {
        return readArray[y][x];
    }
}
