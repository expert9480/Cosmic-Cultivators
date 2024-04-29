
//import static java.awt.Container.log;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener, Serializable {

    private BufferedImage back;
    private int key, count, lineCount;
    private String screen;
    private Sound sound;
    private Icons play, playSelected, logo, inventoryMenu, house, save, sbg;
    private Boolean playBoolean, sprint, showInvetory, airlockCreation, showPause, saveBoolean;
    private Farmer farmer;
    private ArrayList<Crops> cropList;
    private ArrayList<Inventory> inventory;
    private ArrayList<Airlocks> airlocks;

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
        // screenHeight = screenSize.height-(int)(screenSize.height*.083);
        screenHeight = Main.screenSizeFigureOuter();

        key = -1;
        sound = new Sound();
        screen = "start";
        count = 0;
        // time=System.currentTimeMillis();
        curtime = 0;
        bestTime = 0;

        // iniztialize here
        farmer = new Farmer(new ImageIcon("assets/farmer/idleDown.png"), 100, 100, 0, 0, 16 * Main.scale(), 16 * Main.scale(), false, false);
        sprint = false;

        play = new Icons(new ImageIcon("assets/icons/start.png"), 25, screenHeight - 300, 36*8, 12*8);
        //playSelected = new Icons(new ImageIcon("assets/icons/selectedPlay.png"), 25, screenHeight - 400, 300, 300);
        playBoolean = false;

        logo = new Icons(new ImageIcon("assets/icons/logo.png"), 25, 100, 2264 / 2, 339 / 2);
        house = new Icons(new ImageIcon("assets/buildings/house.gif"), (screenWidth - (128 * (Main.scale()))) / 2,
                (screenHeight - (72 * (Main.scale()))) / 2, 128 * (Main.scale()), 72 * (Main.scale()));

        inventory = new ArrayList<Inventory>();
        showInvetory = false;
        // current img size of invetory 256x192
        // inventory size scale needs tweaking
        inventoryMenu = new Icons(new ImageIcon("assets/icons/Inventory.png"),
                (screenWidth - (256 * (Main.scale() - 2))) / 2, (screenHeight - (192 * (Main.scale() - 2))) / 2,
                256 * (Main.scale() - 2), 192 * (Main.scale() - 2));

        cropList = new ArrayList<Crops>();
        airlocks = new ArrayList<Airlocks>();
        airlockCreation = false;

        showPause = false;
        save = new Icons(new ImageIcon("assets/icons/save.png"), 25, 200, 320, 80);
        saveBoolean = false;

        sbg = new Icons(new ImageIcon("assets/icons/autismbg.png"), 0, 0, screenWidth, screenHeight);

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
                }
                createSave();
                break;
            case "mainFarm":
                createSave();
                // Base
                drawCrops(g2d);
                drawFarmer(g2d);
                updateInventory();
                drawInvetory(g2d);
                drawPause(g2d);
                break;
            case "greenhouse1":
                break;
            case "greenhouse2":
                break;
            case "greenhouseBig":
                break;
            case "home":
                g2d.drawImage(house.getPic().getImage(), house.getX(), house.getY(), house.getW(), house.getH(), this);
                drawAirlock(g2d);
                drawCrops(g2d);
                drawFarmer(g2d);
                updateInventory();
                drawInvetory(g2d);
                drawPause(g2d);
                break;
        }
    }

    public void drawStartScreen(Graphics g2d) {
        // create start screen
        g2d.drawImage(sbg.getPic().getImage(), sbg.getX(), sbg.getY(), sbg.getW(), sbg.getH(), this);
        g2d.drawImage(logo.getPic().getImage(), logo.getX(), logo.getY(), logo.getW(), logo.getH(), this);
        // if (playBoolean == true) {
        //     g2d.drawImage(playSelected.getPic().getImage(), playSelected.getX(), playSelected.getY(), playSelected.getW(), playSelected.getH(), this);
        // } else {
        //     g2d.drawImage(play.getPic().getImage(), play.getX(), play.getY(), play.getW(), play.getH(), this);
        // }
        g2d.drawImage(play.getPic().getImage(), play.getX(), play.getY(), play.getW(), play.getH(), this);
    }

    public void drawFarmer(Graphics g2d) {
        g2d.drawImage(farmer.getPic().getImage(), farmer.getX(), farmer.getY(), farmer.getW(), farmer.getH(), this);
        move();
    }

    public int returnCurrentBackgroundWidth() {
        if (screen == "home")
            return house.getX();
        else
            return 0;
    }

    public int returnCurrentBackgroundHeight() {
        if (screen == "home")
            return house.getY();
        else
            return 0;
    }

    public int returnCurrentBackgroundScaleW() {
        if (screen == "home")
            // return ((screenWidth-(128*(Main.scale())))/2) + (128*(Main.scale()));
            return house.getX() + house.getW();
        else
            return screenWidth;
    }

    public int returnCurrentBackgroundScaleH() {
        if (screen == "home")
            // return ((screenHeight-(72*(Main.scale())))/2) + (72*(Main.scale()));
            return house.getY() + house.getH();
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
        airlocks.add(new Airlocks(new ImageIcon("assets/buildings/airlock.gif"), (house.getX() + (house.getW()/2)) - (32 * (Main.scale() + 2)) / 2, house.getY() + house.getH() - (Main.scale() * 2), 32 * (Main.scale() + 2), 32 * (Main.scale() + 2), "home", "mainFarm"));
    }

    public void move() {
        // change screenwidth and height to image width and height
        // account for the image being in a diff location than top corner
        farmer.move(returnCurrentBackgroundWidth(), returnCurrentBackgroundHeight(), returnCurrentBackgroundScaleW(),
                returnCurrentBackgroundScaleH());
    }

    public void addCrop() {
        cropList.add(new Crops(new ImageIcon("assets/plants/testPlant/testPlant.png"),
                (farmer.getX() + (farmer.getW() / 2)), (farmer.getY() + (farmer.getH() / 2)), 32, 32, screen));
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
                inventory.add(new Inventory(new ImageIcon("assets/icons/empty.png"), 0, 0, 64, 64, 1, i + 1));
            }
        }
    }

    public void drawInvetory(Graphics g2d) {
        //add temp item into slot 1 is "q"
        if (showInvetory == true) {
            g2d.drawImage(inventoryMenu.getPic().getImage(), inventoryMenu.getX(), inventoryMenu.getY(), inventoryMenu.getW(), inventoryMenu.getH(), this);
            for (Inventory i : inventory) {
                g2d.drawImage(i.getPic().getImage(), i.getX(), i.getY(), i.getW(), i.getH(), this);
                g2d.setColor(java.awt.Color.BLACK);
                g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
                if (i.getPic().getDescription() != ("assets/icons/empty.png")){
                    g2d.drawString(Integer.toString(i.getQuantity()), (i.getX()+i.getW()), (i.getY()+i.getH()));
                }
            }
        }
    }

    public void addItemInInventory(int slot){
        //add item to inventory
        //add item to first empty slot
        //if no empty slots, do nothing
        for (Inventory i: inventory){
            if (i.getSlot() == slot){
                i.setPic(new ImageIcon("assets/plants/testPlant/testPlant.png"));
                i.setQuantity(25);
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
                i.setX(x+24);
            }
            else if (( i.getSlot() % 4) == 2){
                i.setX(x+(12+150));
            }
            else if ((i.getSlot() % 4) == 3){
                i.setX(x+(300));
            }
            else if (( i.getSlot() % 4) == 0){
                i.setX(x+(16+400));
            }

            //set y
            float floatSlot = (float) i.getSlot();

            if (( floatSlot / 4) >3){
                i.setY(y+16+300);
            } 
            else if (( floatSlot / 4) >2){
                i.setY(y+16+200);
            }
            else if (( floatSlot / 4) > 1){
                i.setY(y+16+100);
            }
            else if ((  floatSlot / 4) <=1){
                i.setY(y+16);
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
        //i think something is broken here
        //column 1 (minus the first slot) and column 3 arent working for moving items
        
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
        System.out.println(key);

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

        if (key == 87) {
            farmer.setDy(-1);
            farmer.setPic(new ImageIcon("assets/farmer/walkUp.gif"));
        }
        if (key == 83) {
            farmer.setDy(1);
            farmer.setPic(new ImageIcon("assets/farmer/walkDown.gif"));
        }
        if (key == 65) {
            farmer.setDx(-1);
            farmer.setPic(new ImageIcon("assets/farmer/walkLeft.gif"));
        }
        if (key == 68) {
            farmer.setDx(1);
            farmer.setPic(new ImageIcon("assets/farmer/walkRight.gif"));
        }

        if ((sprint) && farmer.getDX() == -1) {
            farmer.setDx(-2);
        } else if ((sprint) && farmer.getDX() == 1) {
            farmer.setDx(2);
        } else if ((sprint) && farmer.getDY() == -1) {
            farmer.setDy(-2);
        } else if ((sprint) && farmer.getDY() == 1) {
            farmer.setDy(2);
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

        if ((key == 81) ){
            //inventory.add(new Inventory(new ImageIcon("assets/icons/empty.png"), 0, 0, 64, 64, 5, 11));
            //inventory.add(new Inventory(new ImageIcon("assets/plants/testPlant/testPlant.png"), 0, 0, 64, 64, 23, 15));
            // for (Inventory i: inventory){
            //     if (i.getSlot() == 2)
            //         i.setPic(new ImageIcon("assets/plants/testPlant/testPlant.png"));
            //         i.setQuantity(25);
            // }
            addItemInInventory(2);
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

        if (showInvetory == true && m.getButton() == 1){
            moveItemsInInventory(m.getX(), m.getY());
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
        if ((screen == "mainFarm") && (m.getButton() == 3)) {
            addCrop();
        }

        for (Airlocks a : airlocks) {
            if ((a.getCurscreen() == screen) && (airlockCollision(farmer, a)) && (m.getButton() == 3)) {
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
