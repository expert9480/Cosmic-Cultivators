import javax.swing.ImageIcon;

public class Planters {
    private ImageIcon pic;
	private int x;
	private int y;
	private int width;
	private int height;
    private String screen;
    private Boolean hasCrop;
    private Boolean watered;

	public Planters() {
		pic=new ImageIcon();
		x=0;
		y=0;
		width=0;
		height=0;
        screen = "";
        hasCrop = false;
        watered = false;
	}

    public Planters(ImageIcon s, int x1, int y1, int w1, int h1, String sc, Boolean hc, Boolean w) {
		pic=s;
		x=x1;
		y=y1;
		width=w1;
		height=h1;
        screen = sc;
        hasCrop = hc;
        watered = w;
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

    public String getScreen(){
        return screen; 
    }

    public Boolean getHasCrop(){
        return hasCrop;
    }
    public void setHasCrop(Boolean s){
        hasCrop = s;
    }

    public Boolean getWatered(){
        return watered;
    }
    public void setWatered(boolean s){
        watered = s;
    }

	public boolean Collision(Farmer b) {
		return getX()+getW()>=b.getX()&&getX()<=b.getX()+b.getW()&&getY()+getH()>=b.getY()&&getY()<=b.getY()+b.getH();
	}
	public boolean Collision(Crops b) {
		return getX()+getW()>=b.getX()&&getX()<=b.getX()+b.getW()&&getY()+getH()>=b.getY()&&getY()<=b.getY()+b.getH();
	}
}
