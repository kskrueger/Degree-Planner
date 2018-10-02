package com.kskrueger.college.other;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

class Site {
    private Document site;

    Site(String url) {
        try {
            site = Jsoup.connect(url).get();
        } catch (IOException e) {
            //e.printStackTrace();
            //Course not found!
        }
    }

    String getText(String path) {
        try {
            return site.select(path).first().text();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("DOES NOT EXIST!");
            return "";
        }
    }

}
