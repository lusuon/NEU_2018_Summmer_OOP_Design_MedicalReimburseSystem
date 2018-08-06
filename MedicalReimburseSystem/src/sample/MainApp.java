package sample;
/**
 * @author 马杰生
 * @date 2018-7-30
 * 主类：加载GUI
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Model.*;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MainApp extends Application {
    /**
     * 加载GUI容器
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("医保报销系统_1711_20175364_马杰生");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);//禁止最大化
        primaryStage.show();
        }

    public MainApp(){        }




    public static void main(String[] args) {
        launch(args);
    }






}
