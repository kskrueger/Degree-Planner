package com.kskrueger.college.tests;

import com.kskrueger.college.util.Website;
import com.kskrueger.college.util.csv.CSVRead;
import com.kskrueger.college.util.csv.CSVWrite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class testing {
    public static void main2(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://catalog.iastate.edu/search/?P=CHEM%20167").get();

        Element name = doc.select("#fssearchresults > div:nth-child(1) > h2").first();

        Element sem = doc.select("#fssearchresults > div:nth-child(1) > div > div > p.credits.noindent").first();

        Element preReq = doc.select("#fssearchresults > div:nth-child(1) > div > div > p.prereq").first();

        System.out.println(name.text());
        System.out.println(sem.text());
        System.out.println(preReq.text());
    }


    private static CSVRead csvRead = new CSVRead("src/com/kskrueger/college/data/Courses/AllCourses.csv");
    private static CSVWrite csvWrite = new CSVWrite();


    private static Website course;
    private static String url;

    public static void main(String[] args) throws IOException {
        String sem = "#courseinventorycontainer > div > div:nth-child(5) > div.courseblockdesc.accordion-content > p.credits.noindent";
        String prereq = "#courseinventorycontainer > div > div:nth-child(5) > div.courseblockdesc.accordion-content > p.prereq > em";
        String description = "#courseinventorycontainer > div > div:nth-child(5) > div.courseblockdesc.accordion-content > p.prereq > br";
        String title = "#courseinventorycontainer > div > div:nth-child(5) > div.courseblocktitle > a > strong";

        String departmentName = "Cpr E";

        departmentName = departmentName.replace(" ", "_");

        url = "http://catalog.iastate.edu/azcourses/"+departmentName.toLowerCase();
        course = new Website(url);

        ArrayList<String> courseOutput = new ArrayList<>();
        String xpath;
        for (int i = 5; i<200; i++) {
            try {
                xpath = course.getText("#courseinventorycontainer > div > div:nth-child("+i+") > div.courseblockdesc.accordion-content > p.credits.noindent");
                courseOutput.add(xpath);
                System.out.println(xpath);
            } catch (NullPointerException e) {
                //System.out.println(i + ": null");
                //e.printStackTrace();
            }
        }
    }

    public void test () throws IOException {
        String[] allCourses = csvRead.readArray[0];
        String[][] out = new String[allCourses.length][1];
        for (int i = 0; i < allCourses.length; i++) {
            out[i][0] = allCourses[i];
        }
        csvWrite.saveRaw(out,"src/com/kskrueger/college/data/Courses/AllCourses2.csv");
    }
}