
public class Wall {
	private int wx,wy;
	private boolean exist;
	private final static int WIDTH = 40;
	public final static int HEIGHT = 40;
	private final static int SPEED = 80;
	private Model model;
	
	public Wall(int wy, Model model) {
		super();
		this.wx = Game.WIN_WIDTH;
		this.wy = wy;
		this.exist = true;
		this.model = model;
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
	
}
