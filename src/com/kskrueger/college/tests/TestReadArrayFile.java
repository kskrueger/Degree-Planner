package com.kskrueger.college.tests;

import com.kskrueger.college.util.csv.CSVRead;

public class TestReadArrayFile {

    static CSVRead reader = new CSVRead("TestCSV.csv");

    public static void main(String[] args) {
        System.out.println(reader.readString(0,0));
    }
}
