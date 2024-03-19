
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

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	private BufferedImage back; 
	private int key, count; 
    private char screen;
    private Sound sound;
   

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

		key =-1; 
        sound = new Sound();
        screen='S';
        count=0;
        

        
		//time=System.currentTimeMillis();
		curtime=0;
        bestTime=0;

	}

    public void screen(Graphics g2d){
        switch(screen){
            case 'S':
                //Start Screen
                break;
            case 'F':
                //Farm screen
                break;
            case 'M':
                break;
        }
    }


    public void drawStartScreen(Graphics g2d){
        //create start screen

    }
    

	
	
	public void run()
	   {
	   	try
	   	{
	   		while(true)
	   		{
	   		   Thread.currentThread().sleep(5);
	            repaint();
	         }
	      }
	   		catch(Exception e)
	      {
	      }
	  	}
	

	
	
	
	public void paint(Graphics g){
		
		Graphics2D twoDgraph = (Graphics2D) g; 
		if( back ==null)
			back=(BufferedImage)( (createImage(getWidth(), getHeight()))); 
		

		Graphics g2d = back.createGraphics();
		g2d.clearRect(0,0,getSize().width, getSize().height);
		
        //start graphics  here
        screen(g2d);
		
        //end
		twoDgraph.drawImage(back, null, 0, 0);

	}

	



	//DO NOT DELETE
	@Override
	public void keyTyped(KeyEvent e) {
	}




//DO NOT DELETE
	@Override
	public void keyPressed(KeyEvent e) {
		
		key= e.getKeyCode();
		System.out.println(key);
	
	}


	//DO NOT DELETE
	@Override
	public void keyReleased(KeyEvent e) {
		
		
		
		
	}

    @Override
    public void mouseDragged(java.awt.event.MouseEvent m) {
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent m) {
        

    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent m) {
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent m) {
    //m.getButton()==1 is Left Click

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
