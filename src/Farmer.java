import javax.swing.ImageIcon;

public class Farmer {

	private ImageIcon pic;
	private int x;
	private int y;
	private int dx;
	private int dy;
	private int width;
	private int height;
	private boolean movert;
	private boolean movedn;
	
	// public Farmer() {
	// 	pic="";
	// 	x=0;
	// 	y=0;
	// 	dx=0;
	// 	dy=0;
	// 	width=0;
	// 	height=0;
	// 	movert=false;
	// 	movedn=false;
	// }
	
	//moving pictures
	public Farmer(ImageIcon p, int x1, int y1, int dx1, int dy1, int w, int h, boolean r, boolean d) {
        pic=p;
		x=x1;
		y=y1;
		dx=dx1;
		dy=dy1;
		height=h;
		width=w;
		movert=r;
		movedn=d;
	}
	public void setDx(int c) {
		dx=c;
	}
	public void setDy(int c){
		dy=c;
	}
	
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
	public int getDX() {
		return dx;
	}
	public int getDY() {
		return dy;
	}
	
	public int getW() {
		return width;
	}
	public int getH() {
		return height;
	}
	
	
	public void move(int screenW, int screenH, int maxW, int maxH) {
		//fix move method
		x+=dx;
		if (x+width>maxW) {
			x=maxW-width;
		}
		else if (x<screenW){
			x=screenW;
		}
		
		y+=dy;
		//
		if (y+height>maxH)
			y=maxH-height;
		else if (y<screenH-(height/2))
			y=screenH-(height/2);
	}
	
	public boolean Collision(Farmer b) {
		return getX()+getW()>=b.getX()&&getX()<=b.getX()+b.getW()&&getY()+getH()>=b.getY()&&getY()<=b.getY()+b.getH();
	}
}


