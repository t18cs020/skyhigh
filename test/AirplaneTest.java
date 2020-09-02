import static org.junit.Assert.*;

import org.junit.Test;

public class AirplaneTest {

	@Test
	public void isOutOfScreenは画面外に出たら画面内に戻す() {
		Model model = new Model();
		Airplane ap = new Airplane(model);
		for(int i = 0; i < 10; i++) {//左
			ap.move("a");
		}
		assertEquals(0, ap.getApx());
		ap.move("a");
		assertEquals(0, ap.getApx());
		
		for(int i = 0; i < 10; i++) {//上
			ap.move("w");
		}
		assertEquals(0, ap.getApy());
		ap.move("w");
		assertEquals(0, ap.getApy());
		
		for(int i = 0; i < 80; i++) {//右
			ap.move("d");
		}
		assertEquals(800, ap.getApx());
		ap.move("d");
		assertEquals(800, ap.getApx());
		
		for(int i = 0; i < 60; i++) {//下
			ap.move("s");
		}
		assertEquals(600, ap.getApy());
		ap.move("s");
		assertEquals(Airplane.DEFAULT_Y, ap.getApy());
		
		for(int i = 0; i < 50; i++) {//下
			ap.move("s");
		}
		assertEquals(600, ap.getApy());
		ap.update();
		assertEquals(Airplane.DEFAULT_Y, ap.getApy());
	}
	
	@Test
	public void shotのテスト() {
		Model model = new Model();
		Airplane ap = new Airplane(model);
		assertEquals(false, ap.getAttack().isExist());
		assertEquals(0, ap.getDeltaAttackCount());
		ap.shot();
		assertEquals(true, ap.getAttack().isExist());
	}
	
	@Test
	public void ライフが0になったときのテスト() {
		Model model = new Model();
		Airplane ap = new Airplane(model);
		//ダメージを受けていない
		assertEquals(false, ap.isApUndamegedTime());
		assertEquals(3, ap.getLife());
		//ダメージを受けた時
		ap.damagedAirplane();
		assertEquals(true, ap.isApUndamegedTime());
		assertEquals(2, ap.getLife());
		assertEquals(true, ap.isExist());
		//無敵時間終了
		ap.setApUndamegedTime(false);
		assertEquals(false, ap.isApUndamegedTime());
		//ライフが0になる
		ap.damagedAirplane();
		ap.damagedAirplane();
		
		assertEquals(0, ap.getLife());
		assertEquals(false, ap.isExist());
		//resetを行う
		ap.reset();
		assertEquals(true, ap.isApUndamegedTime());
		assertEquals(3, ap.getLife());
		assertEquals(true, ap.isExist());
	}

}
