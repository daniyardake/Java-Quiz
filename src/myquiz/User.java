/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myquiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author daniyar
 */
public class User {

    String name;
    String id;
    int attempts = 0;
    //File usersFile = new File(System.getProperty("user.dir") + "/src/questionBank/users.txt");
    File usersFile = new File(new myFiles("users", 0).getFile());
    FileWriter newUserAddtoDatabase = new FileWriter(usersFile, true);

    public User(String n, String i) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(usersFile);
        Scanner scan = new Scanner(fr);

        boolean isFound = false;
        String tempName = "";
        String tempId = "";
        String tempAttempts = "";

        while (scan.hasNext() && !isFound) {
            tempName = scan.nextLine();
            tempId = scan.nextLine();
            tempAttempts = scan.nextLine();
            isFound = tempName.equals(n) && tempId.equals(i);
        }

        if (isFound) {
            name = n;
            id = i;
            attempts = Integer.parseInt(tempAttempts);
            System.out.println("User Found");
        } else {
            name = n;
            id = i;
            attempts = 0;
            newUserAddtoDatabase.write(name + "\n");
            newUserAddtoDatabase.write(id + "\n");
            newUserAddtoDatabase.write(attempts + "\n");
            System.out.println("User was added");
            newUserAddtoDatabase.close();

        }

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getAttempts() throws FileNotFoundException {
        FileReader fr = new FileReader(usersFile);
        Scanner scan = new Scanner(fr);
        String tempId = "";
        boolean flag = false;
        while (scan.hasNextLine()) {
            tempId = scan.nextLine();
            tempId = scan.nextLine();
            if (tempId.equals(id)) {
                flag = true;
            }
            tempId = scan.nextLine();
            if (flag) {
                return Integer.parseInt(tempId);
            }
        }
        return 0;
    }

    public void setName(String n1) {
        name = n1;
    }

    public void setId(String i1) {
        id = i1;
    }

    public void setAttempts(int a1) throws IOException {
        FileReader fr = new FileReader(usersFile);
        Scanner scan = new Scanner(fr);
        ArrayList<String> oldFiles = new ArrayList<String>();
        String tempId = "";
        int newInt = a1;
        boolean flag = false;

        while (scan.hasNextLine()) {
            tempId = scan.nextLine();
            oldFiles.add(tempId);

            tempId = scan.nextLine();
            oldFiles.add(tempId);
            if (tempId.equals(id)) {
                flag = true;
            }
            tempId = scan.nextLine();
            if (flag) {
                oldFiles.add(Integer.toString(newInt));
                flag = false;
            } else {
                oldFiles.add(tempId);
            }
        }

        System.out.println(oldFiles);
        File file = new File(new myFiles("temp", 0).getFile());
        file.createNewFile();

        FileWriter tempWritter = new FileWriter(file, true);
        for (int i = 0; i < oldFiles.size(); i++) {
            tempWritter.write(oldFiles.get(i) + "\n");
        }
        tempWritter.close();
        usersFile.delete();
        file.renameTo(new File(new myFiles("users", 0).getFile()));

        //change line 3*getNofUser-2 to a1
    }

    public int getNofUser() throws FileNotFoundException {
        FileReader fr = new FileReader(usersFile);
        Scanner scan = new Scanner(fr);

        int i = 0;
        String tempId;
        while (scan.hasNextLine()) {
            i++;
            tempId = scan.nextLine();
            tempId = scan.nextLine();
            if (tempId.equals(this.id)) {
                return i;
            }
            tempId = scan.nextLine();
        }
        return 0;
    }

}
