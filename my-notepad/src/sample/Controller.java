package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.*;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller extends Stage implements Initializable {
    Stage stage;
    @FXML
    private MenuItem cm1;

    @FXML
    private MenuItem cm3;

    @FXML
    private MenuItem m51;

    @FXML
    private MenuItem cm2;

    @FXML
    private MenuItem cm5;

    @FXML
    private CheckMenuItem m31;

    @FXML
    private MenuItem cm4;

    @FXML
    private MenuBar my_MenuBar;

    @FXML
    private CheckMenuItem cm8;
    @FXML
    private MenuItem m52;

    @FXML
    private MenuItem cm7;
    @FXML
    private MenuItem cm9;

    @FXML
    private MenuItem m11;

    @FXML
    private MenuItem m33;

    @FXML
    private MenuItem cm6;

    @FXML
    private MenuItem m32;

    @FXML
    private MenuItem m13;

    @FXML
    private MenuItem m12;

    @FXML
    private MenuItem m34;

    @FXML
    private MenuItem m15;

    @FXML
    private MenuItem m14;

    @FXML
    private MenuItem m16;

    @FXML
    private TextArea my_TextArea;

    @FXML
    private Menu m1;

    @FXML
    private Menu m2;

    @FXML
    private Menu m3;

    @FXML
    private Menu m4;

    @FXML
    private Menu m5;
    @FXML
    private Label my_Label;

    @FXML
    private CheckMenuItem m41;

    @FXML
    private MenuItem m22;

    @FXML
    private MenuItem m21;

    @FXML
    private MenuItem m24;

    @FXML
    private MenuItem m23;

    @FXML
    private MenuItem m26;

    @FXML
    private MenuItem m25;

    @FXML
    private BorderPane my_BorderPane;

    @FXML
    private MenuItem m28;

    @FXML
    private MenuItem m27;

    @FXML
    private ContextMenu my_ContextMenu;

    @FXML
    private MenuItem m29;

    @FXML
    private MenuItem m2B;

    @FXML
    private MenuItem m2A;

    @FXML
    private MenuItem m2C;
    private Stage myfont_stage;
    private Stage mybackgroundcolor_stage;


    public int getFlag() {
        return flag;
    }

    //1：新建
    //2：修改过
    //3：保存过的
    int flag = 1;

    public String getCurrentPath() {
        return currentPath;
    }

    //当前文件路径
    String currentPath = null;
    //当前文件名
    String currentFileName = null;
    //全局变量
    public Global global;
    //字体颜色
    private String fontcolor = "BLACK";
    //背景颜色
    private String backgroundcolor="WHITE";
    //字体
    private Font font;
    //字体尺寸
    private double size = 12;

    //初始化
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //全局变量激活
        global = (Global) Main.globals.get("global");
        Main.controllers.put(this.getClass().getSimpleName(), this);
        //初始化字体
        font = Font.font("微软雅黑", FontWeight.NORMAL, FontPosture.REGULAR, 12);
        my_TextArea.setFont(font);
        my_Label.setText("    第 " + 1 + " 行, 第 " + (0 + 1) + " 列  " + " 共 " + 0 + " 字 ");
        myedit();
        //监听鼠标点击
        my_TextArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mydetils();
                myedit();
            }
        });
        //监听键盘按下,ALT键切换焦点
        my_TextArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ALT) {
                    my_MenuBar.requestFocus();
                } else {
                    //mydetils();
                }
            }
        });
        //监听键盘抬起
        my_TextArea.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ALT) {
                    my_MenuBar.requestFocus();
                } else {
                    mydetils();
                }

            }
        });
        //监听鼠标滑轮
        my_TextArea.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                //判断是否CTRL键按下
                if (event.isControlDown() && event.getDeltaY() < 0) {
                    zoomout();
                } else if (event.isControlDown() && event.getDeltaY() > 0) {
                    zoomin();
                }
            }
        });
        //监听文本变化
        my_TextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                myedit();
            }
        });

    }

    //更新状态栏信息
    private void mydetils() {
        flag = 2;
        try {
            int line, row, FindStartPos;
            String strA, strB;
            String[] strings;
            strA = my_TextArea.getText();
            FindStartPos = my_TextArea.getCaretPosition();
            strB = strA.substring(0, FindStartPos);
            strings = strB.split("\n");
            line = strings.length;
            //有连续按下回车的情况
            if (line == 0) {
                strA = strA + "*";
                strB = strA.substring(0, FindStartPos + 1);
                strings = strB.split("\n");
                line = strings.length;
                row = strings[line - 1].length() - 1;
            } else {
                row = strings[line - 1].length();
            }
            my_Label.setText("    第 " + line + " 行, 第 " + (row + 1) + " 列  " + " 共 " + strA.length() + " 字 ");
        } catch (Exception e) {

        }

    }
    private void myedit(){
        if (!my_TextArea.isUndoable()){
            m21.setDisable(true);
            cm1.setDisable(true);
        }else if (my_TextArea.isUndoable()){
            m21.setDisable(false);
            cm1.setDisable(false);
        }

        if (!my_TextArea.isRedoable()){
            m22.setDisable(true);
            cm2.setDisable(true);
        }else if (my_TextArea.isRedoable()){
            m22.setDisable(false);
            cm2.setDisable(false);
        }

        if (my_TextArea.getSelectedText().equals("")){
            m23.setDisable(true);
            m24.setDisable(true);
            cm3.setDisable(true);
            cm4.setDisable(true);
        }else if (!my_TextArea.getSelectedText().equals("")){
            m23.setDisable(false);
            m24.setDisable(false);
            cm3.setDisable(false);
            cm4.setDisable(false);
        }
    }

    //获取main中的stage
    public void main_stage(Stage stage) {
        this.stage = stage;
    }

    //新建
    @FXML
    private void newFile() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("新建");
        alert.setHeaderText("是否确认新建");
        alert.setContentText("如果有需要请返回并保存已有文件");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            my_TextArea.clear();
            stage.setTitle("未命名");
            flag = 1;
            currentPath = null;
        }
    }

    //打开
    @FXML
    private void openFile() {
        FileChooser chooser = new FileChooser(); // 创建一个文件对话框
        chooser.setTitle("打开文件"); // 设置文件对话框的标题
        chooser.setInitialDirectory(new File("C:\\")); // 设置文件对话框的初始目录
        // 给文件对话框添加文件类型的过滤器
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("所有文件", "*.*"));
        File file = chooser.showOpenDialog(this); // 显示文件打开对话框
        if (file == null) { // 文件对象为空，表示没有选择任何文件
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
            alert.setTitle("打开文件");
            alert.setHeaderText("未选择任何文件");
            alert.setContentText("未打开新文件");
            alert.showAndWait(); //模态显示对话框
        } else { // 文件对象非空，表示选择了某个文件
            flag = 2;
            currentPath = file.getAbsolutePath();
            stage.setTitle(file.getName());
            BufferedReader br = null;
            try {
                //建立文件流[字符流]
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");
                br = new BufferedReader(isr);//动态绑定
                //读取内容
                StringBuffer sb = new StringBuffer();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append(System.getProperty("line.separator"));
                    //换行符
                }
                //显示在文本框[多框]
                my_TextArea.setText(sb.toString());
            } catch (IOException e) {
                //e.printStackTrace();
            } finally {
                try {
                    if (br != null) br.close();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    //保存
    public void main_save() {
        save();
    }

    @FXML
    private void save() {
        flag = 3;
        if (currentPath == null) {
            saveAs();
        } else {
            FileWriter fw = null;
            //保存
            try {
                fw = new FileWriter(new File(currentPath));
                fw.write(my_TextArea.getText());
                fw.flush();
                flag = 3;
                stage.setTitle(currentPath);
            } catch (IOException e) {
                //e.printStackTrace();
            } finally {
                try {
                    if (fw != null) fw.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }

    }

    //另存为
    public void main_saveAs() {
        saveAs();
    }

    @FXML
    private void saveAs() {
        //打开保存框
        FileChooser chooser = new FileChooser(); // 创建一个文件对话框
        chooser.setTitle("保存文件"); // 设置文件对话框的标题
        chooser.setInitialDirectory(new File("C:\\")); // 设置文件对话框的初始目录
        // 创建一个文件类型过滤器
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("文本文件(*.txt)", "*.txt");
        // 给文件对话框添加文件类型过滤器
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showSaveDialog(this); // 显示文件保存对话框
        if (file == null) { // 文件对象为空，表示没有选择任何文件
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
            alert.setTitle("保存文件");
            alert.setHeaderText(" ");
            alert.setContentText("未保存文件");
            alert.showAndWait(); //模态显示对话框
        } else { // 文件对象非空，表示选择了某个文件
            flag = 3;
            FileWriter fw = null;
            //保存
            try {
                fw = new FileWriter(file);
                fw.write(my_TextArea.getText());
                currentFileName = file.getName();
                currentPath = file.getAbsolutePath();
                fw.flush();
                this.flag = 3;
                stage.setTitle(currentPath);
            } catch (IOException e) {
                //e1.printStackTrace();
            } finally {
                try {
                    if (fw != null) fw.close();
                } catch (IOException e) {
                    //e1.printStackTrace();
                }
            }
        }
    }

    //页面设置
    @FXML
    private void Pageformat() {
        PageFormat pf = new PageFormat();
        PrinterJob.getPrinterJob().pageDialog(pf);
    }

    //退出
    @FXML
    private void exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("关闭");
        alert.setHeaderText("是否退出");
        alert.setContentText("退出前注意保存文件");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (flag != 3) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
                alert2.setTitle("保存");
                alert2.setHeaderText("正在保存");
                alert2.setContentText(" ");
                alert2.showAndWait(); //模态显示对话框
                if (currentPath == null) {
                    saveAs();
                } else {
                    save();
                }
            }
            stage.close();
        }
    }

    //撤销
    @FXML
    private void undo() {
        my_TextArea.undo();
    }

    //恢复
    @FXML
    private void redo() {
        my_TextArea.redo();
    }

    //剪切
    @FXML
    private void cut() {
        my_TextArea.cut();
    }

    //复制
    @FXML
    private void copy() {
        my_TextArea.copy();
    }

    //粘贴
    @FXML
    private void paste() {
        my_TextArea.paste();
    }

    //删除
    @FXML
    private void delete() {
        //my_TextArea.deselect();
        my_TextArea.replaceSelection("");
    }

    //查找
    @FXML
    public void mySearch() {
        Parent mysearch_root = null;
        try {
            mysearch_root = FXMLLoader.load(getClass().getResource("mysearch.fxml"));
        } catch (IOException e) {
            //e.printStackTrace();
        }
        Scene mysearch_scene = null;
        if (mysearch_root != null) {
            mysearch_scene = new Scene(mysearch_root);
        }
        Stage mysearch_stage = new Stage();
        mysearch_stage.setScene(mysearch_scene);
        mysearch_stage.show(); //显示窗口；
        mysearch_stage.setTitle("搜索与替换");
        mysearch_stage.setResizable(false);
    }

    //查找
    public void search() {
        int a = 0, b = 0;
        int FindStartPos = my_TextArea.getCaretPosition();
        String strA, strB;
        strA = my_TextArea.getText();
        strB = global.search;
        if (strA.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
            alert.setTitle("查询与替换");
            alert.setHeaderText("查找结果");
            // 设置对话框的内容文本
            alert.setContentText("请填写查找内容");
            alert.showAndWait(); //模态显示对话框
            return;
        }
        if (FindStartPos >= strA.length()) {
            FindStartPos = 0;
        }
        if (my_TextArea.getSelectedText() == null) {
            a = strA.indexOf(strB, FindStartPos);
        } else {
            a = strA.indexOf(strB, FindStartPos - strB.length() + 1);
        }
        if (a > -1) {
            my_TextArea.positionCaret(a);
            b = strB.length();
            my_TextArea.selectRange(a, a + b);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
            alert.setTitle("查询与替换");
            alert.setHeaderText("查找结果");
            alert.setContentText("后面没有您查找的内容，请返回开头或检查输入文本");
            alert.showAndWait(); //模态显示对话框
        }
    }

    //替换
    public void replace() {
        String strA, strB, strC;
        strA = my_TextArea.getText();
        strB = global.search;
        strC = global.replace;
        if (strB.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
            alert.setTitle("查询与替换");
            alert.setHeaderText("替换结果");
            alert.setContentText("请填写查找内容");
            alert.showAndWait(); //模态显示对话框
            return;
        }
        if (strC.length() == 0 && my_TextArea.getSelectedText() != null)
            my_TextArea.replaceSelection("");
        if (strC.length() > 0 && my_TextArea.getSelectedText() != null)
            my_TextArea.replaceSelection(strC);
    }

    public void replaceall() {
        my_TextArea.positionCaret(0); // 将光标放到编辑区开头
        int a = 0, b = 0, replaceCount = 0;
        while (a > -1) {
            int FindStartPos = my_TextArea.getCaretPosition();
            String strA, strB, strC;
            strA = my_TextArea.getText();
            strB = global.search;
            strC = global.replace;
            if (strB.length() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
                alert.setTitle("查询与替换");
                alert.setHeaderText("替换结果");
                alert.setContentText("请填写查找内容");
                alert.showAndWait(); //模态显示对话框
                return;
            }
            if (my_TextArea.getSelectedText() == null) {
                a = strA.indexOf(strB, FindStartPos);
            } else {
                a = strA.indexOf(strB, FindStartPos - strB.length() + 1);
            }
            if (a > -1) {
                my_TextArea.positionCaret(a);
                b = strB.length();
                my_TextArea.selectRange(a, a + b);
            } else {
                if (replaceCount == 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
                    alert.setTitle("查询与替换");
                    alert.setHeaderText("替换结果");
                    alert.setContentText("找不到您查找的内容");
                    alert.showAndWait(); //模态显示对话框
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
                    alert.setTitle("查询与替换");
                    alert.setHeaderText("替换结果");
                    alert.setContentText("成功替换 " + replaceCount + "个匹配内容!");
                    alert.showAndWait(); //模态显示对话框
                }
            }
            if (strC.length() == 0 && my_TextArea.getSelectedText() != null) {
                my_TextArea.replaceSelection("");
                replaceCount++;
            }
            if (strC.length() > 0 && my_TextArea.getSelectedText() != null) {
                my_TextArea.replaceSelection(strC);
                replaceCount++;
            }
        }
    }

    //转到
    @FXML
    private void myTurnto() {
        Parent myturnto_root = null;
        try {
            myturnto_root = FXMLLoader.load(getClass().getResource("myturnto.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene myturnto_scene = null;
        if (myturnto_root != null) {
            myturnto_scene = new Scene(myturnto_root);
        }
        Stage myturnto_stage = new Stage();
        myturnto_stage.setScene(myturnto_scene);
        myturnto_stage.show(); //显示窗口；
        myturnto_stage.setTitle("转到");
        myturnto_stage.setResizable(false);
    }

    public void myturnto() {
        int turnto = global.turnto;
        int line, row;
        String strA, strB;
        String[] strings;
        strA = my_TextArea.getText();
        strings = strA.split("\n");
        line = strings.length;
        row = strings[line - 1].length();
        my_TextArea.positionCaret(0);
        if (turnto >= line) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
            alert.setTitle("查询与替换");
            alert.setHeaderText("查找结果");
            alert.setContentText("超出最大行数");
            alert.showAndWait(); //模态显示对话框
            return;
        }
        int i = 0;
        int FindStartPos = my_TextArea.getCaretPosition();
        while (FindStartPos <= strA.length()) {
            FindStartPos = my_TextArea.getCaretPosition();
            my_TextArea.selectRange(0, FindStartPos);
            strB = my_TextArea.getSelectedText();
            strings = strB.split("\n");
            i = strings.length;
            if (i == turnto) {
                my_TextArea.positionCaret(FindStartPos);
                break;
            }
            FindStartPos = FindStartPos + 1;
            my_TextArea.positionCaret(FindStartPos);
        }
    }

    //全选
    @FXML
    private void selectall() {
        my_TextArea.selectAll();
    }

    //时间日期
    @FXML
    private void datetime() {
        GregorianCalendar calendar = new GregorianCalendar();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        String str = (hour + ":" + min + " " + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH));
        my_TextArea.insertText(my_TextArea.getCaretPosition(), str);
    }
    //自动换行
    @FXML
    private void nextline() {
        my_TextArea.setWrapText(m31.isSelected());
    }
    //字体
    @FXML
    private void myfontchooser() {
        Parent myfont_root = null;
        try {
            myfont_root = FXMLLoader.load(getClass().getResource("myfont.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene myfont_scene = null;
        if (myfont_root != null) {
            myfont_scene = new Scene(myfont_root);
        }
        myfont_stage = new Stage();
        myfont_stage.setScene(myfont_scene);
        myfont_stage.show();
        myfont_stage.setTitle("字体");
        myfont_stage.setResizable(false);
    }

    public void myfontchooser_close() {
        myfont_stage.close();
    }

    public void fontchooser() {
        fontcolor = global.fontcolor;
        font = global.font;
        size=global.size;
        //设置字体
        my_TextArea.setFont(font);
        //设置字体颜色
        my_TextArea.setStyle("-fx-text-fill:" + fontcolor);
    }
    //设置背景颜色
    @FXML
    private void mybackgroundcolor() {
        Parent mybackgroundcolor_root = null;
        try {
            mybackgroundcolor_root = FXMLLoader.load(getClass().getResource("mybackgroundcolor.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene mybackgroundcolor_scene = null;
        if (mybackgroundcolor_root != null) {
            mybackgroundcolor_scene = new Scene(mybackgroundcolor_root);
        }
        mybackgroundcolor_stage = new Stage();
        mybackgroundcolor_stage.setScene(mybackgroundcolor_scene);
        mybackgroundcolor_stage.show();
        mybackgroundcolor_stage.setTitle("背景");
    }

    public void mybackgroundcolor_close() {
        mybackgroundcolor_stage.close();
    }

    public void backgroundcolor() {
        backgroundcolor = global.backgroundcolor;
        //设置背景颜色
        my_TextArea.lookup(".content").setStyle("-fx-background-color:" + backgroundcolor);
        my_TextArea.setStyle("-fx-text-fill:" + fontcolor);
    }
    //状态栏
    @FXML
    private void state() {
        my_Label.setVisible(m41.isSelected());
    }
    //缩放
    @FXML
    private void zoomin() {
        size = size + 1;
        if (size >= 72) {
            size = 72;
        }
        font = Font.font(global.fontFamily,global.fontWeight,global.fontPosture,size);
        my_TextArea.setFont(font);
    }

    @FXML
    private void zoomout() {
        size = size - 1;
        if (size <= 1) {
            size = 1;
        }
        font = Font.font(global.fontFamily,global.fontWeight,global.fontPosture,size);
        my_TextArea.setFont(font);
    }

    @FXML
    private void zoominit() {
        size = 12;
        font = Font.font(global.fontFamily,global.fontWeight,global.fontPosture,size);
        my_TextArea.setFont(font);
    }

    @FXML
    private void help() {
        java.net.URI uri = java.net.URI.create("https://support.microsoft.com/zh-cn/windows/%E8%AE%B0%E4%BA%8B%E6%9C%AC%E4%B8%AD%E7%9A%84%E5%B8%AE%E5%8A%A9-4d68c388-2ff2-0e7f-b706-35fb2ab88a8c");
        // 获取当前系统桌面扩展
        java.awt.Desktop dp = java.awt.Desktop.getDesktop();
        // 判断系统桌面是否支持要执行的功能
        if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
            // 获取系统默认浏览器打开链接
            try {
                dp.browse(uri);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }

    @FXML
    private void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
        alert.setTitle("关于记事本");
        alert.setHeaderText("作者");
        alert.setContentText("***");
        alert.showAndWait(); //模态显示对话框
    }
    //阅读顺序
    @FXML
    private void order() {
        if (cm8.isSelected()) {
            my_TextArea.nodeOrientationProperty().setValue(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            my_TextArea.nodeOrientationProperty().setValue(NodeOrientation.LEFT_TO_RIGHT);
        }

    }
    //bing搜索
    @FXML
    private void bingsearch() {
        String websearch = my_TextArea.getSelectedText();
        try {
            java.net.URI uri = java.net.URI.create("https://cn.bing.com/search?q=" + websearch + "&form=NPCTXT");
            // 获取当前系统桌面扩展
            java.awt.Desktop dp = java.awt.Desktop.getDesktop();
            // 判断系统桌面是否支持要执行的功能
            if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                // 获取系统默认浏览器打开链接
                try {
                    dp.browse(uri);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);//创建确认对话框
            alert.setTitle("搜索引擎");
            alert.setHeaderText("bing cn版");
            alert.setContentText("搜索内容过长，请手动搜索");
            alert.showAndWait();
            try {
                java.net.URI uri = java.net.URI.create("https://cn.bing.com/?FORM=Z9FD1");
                // 获取当前系统桌面扩展
                java.awt.Desktop dp = java.awt.Desktop.getDesktop();
                // 判断系统桌面是否支持要执行的功能
                if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                    // 获取系统默认浏览器打开链接
                    try {
                        dp.browse(uri);
                    } catch (IOException e3) {
                        //e3.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                //e2.printStackTrace();
            }
        }

    }
}
