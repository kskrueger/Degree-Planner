package com.kskrueger.college.tests;

import com.kskrueger.college.util.csv.CSVRead;
import com.kskrueger.college.util.csv.CSVWrite;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PDFTestExtractor extends Component {

    private JFileChooser fc;
    private static File inputFile;

    private PDFTestExtractor() {
        fc = new JFileChooser();
        selectFilePopup();
    }

    private void selectFilePopup() {
        int returnVal = fc.showOpenDialog(PDFTestExtractor.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            inputFile = fc.getSelectedFile();
        } else {
            System.exit(0);
        }
    }

    private static CSVRead csvRead = new CSVRead("src/com/kskrueger/college/data/Courses/AllCourses2.csv");
    private static CSVWrite csvWriteOut = new CSVWrite();

    public static void main(String[] args) throws IOException {
        new PDFTestExtractor();
        System.out.println(readName(inputFile));
        String pdfText = readParaFromPDF(inputFile);
        String[][] allCourses = csvRead.readArray;
        ArrayList<String> courseList = new ArrayList<>();
        for (int i = 0; i < allCourses[0].length; i ++) {
            String course = allCourses[0][i];
            if (pdfText.contains(course)) {
                courseList.add(course);
                //System.out.println(course);
            }
        }
        System.out.println(courseList);
        String[][] out = new String[1][courseList.size()];
        for (int i = 0; i < courseList.size(); i++) {
            out[0][i] = courseList.get(i);
        }
        csvWriteOut.saveRaw(out,"src/com/kskrueger/college/data/Audits/"+readName(inputFile));
    }

    public static String readParaFromPDF(File pdfPath) {
        String returnString = "";
        try {
            PDDocument document = PDDocument.load(pdfPath);
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();
                returnString = tStripper.getText(document);
                document.close();
            }
        } catch (Exception e) {
            returnString = "No ParaGraph Found";
        }
        return returnString;
    }

    public static String readName(File pdfPath) {
        String name = pdfPath.getName();
        if (name.indexOf(".")>0) {
            name = name.substring(0,name.indexOf("."));
        }
        return name;
    }
}