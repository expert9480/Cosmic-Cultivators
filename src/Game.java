
//import static java.awt.Container.log;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener, Serializable{

    private BufferedImage back;
    private int key, count, lineCount, cash, selectedItem;
    private String screen, font;
    private Sound sound;
    private Icons play, playSelected, logo, inventoryMenu, house, house2, save, sbg, load, hotbar, floorGreenhouse, glassGreenhouse, spaceBackground, controls, commonWalkWay, ground;
    private Icons kash, terminalHitBox, terminalMainScreen, terminalShopButton, terminalSellButton, terminalRepairsButton, shopBlank, kornflowerShop, celestialWheatShop, gooseberryShop;
    private Boolean playBoolean, sprint, showInvetory, airlockCreation, showPause, saveBoolean, loadBoolean, showHotbar, showTerminal, showSell, showShop, showRepairs;
    private Farmer farmer;
    private ArrayList<Crops> cropList;
    private ArrayList<Inventory> inventory;
    private ArrayList<Airlocks> airlocks;
    private ArrayList<Planters> planters;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenHeight;
    private int screenWidth;

    private double time;
    private double curtime;

    public Game() {
        new Thread(this).start();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // screenHeight = screenSize.height;
        screenWidth = screenSize.width;
        // screenHeight = screenSize.height-(int)(screenSize.height*.083);
        screenHeight = Main.screenSizeFigureOuter();

        key = -1;
        sound = new Sound();
        screen = "start";
        count = 0;
        time=System.currentTimeMillis();
        curtime = 0;

        // iniztialize here
        farmer = new Farmer(new ImageIcon("assets/farmer/idleDown.png"), 100, 100, 0, 0, 16 * Main.scale(), 16 * Main.scale(), false, false);
        sprint = false;

        play = new Icons(new ImageIcon("assets/icons/start.png"), 25, screenHeight - 350, 36*8, 12*8);
        //playSelected = new Icons(new ImageIcon("assets/icons/selectedPlay.png"), 25, screenHeight - 400, 300, 300);
        playBoolean = false;

        logo = new Icons(new ImageIcon("assets/icons/logo.png"), 25, 100, 2264 / 2, 339 / 2);
        house = new Icons(new ImageIcon("assets/buildings/topHome.gif"), ((screenWidth - (128 * (Main.scale()))) / 2), ((screenHeight - ((37/2) * (Main.scale()))) / 2)-(107), 128 * (Main.scale()), (37/2) * (Main.scale()));
        //(screenHeight - ((107/2) * (Main.scale()))) / 2
        house2 = new Icons(new ImageIcon("assets/buildings/bottomHome.png"), (screenWidth - (128 * (Main.scale()))) / 2, house.getY()+house.getH(), 128 * (Main.scale()), (107/2) * (Main.scale()));
        
        inventory = new ArrayList<Inventory>();
        showInvetory = false;
        // current img size of invetory 256x192
        // inventory size scale needs tweaking
        inventoryMenu = new Icons(new ImageIcon("assets/icons/inventory.png"), (screenWidth - (256 * (Main.scale() - 2))) / 2, (screenHeight - (224 * (Main.scale() - 2))) / 2, 256 * (Main.scale() - 2), 224 * (Main.scale() - 2));
        showHotbar = true;
        hotbar = new Icons(new ImageIcon("assets/icons/hotbar.png"), 10, screenHeight - (33*2 * 2), 128*2 *  2, 33*2 *  2);
        selectedItem = 1;
        
        //hotbar = new Icons(new ImageIcon("assets/icons/hotbar.png"), 10, screenHeight - (33 * (Main.scale() - 2)), 128 *  (Main.scale() - 2), 33 *  (Main.scale() - 2));

        cropList = new ArrayList<Crops>();
        airlocks = new ArrayList<Airlocks>();
        airlockCreation = false;

        showPause = false;
        save = new Icons(new ImageIcon("assets/icons/save.png"), 25, 200, 320, 80);
        saveBoolean = false;

        sbg = new Icons(new ImageIcon("assets/icons/autismbg.png"), 0, 0, screenWidth, screenHeight);

        load = new Icons(new ImageIcon("assets/icons/load.png"), 25, screenHeight - 200, 31*8, 14*8);
        loadBoolean = false;

        floorGreenhouse = new Icons(new ImageIcon("assets/buildings/floorGreenhouse.png"),  ((screenWidth - ((232/2) * (Main.scale()))) / 2), ((screenHeight - ((138/2) * (Main.scale()))) / 2), (232/2) * (Main.scale()), (138/2) * (Main.scale()));
        glassGreenhouse = new Icons(new ImageIcon("assets/buildings/glassGreenhouse.png"), ((screenWidth - ((232/2) * (Main.scale()))) / 2), ((screenHeight - ((138/2) * (Main.scale()))) / 2), (232/2) * (Main.scale()), (138/2) * (Main.scale()));

        spaceBackground = new Icons(new ImageIcon("assets/icons/spaceBackground.png"), 0, 0, screenWidth, screenHeight);

        controls = new Icons(new ImageIcon("assets/icons/Controls.png"), 0, 0, 96*2, 69*2);

        commonWalkWay = new Icons(new ImageIcon("assets/buildings/commonareaground.png"), (screenWidth - (256 * (Main.scale() - 3))) / 2, (screenHeight - (256 * (Main.scale() - 3))) / 2, (256 * (Main.scale() - 3)), (256 * (Main.scale() - 3)));
        //ground = new Icons(new ImageIcon("assets/buildings/ground.png"), (screenWidth - (533 * (Main.scale() - 2))) / 2, (screenHeight - (454 * (Main.scale() - 2))) / 2, (533* (Main.scale())) / 2, 454 * (Main.scale()) / 2);

        cash = 0;
        kash = new Icons(new ImageIcon("assets/icons/kash.png"), 0, 0, 32 * (Main.scale()-2), 16 * (Main.scale()-2));

        font = "Ani";
        showTerminal = false;
        terminalHitBox = new Icons(new ImageIcon("assets/farmer/transparent.png"), house.getX(), house.getY(), 64 * (Main.scale() -1), 64 * (Main.scale() - 3));
        //terminalHitBox = new Icons(new ImageIcon("assets/icons/empty.png"), house.getX(), house.getY(), 64 * (Main.scale() -1), 64 * (Main.scale() - 3));

        terminalMainScreen = new Icons(new ImageIcon("assets/terminal/terminalMainScreen.png"), (screenWidth - (144 * (Main.scale() - 2))) / 2, (screenHeight - (144 * (Main.scale() - 2))) / 2, 144*(Main.scale()-2), 144*(Main.scale()-2));
        terminalShopButton = new Icons(new ImageIcon("assets/terminal/terminalShopButton.png"), terminalMainScreen.getX(), terminalMainScreen.getY(), 52*(Main.scale()-2), 13*(Main.scale()-2));
        terminalSellButton = new Icons(new ImageIcon("assets/terminal/terminalSellButton.png"), terminalShopButton.getX(), terminalMainScreen.getY(), 29*(Main.scale()-2), 13*(Main.scale()-2));
        terminalRepairsButton = new Icons(new ImageIcon("assets/terminal/termrinalRepairsButton.png"), terminalSellButton.getX(), terminalMainScreen.getY(), 41*(Main.scale()-2), 13*(Main.scale()-2));

        showSell = true;
        showShop = false;
        showRepairs = false;

        // shopBlank = new Icons(new ImageIcon("assets/terminal/shopBlank.png"), terminalMainScreen.getX(), terminalMainScreen.getY(), 144*(Main.scale()-2), 144*(Main.scale()-2));
        // kornflowerShop = new Icons(new ImageIcon("assets/terminal/kornflowerShop.png"), (screenWidth - (16 * (Main.scale() - 2))) / 2, ((screenHeight - (16 * (Main.scale() - 2))) / 2) + ((Main.scale()-2)*15), 16*(Main.scale()-2), 16*(Main.scale()-2));
        // celestialWheatShop = new Icons(new ImageIcon("assets/terminal/celestialWheatShop.png"), terminalMainScreen.getX() + 415, terminalMainScreen.getY() + (terminalMainScreen.getH()/2) - 85, 16*(Main.scale()-2), 16*(Main.scale()-2));
        // gooseberryShop = new Icons(new ImageIcon("assets/terminal/gooseberryShop.png"), terminalMainScreen.getX() + 85, terminalMainScreen.getY() + (terminalMainScreen.getH()/2) - 105, 16*(Main.scale()-2), 16*(Main.scale()-2));
        
        shopBlank = new Icons(new ImageIcon("assets/terminal/shopBlank.png"), terminalMainScreen.getX(), terminalMainScreen.getY(), 144*(Main.scale()-2), 144*(Main.scale()-2));
        kornflowerShop = new Icons(new ImageIcon("assets/terminal/kornflowerShop.png"), ((shopBlank.getX()) - 30 - (shopBlank.getW()/2)-(16*(Main.scale()-2))), ((screenHeight - (16 * (Main.scale() - 2))) / 2) + ((Main.scale()-2)*15), 16*(Main.scale()-2), 16*(Main.scale()-2));
        celestialWheatShop = new Icons(new ImageIcon("assets/terminal/celestialWheatShop.png"), shopBlank.getX(), terminalMainScreen.getY() + (terminalMainScreen.getH()/2) - 85, 16*(Main.scale()-2), 16*(Main.scale()-2));
        gooseberryShop = new Icons(new ImageIcon("assets/terminal/gooseberryShop.png"), shopBlank.getX() + 85, terminalMainScreen.getY() + (terminalMainScreen.getH()/2) - 105, 16*(Main.scale()-2), 16*(Main.scale()-2));

        planters = new ArrayList<Planters>();
    }   

    public void screen(Graphics g2d) {
        switch (screen) {
            case "start":
                // Start Screen
                setEmptyInvetory();
                drawStartScreen(g2d);
                if (airlockCreation == false) {
                    createAirlockArray();
                    airlockCreation = true;
                    createPlantersArray();
                }
                //createSave();
                break;
            case "mainFarm":
                cropGrowth();
                g2d.drawImage(spaceBackground.getPic().getImage(), 0, 0, screenWidth, screenHeight, this);
                //g2d.drawImage(ground.getPic().getImage(), ground.getX(), ground.getY(), ground.getW(), ground.getH(), this);
                g2d.drawImage(commonWalkWay.getPic().getImage(), commonWalkWay.getX(), commonWalkWay.getY(), commonWalkWay.getW(), commonWalkWay.getH(), this);
                drawControls(g2d);
                drawAirlock(g2d);
                //createSave();
                // Base
                //drawCrops(g2d);
                drawFarmer(g2d);
                updateInventory();
                drawInvetory(g2d);
                drawHotbar(g2d);
                drawPause(g2d);
                break;
            case "greenhouse1":
                cropGrowth();
                g2d.drawImage(spaceBackground.getPic().getImage(), 0, 0, screenWidth, screenHeight, this);
                g2d.drawImage(floorGreenhouse.getPic().getImage(), floorGreenhouse.getX(), floorGreenhouse.getY(), floorGreenhouse.getW(), floorGreenhouse.getH(), this);
                g2d.drawImage(glassGreenhouse.getPic().getImage(), glassGreenhouse.getX(), glassGreenhouse.getY(), glassGreenhouse.getW(), glassGreenhouse.getH(), this);
                
                drawAirlock(g2d);
                drawPlanters(g2d);
                drawControls(g2d);
                drawCrops(g2d);
                drawFarmer(g2d);
                updateInventory();
                drawInvetory(g2d);
                drawHotbar(g2d);
                drawPause(g2d);
                break;
            case "greenhouse2":
                cropGrowth();
                g2d.drawImage(spaceBackground.getPic().getImage(), 0, 0, screenWidth, screenHeight, this);
                g2d.drawImage(floorGreenhouse.getPic().getImage(), floorGreenhouse.getX(), floorGreenhouse.getY(), floorGreenhouse.getW(), floorGreenhouse.getH(), this);
                g2d.drawImage(glassGreenhouse.getPic().getImage(), glassGreenhouse.getX(), glassGreenhouse.getY(), glassGreenhouse.getW(), glassGreenhouse.getH(), this);

                drawAirlock(g2d);
                drawPlanters(g2d);
                drawControls(g2d);
                drawCrops(g2d);
                drawFarmer(g2d);
                updateInventory();
                drawInvetory(g2d);
                drawHotbar(g2d);
                drawPause(g2d);
                break;
            case "greenhouseBig":
                cropGrowth();
                g2d.drawImage(spaceBackground.getPic().getImage(), 0, 0, screenWidth, screenHeight, this);
                drawPlanters(g2d);
                drawControls(g2d);
                break;
            case "home":
                cropGrowth();
                g2d.drawImage(spaceBackground.getPic().getImage(), 0, 0, screenWidth, screenHeight, this);
                g2d.drawImage(house.getPic().getImage(), house.getX(), house.getY(), house.getW(), house.getH(), this);
                g2d.drawImage(house2.getPic().getImage(), house2.getX(), house2.getY(), house2.getW(), house2.getH(), this);
                g2d.drawImage(terminalHitBox.getPic().getImage(), terminalHitBox.getX(), terminalHitBox.getY(), terminalHitBox.getW(), terminalHitBox.getH(), this);
                
                drawControls(g2d);
                drawAirlock(g2d);
                drawCrops(g2d);
                drawFarmer(g2d);
                updateInventory();
                drawInvetory(g2d);
                drawHotbar(g2d);
                drawTerminal(g2d);
                drawPause(g2d);
                break;
        }
    }

    public void drawStartScreen(Graphics g2d) {
        // create start screen
        // if (Main.screenSizeFigureOuter() == 768){
        //     sbg.setW(1366);
        // }
        // else {
        //     sbg.setW(1920);
        // }
        g2d.drawImage(sbg.getPic().getImage(), sbg.getX(), sbg.getY(), sbg.getW(), sbg.getH(), this);
        g2d.drawImage(logo.getPic().getImage(), logo.getX(), logo.getY(), logo.getW(), logo.getH(), this);
        // if (playBoolean == true) {
        //     g2d.drawImage(playSelected.getPic().getImage(), playSelected.getX(), playSelected.getY(), playSelected.getW(), playSelected.getH(), this);
        // } else {
        //     g2d.drawImage(play.getPic().getImage(), play.getX(), play.getY(), play.getW(), play.getH(), this);
        // }
        g2d.drawImage(play.getPic().getImage(), play.getX(), play.getY(), play.getW(), play.getH(), this);
        g2d.drawImage(load.getPic().getImage(), load.getX(), load.getY(), load.getW(), load.getH(), this);
    }

    public void drawFarmer(Graphics g2d) {
        g2d.drawImage(farmer.getPic().getImage(), farmer.getX(), farmer.getY(), farmer.getW(), farmer.getH(), this);
        move();
    }

    public void drawControls(Graphics g2d){
        controls.setX(screenWidth - controls.getW()-25);
        controls.setY(screenHeight - controls.getH()-10 );
        g2d.drawImage(controls.getPic().getImage(), controls.getX(), controls.getY(), controls.getW(), controls.getH(), this);
    }

    public int returnCurrentBackgroundWidth() {
        if (screen == "home")
            return house2.getX();
        else if (screen == "greenhouse1"){
            return floorGreenhouse.getX();
        }
           else if (screen == "greenhouse2"){
            return floorGreenhouse.getX();
        }
        else if (screen == "greenhouseBig"){
            return floorGreenhouse.getX();
        }
        else if (screen == "mainFarm"){
            return commonWalkWay.getX();
        }
        else
            return 0;
    }

    public int returnCurrentBackgroundHeight() {
        if (screen == "home")
            return house2.getY();
        else if (screen == "greenhouse1"){
            return floorGreenhouse.getY();
        }
        else if (screen == "greenhouse2"){
            return floorGreenhouse.getY();
        }
        else if (screen == "greenhouseBig"){
            return floorGreenhouse.getY();
        }
        else if (screen == "mainFarm"){
            return commonWalkWay.getY();
        }
        else
            return 0;
    }

    public int returnCurrentBackgroundScaleW() {
        if (screen == "home")
            // return ((screenWidth-(128*(Main.scale())))/2) + (128*(Main.scale()));
            return house2.getX() + house2.getW();
        else if (screen == "greenhouse1"){
            return floorGreenhouse.getX() + floorGreenhouse.getW();
        }
        else if (screen == "greenhouse2"){
            return floorGreenhouse.getX() + floorGreenhouse.getW();
        }
        else if (screen == "greenhouseBig"){
            return floorGreenhouse.getX() + floorGreenhouse.getW();
        }
        else if (screen == "mainFarm"){
            return commonWalkWay.getX() + commonWalkWay.getW();
        }
        else
            return screenWidth;
    }

    public int returnCurrentBackgroundScaleH() {
        if (screen == "home")
            // return ((screenHeight-(72*(Main.scale())))/2) + (72*(Main.scale()));
            return house2.getY() + house2.getH();
        else if (screen == "greenhouse1"){
            return floorGreenhouse.getY() + floorGreenhouse.getH();
        }
        else if (screen == "greenhouse2"){
            return floorGreenhouse.getY() + floorGreenhouse.getH();
        }
        else if (screen == "greenhouseBig"){
            return floorGreenhouse.getY() + floorGreenhouse.getH();
        }
        else if (screen == "mainFarm"){
            return commonWalkWay.getY() + commonWalkWay.getH();
        }
        else
            return screenHeight;
    }

    public void drawAirlock(Graphics g2d) {
        for (Airlocks a : airlocks) {
            if (a.getCurscreen() == screen) {
                g2d.drawImage(a.getPic().getImage(), a.getX(), a.getY(), a.getW(), a.getH(), this);
            }
        }
    }

    public void createAirlockArray() {
        // add the other airlocks here
        //if position is incorrect on larger screen make an if statement that will have to array input lists
        airlocks.add(new Airlocks(new ImageIcon("assets/buildings/airlock.png"), (house2.getX() + (house2.getW()/2)) - (32 * (Main.scale() + 2)) / 2, house2.getY() + house2.getH() - (Main.scale() * 2), 32 * (Main.scale() + 2), 32 * (Main.scale() + 2), "home", "mainFarm"));
        //floorGreenhouse.getX() + (floorGreenhouse.getW()/2)) - (32 * (Main.scale() + 2)) / 2
        //floorGreenhouse.getY() + floorGreenhouse.getH() - (Main.scale() * 2)
        airlocks.add(new Airlocks(new ImageIcon("assets/buildings/airlock.png"), (floorGreenhouse.getX() + (floorGreenhouse.getW())), floorGreenhouse.getY() + (floorGreenhouse.getH()/2) - (32 * (Main.scale() + 2)) / 2, 32 * (Main.scale() + 2), 32 * (Main.scale() + 2), "greenhouse1", "mainFarm"));
        airlocks.add(new Airlocks(new ImageIcon("assets/buildings/airlock.png"), (floorGreenhouse.getX() + (floorGreenhouse.getW())), floorGreenhouse.getY() + (floorGreenhouse.getH()/2) - (32 * (Main.scale() + 2)) / 2, 32 * (Main.scale() + 2), 32 * (Main.scale() + 2), "greenhouse2", "mainFarm"));
        
        airlocks.add(new Airlocks(new ImageIcon("assets/buildings/airlock.png"), (commonWalkWay.getX() + (commonWalkWay.getW()/2)) - (32 * (Main.scale() + 2)) / 2, commonWalkWay.getY() - 32 * (Main.scale() + 2), 32 * (Main.scale() + 2), 32 * (Main.scale() + 2), "mainFarm", "home"));
        //greenhouseBig -  deactivated since there is no greenhouseBig 
        airlocks.add(new Airlocks(new ImageIcon("assets/buildings/airlock.png"), (commonWalkWay.getX() + (commonWalkWay.getW()/2)) - (32 * (Main.scale() + 2)) / 2, commonWalkWay.getY() + commonWalkWay.getH() - (Main.scale() * 2), 32 * (Main.scale() + 2), 32 * (Main.scale() + 2), "mainFarm", "mainFarm"));
        airlocks.add(new Airlocks(new ImageIcon("assets/buildings/airlock.png"), (commonWalkWay.getX() - 32 * (Main.scale() + 2)), commonWalkWay.getY() + (commonWalkWay.getH()/3) - (32 * (Main.scale() + 2)) / 2, 32 * (Main.scale() + 2), 32 * (Main.scale() + 2), "mainFarm", "greenhouse1"));
        airlocks.add(new Airlocks(new ImageIcon("assets/buildings/airlock.png"), (commonWalkWay.getX() - 32 * (Main.scale() + 2)), commonWalkWay.getY() + ((commonWalkWay.getH()*2)/3) - (32 * (Main.scale() + 2)) / 2, 32 * (Main.scale() + 2), 32 * (Main.scale() + 2), "mainFarm", "greenhouse2"));
    }

    public void drawPlanters(Graphics g2d) {
        for (Planters p : planters) {
            if (p.getScreen() == screen) {
                g2d.drawImage(p.getPic().getImage(), p.getX(), p.getY(), p.getW(), p.getH(), this);
            }
        }
    }

    public void createPlantersArray(){
        //greenhouse 1
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)-(Main.scale()+2)),floorGreenhouse.getY()+ ((floorGreenhouse.getH()/6)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse1", false, false));
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)+((Main.scale()-2)*37)),floorGreenhouse.getY()+ ((floorGreenhouse.getH()/6)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse1", false, false));
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)+((Main.scale()-2)*(37*2))+4),floorGreenhouse.getY()+ ((floorGreenhouse.getH()/6)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse1", false, false));
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)+((Main.scale()-2)*(37*3))+4),floorGreenhouse.getY()+ ((floorGreenhouse.getH()/6)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse1", false, false));

        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)-(Main.scale()+2)),floorGreenhouse.getY() + (Main.scale()*5) + ((floorGreenhouse.getH()/2)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse1", false, false));
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)+((Main.scale()-2)*37)),floorGreenhouse.getY() + (Main.scale()*5) + ((floorGreenhouse.getH()/2)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse1", false, false));
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)+((Main.scale()-2)*(37*2))+4),floorGreenhouse.getY() + (Main.scale()*5) + ((floorGreenhouse.getH()/2)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse1", false, false));
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)+((Main.scale()-2)*(37*3))+4),floorGreenhouse.getY() + (Main.scale()*5) + ((floorGreenhouse.getH()/2)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse1", false, false));

        //greenhouse 2
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)-(Main.scale()+2)),floorGreenhouse.getY()+ ((floorGreenhouse.getH()/6)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse2", false, false));
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)+((Main.scale()-2)*37)),floorGreenhouse.getY()+ ((floorGreenhouse.getH()/6)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse2", false, false));
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)+((Main.scale()-2)*(37*2))+4),floorGreenhouse.getY()+ ((floorGreenhouse.getH()/6)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse2", false, false));
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)+((Main.scale()-2)*(37*3))+4),floorGreenhouse.getY()+ ((floorGreenhouse.getH()/6)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse2", false, false));

        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)-(Main.scale()+2)),floorGreenhouse.getY() + (Main.scale()*5) + ((floorGreenhouse.getH()/2)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse2", false, false));
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)+((Main.scale()-2)*37)),floorGreenhouse.getY() + (Main.scale()*5) + ((floorGreenhouse.getH()/2)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse2", false, false));
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)+((Main.scale()-2)*(37*2))+4),floorGreenhouse.getY() + (Main.scale()*5) + ((floorGreenhouse.getH()/2)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse2", false, false));
        planters.add(new Planters(new ImageIcon("assets/buildings/planter.png"), floorGreenhouse.getX()+ ((floorGreenhouse.getW()/9)+((Main.scale()-2)*(37*3))+4),floorGreenhouse.getY() + (Main.scale()*5) + ((floorGreenhouse.getH()/2)-(Main.scale()-3)),(35)* ((Main.scale()) / 2),(38)* ((Main.scale()) / 2), "greenhouse2", false, false));
    }

    public void move() {
        // change screenwidth and height to image width and height
        // account for the image being in a diff location than top corner
        farmer.move(returnCurrentBackgroundWidth(), returnCurrentBackgroundHeight(), returnCurrentBackgroundScaleW(),
                returnCurrentBackgroundScaleH());
    }

    public void cropGrowth(){
        curtime=(System.currentTimeMillis()-time)/1000;
        for (Crops c: cropList){
            if ((c.getPic().getDescription()=="assets/plants/kornflower/kornflower1.png") && ((curtime-c.getStartTime() >= 5.00) && (curtime-c.getStartTime() <= 6.00))){
                c.setPic(new ImageIcon("assets/plants/kornflower/kornflower2.png"));
                
            }
            else if ((c.getPic().getDescription()=="assets/plants/gooseberry/gooseberry1.png") && ((curtime-c.getStartTime() >= 5.00) && (curtime-c.getStartTime() <= 6.00))){
                c.setPic(new ImageIcon("assets/plants/gooseberry/gooseberry2.png"));
                
            }
            else if ((c.getPic().getDescription()=="assets/plants/celestialWheat/celestialwheat1.png") && ((curtime-c.getStartTime() >= 5.00) && (curtime-c.getStartTime() <= 6.00))){
                c.setPic(new ImageIcon("assets/plants/celestialWheat/celestialwheat2.png"));
                
            }
            else if ((c.getPic().getDescription()=="assets/plants/kornflower/kornflower2.png") && ((curtime-c.getStartTime() >= 10.00) && (curtime-c.getStartTime() <= 11.00))){
                c.setPic(new ImageIcon("assets/plants/kornflower/kornflower3.png"));
                
            }
            else if ((c.getPic().getDescription()=="assets/plants/gooseberry/gooseberry2.png") && ((curtime-c.getStartTime() >= 10.00) && (curtime-c.getStartTime() <= 11.00))){
                c.setPic(new ImageIcon("assets/plants/gooseberry/gooseberry3.png"));
                
            }
            else if ((c.getPic().getDescription()=="assets/plants/celestialWheat/celestialwheat2.png") && ((curtime-c.getStartTime() >= 10.00) && (curtime-c.getStartTime() <= 11.00))){
                c.setPic(new ImageIcon("assets/plants/celestialWheat/celestialwheat3.png"));
                
            }
        }
    }

    public Boolean addCrop() {
        for (Planters p: planters){
            //if ((farmer.getX() > p.getX() && farmer.getX() < p.getX() + p.getW()) && (farmer.getY() > p.getY() && farmer.getY() < p.getY() + p.getH())){
            if ((p.Collision(farmer) == true) && (p.getHasCrop() == false)){
                for (Inventory i: inventory){
                    if (selectedItem == i.getSlot()){
                        if (i.getPic().getDescription() == ("assets/plants/kornflower/kornflowerSeeds.png")){
                            p.setHasCrop(true);
                            cropList.add(new Crops(new ImageIcon("assets/plants/kornflower/kornflower1.png"), (p.getX() + (p.getW()-(32*(Main.scale()-2)))), (p.getY() + (p.getH()/2)-(32*(Main.scale()-2))), 32*(Main.scale()-2), 32*(Main.scale()-2), screen, false, curtime));
                            //cropList.add(new Crops(new ImageIcon("assets/plants/kornflower/kornflower1.png"), ((p.getX() + (p.getW()/2)) - (32 * (Main.scale() - 2))) / 2, (p.getY() + (p.getH() / 2)), 32*(Main.scale()-2), 32*(Main.scale()-2), screen, false));
                            i.setQuantity(i.getQuantity()-1);
                            if (i.getQuantity() == 0){
                                i.setPic(new ImageIcon("assets/icons/empty.png"));
                            }
                            
                            return true;
                        }
                        else if (i.getPic().getDescription() == ("assets/plants/gooseberry/gooseberrySeeds.gif")){
                            p.setHasCrop(true);
                            cropList.add(new Crops(new ImageIcon("assets/plants/gooseberry/gooseberry1.png"),  (p.getX() + (p.getW()-(32*(Main.scale()-2)))), (p.getY() + (p.getH()/2)-(32*(Main.scale()-2))), 32*(Main.scale()-2), 32*(Main.scale()-2), screen, false, curtime));
                            i.setQuantity(i.getQuantity()-1);
                            if (i.getQuantity() == 0){
                                i.setPic(new ImageIcon("assets/icons/empty.png"));
                            }
                            
                            return true;
                        }
                        else if (i.getPic().getDescription() == ("assets/plants/celestialWheat/celestialWheatSeeds.png")){
                            p.setHasCrop(true);
                            cropList.add(new Crops(new ImageIcon("assets/plants/celestialWheat/celestialwheat1.png"),  (p.getX() + (p.getW()-(32*(Main.scale()-2)))), (p.getY() + (p.getH()/2)-(32*(Main.scale()-2))), 32*(Main.scale()-2), 32*(Main.scale()-2), screen, false, curtime));
                            i.setQuantity(i.getQuantity()-1);
                            if (i.getQuantity() == 0){
                                i.setPic(new ImageIcon("assets/icons/empty.png"));
                            }
                            
                            return true;
                        }
                    }
                }
            }
        
            
        }
        return false;



        // for (Inventory i: inventory){ 
        //     if (selectedItem == i.getSlot()){
        //         if (i.getPic().getDescription() == ("assets/plants/kornflower/kornflowerSeeds.png")){
        //             cropList.add(new Crops(new ImageIcon("assets/plants/kornflower/kornflower1.png"), (farmer.getX() - (farmer.getW() / 2)), (farmer.getY() - (farmer.getH() / 2)), 32*(Main.scale()-2), 32*(Main.scale()-2), screen, false));
        //             i.setQuantity(i.getQuantity()-1);
        //             return true;
        //         }
        //         else if (i.getPic().getDescription() == ("assets/plants/gooseberry/gooseberrySeeds.gif")){
        //             cropList.add(new Crops(new ImageIcon("assets/plants/gooseberry/gooseberry1.png"), (farmer.getX() + (farmer.getW() / 2)), (farmer.getY() + (farmer.getH() / 2)), 32*(Main.scale()-2), 32*(Main.scale()-2), screen, false));
        //             i.setQuantity(i.getQuantity()-1);
        //             return true;
        //         }
        //         else if (i.getPic().getDescription() == ("assets/plants/celestialWheat/celestialWheatSeeds.png")){
        //             cropList.add(new Crops(new ImageIcon("assets/plants/celestialWheat/celestialwheat1.png"), (farmer.getX() + (farmer.getW() / 2)), (farmer.getY() + (farmer.getH() / 2)), 32*(Main.scale()-2), 32*(Main.scale()-2), screen, false));
        //             i.setQuantity(i.getQuantity()-1);
        //             return true;
        //         }
                
                
            //}
            
            
        //}
        //cropList.add(new Crops(new ImageIcon("assets/plants/testPlant/testPlant.png"), (farmer.getX() + (farmer.getW() / 2)), (farmer.getY() + (farmer.getH() / 2)), 32, 32, screen, false));
    }

    public void drawCrops(Graphics g2d) {
        for (Crops c : cropList) {
            if (screen == c.getScreen())
                g2d.drawImage(c.getPic().getImage(), c.getX(), c.getY(), c.getW(), c.getH(), this);
        }
    }

    public void setEmptyInvetory() {
        if (inventory.size() < 1) {
            for (int i = 0; i < 16; i++) {
                int scale = 0;
                if (Main.scale() == 4){
                    scale = 1;
                }
                else if (Main.scale() == 6){
                    scale = 2;
                }

                inventory.add(new Inventory(new ImageIcon("assets/icons/empty.png"), 0, 0, 64*scale, 64*scale, 1, i + 1));
            }
        }
    }

    public void drawTerminal(Graphics g2d){
        if (showTerminal == true){
            g2d.drawImage(terminalMainScreen.getPic().getImage(), terminalMainScreen.getX(), terminalMainScreen.getY(), terminalMainScreen.getW(), terminalMainScreen.getH(), this);
            g2d.drawImage(terminalShopButton.getPic().getImage(), terminalShopButton.getX(), terminalShopButton.getY(), terminalShopButton.getW(), terminalShopButton.getH(), this);
            terminalSellButton.setX(terminalShopButton.getX() + terminalShopButton.getW());
            g2d.drawImage(terminalSellButton.getPic().getImage(), terminalSellButton.getX(), terminalSellButton.getY(), terminalSellButton.getW(), terminalSellButton.getH(), this);
            terminalRepairsButton.setX(terminalSellButton.getX() + terminalSellButton.getW());
            g2d.drawImage(terminalRepairsButton.getPic().getImage(), terminalRepairsButton.getX(), terminalRepairsButton.getY(), terminalRepairsButton.getW(), terminalRepairsButton.getH(), this);

            //draw cash image and #
            // + (terminalMainScreen.getW())/2-kash.getW()
            kash.setX((terminalMainScreen.getX()));
            kash.setY(terminalMainScreen.getY()+terminalMainScreen.getH()-kash.getH());
            g2d.drawImage(kash.getPic().getImage(), kash.getX(), kash.getY(), kash.getW(), kash.getH(), this);
            Color green = new Color(119, 199, 44);
            g2d.setColor(green);
            g2d.setFont(new java.awt.Font(font, java.awt.Font.BOLD, 10*(Main.scale()-2)));
            g2d.drawString(Integer.toString(cash), (kash.getX()+(kash.getW())), (kash.getY()+5+(10*(Main.scale()-2))));

            if (showSell == true){
                //draw sell screen
                
            }
            else if (showShop == true){
                //draw shop screen
                
                g2d.drawImage(shopBlank.getPic().getImage(), shopBlank.getX(), shopBlank.getY(), shopBlank.getW(), shopBlank.getH(), this);
                g2d.drawImage(kornflowerShop.getPic().getImage(), kornflowerShop.getX(), kornflowerShop.getY(), kornflowerShop.getW(), kornflowerShop.getH(), this);
                g2d.drawImage(celestialWheatShop.getPic().getImage(), celestialWheatShop.getX(), celestialWheatShop.getY(), celestialWheatShop.getW(), celestialWheatShop.getH(), this);
                g2d.drawImage(gooseberryShop.getPic().getImage(), gooseberryShop.getX(), gooseberryShop.getY(), gooseberryShop.getW(), gooseberryShop.getH(), this);
            }
            else if (showRepairs == true){terminalMainScreen.setX((screenWidth - (144 * (Main.scale() - 2))) / 2);
                //draw repairs screen
            }
            //if (showSell ==true){
            if (showSell ==true || showShop == true){
                terminalMainScreen.setX(screenWidth/50);
                terminalShopButton.setX(terminalMainScreen.getX());
                shopBlank.setX(terminalMainScreen.getX());
                kornflowerShop.setX(kornflowerShop.getX());
                celestialWheatShop.setX(shopBlank.getX()+415);
                gooseberryShop.setX(shopBlank.getX()+77);

                showInvetory = true;
                inventoryMenu.setX(screenWidth-10-inventoryMenu.getW());
            }
            //else if (showSell == false){ 
            else if (showSell == false && showShop == false){  
                terminalMainScreen.setX((screenWidth - (144 * (Main.scale() - 2))) / 2);
                terminalShopButton.setX(terminalMainScreen.getX());

                showInvetory = false;
                inventoryMenu.setX((screenWidth - (256 * (Main.scale() - 2))) / 2);
            }
        }
    }

    int key3 = -1;
    public void sellItem(int x, int y){
        for (Inventory i: inventory){
            if (i.getX() + i.getW() >= x && i.getX() <= x && i.getY() + i.getH() >= y&& i.getY() <= y){
                key3=i.getSlot();
            }
        }

        if (key3 != -1){
            for (Inventory i: inventory){
                if (i.getSlot() == key3){
                    if (i.getPic().getDescription() != "assets/icons/empty.png"){
                        if (i.getPic().getDescription() == "assets/plants/kornflower/kornflower3.png"){
                            cash+=150;
                            i.setQuantity(i.getQuantity()-1);
                            if (i.getQuantity() == 0){
                                i.setPic(new ImageIcon("assets/icons/empty.png"));
                            }
                            
                        }
                        else if (i.getPic().getDescription() == "assets/plants/gooseberry/gooseberry3.png"){
                            cash+=10;
                            i.setQuantity(i.getQuantity()-1);
                            if (i.getQuantity() == 0){
                                i.setPic(new ImageIcon("assets/icons/empty.png"));
                            }
                            
                        }
                        else if (i.getPic().getDescription() == "assets/plants/celestialWheat/celestialwheat3.png"){
                            cash+=50;
                            i.setQuantity(i.getQuantity()-1);
                            if (i.getQuantity() == 0){
                                i.setPic(new ImageIcon("assets/icons/empty.png"));
                            }
                        }
                    }
                }
            }
        }
    }

    public void drawHotbar(Graphics g2d) {
        if (showTerminal == true){
            showHotbar = false;
        }
        else if (showInvetory == true){
            showHotbar = false;
        }
        else if (showTerminal == false && showInvetory == false){
            showHotbar = true;
        }
        if (showHotbar == true){
            g2d.drawImage(hotbar.getPic().getImage(), hotbar.getX(), hotbar.getY(), hotbar.getW(), hotbar.getH(), this);
            for (Inventory i: inventory){
                int x=hotbar.getX();
                int y=hotbar.getY();

                //setx and sety will need to be adjusted for screensize
                //set x
                if (i.getSlot()<=4){
                    if ((  i.getSlot() % 4) == 1){
                        i.setX(x+(128*2 * 2/23));
                    }
                    else if (( i.getSlot() % 4) == 2){
                        i.setX(x+(int)(((float)(128*2 * 2))/3.5));
                    }
                    else if ((i.getSlot() % 4) == 3){
                        i.setX(x+(int)(((float)(128*2 * 2))/1.85));
                    }
                    else if (( i.getSlot() % 4) == 0){
                        i.setX(x+(int)(((float)(128*2 * 2))*0.79));
                    }
        
                    //set y
                    i.setY(y+(int)(((float)(33*2 *2))/4));

                    //set w and h
                    i.setW(64);
                    i.setH(64);
                }   
                }
                for (Inventory i : inventory) {
                    if (i.getSlot()<=4){
                        g2d.drawImage(i.getPic().getImage(), i.getX(), i.getY(), i.getW(), i.getH(), this);
                        g2d.setColor(java.awt.Color.BLACK);
                        g2d.setFont(new java.awt.Font(font, java.awt.Font.BOLD, 10*2));
                        if (i.getPic().getDescription() != ("assets/icons/empty.png")){
                            g2d.drawString(Integer.toString(i.getQuantity()), (i.getX()+i.getW())-(i.getW()/10), (i.getY()+i.getH()));
                    }
                    }
                    
                }
        }
        
        
    }

    public void drawInvetory(Graphics g2d) {
        //add temp item into slot 1 is "q"
        if (showTerminal == false){
            // if (showInvetory == true){
            //     showInvetory = true;
            // }
            // else if (showInvetory == false){
            //     showInvetory = false;
            // }
            //showInvetory = false;
            inventoryMenu.setX((screenWidth - (256 * (Main.scale() - 2))) / 2);
        }
        if (showInvetory == true) {
            g2d.drawImage(inventoryMenu.getPic().getImage(), inventoryMenu.getX(), inventoryMenu.getY(), inventoryMenu.getW(), inventoryMenu.getH(), this);
            for (Inventory i : inventory) {

                //set w and h
                // i.setW(64*(Main.scale()-2));
                // i.setH(64*(Main.scale()-2));
                i.setW(64*2);
                i.setH(64*2);

                g2d.drawImage(i.getPic().getImage(), i.getX(), i.getY(), i.getW(), i.getH(), this);
                g2d.setColor(java.awt.Color.BLACK);
                g2d.setFont(new java.awt.Font(font, java.awt.Font.BOLD, 10*(Main.scale()-2)));
                if (i.getPic().getDescription() != ("assets/icons/empty.png")){
                    g2d.drawString(Integer.toString(i.getQuantity()), (i.getX()+i.getW())-(i.getW()/10), (i.getY()+i.getH()));
                }
            }
        }
    }

    public void addItemInInventory(ImageIcon item, int quantity){
        //add item to inventory
        //add item to first empty slot
        //if no empty slots, do nothing

        //Add throw error if there are no empty slots and refuse the item
        for (Inventory i: inventory){
            
            if (i.getPic().getDescription() == item.getDescription()){
                i.setQuantity(i.getQuantity()+quantity);
                break;
            }
            else if (i.getPic().getDescription() == "assets/icons/empty.png"){
                i.setPic(item);
                i.setQuantity(quantity);
                break;
            }
        }
    }

    public void testaddItemInInventory(int slot, ImageIcon item, int quantity){
        //add item to inventory
        //add item to first empty slot
        //if no empty slots, do nothing

        //Add throw error if there are no empty slots and refuse the item

        if (slot == -1){
            for (Inventory i: inventory){
                if (i.getPic().getDescription() == "assets/icons/empty.png"){
                    i.setPic(item);
                    i.setQuantity(quantity);
                    i.setSlot(i.getSlot());
                    break;
                }
            }
        }
        else {
            for (Inventory i: inventory){
                if (i.getSlot() == slot){
                    i.setPic(item);
                    i.setQuantity(quantity);
                }
            }
        }
    }
    
    public void updateInventory() {
        for (Inventory i: inventory){
            int x=inventoryMenu.getX();
            int y=inventoryMenu.getY();

            //setx and sety will need to be adjusted for screensize
            //set x
            if ((  i.getSlot() % 4) == 1){
                i.setX(x+(256 * (Main.scale() - 2)/23));
            }
            else if (( i.getSlot() % 4) == 2){
                i.setX(x+(int)(((float)(256 * (Main.scale() - 2)))/3.5));
            }
            else if ((i.getSlot() % 4) == 3){
                i.setX(x+(int)(((float)(256 * (Main.scale() - 2)))/1.85));
            }
            else if (( i.getSlot() % 4) == 0){
                i.setX(x+(int)(((float)(256 * (Main.scale() - 2)))*0.79));
            }

            //set y
            float floatSlot = (float) i.getSlot();

            if (( floatSlot / 4) >3){
                i.setY(y+(int)(((float)(224 * (Main.scale() - 2)))*0.782));
            } 
            else if (( floatSlot / 4) >2){
                i.setY(y+(int)(((float)(224 * (Main.scale() - 2)))/1.875));
            }
            else if (( floatSlot / 4) > 1){
                i.setY(y+(int)(((float)(224 * (Main.scale() - 2)))/3.45));
            }
            else if ((  floatSlot / 4) <=1){
                i.setY(y+(int)(((float)(224 * (Main.scale() - 2)))/20));
                
            }
        }
    }

    int key1 = -1;
    int key2 = -1;
    public void moveItemsInInventory(int x, int y) {
        // move items in inventory
        // change from 
        

        for (Inventory i: inventory){
            if (key1 == -1){
                // key1=Integer.parseInt(key);
                if (i.getX() + i.getW() >= x && i.getX() <= x && i.getY() + i.getH() >= y&& i.getY() <= y){
                    key1=i.getSlot();
                }
            }
            else if ((key1 != -1) && (key2 == -1)){
                if (i.getX() + i.getW() >= x && i.getX() <= x && i.getY() + i.getH() >= y&& i.getY() <= y){
                    key2=i.getSlot();
                }
            }
        }

        int slot1 = -1;
        int slot2 = -1;
        
        for (Inventory i : inventory) {
            
            // System.out.println("key1: " + key1);
            // System.out.println("key2: " + key2);
            if (i.getSlot() == key1) {
                slot1 = i.getSlot();
            }
            if (i.getSlot() == key2) {
                slot2 = i.getSlot();
            }

            if (slot1 != -1 && slot2 != -1){
                for (Inventory j: inventory){
                    if (j.getSlot() == slot1){
                        j.setSlot(slot2);
                    }
                    else if (j.getSlot() == slot2){
                        j.setSlot(slot1);
                    }
                }
                slot1 = -1;
                slot2 = -1;
                key1 = -1;
                key2 = -1;
            }
        }
    }

    public void drawPause(Graphics g2d){
        if (showPause == true){
            g2d.drawImage(save.getPic().getImage(), save.getX(), save.getY(), save.getW(), save.getH(), this);
        }
    }

    public boolean Collision(Farmer b, Icons a) {
        return a.getX() + a.getW() >= b.getX() && a.getX() <= b.getX() + b.getW() && a.getY() + a.getH() >= b.getY()
                && a.getY() <= b.getY() + b.getH();
    }

    public boolean airlockCollision(Farmer b, Airlocks a) {
        return a.getX() + a.getW() >= b.getX() && a.getX() <= b.getX() + b.getW() && a.getY() + a.getH() >= b.getY()
                && a.getY() <= b.getY() + b.getH();
    }

    public void createSave(){
        File save = new File("save.txt");

        //This Try Statement writes to the file once it has been made
        try {
            if (save.createNewFile()){}
            else{}
        }
        catch(IOException e){
            e.printStackTrace();
        }
        // try (FileOutputStream fos = new FileOutputStream("cropArray");
        //     ObjectOutputStream oos = new ObjectOutputStream(fos);) {

        //     oos.writeObject(cropList);

        // } catch (FileNotFoundException e) {
        //     //log.error("File not found : ", e);
        //     throw new RuntimeException(e);
        // } catch (IOException ioe) {
        //     //log.error("Error while writing data : ", ioe);
        //     ioe.printStackTrace();
        // }
    }

    public final void saveCrops(ArrayList<Crops> cropList) {
        try {
            FileOutputStream fileOut = new FileOutputStream("save/crops.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(cropList);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in save.txt");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    // public final void loadCrops() {
    //     try {
    //         FileInputStream fileIn = new FileInputStream("save/crops.txt");
    //         ObjectInputStream in = new ObjectInputStream(fileIn);
    //         cropList = (ArrayList<Crops>) in.readObject();
    //         in.close();
    //         fileIn.close();
    //     } catch (IOException i) {
    //         i.printStackTrace();
    //         return;
    //     } catch (ClassNotFoundException c) {
    //         System.out.println("Crops class not found");
    //         c.printStackTrace();
    //         return;
    //     }
    // }

    public final ArrayList<Crops> loadCrops() {
        ArrayList<Crops> loadedList = null;
        try {
            FileInputStream fileIn = new FileInputStream("save/crops.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            loadedList = (ArrayList<Crops>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Crops class not found");
            c.printStackTrace();
        }
        return loadedList;
    }

    // public void saveSystem() throws IOException{
    //     FileWriter save = new FileWriter("save.txt");
    //     save.write((Integer.toString(farmer.getX()))+"\n");

        
    //     save.close();

        
        

    //     // File save = new File("save.txt");
    //     // try {
    //     //     if (save.createNewFile()){

    //     //     }
    //     //     else{

    //     //     }

    //     //     // for (int i=3; i>3; i++){
    //     //     //     cropList.add(Files.readAllLines(Paths.get("save.txt")).get(i));
    //     //     // }
    //     // }
    //     // catch(IOException e){
    //     //     e.printStackTrace();
    //     // }
    // }


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
        //System.out.println(key);

        if (key == 16) {
            sprint = true;
        }

        if (key == 79) {
            ArrayList<Crops> loadedCrops = loadCrops();
            if (loadedCrops != null) {
                cropList = loadedCrops;
            } else {
                System.out.println("Failed to load crops from file");
            }
        }

        if (key == 72) {
            screen = "home";
            // farmer.setX(tempAirlock.getX()+(tempAirlock.getW()/2));
            // farmer.setY(tempAirlock.getY());
        }

        if (key == 71){
            screen = "greenhouse1";
        }

        // if (key == 87) {
        //     //-1
        //     farmer.setDy(-(Main.scale()-3));
        //     farmer.setPic(new ImageIcon("assets/farmer/walkUp.gif"));
        // }
        // if (key == 83) {
        //     farmer.setDy((Main.scale()-3));
        //     farmer.setPic(new ImageIcon("assets/farmer/walkDown.gif"));
        // }
        // if (key == 65) {
        //     //-1
        //     farmer.setDx(-(Main.scale()-3));
        //     farmer.setPic(new ImageIcon("assets/farmer/walkLeft.gif"));
        // }
        // if (key == 68) {
        //     farmer.setDx((Main.scale()-3));
        //     farmer.setPic(new ImageIcon("assets/farmer/walkRight.gif"));
        // }

        // //-1
        // if ((sprint) && farmer.getDX() == -(Main.scale()-3)) {
        //     farmer.setDx(-2*(Main.scale()-3));
        //     //1
        // } else if ((sprint) && farmer.getDX() == (Main.scale()-3)) {
        //     farmer.setDx(2*(Main.scale()-3));
        // } else if ((sprint) && farmer.getDY() == -(Main.scale()-3)) {
        //     farmer.setDy(-2*(Main.scale()-3));
        // } else if ((sprint) && farmer.getDY() == (Main.scale()-3)) {
        //     farmer.setDy(2*(Main.scale()-3));
        // }
        if (key == 87) {
            //-1
            farmer.setDy(-1);
            farmer.setPic(new ImageIcon("assets/farmer/walkUp.gif"));
        }
        if (key == 83) {
            farmer.setDy(1);
            farmer.setPic(new ImageIcon("assets/farmer/walkDown.gif"));
        }
        if (key == 65) {
            //-1
            farmer.setDx(-1);
            farmer.setPic(new ImageIcon("assets/farmer/walkLeft.gif"));
        }
        if (key == 68) {
            farmer.setDx(1);
            farmer.setPic(new ImageIcon("assets/farmer/walkRight.gif"));
        }

        //-1
        if ((sprint) && farmer.getDX() == -1) {
            farmer.setDx(-2*1);
            //1
        } else if ((sprint) && farmer.getDX() == 1) {
            farmer.setDx(2*1);
        } else if ((sprint) && farmer.getDY() == -1) {
            farmer.setDy(-2*1);
        } else if ((sprint) && farmer.getDY() == 1) {
            farmer.setDy(2*1);
        }

    }

    // DO NOT DELETE
    @Override
    public void keyReleased(KeyEvent e) {
        key = e.getKeyCode();

        if ((key == 69) && (screen != "start")) {
            showInvetory = !showInvetory;
            // if (showInvetory == true){
            //     moveItemsInInventory(KeyEvent.getKeyText(key));
            // }
            showHotbar = !showHotbar;
        }

        if ((key == 27) && (screen != "start")) {
            showPause = !showPause;
        }

        if (key == 16) {
            sprint = false;
        }

        if ((key == 10) && (showPause == true)) {
            saveBoolean = true;
            screen = "start";
            showPause = false;
        } else {
            saveBoolean = false;
        }

        if ((key ==10) && (screen == "start")) {
            playBoolean = true;
            screen = "mainFarm";
        } else {
            playBoolean = false;
        }

        for (Airlocks a : airlocks) {
            if ((a.getCurscreen() == screen) && (airlockCollision(farmer, a)) && (key == 10)) {
                screen = a.getGTS();
            }
        }

        // if ((key == 81) ){
        //     //inventory.add(new Inventory(new ImageIcon("assets/icons/empty.png"), 0, 0, 64, 64, 5, 11));
        //     //inventory.add(new Inventory(new ImageIcon("assets/plants/testPlant/testPlant.png"), 0, 0, 64, 64, 23, 15));
        //     // for (Inventory i: inventory){
        //     //     if (i.getSlot() == 2)
        //     //         i.setPic(new ImageIcon("assets/plants/testPlant/testPlant.png"));
        //     //         i.setQuantity(25);
        //     // }
        //     testaddItemInInventory(1, new ImageIcon("assets/plants/kornflower/kornflowerSeeds.png"), 64);
        //     testaddItemInInventory(5, new ImageIcon("assets/plants/testPlant/testPlant.png"),64);
        //     testaddItemInInventory(2, new ImageIcon("assets/plants/celestialWheat/celestialWheatSeeds.png"),64);
        //     testaddItemInInventory(3, new ImageIcon("assets/plants/gooseberry/gooseberrySeeds.gif"),64);
        // }
        
        if (key ==81){
            cash = 10000;
            testaddItemInInventory(12, new ImageIcon("assets/plants/gooseberry/gooseberry3.png"), 16);
            testaddItemInInventory(13, new ImageIcon("assets/plants/kornflower/kornflower3.png"), 16);
            testaddItemInInventory(14, new ImageIcon("assets/plants/celestialWheat/celestialwheat3.png"), 16);
        }
        

        if (key == 87) {
            farmer.setDy(0);
            if ((farmer.getDX() == 0) && (farmer.getDY() == 0))
                farmer.setPic(new ImageIcon("assets/farmer/idleUp.png"));
        }
        if (key == 83) {
            farmer.setDy(0);
            if ((farmer.getDX() == 0) && (farmer.getDY() == 0))
                farmer.setPic(new ImageIcon("assets/farmer/idleDown.png"));
        }
        if (key == 65) {
            farmer.setDx(0);
            if ((farmer.getDX() == 0) && (farmer.getDY() == 0))
                farmer.setPic(new ImageIcon("assets/farmer/idleLeft.png"));
        }
        if (key == 68) {
            farmer.setDx(0);
            if ((farmer.getDX() == 0) && (farmer.getDY() == 0))
                farmer.setPic(new ImageIcon("assets/farmer/idleRight.png"));
        }

        if (!(sprint) && farmer.getDX() == -2) {
            farmer.setDx(-1);
        } else if (!(sprint) && farmer.getDX() == 2) {
            farmer.setDx(1);
        } else if (!(sprint) && farmer.getDY() == -2) {
            farmer.setDy(-1);
        } else if (!(sprint) && farmer.getDY() == 2) {
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

        if ((save.getX() + save.getW() >= m.getX() && save.getX() <= m.getX() && save.getY() + save.getH() >= m.getY()
                && save.getY() <= m.getY()) && (showPause == true)) {
            saveBoolean = true;
        } else {
            saveBoolean = false;
        }

        if ((load.getX() + load.getW() >= m.getX() && load.getX() <= m.getX() && load.getY() + load.getH() >= m.getY()
                && load.getY() <= m.getY()) && (screen == "start")) {
            loadBoolean = true;
        } else {
            loadBoolean = false;
        }

    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent m) {
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent m) {
        // m.getButton()==1 is Left Click
        // 2 is middle click
        // 3 is right click

        if ((playBoolean == true) && (screen == "start")) {
            screen = "mainFarm";
        }

        if ((loadBoolean == true) && (screen == "start")) {
            screen = "mainFarm";
            //add load system here
        }   

        if (showInvetory == true && m.getButton() == 1 && showTerminal == false){
            moveItemsInInventory(m.getX(), m.getY());
        }

        if (showInvetory == true && m.getButton() == 1 && showTerminal == true){
            sellItem(m.getX(), m.getY());
        }

        if ((saveBoolean == true) && (showPause == true)){
            try {
                saveCrops(cropList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }
        }

        

        // for (Airlocks a : airlocks) {
        // if ((screen == "mainFarm") && (m.getButton() == 3) &&
        // !(airlockCollision(farmer, a))) {
        // addCrop();
        // }
        // }

        if (((screen == "greenhouse1") || (screen == "greenhouse2") || (screen == "greenhouseBig")) && (m.getButton() == 3) && ((farmer.getDX() == 0) && (farmer.getDY() == 0))) { 
            for (Crops c : cropList) {
                if (c.Collision(farmer) == true) {
                    //(c.getPic().getDescription() == "assets/plants/celestialWheat/celestialwheat3") || (c.getPic().getDescription() == "assets/plants/gooseberry/gooseberry3") || (c.getPic().getDescription() == "assets/plants/kornflower/kornflower3")
                    if ((c.getPic().getDescription() == "assets/plants/celestialWheat/celestialwheat3.png") || (c.getPic().getDescription() == "assets/plants/gooseberry/gooseberry3.png") || (c.getPic().getDescription() == "assets/plants/kornflower/kornflower3.png")){
                        addItemInInventory(c.getPic(), 1);
                        cropList.remove(c);
                        for (Planters p: planters){
                            if (p.Collision(farmer) == true){
                                p.setHasCrop(false);
                            }
                        }
                    }
                }
            }
            
        }

//         planting

// if (correct screen)
// 	for (plant)
// 		if (player collides with plant)
// 			if (collide with planter)
// 				plant

        if (((screen == "greenhouse1") || (screen == "greenhouse2") || (screen == "greenhouseBig")) && (m.getButton() == 3) && ((farmer.getDX() == 0) && (farmer.getDY() == 0))) {
            //first check if correct item is being held
            //check orientation of farmer
            //then add crop

            if (addCrop()){
                if ((farmer.getPic().getDescription() == "assets/farmer/walkDown.gif") || (farmer.getPic().getDescription() == "assets/farmer/idleDown.png")){
                    farmer.setPic(new ImageIcon("assets/farmer/farmerPlantDown.gif"));
                }
                else if ((farmer.getPic().getDescription() == "assets/farmer/walkUp.gif") || (farmer.getPic().getDescription() == "assets/farmer/idleUp.png")){
                    farmer.setPic(new ImageIcon("assets/farmer/farmerPlantUp.gif"));
                }
                else if ((farmer.getPic().getDescription() == "assets/farmer/walkLeft.gif") || (farmer.getPic().getDescription() == "assets/farmer/idleLeft.png")){
                    farmer.setPic(new ImageIcon("assets/farmer/farmerPlantLeft.gif"));
                }
                else if ((farmer.getPic().getDescription() == "assets/farmer/walkRight.gif") || (farmer.getPic().getDescription() == "assets/farmer/idleRight.png")){
                    farmer.setPic(new ImageIcon("assets/farmer/farmerPlantRight.gif"));
                }
    
                int delay = 950;
                javax.swing.Timer timer = new javax.swing.Timer(delay, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if ((farmer.getDX() == 0) && (farmer.getDY() == 0))
                            addCrop();
    
                        if ((farmer.getPic().getDescription() == "assets/farmer/farmerPlantDown.gif")){
                            farmer.setPic(new ImageIcon("assets/farmer/idleDown.png"));
                        }
                        else if ((farmer.getPic().getDescription() == "assets/farmer/farmerPlantUp.gif")){
                            farmer.setPic(new ImageIcon("assets/farmer/idleUp.png"));
                        }
                        else if ((farmer.getPic().getDescription() == "assets/farmer/farmerPlantLeft.gif")){
                            farmer.setPic(new ImageIcon("assets/farmer/idleLeft.png"));
                        }
                        else if ((farmer.getPic().getDescription() == "assets/farmer/farmerPlantRight.gif")){
                            farmer.setPic(new ImageIcon("assets/farmer/idleRight.png"));
                        }
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }

            
            
            
        }

        for (Airlocks a : airlocks) {
            if ((a.getCurscreen() == screen) && (airlockCollision(farmer, a)) && (m.getButton() == 3)) {
                ImageIcon temp = farmer.getPic();
                farmer.setPic(new ImageIcon("assets/farmer/transparent.png"));
                a.setPic(new ImageIcon("assets/buildings/airlock.gif"));
        
                int delay = 3000;
                javax.swing.Timer timer = new javax.swing.Timer(delay, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        screen = a.getGTS();
                        farmer.setPic(temp);
                        a.setPic(new ImageIcon("assets/buildings/airlock.png"));
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }

        if (screen == "home" && (m.getButton() == 3) && (Collision(farmer, terminalHitBox)) && (farmer.getDX() == 0) && (farmer.getDY() == 0)) {
            showTerminal = !showTerminal;
        }
        // else {
        //     showTerminal = false;
        // }
        
        if (screen == "home" && (m.getButton() == 1) && (Collision(farmer, terminalHitBox)) && (farmer.getDX() == 0) && (farmer.getDY() == 0) && (showTerminal == true)) {
            if ((m.getX() >= terminalShopButton.getX() && m.getX() <= terminalShopButton.getX() + terminalShopButton.getW()) && (m.getY() >= terminalShopButton.getY() && m.getY() <= terminalShopButton.getY() + terminalShopButton.getH())){
                //add shop screen
                showShop=true;
                showSell=false;
                showRepairs=false;
            }
            else if ((m.getX() >= terminalSellButton.getX() && m.getX() <= terminalSellButton.getX() + terminalSellButton.getW()) && (m.getY() >= terminalSellButton.getY() && m.getY() <= terminalSellButton.getY() + terminalSellButton.getH())){
                //add sell screen
                showShop=false;
                showSell=true;
                showRepairs=false;
            }
            else if ((m.getX() >= terminalRepairsButton.getX() && m.getX() <= terminalRepairsButton.getX() + terminalRepairsButton.getW()) && (m.getY() >= terminalRepairsButton.getY() && m.getY() <= terminalRepairsButton.getY() + terminalRepairsButton.getH())){
                //add repairs screen
                showShop=false;
                showSell=false;
                showRepairs=true;
            }

            if ((m.getX() >= gooseberryShop.getX() && m.getX() <= gooseberryShop.getX() + gooseberryShop.getW()) && (m.getY() >= gooseberryShop.getY() && m.getY() <= gooseberryShop.getY() + gooseberryShop.getH())){
                addItemInInventory(new ImageIcon("assets/plants/gooseberry/gooseberrySeeds.gif"), 1);
            }
            else if ((cash >= 100) && (m.getX() >= kornflowerShop.getX() && m.getX() <= kornflowerShop.getX() + kornflowerShop.getW()) && (m.getY() >= kornflowerShop.getY() && m.getY() <= kornflowerShop.getY() + kornflowerShop.getH())){
                addItemInInventory(new ImageIcon("assets/plants/kornflower/kornflowerSeeds.png"), 1);
                cash -= 100;
            }
            else if ((cash >= 20) && (m.getX() >= celestialWheatShop.getX() && m.getX() <= celestialWheatShop.getX() + celestialWheatShop.getW()) && (m.getY() >= celestialWheatShop.getY() && m.getY() <= celestialWheatShop.getY() + celestialWheatShop.getH())){
                addItemInInventory(new ImageIcon("assets/plants/celestialWheat/celestialWheatSeeds.png"), 1);
                cash -= 20;
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
