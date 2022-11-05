
public class Attack {//プレイヤーの弾のクラス

	private int atx;//x座標
	private int aty;//y座標
	private boolean exist;//弾があるかどうか
	private boolean through;//衝突したかどうか
	private static final int WIDTH = Game.WIN_WIDTH;
	private static final int HEIGHT = Game.WIN_HEIGHT;
	public static final int SIZE = 40;
	
	public Attack() {
		super();
		this.atx = 1000;
		this.aty = 1000;
		this.exist = false;
		this.through = false;
	}
	/*画面外に出た時の処理*/
	public void isOutOfScreen() {
		if(atx <= 0 || atx > WIDTH || aty < 0 || aty > HEIGHT)
			exist = false;
	}
	/*撃った時の処理*/
	public void shotAttack(int apx, int apy) {
		exist = true;
		through = false;
		atx = apx;
		aty = apy;
	}
	/*更新*/
	public void updateAttack(int speed) {
		atx += speed;
	}
	/*弾が当たった時の処理*/
	public void reach() {
		exist = false;
		through = true;
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
