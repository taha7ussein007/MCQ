/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;




public class FileManager {
    private static final String header = ""
            + "<Questions>"
            + "\n"
            + "</Questions>";
    
    public static Boolean makeNewXMLFile(String name) {
        File f = new File(name);
        try {
            f.delete();
            f.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(f,true));
            bw.write(header);
            bw.close();
            return Boolean.TRUE;
        }
        catch(Exception e) {return Boolean.FALSE;} // to be enhanced with specific handling
        
    }
    public static String FileToString(String name) {
        File f = new File(name);
        if(f.exists() == false) makeNewXMLFile(name);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            ArrayList<String> list = new ArrayList<>(0);
            String line = "";
            while((line = br.readLine()) != null) {
                list.add(line);
            }
            String Content = "";
            while(list.size() > 0) {
                Content += list.get(0) + '\n';
                list.remove(0);
            }
            br.close();
            return Content;
        }catch(Exception e) {}
        return null;
    }
    public static void addQuestion(String fname, Question q) {
        String Editor = FileToString(fname);
        String end = "</Questions>";
        int writeIndex = Editor.indexOf(end);
        if(writeIndex == -1) {
            makeNewXMLFile(fname);
            Editor = FileToString(fname);
            writeIndex = Editor.indexOf(end);
        }
        String tmp = Editor.substring(writeIndex);
        Editor = Editor.substring(0,writeIndex);
        Editor += q.toXML() + tmp;
        System.gc();
        File f = new File(fname);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(Editor);
            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static ArrayList<Question> getAll(String fname) {
        String Editor = FileToString(fname);
        String Open = "<Question i"; String Close = "</Question>";
        ArrayList<Question> list = new ArrayList<>(0);
        while(Editor.contains("<Question id")) {
            int begIndex = Editor.indexOf(Open);
            int endIndex = Editor.indexOf(Close) + Close.length();
            list.add(Question.toQuestion(Editor.substring(begIndex,endIndex)));
            Editor = Editor.substring(endIndex);
        }
        return list;
    } 
    
    public static String searchQuestion(String fname, String contain) {
        ArrayList<Question> list = getAll(fname);
        String ans = "";
        String t;
        contain = contain.toLowerCase();
        for(Question m: list) {
            t = m.QuestionText.toLowerCase();
            if(t.contains(contain)) {
                ans += m.ID.toString() + ")" + m.QuestionText + '\n';
            }
        }
        return ans;
    }
    
    public static Question searchQuestion(String fname, Integer QuestionID) {
        ArrayList<Question> list = getAll(fname);
        try {
            for(Question t:list) {
                if(t.ID.equals(QuestionID)) return t;
            }
        }catch(Exception ex) {}
        return null;
    }
    
    
     
    public static Boolean editQuestion(String fname, Question newQuestion, Integer ID) {
        ArrayList<Question> list = getAll(fname);
        try {
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).ID.equals(ID)) {
                    list.set(i, newQuestion);
                    updateFile(fname, list);
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }
        catch(Exception ex) {}
        return Boolean.FALSE;
    }
    ////////////////////////////////////////////////////////////////////////
    public static Boolean deleteQuestion(String fname, Integer ID) {
        ArrayList<Question> list = getAll(fname);
        try {
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).ID.equals(ID)) {
                    list.remove(i);
                    updateFile(fname, list);
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }
        catch(Exception ex) {}
        return Boolean.TRUE;
    }

    public static Boolean updateFile(String fname, ArrayList<Question> l) {
        makeNewXMLFile(fname);
        try {
            for(Question t: l) {
                addQuestion(fname, t);
            }
            return Boolean.TRUE;
        }catch(Exception ex) {
            return Boolean.FALSE;
        }
    }
    public static Integer getAvaliableID(String fname) {
        ArrayList<Question> list = getAll(fname);
        if(list.isEmpty()) return 0;
        ArrayList<Integer> intList = new ArrayList<>(0);
        for(Question q: list) {
                intList.add(q.ID);
        }
        for(int i = 0; i < intList.size(); i++) {
            if(i != intList.get(i) && intList.contains(i) == false) return i;
        }
        return list.size();
    }
    
    public static Boolean makePDF(String fname, String wString) {
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fname));
            document.open();
            document.add(new Paragraph("Welcome AT MCQ EXAM"));
            document.add(new Paragraph("\n\n\n\n\n"));
            document.add(new Paragraph(wString));
            document.close();
            writer.close();
        }
        catch(FileNotFoundException | DocumentException e) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    
    public static ArrayList<Question> generateRandomQuestions(String fname, int size) {
        ArrayList<Question> list = getAll(fname);
        Random rand = new Random();
        ArrayList<Question> r = new ArrayList<>(0);
        for(int i = 0; i < size; i++) {
            int v = rand.nextInt(list.size());
            r.add(list.get(v));
            list.remove(v);
        }
        return r;
    }
    
    public static Boolean generateExam(String fname, int exam_size, String target_file_name) {
        try {
            ArrayList<Question> q = generateRandomQuestions(fname, exam_size);
            String r = "";
            int i = 1;
            for(Question t:q) {
                r += i++ + ") " + t.toPDFString();
            }
            makePDF(target_file_name, r);
            return Boolean.TRUE;
        }catch(Exception e) {
            return Boolean.FALSE;
        }
    }
}
