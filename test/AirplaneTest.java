import static org.junit.Assert.*;

import org.junit.Test;

public class AirplaneTest {

	@Test
	public void isOutOfScreenは画面外に出たら画面内に戻す() {
		Airplane ap = new Airplane();
		for(int i = 0; i < 50; i++) {//左
			ap.move("a");
		}
		assertEquals(0, ap.getApx());
		ap.move("a");
		assertEquals(0, ap.getApx());
		
		for(int i = 0; i < 50; i++) {//上
			ap.move("w");
		}
		assertEquals(0, ap.getApy());
		ap.move("w");
		assertEquals(0, ap.getApy());
		
		for(int i = 0; i < 275; i++) {//右
			ap.move("d");
		}
		assertEquals(550, ap.getApx());
		ap.move("d");
		assertEquals(550, ap.getApx());
		
		for(int i = 0; i < 215; i++) {//下
			ap.move("s");
		}
		assertEquals(430, ap.getApy());
		ap.move("s");
		assertEquals(430, ap.getApy());
		
		
	}

}
