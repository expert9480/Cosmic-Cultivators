
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private BufferedImage back;
    private int key, count;
    private String screen;
    private Sound sound;
    private Icons play, playSelected;
    private Boolean playBoolean;
    private Farmer farmer;

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

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;
        key = -1;
        sound = new Sound();
        screen = "start";
        count = 0;
        // time=System.currentTimeMillis();
        curtime = 0;
        bestTime = 0;

        // iniztialize here
        farmer = new Farmer(new ImageIcon("farmerIdle.png"), 100, 100, 0, 0, 75, 75, false, false);

        play = new Icons("unselectedPlay.png", 25, screenHeight - 400, 300, 300);
        playSelected = new Icons("selectedPlay.png", 25, screenHeight - 400, 300, 300);
        playBoolean = false;

    }

    public void screen(Graphics g2d) {
        switch (screen) {
            case "start":
                // Start Screen
                drawStartScreen(g2d);
                break;
            case "mainFarm":
                drawFarmer(g2d);
                // Base
                break;
            case "greenhouse1":
                break;
            case "greenhouse2":
                break;
            case "greenhouseBig":
                break;
        }
    }

    public void drawStartScreen(Graphics g2d) {
        // create start screen
        if (playBoolean == true) {
            g2d.drawImage(new ImageIcon(playSelected.getPic()).getImage(), playSelected.getX(), playSelected.getY(),
                    playSelected.getW(), playSelected.getH(), this);
        } else {
            g2d.drawImage(new ImageIcon(play.getPic()).getImage(), play.getX(), play.getY(), play.getW(), play.getH(),
                    this);
        }
    }

    public void drawFarmer(Graphics g2d) {
        g2d.drawImage(farmer.getPic().getImage(), farmer.getX(), farmer.getY(), farmer.getW(), farmer.getH(), this);
        move();
    }

    public void move() {
        if (((farmer.getDX() > 0) || (farmer.getDY() > 0)) || ((farmer.getDX() < 0) || (farmer.getDY() < 0))) {
            farmer.setPic(new ImageIcon("farmerWalk.gif"));
        } else {
            farmer.setPic(new ImageIcon("farmerIdle.png"));
        }
        // change screenwidth and height to image width and height
        farmer.move(screenWidth, screenHeight);
    }

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

        if (key == 87)
            farmer.setDy(-2);
        if (key == 83)
            farmer.setDy(2);
        if (key == 65)
            farmer.setDx(-2);
        if (key == 68)
            farmer.setDx(2);

    }

    // DO NOT DELETE
    @Override
    public void keyReleased(KeyEvent e) {
        key=e.getKeyCode();
        if (key == 87 || key == 83)
            farmer.setDy(0);
        if (key == 65 || key == 68)
            farmer.setDx(0);

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
