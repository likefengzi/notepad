package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Myfont implements Initializable {
    @FXML
    private TextArea t4;

    @FXML
    private Button b2;

    @FXML
    private ComboBox<String> c;

    @FXML
    private ListView<String> l1;

    @FXML
    private ListView<String> l2;

    @FXML
    private ListView<String> l3;

    @FXML
    private TextField t1;

    @FXML
    private TextField t2;

    @FXML
    private TextField t3;

    @FXML
    private Button b1;
    //中文预览的字符串
    private static final String CH_STRING = "记事本";
    //英文预览的字符串
    private static final String EN_STRING = "notepad";
    //数字预览的字符串
    private static final String NUMBER_STRING = "0123456789";
    public Controller controller;
    public Global global;
    //字体颜色
    private String color;
    // 预设字体，也是将来要返回的字体
    private Font font = null;
    // 所有字体
    private ObservableList<String> fontArray = null;
    // 所有样式
    private ObservableList<String> styleArray = FXCollections.observableArrayList("常规", "粗体", "斜体");
    // 所有预设字体大小
    private ObservableList<String> sizeArray = FXCollections.observableArrayList("8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72", "初号", "小初", "一号", "小一", "二号", "小二", "三号", "小三", "四号", "小四", "五号", "小五", "六号", "小六", "七号", "八号");
    // 上面数组中对应的字体大小
    private int[] sizeIntArray = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72, 42, 36, 26, 24, 22, 18, 16, 15, 14, 12, 10, 9, 8, 7, 6, 5};
    //所有颜色
    private ObservableList<String> colorArray = FXCollections.observableArrayList("BLACK", "AQUA", "BLUE", "CORAL", "GREEN", "GREY", "RED", "WHITE", "YELLOW");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //加载全局变量
        global = (Global) Main.globals.get("global");
        controller = (Controller) Main.controllers.get(Controller.class.getSimpleName());
        //读取系统字体
        GraphicsEnvironment eq = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fontArray = FXCollections.observableArrayList(eq.getAvailableFontFamilyNames());
        //初始化listview和combobox
        l1.setItems(fontArray);
        l2.setItems(styleArray);
        l3.setItems(sizeArray);
        c.setItems(colorArray);
        //textfield和listview绑定
        t1.textProperty().bind(l1.getSelectionModel().selectedItemProperty());
        t2.textProperty().bind(l2.getSelectionModel().selectedItemProperty());
        t3.textProperty().bind(l3.getSelectionModel().selectedItemProperty());
        //listview默认值
        l1.getSelectionModel().select("微软雅黑");
        l2.getSelectionModel().select(0);
        l3.getSelectionModel().select(4);
        c.getSelectionModel().select(0);
        //初始化默认字体
        my_font();
        //listview监听
        l1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                my_font();
            }
        });
        l2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                my_font();
            }
        });
        l3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                my_font();
            }
        });
        c.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                my_font();
            }
        });

    }

    //字体设置
    private void my_font() {
        double size = 12;
        for (int i = 0; i < sizeIntArray.length; i++) {
            //字号
            if (l3.getSelectionModel().getSelectedItem().equals(sizeArray.get(i))) {
                size = Double.parseDouble(String.valueOf(sizeIntArray[i]));
                break;
            }
        }
        if (l2.getSelectionModel().getSelectedItem().equals("常规")) {
            font = Font.font(l1.getSelectionModel().getSelectedItem(), FontWeight.NORMAL, FontPosture.REGULAR, size);
            global.fontWeight=FontWeight.NORMAL;
            global.fontPosture=FontPosture.REGULAR;
        } else if (l2.getSelectionModel().getSelectedItem().equals("粗体")) {
            font = Font.font(l1.getSelectionModel().getSelectedItem(), FontWeight.BOLD, FontPosture.REGULAR, size);
            global.fontWeight=FontWeight.BOLD;
            global.fontPosture=FontPosture.REGULAR;
        } else if (l2.getSelectionModel().getSelectedItem().equals("斜体")) {
            font = Font.font(l1.getSelectionModel().getSelectedItem(), FontWeight.NORMAL, FontPosture.ITALIC, size);
            global.fontWeight=FontWeight.NORMAL;
            global.fontPosture=FontPosture.ITALIC;
        }
        color = c.getSelectionModel().getSelectedItem();
        global.font = font;
        global.fontcolor = color;
        global.fontFamily=l1.getSelectionModel().getSelectedItem();
        global.size=size;
        t4.setFont(font);
        t4.setStyle("-fx-text-fill:" + color);
        t4.setText(CH_STRING + "\n" + EN_STRING + "\n" + NUMBER_STRING);
        //t4.nodeOrientationProperty().setValue(NodeOrientation.RIGHT_TO_LEFT);
    }

    @FXML
    private void my_close() {
        controller.myfontchooser_close();
    }

    @FXML
    private void my_choose() {
        controller.fontchooser();
        controller.myfontchooser_close();
    }

}
