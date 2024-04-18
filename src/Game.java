
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
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
    private Icons play, playSelected, logo, inventoryMenu, house, tempAirlock;
    private Boolean playBoolean, sprint, showInvetory, airlockCreation;
    private Farmer farmer;
    private ArrayList <Crops> cropList;
    private ArrayList <Icons> inventory;
    private ArrayList <Airlocks> airlocks;

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
        farmer = new Farmer(new ImageIcon("assets/farmer/idleDown.png"), 100, 100, 0, 0, 16*Main.scale(), 16*Main.scale(), false, false);
        sprint = false;

        play = new Icons(new ImageIcon("assets/icons/unselectedPlay.png"), 25, screenHeight - 400, 300, 300);
        playSelected = new Icons(new ImageIcon("assets/icons/selectedPlay.png"), 25, screenHeight - 400, 300, 300);
        playBoolean = false;

        logo = new Icons(new ImageIcon("assets/icons/logo.png"), 25, 100, 2264/2, 339/2);
        house = new Icons(new ImageIcon("assets/buildings/house.png"), (screenWidth-(128*(Main.scale())))/2, (screenHeight-(72*(Main.scale())))/2, 128*(Main.scale()), 72*(Main.scale()));
        //tempAirlock= new Icons(new ImageIcon("assets/buildings/airlock.png"), (house.getX()-(32*(Main.scale())))/2, (house.getY()-(32*(Main.scale())))/2, 32*(Main.scale()), 32*(Main.scale()));
        tempAirlock= new Icons(new ImageIcon("assets/buildings/airlock.png"), (house.getX()+(house.getW()/2))-(32*(Main.scale()))/2, house.getY()+house.getH()-(Main.scale()*2), 32*(Main.scale()+2), 32*(Main.scale()+2));
        
        inventory = new ArrayList<Icons>();
        showInvetory = false;
        //current img size of invetory 256x192
        //inventory size scale needs tweaking
        inventoryMenu = new Icons(new ImageIcon("assets/icons/inventory.png"), (screenWidth-(256*(Main.scale()-2)))/2, (screenHeight-(192*(Main.scale()-2)))/2, 256*(Main.scale()-2), 192*(Main.scale()-2));

        cropList = new ArrayList<Crops>();
        airlocks = new ArrayList<Airlocks>();
        airlockCreation = false;

        
    }

    public void screen(Graphics g2d) {
        switch (screen) {
            case "start":
                // Start Screen
                drawStartScreen(g2d);
                if (airlockCreation == false){
                    createAirlockArray();
                    airlockCreation = true;
                }
                break;
            case "mainFarm":
                //saveSystem();
                // Base
                drawCrops(g2d);
                drawFarmer(g2d);
                drawInvetory(g2d);
                break;
            case "greenhouse1":
                break;
            case "greenhouse2":
                break;
            case "greenhouseBig":
                break;
            case "home":
                g2d.drawImage(house.getPic().getImage(), house.getX(), house.getY(), house.getW(), house.getH(),this);
                drawAirlock(g2d);
                drawCrops(g2d);
                drawFarmer(g2d);
                drawInvetory(g2d);
                break;
        }
    }

    public void drawStartScreen(Graphics g2d) {
        // create start screen
        g2d.drawImage(logo.getPic().getImage(), logo.getX(), logo.getY(), logo.getW(), logo.getH(),this);
        if (playBoolean == true) {
            g2d.drawImage(playSelected.getPic().getImage(), playSelected.getX(), playSelected.getY(), playSelected.getW(), playSelected.getH(), this);
        } else {
            g2d.drawImage(play.getPic().getImage(), play.getX(), play.getY(), play.getW(), play.getH(), this);
        }
    }
    
    public void drawFarmer(Graphics g2d) {
        g2d.drawImage(farmer.getPic().getImage(), farmer.getX(), farmer.getY(), farmer.getW(), farmer.getH(), this);
        move();
    }

    public int returnCurrentBackgroundWidth(){
        if (screen == "home")
            return house.getX();
        else
            return 0;
    }
    public int returnCurrentBackgroundHeight(){
        if (screen == "home")
            return house.getY();
        else
            return 0;
    }
    public int returnCurrentBackgroundScaleW(){
        if (screen == "home")
            //return ((screenWidth-(128*(Main.scale())))/2) + (128*(Main.scale()));
            return house.getX() + house.getW();
        else
            return screenWidth;
    }
    public int returnCurrentBackgroundScaleH(){
        if (screen == "home")
            //return ((screenHeight-(72*(Main.scale())))/2) + (72*(Main.scale()));
            return house.getY() + house.getH();
        else
            return screenHeight;
    }
    
    public void drawAirlock(Graphics g2d){
        for (Airlocks a: airlocks){
            if (a.getCurscreen() == screen){
                g2d.drawImage(a.getPic().getImage(), a.getX(), a.getY(), a.getW(), a.getH(), this);
            }
        }
    } 
    
    public void createAirlockArray(){
        //add the other airlocks here
        airlocks.add(new Airlocks (new ImageIcon("assets/buildings/airlock.png"), (house.getX()+(house.getW()/2))-(32*(Main.scale()))/2, house.getY()+house.getH()-(Main.scale()*2), 32*(Main.scale()+2), 32*(Main.scale()+2), "home", "mainFarm"));
    }

    
    public void move() {
        // change screenwidth and height to image width and height
        //account for the image being in a diff location than top corner
        farmer.move(returnCurrentBackgroundWidth(), returnCurrentBackgroundHeight(), returnCurrentBackgroundScaleW(), returnCurrentBackgroundScaleH());
    }

    public void addCrop(){
        cropList.add(new Crops(new ImageIcon("assets/plants/testPlant/testPlant.png"),(farmer.getX()+(farmer.getW()/2)),(farmer.getY()+(farmer.getH()/2)), 32, 32, screen));
    }

    public void drawCrops(Graphics g2d){
        for (Crops c: cropList){
            if (screen == c.getScreen())
                g2d.drawImage(c.getPic().getImage(), c.getX(), c.getY(), c.getW(), c.getH(), this);
        }
    }

    public void drawInvetory(Graphics g2d){
        if (showInvetory==true){
            g2d.drawImage(inventoryMenu.getPic().getImage(), inventoryMenu.getX(), inventoryMenu.getY(), inventoryMenu.getW(), inventoryMenu.getH(), this);
        }
    }

    public boolean Collision(Farmer b, Icons a) {
		return a.getX()+a.getW()>=b.getX()&&a.getX()<=b.getX()+b.getW()&&a.getY()+a.getH()>=b.getY()&&a.getY()<=b.getY()+b.getH();
	}

    public boolean airlockCollision(Farmer b, Airlocks a) {
		return a.getX()+a.getW()>=b.getX()&&a.getX()<=b.getX()+b.getW()&&a.getY()+a.getH()>=b.getY()&&a.getY()<=b.getY()+b.getH();
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

        if (key == 72){
            screen = "home";
            // farmer.setX(tempAirlock.getX()+(tempAirlock.getW()/2));
            // farmer.setY(tempAirlock.getY());
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

        if ((key==69) && (screen!="start")){
            showInvetory=!showInvetory;
        }

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
        //2 is middle click
        //3 is right click

        if ((playBoolean == true) && (screen == "start")) {
            screen = "mainFarm";
        }
        if ((screen == "mainFarm") && (m.getButton()==3) && !(Collision(farmer, tempAirlock))){
            addCrop();
        }

        for (Airlocks a: airlocks){
            if ((a.getCurscreen() == screen) && (airlockCollision(farmer, a)) && (m.getButton()==3)){
                screen = a.getGTS();
            }
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
