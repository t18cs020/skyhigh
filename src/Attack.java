
public class Attack {

	private int atx, aty;
	private boolean exist;
	private boolean through;
	private final static int width = Game.WIN_WIDTH;
	private final static int height = Game.WIN_HEIGHT;
	public final static int SIZE = 40;
	
	public Attack() {
		super();
		this.atx = 0;
		this.aty = 0;
		this.exist = false;
		this.through = false;
	}
	
	public void isOutOfScreen() {
		if(atx <= 0 || atx > width || aty < 0 || aty > height)
			exist = false;
	}
	
	public void shotAttack(int apx, int apy) {
		exist = true;
		through = false;
		atx = apx;
		aty = apy;
	}
	
	public void updateAttack(int Speed) {
		atx += Speed;
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
