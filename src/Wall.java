
public class Wall {
	private int wx;
	private int wy;
	private boolean exist;
	private boolean through;
	public static final int HEIGHT = 40;
	private static final int SPEED = 15;
	
	public Wall(int wy) {
		super();
		this.wx = Game.WIN_WIDTH;
		this.wy = wy;
		this.exist = true;
		this.through = false;
	}
	
	public void updateWall() {
		wx -= SPEED;
		if(wx < 0) 
			exist = false;
	}

	
	public boolean hitWall(int atx) {
		if(atx >= wx) {
			exist = false;
		}
		return exist;
	}

	public int getWx() {
		return wx;
	}
	
	public int getWy() {
		return wy;
	}

	public boolean getExist() {
		return exist;
	}

	public boolean isThrough() {
		return through;
	}

	public void setThrough(boolean through) {
		this.through = through;
	}
	
}
