/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myquiz;

/**
 *
 * @author daniyar
 */
public class myFiles {

    String description;
    String filePath;

    public myFiles(String description, int level) {
        this.description = description;

        if (description.equals("level")) {
            filePath = System.getProperty("user.dir") + "/src/questionBank/level" + level + ".txt";
        } else if (description.equals("users")) {
            filePath = System.getProperty("user.dir") + "/src/questionBank/users.txt";
        } else if (description.equals("temp")) {
            filePath = System.getProperty("user.dir") + "/src/questionBank/temp.txt";
        }

    }

    public String getFile() {
        return filePath;
    }
}
