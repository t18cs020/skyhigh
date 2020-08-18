
public class Airplane {
	private int life;
	private int apx, apy;
	private Model model;
	private Attack atk;
	private boolean undameged_time;
	private final static int SPEED = 10;
	private final static int ATTACK_SPEED = 25;
	
	public Airplane(Model model) {
		super();
		this.model = model;
		this.life = 3;
		this.apx = 100;
		this.apy = 100;
		undameged_time = false;
		atk = new Attack();
	}

	
	public void update() {
		apy += 1;
		if(atk.isExist())
			atk.updateAttack(ATTACK_SPEED);
		isOutOfScreen();
	}
	
	public void isOutOfScreen() {
		if(apx < 0)apx = 0;
		if(apx > Game.WIN_WIDTH)apx = Game.WIN_WIDTH;
		if(apy < 0)apy = 0;
		if(apy > Game.WIN_HEIGHT)apy = Game.WIN_HEIGHT;
		atk.isOutOfScreen();
	}
	
	public void move(String typedChar) {
		switch(typedChar) {
		case "w":
			apy -= SPEED;
			break;
		case "s":
			apy += SPEED;
			break;
		case "a":
			apx -= SPEED;
			break;
		case "d":
			apx += SPEED;
			break;
		}
		isOutOfScreen();
	}
	
	public void shot() {
		if(!atk.isExist()) {
			atk.shotAttack(apx, apy);
		}
	}
	
	public int getApx() {
		return apx;
	}

	public int getApy() {
		return apy;
	}
	
	public Attack getAttack () {
		return atk;
	}
	
	public int getLife() {
		return life;
	}

	public void damagedAirplane() {
		life--;
		undameged_time = true;
	}
	
	public boolean isUndameged_time() {
		return undameged_time;
	}

	public void setUndameged_time(boolean undameged_time) {
		this.undameged_time = undameged_time;
	}

	
}
