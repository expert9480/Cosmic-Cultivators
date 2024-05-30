import javax.swing.ImageIcon;

public class Icons {

	private ImageIcon pic;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Icons() {
		pic=new ImageIcon();
		x=0;
		y=0;
		width=0;
		height=0;
	}
	//still pictures
	public Icons(ImageIcon s, int x1, int y1, int w1, int h1) {
		pic=s;
		x=x1;
		y=y1;
		width=w1;
		height=h1;
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


}


