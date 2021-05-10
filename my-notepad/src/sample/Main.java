package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main extends Application {
    //全局容器
    public static Map<String, Object> controllers = new HashMap<String, Object>();
    public static Map<String, Object> globals = new HashMap<String, Object>();
    //全局变量实例化
    Global global = new Global();

    @Override
    public void start(Stage primaryStage) throws Exception {
        //全局变量放入容器
        globals.put("global", global);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("notepad");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("/sample/notepad.png"));
        primaryStage.show();
        //获取controller实例对象
        Controller controller = fxmlLoader.getController();
        controller.main_stage(primaryStage);
        //监听窗口关闭事件
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//创建确认对话框
                alert.setTitle("关闭");
                alert.setHeaderText("是否退出");
                alert.setContentText("退出前注意保存文件");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (controller.getFlag() != 3) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
                        alert2.setTitle("保存");
                        alert2.setHeaderText("正在保存");
                        alert2.setContentText(" ");
                        alert2.showAndWait(); // 显示模态对话框
                        if (controller.getCurrentPath() == null) {
                            controller.main_saveAs();
                        } else {
                            controller.main_save();
                        }
                    }
                    primaryStage.close();
                } else {
                    event.consume();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
