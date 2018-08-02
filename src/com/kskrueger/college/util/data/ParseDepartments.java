package com.kskrueger.college.util.data;

import com.kskrueger.college.util.csv.CSVWrite;

import java.io.*;
import java.util.Arrays;

public class ParseDepartments {
    private static String[] departments;
    private static final String fileName = "src/com/kskrueger/college/tests/Departments.txt";
    private static CSVWrite csvWrite = new CSVWrite();

    public static void main(String[] args) throws IOException {
        departments = new String[lineCount(fileName)];
        System.out.println("Line count: "+departments.length);

        String text = readText(fileName);
        System.out.println(text);

        writeArray("DepartmentExport",departments);
        String[][] out = new String[departments.length][];
        out[0] = departments;
        csvWrite.saveRaw(out,"src/com/kskrueger/college/data/Courses/Departments.csv");
    }

    private static String readText(String file) throws IOException {
        String everything;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int count = 0;
            while (line != null) {
                if (line.contains("(") && line.contains(")")) {
                    int start = line.indexOf("(");
                    int end = line.indexOf(")");
                    departments[count] = line.substring(start+1,end);
                    count += 1;
                }
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        }
        return everything;
    }

    private static int lineCount(String file) throws IOException {
        int lines = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                if (line.length()>1) {
                    lines++;
                }
                line = br.readLine();
            }
        }
        return lines;
    }

    private static void writeArray(String filename, String[] x) throws IOException{
        BufferedWriter outputWriter;
        outputWriter = new BufferedWriter(new FileWriter(filename));

        outputWriter.write(Arrays.toString(x));

        outputWriter.flush();
        outputWriter.close();
    }
}
