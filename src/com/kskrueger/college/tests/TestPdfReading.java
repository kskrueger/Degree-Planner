package com.kskrueger.college.tests;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TestPdfReading extends Component {
    private JFileChooser fc;
    private static File inputFile;

    private TestPdfReading() {
        fc = new JFileChooser();
        selectFilePopup();
    }

    public static void main(String args[]) {
        new TestPdfReading();
        try {
            String text = getText(inputFile);
            System.out.println("Text in PDF: " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectFilePopup() {
        int returnVal = fc.showOpenDialog(TestPdfReading.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            inputFile = fc.getSelectedFile();
        }
    }

    static String getText(File pdfFile) throws IOException {
        PDDocument doc = PDDocument.load(pdfFile);
        return new PDFTextStripper().getText(doc);
    }
}
