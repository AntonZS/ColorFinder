package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.io.File;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Controller implements PixelReader {

    int red,
            green,
            blue;
    double l,
            a,
            b;
    int x;
    int y;
    ColorFromFile f;
    @FXML
    private TextField tfRed;
    @FXML
    private TextField tfGreen;
    @FXML
    private TextField tfBlue;
    @FXML
    private TextField tfL;
    @FXML
    private TextField tfa;
    @FXML
    private TextField tfb;
    @FXML
    private Button btnRL;
    @FXML
    private Button openFile;
    @FXML
    private Button findOfColor;
    @FXML
    private AnchorPane colorPane;
    @FXML
    private AnchorPane colorPane1;
    @FXML
    private AnchorPane colorPane2;
    @FXML
    private AnchorPane colorPane3;
    @FXML
    private AnchorPane colorPane4;
    @FXML
    private AnchorPane colorPane5;
    @FXML
    private Label l1;
    @FXML
    private Label l2;
    @FXML
    private Label l3;
    @FXML
    private Label l4;
    @FXML
    private Label l5;
    private Window primaryStage;

    public void setLabFromRGB(int[] rgb) {
        int[] lab = UserColor.rgb2lab(rgb[0], rgb[1], rgb[2]);
        this.l = lab[0];
        this.a = lab[1];
        this.b = lab[2];
        tfL.setText(String.valueOf(l));
        tfa.setText(String.valueOf(a));
        tfb.setText(String.valueOf(b));
    }


    public UserColor colorFromLab() {
        return new UserColor(l, a, b);
    }


    public  void setRGB(int[] rgb) {
    tfRed.setText(String.valueOf(rgb[0]));
    tfGreen.setText(String.valueOf(rgb[1]));
    tfBlue.setText(String.valueOf(rgb[2]));
    }

    public void setColorTocolorPane(int[] rgb) {
        colorPane.setBackground(new Background(new BackgroundFill(Color.rgb(rgb[0], rgb[1], rgb[2]), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void setLabelText(ArrayList<String[]> s) {
        l1.setText(s.get(0)[0].toString());
        l2.setText(s.get(1)[0].toString());
        l3.setText(s.get(2)[0].toString());
        l4.setText(s.get(3)[0].toString());
        l5.setText(s.get(4)[0].toString());

    }

    public void setColorTocolorPane1(ArrayList<UserColor> c) {
        colorPane1.setBackground(new Background(new BackgroundFill(Color.rgb(c.get(0).rgb()[0], c.get(0).rgb()[1], c.get(0).rgb()[2]), CornerRadii.EMPTY, Insets.EMPTY)));
        colorPane2.setBackground(new Background(new BackgroundFill(Color.rgb(c.get(1).rgb()[0], c.get(1).rgb()[1], c.get(1).rgb()[2]), CornerRadii.EMPTY, Insets.EMPTY)));
        colorPane3.setBackground(new Background(new BackgroundFill(Color.rgb(c.get(2).rgb()[0], c.get(2).rgb()[1], c.get(2).rgb()[2]), CornerRadii.EMPTY, Insets.EMPTY)));
        colorPane4.setBackground(new Background(new BackgroundFill(Color.rgb(c.get(3).rgb()[0], c.get(3).rgb()[1], c.get(3).rgb()[2]), CornerRadii.EMPTY, Insets.EMPTY)));
        colorPane5.setBackground(new Background(new BackgroundFill(Color.rgb(c.get(4).rgb()[0], c.get(4).rgb()[1], c.get(4).rgb()[2]), CornerRadii.EMPTY, Insets.EMPTY)));


    }


    @FXML
    public void initialize() {
        btnRL.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (tfRed.getText() != null &&
                        tfGreen.getText() != null &&
                        tfBlue.getText() != null) {
                    try {
                        red = Integer.parseInt(tfRed.getText());
                        green = Integer.parseInt(tfGreen.getText());
                        blue = Integer.parseInt(tfBlue.getText());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int[] rgb = {red, green, blue};
                    setLabFromRGB(rgb);
                    setColorTocolorPane(rgb);
                }
            }//конвертирует RGB Lab
        });
        openFile.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
                fileChooser.setTitle("Open Document");//Заголовок диалога
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("Text file (*.txt)", "*.txt");//Расширение
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showOpenDialog(primaryStage);//Указываем текущую сцену CodeNote.mainStage
                try {
                    f = new ColorFromFile(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findOfColor.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ArrayList<UserColor> similarColors = new ArrayList<UserColor>();
                ArrayList<String[]> firstFiveDE = new ArrayList<String[]>();
                try {
                    similarColors = f.similarColors(colorFromLab());
                    firstFiveDE = f.getFirstFiveDE();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (UserColor x : similarColors) {
                    System.out.println(x);
                }
                setColorTocolorPane1(similarColors);
                setLabelText(firstFiveDE);
            }
        });
        btnRL.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        switch (event.getCode()) {
                            case SPACE:
                                System.out.println("Space has been pressed");
                                Point p = MouseInfo.getPointerInfo().getLocation();
                x = (int) p.getX();
                y = (int) p.getY();
                System.out.println("x = " + x + " y = "+y);
                Color color = getColor(x,y);
                red = (int) (color.getRed()*255);
                green = (int) (color.getGreen()*255);
                blue = (int) (color.getBlue()*255);
                int[] rgb = {red, green, blue};
                                tfRed.setText(String.valueOf(rgb[0]));
                                tfGreen.setText(String.valueOf(rgb[1]));
                                tfBlue.setText(String.valueOf(rgb[2]));
                setLabFromRGB(rgb);
                setColorTocolorPane(rgb);
                findColor();
                        }
                    }
                }
        );
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getCode()){
            case SPACE:
                System.out.println("Space has been pressed");
                /*Point p = MouseInfo.getPointerInfo().getLocation();
                x = (int) p.getX();
                y = (int) p.getY();
                System.out.println("x = " + x + " y = "+y);
                Color color = getColor(x,y);
                int r = (int) (color.getRed()*255);
                int g = (int) (color.getGreen()*255);
                int b = (int) (color.getBlue()*255);
                int[] rgb = new int[]{r,g,b,};
                setRGB(rgb);
                System.out.println("r = "+r + " g =  " + g + " b " + b);*/

        }
    }

    @Override
    public PixelFormat getPixelFormat() {
        return null;
    }

    @Override
    public int getArgb(int x, int y) {
        return 0;
    }

    @Override
    public Color getColor(int x, int y) {
        Robot r = null;
        try {
            r = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        java.awt.Color color = r.getPixelColor(x, y);
        Color cl = Color.rgb(color.getRed(),color.getGreen(),color.getBlue());
        return cl;
    }

    @Override
    public <T extends Buffer> void getPixels(int x, int y, int w, int h, WritablePixelFormat<T> pixelformat, T buffer, int scanlineStride) {

    }

    @Override
    public void getPixels(int x, int y, int w, int h, WritablePixelFormat<ByteBuffer> pixelformat, byte[] buffer, int offset, int scanlineStride) {

    }

    @Override
    public void getPixels(int x, int y, int w, int h, WritablePixelFormat<IntBuffer> pixelformat, int[] buffer, int offset, int scanlineStride) {

    }
    public void findColor(){
        ArrayList<UserColor> similarColors = new ArrayList<UserColor>();
        ArrayList<String[]> firstFiveDE = new ArrayList<String[]>();
        try {
            similarColors = f.similarColors(colorFromLab());
            firstFiveDE = f.getFirstFiveDE();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (UserColor x : similarColors) {
            System.out.println(x);
        }
        setColorTocolorPane1(similarColors);
        setLabelText(firstFiveDE);
    }
}
