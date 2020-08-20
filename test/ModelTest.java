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

}
