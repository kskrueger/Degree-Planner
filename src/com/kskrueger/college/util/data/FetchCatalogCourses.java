package com.kskrueger.college.util.data;

import com.kskrueger.college.util.Website;

import java.util.ArrayList;
import java.util.Arrays;

class FetchCatalogCourses {
    private Website course;
    private String url;
    private String[] departmentNames;

    FetchCatalogCourses(String departmentName) {
        departmentName = departmentName.replace(" ","_");
        this.url = "http://catalog.iastate.edu/azcourses/"+departmentName.toLowerCase();
        this.course = new Website(url);
    }

    FetchCatalogCourses(String[] departmentNames) {
        this.departmentNames = departmentNames;
    }

    String[] pullAllCourses() {
        ArrayList<String> output = new ArrayList<>();
        for (String name : departmentNames) {
            name = name.replace(" ","_");
            this.url = "http://catalog.iastate.edu/azcourses/"+name.toLowerCase();
            this.course = new Website(url);
            output.addAll(Arrays.asList(pullCourses()));
        }
        return output.toArray(new String[output.size()]);
    }

    public String[] pullCourses() {
        /*String sem = "#courseinventorycontainer > div > div:nth-child(5) > div.courseblockdesc.accordion-content > p.credits.noindent";
        String prereq = "#courseinventorycontainer > div > div:nth-child(5) > div.courseblockdesc.accordion-content > p.prereq > em";
        String description = "#courseinventorycontainer > div > div:nth-child(5) > div.courseblockdesc.accordion-content > p.prereq > br";
        String title = "#courseinventorycontainer > div > div:nth-child(5) > div.courseblocktitle > a > strong";*/

        ArrayList<String> courseOutput = new ArrayList<>();
        String xpath;
        for (int i = 5; i<200; i++) {
            try {
                xpath = course.getText("#courseinventorycontainer > div > div:nth-child("+i+") > div.courseblockdesc.accordion-content > p.prereq > br");
                xpath = xpath.substring(0,xpath.indexOf(":"));
                courseOutput.add(xpath);
                //System.out.println(xpath);
            } catch (NullPointerException e) {
                //System.out.println(i + ": null");
                //e.printStackTrace();
            }
        }
        return courseOutput.toArray(new String[courseOutput.size()]);

    }

    //    //return null;return null;

}
