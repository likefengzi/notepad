
package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Myturnto implements Initializable {

    @FXML
    private TextField t1;

    @FXML
    private Button b1;
    public Controller controller;
    public Global global;
    public int myturnto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //加载全局变量
        global = (Global) Main.globals.get("global");
        controller = (Controller) Main.controllers.get(Controller.class.getSimpleName());
    }

    //转到
    @FXML
    private void turnto() {
        myturnto = Integer.parseInt(t1.getText());
        global.turnto = myturnto;
        controller.myturnto();
    }
}
