
public class Airplane {
	private int life;
	private int apX;
	private int apY;
	private boolean exist;
	private Attack atk;
	private boolean apUndamagedTime;
	private static final int SPEED = 10;
	private static final int AP_LIFE = 3;
	private static final int ATTACK_SPEED = 25;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	public static final int DEFAULT_X = 100;
	public static final int DEFAULT_Y = 100;
	
	public Airplane() {
		super();
		this.life = AP_LIFE;
		this.apX = DEFAULT_X;
		this.apY = DEFAULT_Y;
		exist = true;
		apUndamagedTime = false;
		atk = new Attack();
	}

	
	public void update() {
		apY += 1;
		if(atk.isExist())
			atk.updateAttack(ATTACK_SPEED);
		isOutOfScreen();
	}
	
	public void isOutOfScreen() {
		if(apX < 0)apX = 0;
		if(apX > Game.WIN_WIDTH)apX = Game.WIN_WIDTH;
		if(apY < 0)apY = 0;
		if(apY > Game.WIN_HEIGHT)apY = Game.WIN_HEIGHT;
		atk.isOutOfScreen();
	}
	
	public void move(String typedChar) {
		switch(typedChar) {
		case "w":
			apY -= SPEED;
			break;
		case "s":
			apY += SPEED;
			break;
		case "a":
			apX -= SPEED;
			break;
		case "d":
			apX += SPEED;
			break;	
		default:
			break;
		}
		isOutOfScreen();
	}
	
	public void shot() {
		if(!atk.isExist()) {
			atk.shotAttack(apX, apY);
		}
	}
	
	public int getApx() {
		return apX;
	}

	public int getApy() {
		return apY;
	}
	
	public Attack getAttack () {
		return atk;
	}
	
	public int getLife() {
		return life;
	}

	public void damagedAirplane() {
		life--;
		apUndamagedTime = true;
		if(life == 0) {
			exist = false;
		}
	}
	
	public void reset() {
		life = AP_LIFE;
		apUndamagedTime = true;	
		exist = true;
	}
	
	public boolean isApUndamegedTime() {
		return apUndamagedTime;
	}

	public void setApUndamegedTime(boolean undameged_time) {
		this.apUndamagedTime = undameged_time;
	}

	public boolean isExist() {
		return exist;
	}

}
