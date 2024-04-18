import javax.swing.ImageIcon;

public class Airlocks {
    private ImageIcon pic;
	private int x;
	private int y;
	private int width;
	private int height;
    private String curScreen;
    private String gotoScreen;

	public Airlocks() {
		pic=new ImageIcon();
		x=0;
		y=0;
		width=0;
		height=0;
        curScreen = "";
        gotoScreen = "";
	}

    public Airlocks(ImageIcon s, int x1, int y1, int w1, int h1, String cS, String gtS) {
		pic=s;
		x=x1;
		y=y1;
		width=w1;
		height=h1;
        curScreen = cS;
        gotoScreen = gtS;
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

	
	public int getW() {
		return width;
	}
	public int getH() {
		return height;
	}

    public String getCurscreen() {
		return curScreen;
	}
	public String getGTS() {
		return gotoScreen;
	}


}
