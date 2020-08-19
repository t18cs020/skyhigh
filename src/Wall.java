
public class Wall {
	private int wx,wy;
	private boolean exist;
	private boolean through;
	private final static int WIDTH = 40;
	public final static int HEIGHT = 40;
	private final static int SPEED = 12;
	private Model model;
	
	public Wall(int wy, Model model) {
		super();
		this.wx = Game.WIN_WIDTH;
		this.wy = wy;
		this.exist = true;
		this.model = model;
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
