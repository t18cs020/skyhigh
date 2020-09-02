import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BossTest {
	@Test
	public void shotのテスト() {
		Boss b = new Boss(0);
		assertEquals(false, b.getAttack().isExist());
		b.shot();
		assertEquals(true, b.getAttack().isExist());
	}
	
	@Test
	public void updateのテスト() {
		Boss b = new Boss(0);
		b.shot();
		assertEquals(600, b.getBx());
		assertEquals(200, b.getBy());
		b.update();
		assertEquals(600 + Boss.ATTACK_SPEED, b.getAttack().getAtx());
	}
}
