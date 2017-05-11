/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Mohammad
 */
public class manager_class {
    public static void main(String[] args) {
        FileManager.generateExam("pl2.xml", 60, "new.pdf");
        /*
        System.out.println(FileManager.getAvaliableID("pl2.xml"));
        /*Question w = FileManager.searchQuestion("Pl2.xml", 1);
        System.out.println(w);
/*
        
        /*
        Question q = new Question(3);
        q.QuestionText = "Hello, from Egypt";
        q.setAnswer("Answer 1",0, Boolean.FALSE);
        q.setAnswer("Answer 2",1, Boolean.TRUE);
        q.setAnswer("Answer 3",2, Boolean.TRUE);
        q.setAnswer("Answer 4",3, Boolean.TRUE);
        //FileManager.editQuestion("pl2.xml", q, 3);
        Boolean t = FileManager.deleteQuestion("pl2.xml", 3);
        System.out.println(t);
        /*
        
        /*
        System.out.println(q.AnswersList[0].getAnswerXML());
        for(int i = 0; i < 4; i++) System.out.println (q.AnswersList[i].getAnswerXML());
        */
        //String[] ar = q.toXML().split("/n");
        //for(int i = 0; i < ar.length; i++) System.out.print(ar[i]);
        /*
        Question rf = Question.toQuestion(q.toXML());
        System.out.println(rf.ID + " " + rf.QuestionText);
        for(int i = 0; i < rf.AnswersList.length; i++) {
            System.out.println(rf.AnswersList[i].AnswerText + " ," + rf.AnswersList[i].IsValid);
        }
        /*
        System.out.println("First Before edit : " + q.AnswersList[0].getAnswerXML());
        Answer t = Answer.toAnswer(q.AnswersList[0].getAnswerXML());
        System.out.println(t.AnswerText + " , " + t.IsValid);
        */
        /*
        System.out.println("\n");
        Question.toQuestion(q.toXML());
                */
        //String[] m = q.ansList(q.toXML());
        //for(String t:m) System.out.println(t);
    }

}
