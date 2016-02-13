/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Procedure;
import Model.Queue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import static javafx.scene.input.KeyCode.E;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.GroupLayout;

/**
 *
 * @author igorbashka
 */
public class hView  {
    private Stage stage;
    private VBox box = new VBox(10);
    private Label numberOfCustomers = new Label("Ожидающих в очереди: ");
    private Label timeToWait = new Label("Примерное время ожидания: ");
    private Label wish = new Label("Желаете ли вы продолжить ? ");
    private Button yesWait = new Button("Да");
    private Button noWait = new Button("Нет");
    private List <Button> buttons = new ArrayList<>();
    private ObservableList<Node> buttonsAdd;
    private Queue queue = new Queue();
    private long expectedTime;
    private Procedure proc;
    public int numOfCustomers;
    
    public hView(Stage stage){
    this.stage = stage;    
    }
    public void start(){
        setMainBox();
        operatorWindow();
    }
    private void setButtons(){
        Button deposit = new Button("Открыть депозит");
        deposit.setPrefWidth(200);
        deposit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
               expectedTime = queue.getExpectedTime("Deposit");
               numOfCustomers = queue.getNumOfCustomers("Deposit");
               wishWindow(expectedTime, numOfCustomers, "Deposit");
           }
        });
        Button credit = new Button("Взять кредит");
        credit.setPrefWidth(200);
        credit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                wishWindow(queue.getExpectedTime("Credit"), queue.getNumOfCustomers("Credit"),
                        "Credit");
            }
        });
        Button other = new  Button("Остальное");
        other.setPrefWidth(200);
        other.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                String name = "Other";
                wishWindow(queue.getExpectedTime(name), queue.getNumOfCustomers(name),
                        name);
            }
        });
        buttons.add(deposit);
        buttons.add(credit);
        buttons.add(other);
        this.buttonsAdd = FXCollections.observableArrayList(buttons);
    }
    private void setMainBox(){
        setButtons();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(buttonsAdd);
        Scene mainScene = new Scene(box, 250, 150);
        stage.setScene(mainScene);
        stage.show();
    }
    
   private void wishWindow(long expectedTime, int numOfCustomers, String name){
   
    Stage hintStage = new Stage();
    hintStage.initOwner(stage);
    hintStage.initModality(Modality.APPLICATION_MODAL);
    setWishButton(hintStage, name, expectedTime);
    GridPane wishWindow = new GridPane();
    Label numOfCus = new Label(Integer.toString(numOfCustomers));
    Label timeToWait = new Label(Long.toString(expectedTime/1000)+" секунд");
    wishWindow.setVgap(10);
    wishWindow.setHgap(10);
    wishWindow.setPadding(new Insets(0,10,0,10));
    wishWindow.add(this.numberOfCustomers, 0,0); //ads people in the queue label
    wishWindow.add(numOfCus, 1, 0);
    wishWindow.add(this.timeToWait, 0, 1);
    wishWindow.add(timeToWait, 1, 1);
    wishWindow.add(this.wish, 0, 2);
    wishWindow.add(this.yesWait, 0, 3);
    wishWindow.add(this.noWait, 1, 3);
   Scene wishScene = new Scene(wishWindow, 320, 130);
   hintStage.setScene(wishScene);
   hintStage.show();
   }
   
   private void setWishButton(Stage wishStage, String name, long expectedTime){
       yesWait.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
            proc = new Procedure(name, expectedTime);
            queue.addToQueue(proc);
            wishStage.close();
           }
       });
       noWait.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
               wishStage.close();
           }
       });
   }
   private void operatorWindow(){
       Stage operator = new Stage();
       VBox operatorBox = new VBox(10);
       operatorBox.setAlignment(Pos.CENTER);
       Button removeDep = new Button("Закончено Депозит");
       removeDep.setPrefWidth(200);
       Button removeCre = new Button("Закончено Кредит");
       removeCre.setPrefWidth(200);
       Button removeOth = new Button("Закончено остальное");
       removeOth.setPrefWidth(200);
       removeDep.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
               queue.removeFromQueue("Deposit");
           }
       });
       removeCre.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
               queue.removeFromQueue("Credit");
           }
       });
       removeOth.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
               queue.removeFromQueue("Other");
           }
       });
       operatorBox.getChildren().addAll(removeDep, removeCre, removeOth);
       Scene operatorScene = new Scene(operatorBox, 250, 150);
       operator.setScene(operatorScene);
       operator.setX(900);
       operator.setY(400);
       operator.show();
   }
}
