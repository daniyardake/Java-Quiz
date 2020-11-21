/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myquiz;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author daniyar
 */
public class QuestionBank {

    Question question[] = new Question[25];
    int level;
    File questionBankFile;

    public QuestionBank(int level) throws FileNotFoundException {
        this.level = level;

        questionBankFile = new File(new myFiles("level", level).getFile());
        FileReader fr = new FileReader(questionBankFile);
        Scanner scan = new Scanner(fr);

        int i = 0;
        String tempStatement = "";
        String tempOption[] = new String[5];
        String tempIndex;
        while (scan.hasNextLine()) {
            tempStatement = scan.nextLine();
            tempOption[0] = scan.nextLine();
            tempOption[1] = scan.nextLine();
            tempOption[2] = scan.nextLine();
            tempOption[3] = scan.nextLine();
            tempOption[4] = scan.nextLine();
            tempIndex = scan.nextLine();

            question[i] = new Question(tempStatement, tempOption[0], tempOption[1], tempOption[2], tempOption[3], tempOption[4], Integer.parseInt(tempIndex));
            question[i].setIsUsed(false);
            i++;
        }

    }

    public Question[] getQuestions() {
        return question;
    }
}
