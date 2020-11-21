package myquiz;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginScreen {

    String checkName, checkId;

    public GridPane showGUI() {

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 50, 50, 50));

        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(20, 20, 20, 30));

        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Implementing Nodes for GridPane
        Label lblUserName = new Label("Username");
        final TextField txtUserName = new TextField();
        Label lblPassword = new Label("ID");
        final PasswordField pf = new PasswordField();
        Button btnLogin = new Button("Login");
        final Label lblMessage = new Label();

        //Adding Nodes to GridPane layout
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(txtUserName, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(pf, 1, 1);
        gridPane.add(btnLogin, 2, 1);
        gridPane.add(lblMessage, 1, 2);

        //Reflection for gridPane
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        gridPane.setEffect(r);

        //DropShadow effect 
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        //Adding text and DropShadow effect to it
        Text text = new Text("JavaFX 2 Login");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);

        //Adding text to HBox
        hb.getChildren().add(text);

        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btnLogin.setId("btnLogin");
        text.setId("text");

        //Action for btnLogin
        btnLogin.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                checkName = txtUserName.getText().toString();
                checkId = pf.getText().toString();
                try {
                    boolean isNum = true;
                    for (int i = 0; i < checkId.length(); i++) {
                        if (!Character.isDigit(checkId.charAt(i))) {
                            isNum = false;
                        }
                    }

                    if (checkId.length() == 4 && isNum) {

                        if (!isExistingId(checkId)) {
                            lblMessage.setText("New user has been added to database.");
                            User user = new User(checkName, checkId);
                            lblMessage.setTextFill(Color.RED);
                            InstructionsGUI ig = new InstructionsGUI(user);
                            ig.showGUI().show();
                        } else {
                            if (idMatchesName(checkName, checkId)) {
                                lblMessage.setText("Existing user signed in!");
                                User user = new User(checkName, checkId);

                                if (user.getAttempts() >= 2) {
                                    lblMessage.setText("Sorry, you have used all your attempts");
                                } else {
                                    InstructionsGUI ig = new InstructionsGUI(user);
                                    lblMessage.setTextFill(Color.GREEN);
                                    ig.showGUI().show();
                                }
                            } else {
                                lblMessage.setText("Your name doesn't match id");
                            }
                        }

                    } else {
                        lblMessage.setText("Sorry Your ID MUST BE exactly 4 numbers\nNo letters allowed");
                    }

                } catch (IOException ex) {
                    Logger.getLogger(LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtUserName.setText("");
                pf.setText("");
            }
        });
        return gridPane;
    }

    private boolean isExistingId(String id) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(new myFiles("users", 0).getFile());

        Scanner scan = new Scanner(fr);

        String tempId = "";
        while (scan.hasNextLine()) {
            tempId = scan.nextLine();
            tempId = scan.nextLine();
            if (tempId.equals(id)) {
                fr.close();
                return true;
            }
            tempId = scan.nextLine();

        }
        fr.close();
        return false;
    }

    private boolean idMatchesName(String name, String id) throws FileNotFoundException, IOException {

        FileReader fr = new FileReader(new myFiles("users", 0).getFile());

        Scanner scan = new Scanner(fr);

        while (scan.hasNextLine()) {
            String tempName = scan.nextLine();
            String tempId = scan.nextLine();
            String tempAttempts = scan.nextLine();
            if (tempName.equals(name) && tempId.equals(id)) {
                fr.close();
                return true;
            }

        }

        fr.close();
        return false;
    }

}
