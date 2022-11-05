
public class Wall {//壁（障害物）のクラス
	private int wx;//x座標
	private int wy;//y座標
	private boolean exist;//存在するかどうか
	private boolean through;//衝突したか
	public static final int HEIGHT = 40;//サイズ
	private static final int[] SPEED = {5,5,7};//速さ
	
	public Wall(int wy) {
		super();
		this.wx = Game.WIN_WIDTH;
		this.wy = wy;
		this.exist = true;
		this.through = false;
	}
	/*更新*/
	public void updateWall(int level) {
		wx -= SPEED[level];
		if(wx < 0) 
			exist = false;
	}

	/*弾が当たった時の処理*/
	public boolean hitWall(int atx) {
		if(atx >= wx) {
			exist = false;
		}
		return exist;
	}

	public int getWx() {
		return wx;
	}
	
	public int getWy() {
		return wy;
	}

	public boolean getExist() {
		return exist;
	}

	public boolean isThrough() {
		return through;
	}

	public void setThrough(boolean through) {
		this.through = through;
	}
	
}
