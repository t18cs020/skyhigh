
public class Airplane {
	private int life;
	private int apX, apY;
	private boolean exist;
	private Attack atk;
	private boolean ap_undamaged_time;
	private static final int SPEED = 10;
	private static final int LIFE = 3;
	private static final int ATTACK_SPEED = 25;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	public static final int DEFAULT_X = 100;
	public static final int DEFAULT_Y = 100;
	
	public Airplane() {
		super();
		this.life = LIFE;
		this.apX = DEFAULT_X;
		this.apY = DEFAULT_Y;
		exist = true;
		ap_undamaged_time = false;
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
		ap_undamaged_time = true;
		if(life == 0) {
			exist = false;
		}
	}
	
	public void reset() {
		life = LIFE;
		ap_undamaged_time = true;	
		exist = true;
	}
	
	public boolean isUndameged_time() {
		return ap_undamaged_time;
	}

	public void setUndameged_time(boolean undameged_time) {
		this.ap_undamaged_time = undameged_time;
	}

	public boolean isExist() {
		return exist;
	}

}
