/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myquiz;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author daniyar
 */
public class InstructionsGUI {

    User user;

    public InstructionsGUI(User user) {
        this.user = user;
        System.out.println("instructions gui");
    }

    public Stage showGUI() {
        Stage stage = new Stage();
        stage.setHeight(600);
        stage.setWidth(1000);
        GridPane gp = new GridPane();
        Scene scene = new Scene(gp);
        stage.setScene(scene);

        TextArea instructionText = new TextArea();
        instructionText.setEditable(false);
        instructionText.setText("This is Java sertification examination by Daniyar Aubekerov\n"
                + "You will be given 10 minutes to complete 25 question\n"
                + "You are allowed to use calculator\n"
                + "Once you are ready hit the START button\n"
                + "You can use SKIP button only 5 times"
        );

        Button startBtn = new Button("START TIMER");

        startBtn.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    gp.getChildren().removeAll(startBtn, instructionText);
                    QuizGui quizGui = new QuizGui(user);
                    quizGui.showGUI().show();
                    stage.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(InstructionsGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Button exit = new Button("EXIT");
        exit.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                System.exit(0);
            }
        });
        gp.add(exit, 2, 2);
        gp.add(startBtn, 1, 2);
        gp.add(instructionText, 1, 1);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);

        return stage;

    }

}