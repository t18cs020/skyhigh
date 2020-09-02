import static org.junit.Assert.*;
import java.awt.event.KeyEvent;
    
import org.junit.Test;

public class ModelTest {

    @Test
    public void モデルの管理する時刻データが時間経過とともに増えるかテスト() {
        Model model = new Model();
        model.processKeyTyped(" ");//ゲーム開始
        int time = model.getTime();
        model.processTimeElapsed(100);
        assertEquals(time + 1, model.getTime());

        // その他のイベントでは増えない
        model.processKeyTyped("a");
        assertEquals(time + 1, model.getTime());
        assertNotEquals(time + 2, model.getTime());
    }

    @Test
    public void モデルがタイプされた文字を保持しているかテスト() {
        Model model = new Model();
        model.processKeyTyped(" ");
        Controller controller = model.getController();
        // View オブジェクトが存在すること
        assertNotEquals(model.getView(), null);
        // Controler オブジェクトが存在すること
        assertNotEquals(controller, null);
        
        controller.keyTyped(new KeyEvent(model.getView(), 1, 1, 0, KeyEvent.VK_A, 'a'));
        assertEquals("a", model.getTypedChar());
    }
    
    @Test
    public void 相手の攻撃がプレイヤーに当たったことを判別する() {
        Model model = new Model();
        Airplane ap = model.getAirplane();
        model.processKeyTyped(" ");
        Attack boss_atk = new Attack();
        boss_atk.shotAttack(Airplane.DEFAULT_X, Airplane.DEFAULT_Y);
        model.hitAp(boss_atk);
        assertEquals(2, ap.getLife());
        assertEquals(true, ap.isApUndamegedTime());
        assertEquals(false, boss_atk.isExist());
        assertEquals(true, boss_atk.isThrough());
    }
    
    @Test
    public void makeWallのテスト() {
    	Model model = new Model();
    	model.makeWall();
    	//壁にはブロック2個分の穴を開けてある
    	assertEquals(Game.WIN_HEIGHT/Wall.HEIGHT -2, model.getWall().size());
    }
    
    @Test
    public void makeObstacleのテスト() {
    	Model model = new Model();
    	model.makeObstacle();
    	//壁にはブロック2個分の穴を開けてある
    	assertEquals(true, model.getObstacle().isExist());
    }
    
    @Test
    public void hitWallのテスト() {
    	Model model = new Model();
    	Wall wall = new Wall(100);
    	Attack atk = new Attack();
    	atk.shotAttack(799, 100);
    	assertEquals(false, model.hitWall(wall, atk));
    	atk.updateAttack(1);
    	assertEquals(true, model.hitWall(wall, atk));
    }
    
    @Test
    public void newGameのテスト() {
    	Model model = new Model();
    	model.newGame();
    	assertEquals(false, model.isHit());
    	assertEquals(false, model.isCleared());
    	assertEquals(false, model.isTypingObstacle());
    	assertEquals(false, model.isTypingBossAtk());
    	assertEquals(false, model.isTypingBoss());
    }

}
