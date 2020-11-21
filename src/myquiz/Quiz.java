/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myquiz;

import java.io.FileNotFoundException;

/**
 *
 * @author daniyar
 */
public class Quiz {

    QuestionBank[] questionBank = new QuestionBank[4];

    public Quiz() throws FileNotFoundException {
        questionBank[0] = new QuestionBank(1);
        questionBank[1] = new QuestionBank(2);
        questionBank[2] = new QuestionBank(3);
        questionBank[3] = new QuestionBank(4);
    }

    public QuestionBank getQuestionBank(int level) {
        return questionBank[level];
    }

}
