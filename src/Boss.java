import java.util.LinkedList;

public class Boss {
	private int life;
	private int bx;
	private int by;
	private LinkedList <Attack> atk;
	private boolean bossExist;
	private boolean undamegedTime;
	private boolean bossThrough;
	protected static final int ATTACK_SPEED = -10;
	protected static final int[] BOSS_SIZE = {30, 80, 100};
	protected static final int[] BOSS_LIFE = {3,5,10};
	protected static final int ATTACKS = 3;
	protected static final int MOVETIME = 150;
	private int moveBoss;
	
	public Boss(int i){
		super();
		this.bx = 600;
		this.by = 200;
		life = BOSS_LIFE[i];
		bossExist = false;
		undamegedTime = false;
		bossThrough = false;
		atk = new LinkedList<>();
		for(int j = 0; j < ATTACKS; j++)
			atk.add(new Attack());
		moveBoss = 0;
	}

	public void update(int stateNumber) {
		moveBoss++;
		for(int i = 0; i < ATTACKS; i++) {
			if(moveBoss % MOVETIME == 0) {
				RandNumGenerator r = RandNumGenerator.getInstance();
				by = r.nextInt(Game.WIN_HEIGHT - BOSS_SIZE[stateNumber]);
			}
			Attack _atk = atk.get(i);
			if(_atk.isExist()) {
				_atk.updateAttack(ATTACK_SPEED);
				_atk.isOutOfScreen();
			}
		}
	}

	
	public void shot(Attack _atk) {
		if(!_atk.isExist()) {
			RandNumGenerator r = RandNumGenerator.getInstance();
			_atk.shotAttack(bx, r.nextInt(Game.WIN_HEIGHT));
		}
	}
	
	public Boss reset(int i) {
		this.bx = 600;
		this.by = 200;
		life = BOSS_LIFE[i];
		bossExist = false;
		undamegedTime = false;
		bossThrough = false;
		atk = new LinkedList<> ();
		for(int j = 0; j < ATTACKS; j++)
			atk.add(new Attack());
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
	
	public LinkedList <Attack> getAttack () {
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
