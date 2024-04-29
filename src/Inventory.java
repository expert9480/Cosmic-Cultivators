import javax.swing.ImageIcon;

public class Inventory {

	private ImageIcon pic;
	private int x;
	private int y;
	private int width;
	private int height;
	private int dx;
	private int dy;
    private int quantity;
	private int slot;

        //temp crop, in future may need sub classes
	
	public Inventory() {
		pic= new ImageIcon();
		x=0;
		y=0;
		width=0;
		height=0;
		dx=0;
		dy=0;
        quantity = 0;
		slot = 0;
	}
	//still pictures
	public Inventory(ImageIcon s, int x1, int y1, int w1, int h1, int q, int s1) {
		pic=s;
		x=x1;
		y=y1;
		width=w1;
		height=h1;
        quantity = q;
		slot = s1;
	}

    //May be needed if we want to visually show that the item is being moved                    set coords to where the mouse is
	// public Inventory(String s, int x1, int y1, int w1, int h1, int q, int dx1, int dy1) {
	// 	pic=s;
	// 	x=x1;
	// 	y=y1;
	// 	width=w1;
	// 	height=h1;
    // 	quantity = q;
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
	public ImageIcon setPic(ImageIcon s) {
		pic=s;
		return pic;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int q) {
		quantity = q;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int s) {
		slot = s;
	}


	// public void setDx(int c) {
	// 	dx=c;
	// }
	// public void setDy(int c){
	// 	dy=c;
	// }
	// public void setmoveup(){
	// 	moveup=!(moveup);
	// }

	// public void moveImage(){
	// 	if (moveup){
	// 		y+=dy;
	// 	}
	// }

}


