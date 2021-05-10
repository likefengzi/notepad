package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Mysearch implements Initializable {

    @FXML
    private Button bdown;

    @FXML
    private Button br;

    @FXML
    private Button bs;

    @FXML
    private TextField t1;

    @FXML
    private TextField t2;

    @FXML
    private Button brall;
    //搜索文本
    private String searchstr;
    //替换文本
    private String replacestr;
    public Controller controller;
    public Global global;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //加载全局变量
        global = (Global) Main.globals.get("global");
        controller = (Controller) Main.controllers.get(Controller.class.getSimpleName());
    }

    //搜索
    @FXML
    private void search() {
        searchstr = t1.getText();
        replacestr = t2.getText();
        global.search = searchstr;
        global.replace = replacestr;
        controller.search();

    }

    //替换
    @FXML
    private void replace() {
        searchstr = t1.getText();
        replacestr = t2.getText();
        global.search = searchstr;
        global.replace = replacestr;
        controller.replace();
    }

    //替换全部
    @FXML
    private void replaceall() {
        searchstr = t1.getText();
        replacestr = t2.getText();
        global.search = searchstr;
        global.replace = replacestr;
        controller.replaceall();
    }
}
