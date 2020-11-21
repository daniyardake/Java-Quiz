/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myquiz;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author daniyar
 */
public class Results {

    User user;
    int result;

    public Results(User user, int result) {
        this.user = user;
        this.result = result;
    }

    public Stage showGUI() {
        Stage stage = new Stage();
        stage.setHeight(600);
        stage.setWidth(1000);
        GridPane gp = new GridPane();
        Scene scene = new Scene(gp);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        stage.setScene(scene);

        Text resultText = new Text();
        resultText.setText("Dear " + user.getName() + "!\nCongratulation on finishing your test!\n"
                + "You answered " + result + " questions correct\n"
                + "Your row score is: " + convert(result));
        gp.add(resultText, 1, 1);

        Text rankText = new Text();

        if (convert(result) > 84.9) {
            rankText.setText("You have achieved Title of" + " Java Certified Architect!");
        } else if (convert(result) > 75) {
            rankText.setText("You have achieved Title of" + " Java Certified Developer!");
        } else if (convert(result) > 65) {
            rankText.setText("You have achieved Title of" + " Java Certified Programmer!");
        } else {
            rankText.setText("You haven't achieved any titles");
        }

        gp.add(rankText, 1, 2);
        return stage;
    }

    double convert(int n) {
        double d = (160*n-800-n*n+0.0)/20;
        return d;
    }

}
