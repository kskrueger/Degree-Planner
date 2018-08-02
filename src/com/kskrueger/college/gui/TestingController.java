package com.kskrueger.college.gui;

import com.kskrueger.college.util.Course;
import com.kskrueger.college.util.csv.CSVRead;
import com.kskrueger.college.util.csv.CSVWrite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestingController {
    @FXML
    private Button testButton;

    @FXML
    private Button button2;

    @FXML
    private TextField field;

    @FXML
    private ListView<String> list;

    private ObservableList<String> courseList = FXCollections.observableArrayList();
    private Course course;


    TestingApplication application = new TestingApplication();

    public void press() {
        System.out.println("Press pressed and text field says " + field.getCharacters());
        list.setEditable(true);
        for(int i = 0; i < courseList.size(); i++) {
            int colonPosition = courseList.get(i).indexOf(":");
            String courseNum = courseList.get(i).substring(0,colonPosition-1);
            course = new Course(courseNum);
            courseList.set(i,courseNum + " : " + course.getDescription());
        }
        list.setItems(courseList);
    }

    public void add() {
        String input = field.getCharacters().toString();
        course = new Course(input);
        list.setEditable(true);
        courseList.add(course.getCourseNumber()+" : "+course.getRealName());
        list.setItems(courseList);
    }

    public void delete() {
        list.setEditable(true);
        courseList.remove(/*courseList.size()-1*/list.getEditingIndex());
        list.setItems(courseList);
    }

    public void load() {
        try {
            loader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateArray() {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                for (String aCourseList : courseList) {
                    int colonPosition = aCourseList.indexOf(":");
                    String courseNum = aCourseList.substring(0, colonPosition - 1);
                    course = new Course(courseNum);
                }
            }
        }).start();
    }

    private File inputFile;

    private void selectFilePopup() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        inputFile = fileChooser.showOpenDialog(application.overallStage);
        System.out.println(inputFile.getAbsolutePath());
    }

    private static CSVRead csvRead = new CSVRead("src/com/kskrueger/college/data/Courses/AllCourses2.csv");
    private static CSVWrite csvWriteOut = new CSVWrite();

    private void loader() throws IOException {
        selectFilePopup();
        System.out.println(readName(inputFile));
        String pdfText = readParaFromPDF(inputFile);
        String[][] allCourses = csvRead.readArray;
        ArrayList<String> courseList = new ArrayList<>();
        for (int i = 0; i < allCourses[0].length; i ++) {
            String course = allCourses[0][i];
            if (pdfText.indexOf(course)>0) {
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

        this.courseList.addAll(courseList);
        list.setItems(this.courseList);
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
