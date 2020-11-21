import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;



public class myTimerController extends TimerTask {
    int timeTemp = 10;
    Label label;
    
    
    
    public myTimerController(Label label){
        this.label = label;
    }
    
    public void setLabel(Label l){
        label = l;
    }

    @Override
    public void run() {
        if(label!=null){
            Platform.runLater(()->{
                
                if(timeTemp == 0){
                        this.cancel();
                    }
                    
                    System.out.println(timeTemp);
                    label.setText(Integer.toString(timeTemp));
                    timeTemp--;        
                label.setText(Integer.toString(timeTemp));
            });
        }
        //row new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}