/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myquiz;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author daniyar
 */
public class QuizGui {

    User user;
    Quiz quiz = new Quiz();
    boolean isUsed[][] = new boolean[4][25];

    int currentLevel;
    int currentQuestion;
    int correctAns = 0;
    int skipHandler = 0;
    int questionN = 1;
//    Question[] question;

    public QuizGui(User user) throws FileNotFoundException {
        this.user = user;
        int level = 0;
        int number = 0;

        System.out.println("quiz gui");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 25; j++) {
                isUsed[i][j] = false;
            }
        }
    }

    public Stage showGUI() {
        Stage stage = new Stage();

        setStage(quiz.getQuestionBank(currentLevel).getQuestions()[currentQuestion], stage, 600);

        return stage;
    }

    public void setStage(Question q, Stage stage, int timeLeft) {

        isUsed[currentLevel][currentQuestion] = true;
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.setTitle("Welcome");
        GridPane gp = new GridPane();

        Text qText = new Text("Question N: " + questionN);
        gp.add(qText, 0, 0);
        gp.setVgap(10);
        TextArea questionArea = new TextArea(); //questio
        questionArea.setEditable(false);
        questionArea.setText(q.getStatement());
        gp.add(questionArea, 1, 1);

        Label timeLabel = new Label();
        timeLabel.setStyle("-fx-font-size: 11pt;\n" +
            "     -fx-font-family: \"Segoe UI Semibold\";\n" +
            "    -fx-text-fill: blue;\n" +
            "    -fx-opacity: 0.6;");
        gp.add(timeLabel, 2, 0);

        gp.setAlignment(Pos.CENTER);
        ToggleGroup tg = new ToggleGroup();

        RadioButton q1 = new RadioButton();
        q1.setSelected(true);
        RadioButton q2 = new RadioButton();
        RadioButton q3 = new RadioButton();
        RadioButton q4 = new RadioButton();
        RadioButton q5 = new RadioButton();

        q1.setToggleGroup(tg);
        q2.setToggleGroup(tg);
        q3.setToggleGroup(tg);
        q4.setToggleGroup(tg);
        q5.setToggleGroup(tg);

        q1.setText(q.getOptions()[0]);
        q2.setText(q.getOptions()[1]);
        q3.setText(q.getOptions()[2]);
        q4.setText(q.getOptions()[3]);
        q5.setText(q.getOptions()[4]);
        VBox box = new VBox(10, q1, q2, q3, q4, q5);
        gp.add(box, 1, 2);

        //<------------------------TIMER BLOCK BEGIN-------------------------------->
        Timer timer = new Timer();
        int tempTime;

        AtomicInteger myTime = new AtomicInteger(timeLeft);

        synchronized (myTime) {

            timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    Platform.runLater(() -> {
                        if (myTime.get() == 0) {
                            try {
                                user.setAttempts(user.getAttempts() + 1);
                            } catch (IOException ex) {
                                Logger.getLogger(QuizGui.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            timer.cancel();
                            Results res = new Results(user, correctAns);
                            res.showGUI().show();
                            stage.close();
                        } else {
                            myTime.set(myTime.get() - 1);
                            timeLabel.setText(convertToTimeFormat(myTime.get()));
                        }

                    });

                }

            }, 0, 1000);

        }

//<------------------------TIMER BLOCK END-------------------------------->
        Button submitBtn = new Button("Submit exam");
        submitBtn.setStyle("-fx-background-color: red;\n"
                + "  -fx-text-fill: white;\n"
                + "  -fx-background-radius: 4px;");
        submitBtn.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    timer.cancel();
                    user.setAttempts(user.getAttempts() + 1);
                } catch (IOException ex) {
                    Logger.getLogger(QuizGui.class.getName()).log(Level.SEVERE, null, ex);
                }
                Results res = new Results(user, correctAns);
                res.showGUI().show();
                stage.close();

            }
        });
        gp.add(submitBtn, 1, 3);

        Button nextButton = new Button("Next Question");
        nextButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                questionN++;
                boolean isUp = true;
                int responceIndex = 0;
                if (q1.isSelected()) {
                    responceIndex = 0;
                }
                if (q2.isSelected()) {
                    responceIndex = 1;
                }
                if (q3.isSelected()) {
                    responceIndex = 2;
                }
                if (q4.isSelected()) {
                    responceIndex = 3;
                }
                if (q5.isSelected()) {
                    responceIndex = 4;
                }

                isUp = responceIndex == q.getIndexOfCorrectAns();

                if (isUp) {
                    correctAns++;
                    if (currentLevel != 3) {
                        currentLevel++;
                    }
                } else {
                    if (currentLevel != 0) {
                        currentLevel--;
                    }
                }
                currentQuestion = getUnusedQuestionNumber(currentLevel, isUsed, quiz);
                System.out.println(currentLevel);
                synchronized (myTime) {
                    setStage(quiz.getQuestionBank(currentLevel).getQuestions()[currentQuestion], stage, myTime.get());
                }

            }
        });
        nextButton.setStyle("-fx-background-color: blue;\n"
                + "  -fx-text-fill: white;\n"
                + "  -fx-background-radius: 4px;");
        gp.add(nextButton, 2, 4);

        if (questionN == 20) {
            nextButton.setVisible(false);
        }

        Button skipButton = new Button("Skip Question");
        skipButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                skipHandler++;
                if (skipHandler < 6) {
                    currentQuestion = getUnusedQuestionNumber(currentLevel, isUsed, quiz);
                    setStage(quiz.getQuestionBank(currentLevel).getQuestions()[currentQuestion], stage, myTime.get());
                } else {
                    Alert newAlert = new Alert(Alert.AlertType.ERROR);
                    newAlert.show();
                }

            }
        });
        skipButton.setStyle("-fx-background-color: blue;\n"
                + "  -fx-text-fill: white;\n"
                + "  -fx-background-radius: 4px;");
        gp.add(skipButton, 1, 4);
        if (skipHandler == 5) {
            skipButton.setVisible(false);
            gp.add(new Text("You have used all skip options"), 1, 4);
        }
        Scene scene = new Scene(gp);
        stage.setScene(scene);
    }

    int getUnusedQuestionNumber(int level, boolean isUsed[][], Quiz quiz) {
        Random random = new Random();
        int unusedQuestion = random.nextInt(25);
        while (isUsed[level][unusedQuestion]) {
            unusedQuestion = random.nextInt(25);
        }
        isUsed[level][unusedQuestion] = true;
        return unusedQuestion;
    }
    
    String convertToTimeFormat(int seconds){
        int minutes = seconds/60;
        int secs = seconds - (minutes)*60;
        if (secs<10){
            return ("Time Left: 0"+minutes+":0"+secs);
        }
        return ("Time Left: 0"+minutes+":"+secs);
    }

}
