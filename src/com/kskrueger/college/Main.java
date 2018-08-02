package com.kskrueger.college;

import com.kskrueger.college.util.Course;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static Course course1 = new Course("Math 165");
    private static Course[] courseListings;
    private static ArrayList<String> list = new ArrayList<>();

    public static void main(String[] args) {
	// write your code here
        /*list.add("Math 165");
        list.add("Math 166");
        list.add("Chem 167");
        list.add("Cpr E 185");
        list.add("Com S 203");
        list.add("Com S 230");
        list.add("Com S 227");
        list.add("Com S 228");*/

        list.addAll(Arrays.asList(
                "Math 165",
                "Com S 227",
                "Chem 167",
                "Com S 101",
                "Math 166",
                "Com S 309",
                "Com S 327",
                "Com S 331",
                "Com S 362"));

        courseListings = new Course[list.size()];

        for(int i = 0; i < list.size(); i++) {
            courseListings[i] = new Course(list.get(i));
            System.out.println(courseListings[i].getRealName() + " : " + courseListings[i].getPrereqs());
        }

        singleCourse();
    }

    public static void singleCourse() {
        System.out.println(course1.getFullName());
        System.out.println(course1.getCourseNumber());
        System.out.println(course1.getRealName());
        System.out.println(course1.getDept());
        System.out.println(course1.getNumber());
        System.out.println(course1.getPrereqs());
        System.out.println(course1.getDescription());
    }

}
