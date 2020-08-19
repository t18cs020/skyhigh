
public class Boss {
	private int life;
	private int bx, by;
	private Model model;
	private Attack atk;
	private boolean bossExist;
	private boolean undameged_time;
	private boolean boss_through;
	private final static int SPEED = 10;
	private final static int ATTACK_SPEED = -30;
	final static int BOSS1_SIZE = 30;
	final static int[] BOSS_LIFE = {7, 10,15};
	private int move_boss;
	
	public Boss(Model model, int i){
		super();
		this.model = model;
		this.bx = 600;
		this.by = 200;
		life = BOSS_LIFE[i];
		bossExist = false;
		undameged_time = false;
		boss_through = false;
		atk = new Attack();
		move_boss = 0;
	}
	
	public void update() {
		move_boss++;
		if(move_boss % 100 == 0) {
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
		undameged_time = true;
		boss_through = true;
	}
	
	public boolean isUndameged_time() {
		return undameged_time;
	}

	public void setUndameged_time(boolean undameged_time) {
		this.undameged_time = undameged_time;
	}
	
	public boolean isBossThrough() {
		return boss_through;
	}
}
