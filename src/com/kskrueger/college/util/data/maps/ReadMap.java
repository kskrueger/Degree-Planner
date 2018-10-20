package com.kskrueger.college.util.data.maps;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadMap {
    //static Map<String, Course> courseMap = new HashMap<>();
    static CourseMap courseMap = new CourseMap();
    static NodeMap nodeMap = new NodeMap();

    public static void main(String[] args) {
        //courseMap = readCourseMap("courseMap");
        //System.out.println(courseMap.get("COM S 252").prereqs);

        nodeMap = readNodeMap("testNodeMap");
        System.out.println(nodeMap.get("Com S 207").course.getCourseNumber());
        //courseMap = readCourseMap("courseMap");
        //System.out.println(courseMap.get("Com S 207").getRealName());

    }

    public static CourseMap readCourseMap(String fileName) {
        String contents = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            contents = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        return gson.fromJson(contents,CourseMap.class);
    }

    public static NodeMap readNodeMap(String fileName) {
        String contents = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            contents = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        return gson.fromJson(contents, NodeMap.class);
    }
}

