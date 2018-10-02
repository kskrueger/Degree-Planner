package com.kskrueger.college.other;

import com.kskrueger.college.util.Website;
import com.kskrueger.college.util.data.PreReqParsing;

import java.util.*;

public class Result {
    private transient Site website;
    private String url;

    public Result(String url) {
        this.url = url;
        this.website = new Site(url);
    }

    public String getUrl() {
        return this.url;
    }

    public String results() {
        return website.getText("#content > div > ul > li:nth-child(1) > a > span.dir-Listing-item-name");
        //#content > div > ul > li:nth-child(17) > a > span.dir-Listing-item-name
        //1-20 per page
    }

    public HashMap<String, String> resultsList() {
        HashMap<String, String> outputList = new HashMap<String, String>();
        for (int i = 1; i <=20; i++) {
            String name = website.getText("#content > div > ul > li:nth-child("+i+") > a > span.dir-Listing-item-name");
            System.out.println("Name: "+name);
            String url = website.getText("#content > div > ul > li:nth-child("+i+") > a.href");
            System.out.println("URL: "+url);
            outputList.put(name,url);
            //#content > div > ul > li:nth-child(1) > a> resultsList()
        }
        return outputList;
    }

    public String getText (String path) {
        return website.getText(path);
    }

}
