import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

public class Controller implements ActionListener, KeyListener, MouseListener {

    private static final int DELAY = 25; // msec
    private Model model;
    private Timer timer;

    public Controller(Model model) {
        // モデルを保持（イベントの通知先）
        this.model = model;
        timer = new Timer(DELAY, this);
    }

    public void start() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.processTimeElapsed(1);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        model.processKeyTyped(Character.toString(e.getKeyChar()));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
        case KeyEvent.VK_ENTER:
            model.processKeyTyped("ENTER");
            break;
        case KeyEvent.VK_UP:
            model.processKeyTyped("UP");
            break;
        case KeyEvent.VK_DOWN:
            model.processKeyTyped("DOWN");
            break;
        case KeyEvent.VK_LEFT:
            model.processKeyTyped("LEFT");
            break;
        case KeyEvent.VK_RIGHT:
            model.processKeyTyped("RIGHT");
            break;
        case KeyEvent.VK_ESCAPE:
            model.processKeyTyped("ESC");
            break;
        default :
        	break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // do nothing
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // do nothing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        model.processMousePressed(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // do nothing
    }
}
