package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class Mybackgroundcolor implements Initializable {
    @FXML
    private ComboBox<String> c;

    @FXML
    private TextArea t;


    @FXML
    private Button b1;

    @FXML
    private Button b2;
    public Controller controller;
    public Global global;
    private String color;
    private ObservableList<String> colorArray = FXCollections.observableArrayList("BLACK", "AQUA", "BLUE", "CORAL", "GREEN", "GREY", "RED", "WHITE", "YELLOW");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        global = (Global) Main.globals.get("global");
        controller = (Controller) Main.controllers.get(Controller.class.getSimpleName());
        c.setItems(colorArray);
        c.getSelectionModel().select(7);
        t.setFont(new Font(25));
        t.setText("背景颜色");
        c.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                color = c.getSelectionModel().getSelectedItem();
                global.backgroundcolor = color;
                t.lookup(".content").setStyle("-fx-background-color:" + color);
            }
        });
    }

    @FXML
    private void my_close() {
        controller.mybackgroundcolor_close();
    }

    @FXML
    private void my_choose() {
        controller.backgroundcolor();
        controller.mybackgroundcolor_close();
    }
}
