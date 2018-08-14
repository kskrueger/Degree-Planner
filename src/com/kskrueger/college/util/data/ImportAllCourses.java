package com.kskrueger.college.util.data;

import com.kskrueger.college.util.csv.CSVRead;
import com.kskrueger.college.util.csv.CSVWrite;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ImportAllCourses {
    private static CSVRead csvRead = new CSVRead("src/com/kskrueger/college/data/Courses/Departments.csv");
    private static FetchCatalogCourses courses;
    private static CSVWrite csvWrite = new CSVWrite();

    public static void main(String[] args) throws IOException {
        courses = new FetchCatalogCourses(csvRead.readArray[0]);
        String[] allCourses = courses.pullAllCourses();
        writeArray("CoursesExport",allCourses);
        String[][] out = new String[allCourses.length][1];
        for (int i = 0; i < allCourses.length; i++) {
            out[i][0] = allCourses[i];
        }
        csvWrite.saveRaw(out,"src/com/kskrueger/college/data/Courses/AllCourses3.csv");
    }

    private static void writeArray(String filename, String[] x) throws IOException {
        BufferedWriter outputWriter;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        outputWriter.write(Arrays.toString(x));
        outputWriter.flush();
        outputWriter.close();
    }
}
