
public class Airplane {
	private int life;//体力
	private int apX;//x座標
	private int apY;//y座標
	private int deltaAttackCount;//ダメージを受けてからの時間
	private boolean exist;//生存しているかどうか
	private Attack atk;//プレイヤーの弾
	private boolean apUndamagedTime;//無敵時間であるかどうか
	private Model model;
	//プレイ開始時の設定
	private static final int SPEED = 10;
	private static final int AP_LIFE = 3;
	private static final int ATTACK_SPEED = 25;
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	public static final int DEFAULT_X = 100;
	public static final int DEFAULT_Y = 100;
	
	public Airplane(Model model) {
		super();
		this.model = model;
		this.life = AP_LIFE;
		this.apX = DEFAULT_X;
		this.apY = DEFAULT_Y;
		exist = true;
		apUndamagedTime = false;
        deltaAttackCount = 0;//ボス戦に入った時の攻撃回数
		atk = new Attack();
	}

	/*更新する*/
	public void update() {
		apY += 1;
		if(atk.isExist())
			atk.updateAttack(ATTACK_SPEED);
		isOutOfScreen();
	}
	/*範囲外へ行かないようにする*/
	public void isOutOfScreen() {
		if(apX < 0)apX = 0;
		if(apX > Game.WIN_WIDTH)apX = Game.WIN_WIDTH;
		if(apY < 0)apY = 0;
		if(apY > Game.WIN_HEIGHT) {
			apY = DEFAULT_Y;
			life = 0;
		}
		atk.isOutOfScreen();
	}
	/*入力に応じて操作を反映*/
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
	/*弾を発射する*/
	public void shot() {
		if(!atk.isExist()) {
			model.getView().startBomb(model.getBombVolume());
			atk.shotAttack(apX, apY);
			if(model.getBoss().isBossExist())
				deltaAttackCount++;
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
	/*ダメージを受けた時の処理*/
	public void damagedAirplane() {
		life--;
		apUndamagedTime = true;
		if(life == 0) {
			exist = false;
		}
	}
	/*状態をリセットする*/
	public void reset() {
		life = AP_LIFE;
		apUndamagedTime = true;	
		exist = true;
	}
	/*無敵時間かどうかを返す*/
	public boolean isApUndamegedTime() {
		return apUndamagedTime;
	}

	public void setApUndamegedTime(boolean undamegedTime) {
		this.apUndamagedTime = undamegedTime;
	}
	/*生きているかどうかを返す*/
	public boolean isExist() {
		return exist;
	}
	
    public int getDeltaAttackCount() {
		return deltaAttackCount;
	}

	public void setDeltaAttackCount(int deltaAttackCount) {
		this.deltaAttackCount = deltaAttackCount;
	}
	/*ボス撃破時に回復*/
	public void heal() {
		life = life + 2;
	}

}
