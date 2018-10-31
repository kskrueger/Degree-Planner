package com.kskrueger.college.util.data.maps;

import com.google.gson.Gson;
import com.kskrueger.college.util.Course;
import com.kskrueger.college.util.csv.CSVRead;
import com.kskrueger.college.util.sorting.Node;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveMap {
    //private static Map<String, Course> courseMap = new HashMap<>();
    private static CourseMap courseMap = new CourseMap();
    private static NodeMap nodeMap = new NodeMap();

    private static String[] courses = {"Com S 207","Com S 227","Com S 230","Com S 309","Cpr E 185","Math 165","Math 150","Math 140","Engl 150","Math 143"};

    //static CSVRead csvRead = new CSVRead("src/com/kskrueger/college/data/Courses/AllCourses2.csv");

    public static void main(String[] args) {
        //long startTime = System.currentTimeMillis();

        //courses = csvRead.readArray;

        /*for (String courseName : courses*//*[0]*//*) {
            System.out.println("Course: "+courseName);
            Course course = new Course(courseName);
            courseMap.put(courseName,course);
            Node node = new Node(course);
            nodeMap.put(courseName,node);
        }*/

        //save(courseMap, "courseMap");
        //save(nodeMap,"nodeMap");

        for (String courseName : courses) {
            //System.out.println("Course: "+courseName);
            Course course = new Course(courseName);
            Node node = new Node(course);
            nodeMap.put(courseName.toUpperCase(),node);
        }

        save(nodeMap,"testNodeMap");

        //System.out.println("Total time: "+(System.currentTimeMillis()-startTime));
    }

    private static void save(HashMap map, String fileName) {
        Gson gson = new Gson();
        String contents = gson.toJson(map);

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(contents);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

