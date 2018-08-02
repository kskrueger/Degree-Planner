package com.kskrueger.college.util.csv;

import java.io.IOException;

public class TransposeCSV {

    private static CSVRead csvRead = new CSVRead("src/com/kskrueger/college/data/Courses/AllCourses.csv");
    private static CSVWrite csvWrite = new CSVWrite();

    public static void main(String[] args) throws IOException {
        String[][] allCourses = csvRead.readArray;
        String[][] out = new String[1][allCourses.length];
        for (int i = 0; i < allCourses.length; i++) {
            out[0][i] = allCourses[i][0];
        }
        csvWrite.saveRaw(out,"src/com/kskrueger/college/data/Courses/AllCourses2.csv");
    }

}
