package com.kskrueger.college.util.csv;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVConvert {
    private static CSVRead csvRead = new CSVRead("src/com/kskrueger/college/data/Courses/AllCourses2.csv");

    public static void main(String[] args) {
        String[][] read = csvRead.readArray;
        ArrayList<String> courseList = new ArrayList<>();

        courseList.addAll(Arrays.asList(read[0]));

        save(courseList,"CourseList");
    }

    private static void save(ArrayList list, String fileName) {
        Gson gson = new Gson();
        String contents = gson.toJson(list);

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(contents);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
