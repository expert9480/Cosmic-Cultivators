
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private BufferedImage back;
    private int key, count, lineCount;
    private String screen;
    private Sound sound;
    private Icons play, playSelected, logo;
    private Boolean playBoolean, sprint;
    private Farmer farmer;
    private ArrayList cropList;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenHeight;
    private int screenWidth;

    private double time;
    private double curtime;
    private double bestTime;

    public Game() {
        new Thread(this).start();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // screenHeight = screenSize.height;
        screenWidth = screenSize.width;
        //screenHeight = screenSize.height-(int)(screenSize.height*.083);
        screenHeight = Main.screenSizeFigureOuter();
        

        key = -1;
        sound = new Sound();
        screen = "start";
        count = 0;
        // time=System.currentTimeMillis();
        curtime = 0;
        bestTime = 0;

        // iniztialize here
        cropList = new ArrayList<>();
        farmer = new Farmer(new ImageIcon("assets/farmer/idleDown.png"), 100, 100, 0, 0, 96, 96, false, false);
        sprint = false;

        play = new Icons("assets/icons/unselectedPlay.png", 25, screenHeight - 400, 300, 300);
        playSelected = new Icons("assets/icons/selectedPlay.png", 25, screenHeight - 400, 300, 300);
        playBoolean = false;

        logo = new Icons("assets/icons/logo.png", 25, 100, 2264/2, 339/2);
    }

    public void screen(Graphics g2d) {
        switch (screen) {
            case "start":
                // Start Screen
                drawStartScreen(g2d);
                break;
            case "mainFarm":
                drawFarmer(g2d);
                //saveSystem();
                // Base
                break;
            case "greenhouse1":
                break;
            case "greenhouse2":
                break;
            case "greenhouseBig":
                break;
            case "home":
                break;
        }
    }

    public void drawStartScreen(Graphics g2d) {
        // create start screen
        g2d.drawImage(new ImageIcon(logo.getPic()).getImage(), logo.getX(), logo.getY(), logo.getW(), logo.getH(),this);
        if (playBoolean == true) {
            g2d.drawImage(new ImageIcon(playSelected.getPic()).getImage(), playSelected.getX(), playSelected.getY(), playSelected.getW(), playSelected.getH(), this);
        } else {
            g2d.drawImage(new ImageIcon(play.getPic()).getImage(), play.getX(), play.getY(), play.getW(), play.getH(), this);
        }
    }

    public void drawFarmer(Graphics g2d) {
        g2d.drawImage(farmer.getPic().getImage(), farmer.getX(), farmer.getY(), farmer.getW(), farmer.getH(), this);
        move();
    }

    public void move() {
        // change screenwidth and height to image width and height
        farmer.move(screenWidth, screenHeight);
    }


//     public void saveSystem(){
//         File save = new File("save.txt");
//         try {
//             if (save.createNewFile()){}
//             else{
                
// }
//                 for (int i=3; i>); i++){
//                     cropList.add(Files.readAllLines(Paths.get("save.txt")).get(i));

//                 }
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
        
//     }

    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(5);
                repaint();
            }
        } catch (Exception e) {
        }
    }

    public void paint(Graphics g) {

        Graphics2D twoDgraph = (Graphics2D) g;
        if (back == null)
            back = (BufferedImage) ((createImage(getWidth(), getHeight())));

        Graphics g2d = back.createGraphics();
        g2d.clearRect(0, 0, getSize().width, getSize().height);

        // start graphics here
        screen(g2d);

        // end
        twoDgraph.drawImage(back, null, 0, 0);

    }

    // DO NOT DELETE
    @Override
    public void keyTyped(KeyEvent e) {
    }

    // DO NOT DELETE
    @Override
    public void keyPressed(KeyEvent e) {

        key = e.getKeyCode();
        System.out.println(key);

        if (key == 16){
            sprint = true;
        }

        if (key == 87){
            farmer.setDy(-1);
            farmer.setPic(new ImageIcon("assets/farmer/walkUp.gif"));
        }
        if (key == 83){
            farmer.setDy(1);
            farmer.setPic(new ImageIcon("assets/farmer/walkDown.gif"));
        }
        if (key == 65){
            farmer.setDx(-1);
            farmer.setPic(new ImageIcon("assets/farmer/walkLeft.gif"));
        }
        if (key == 68){
            farmer.setDx(1);
            farmer.setPic(new ImageIcon("assets/farmer/walkRight.gif"));
        }

        if ((sprint) && farmer.getDX()==-1){
            farmer.setDx(-2);
        }
        else if ((sprint) && farmer.getDX()==1) {
            farmer.setDx(2);
        }
        else if ((sprint) && farmer.getDY()==-1) {
            farmer.setDy(-2);
        }
        else if ((sprint) && farmer.getDY()==1) {
            farmer.setDy(2);
        }
        

    }

    // DO NOT DELETE
    @Override
    public void keyReleased(KeyEvent e) {
        key=e.getKeyCode();
        
        if (key == 16){
            sprint = false;
        }
        
        if (key == 87){
            farmer.setDy(0);
            if ((farmer.getDX()==0)&&(farmer.getDY()==0))
                farmer.setPic(new ImageIcon("assets/farmer/idleUp.png"));
        }
        if (key == 83){
            farmer.setDy(0);
            if ((farmer.getDX()==0)&&(farmer.getDY()==0))
                farmer.setPic(new ImageIcon("assets/farmer/idleDown.png"));
        }
        if (key == 65){
            farmer.setDx(0);
            if ((farmer.getDX()==0)&&(farmer.getDY()==0))
                farmer.setPic(new ImageIcon("assets/farmer/idleLeft.png"));
        }
        if (key == 68){
            farmer.setDx(0);
            if ((farmer.getDX()==0)&&(farmer.getDY()==0))
                farmer.setPic(new ImageIcon("assets/farmer/idleRight.png"));
        }

        if (!(sprint) && farmer.getDX()==-2){
            farmer.setDx(-1);
        }
        else if (!(sprint) && farmer.getDX()==2) {
            farmer.setDx(1);
        }
        else if (!(sprint) && farmer.getDY()==-2) {
            farmer.setDy(-1);
        }
        else if (!(sprint) && farmer.getDY()==2) {
            farmer.setDy(1);
        }

    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent m) {
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent m) {
        if ((play.getX() + play.getW() >= m.getX() && play.getX() <= m.getX() && play.getY() + play.getH() >= m.getY()
                && play.getY() <= m.getY()) && (screen == "start")) {
            playBoolean = true;
        } else {
            playBoolean = false;
        }

    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent m) {
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent m) {
        // m.getButton()==1 is Left Click
        if ((playBoolean == true) && (screen == "start")) {
            screen = "mainFarm";
        }

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent m) {
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent m) {

    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent m) {
    }

}
