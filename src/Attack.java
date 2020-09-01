
public class Attack {

	private int atx;
	private int aty;
	private boolean exist;
	private boolean through;
	private static final int WIDTH = Game.WIN_WIDTH;
	private static final int HEIGHT = Game.WIN_HEIGHT;
	public static final int SIZE = 40;
	
	public Attack() {
		super();
		this.atx = 0;
		this.aty = 0;
		this.exist = false;
		this.through = false;
	}
	
	public void isOutOfScreen() {
		if(atx <= 0 || atx > WIDTH || aty < 0 || aty > HEIGHT)
			exist = false;
	}
	
	public void shotAttack(int apx, int apy) {
		exist = true;
		through = false;
		atx = apx;
		aty = apy;
	}
	
	public void updateAttack(int speed) {
		atx += speed;
	}
	
	public void reach() {
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

	public boolean isThrough() {
		return through;
	}
	
	public void setThrough(boolean through) {
		this.through = through;
	}
	
}
