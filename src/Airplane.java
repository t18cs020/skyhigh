
public class Airplane {
	private int life;
	private int apx, apy;
	private Model model;
	private Attack atk;
	private final static int SPEED = 10;
private int a;
	
	public Airplane(Model model) {
		super();
		this.model = model;
		this.life = 3;
		this.apx = 100;
		this.apy = 100;
		atk = new Attack();
	}

	public void isOutOfScreen() {
		if(apx < 0)apx = 0;
		if(apx > Game.WIN_WIDTH)apx = Game.WIN_WIDTH;
		if(apy < 0)apy = 0;
		if(apy > Game.WIN_HEIGHT)apy = Game.WIN_HEIGHT;
		atk.isOutOfScreen();
	}
	
	public void update() {
		apy += 1;
		if(atk.isExist())
			atk.updateAttack();
		isOutOfScreen();
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
	
}
