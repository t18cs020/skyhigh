
public class Boss {
	private int life;
	private int bx;
	private int by;
	private Attack atk;
	private boolean bossExist;
	private boolean undamegedTime;
	private boolean bossThrough;
	private static final int ATTACK_SPEED = -30;
	public final static int[] BOSS_SIZE = {30, 60, 60};
	public final static int[] BOSS_LIFE = {1, 1,1};
	private int moveBoss;
	
	public Boss(int i){
		super();
		this.bx = 600;
		this.by = 200;
		life = BOSS_LIFE[i];
		bossExist = false;
		undamegedTime = false;
		bossThrough = false;
		atk = new Attack();
		moveBoss = 0;
	}
	
	public void update() {
		moveBoss++;
		if(moveBoss % 100 == 0) {
			RandNumGenerator r = RandNumGenerator.getInstance();
			by = r.nextInt(Game.WIN_HEIGHT);
		}
		
		if(atk.isExist()) {
			atk.updateAttack(ATTACK_SPEED);
			atk.isOutOfScreen();
		}

	}
	public void shot() {
		if(!atk.isExist()) {
			atk.shotAttack(bx, by);
		}
	}
	
	public Boss reset(int i) {
		this.bx = 600;
		this.by = 200;
		life = BOSS_LIFE[i];
		bossExist = false;
		undamegedTime = false;
		bossThrough = false;
		atk = new Attack();
		moveBoss = 0;
		
		return this;
	}

	public boolean isBossExist() {
		return bossExist;
	}

	public void setBossExist(boolean bossExist) {
		this.bossExist = bossExist;
	}

	public int getBx() {
		return bx;
	}

	public int getBy() {
		return by;
	}
	
	public Attack getAttack () {
		return atk;
	}
	
	public int getLife() {
		return life;
	}

	public void damagedBoss() {
		life--;
		undamegedTime = true;
		bossThrough = true;
	}
	
	public boolean isUndamegedTime() {
		return undamegedTime;
	}

	public void setUndamegedTime(boolean undamegedTime) {
		this.undamegedTime = undamegedTime;
	}
	
	public boolean isBossThrough() {
		return bossThrough;
	}
}
