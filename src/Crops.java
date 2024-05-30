import javax.swing.ImageIcon;

public class Crops {

	private ImageIcon pic;
	private int x;
	private int y;
	private int width;
	private int height;
	private String screen;
	private Boolean waterStatus;
	private double startTime;

        //temp crop, in future may need sub classes
	
	public Crops() {
		pic= new ImageIcon();
		x=0;
		y=0;
		width=0;
		height=0;
		screen = "";
		waterStatus = false;
		startTime = 0;
	}
	//still pictures
	public Crops(ImageIcon s, int x1, int y1, int w1, int h1, String sc, Boolean water, double st) {
		pic=s;
		x=x1;
		y=y1;
		width=w1;
		height=h1;
		screen = sc;
		waterStatus = water;
		startTime = st;
	}

	// public Icons(String s, int x1, int y1, int w1, int h1, int dx1, int dy1) {
	// 	pic=s;
	// 	x=x1;
	// 	y=y1;
	// 	width=w1;
	// 	height=h1;
	// 	dx=dx1;
	// 	dy=dy1;
	// }
	

	
	public void setX(int c) {
		x=c;
	}
	public void setY(int c){
		y=c;
	}
	public void setH(int c) {
		height=c;
	}
	public void setW(int c){
		width=c;
	}
	
	public ImageIcon getPic() {
		return pic;
	}
	public void setPic(ImageIcon p) {
		pic = p;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	
	public int getW() {
		return width;
	}
	public int getH() {
		return height;
	}

	public String getScreen() {
		return screen;
	}

	public Boolean getWaterStatus() {
		return waterStatus;
	}
	public void setWaterStatus(Boolean water) {
		waterStatus = water;
	}	

	public double getStartTime() {
		return startTime;
	}
	public void setStartTime(double st) {
		startTime = st;
	}

	public boolean Collision(Farmer b) {
		return getX()+getW()>=b.getX()&&getX()<=b.getX()+b.getW()&&getY()+getH()>=b.getY()&&getY()<=b.getY()+b.getH();
	}

}


