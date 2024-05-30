
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        super("Cosmic Cultivators");
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        Game play = new Game();
        ((Component) play).setFocusable(true);
        setBackground(Color.BLACK);
        getContentPane().add(play);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setState(Frame.NORMAL);


    }

    public static void main(String[] args) {
        Main run = new Main();
    }

    public static int screenSizeFigureOuter(){
        if(Toolkit.getDefaultToolkit().getScreenSize().height==768){
            return 768-(int)(768*.083);
        }
        else if (Toolkit.getDefaultToolkit().getScreenSize().height==1080){
            return 1080-(int)(1080*.06);
        }
        else {
            return Toolkit.getDefaultToolkit().getScreenSize().height;
        }
    }

    public static int scale(){
        if(Toolkit.getDefaultToolkit().getScreenSize().height==768){
            return 4;
        }
        else {
            return 6;
        }
    }

}
