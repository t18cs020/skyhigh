
public class Attack {

	private int atx, aty;
	private boolean exist;
	private final static int width = Game.WIN_WIDTH;
	private final static int height = Game.WIN_HEIGHT;
	public final static int SIZE = 40;
	
	public Attack() {
		super();
		this.atx = 0;
		this.aty = 0;
		this.exist = false;
	}
	
	public void isOutOfScreen() {
		if(atx <= 0 || atx > width || aty < 0 || aty > height)
			exist = false;
	}
	
	public void shotAttack(int apx, int apy) {
		exist = true;
		atx = apx;
		aty = apy;
	}
	
	public void updateAttack(int Speed) {
		atx += Speed;
	}
	
	public void reachWall() {
		this.exist = false;
	}

	public int getAtx() {
		return atx;
	}

	public int getAty() {
		return aty;
	}

	public boolean isExist() {
		return exist;
	}
	
}
