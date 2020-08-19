import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View extends JPanel {

    private Model model;
    // Sample instance variables:
    public Image imageAp, imageDAp, imageAtk, imageWl, imageBoss, imageBack;
    public Image imageDBoss, imageBAtk;
    private AudioClip sound;
    private Dimension size;

    public View(Model model) {
        this.model = model;
        
        // 画像を読み込む．画像ファイルは src においておくと bin に自動コピーされる
        imageAp = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ap.png"));
        imageDAp = Toolkit.getDefaultToolkit().getImage(getClass().getResource("dap.png"));
        imageAtk = Toolkit.getDefaultToolkit().getImage(getClass().getResource("atk.png"));
        imageBAtk = Toolkit.getDefaultToolkit().getImage(getClass().getResource("boss_atk.png"));
        imageWl = Toolkit.getDefaultToolkit().getImage(getClass().getResource("wall.jpg"));
        imageBoss = Toolkit.getDefaultToolkit().getImage(getClass().getResource("boss.png"));
        imageDBoss = Toolkit.getDefaultToolkit().getImage(getClass().getResource("damaged_boss.png"));
        imageBack = Toolkit.getDefaultToolkit().getImage(getClass().getResource("sky.jpg"));
        // サウンドを読み込む
        // サウンドを読み込む       
        sound = Applet.newAudioClip(getClass().getResource("bomb.wav"));
    }
    
    /**
     * 画面を描画する
     * @param g  描画用のグラフィックスオブジェクト
     */
    @Override
    public void paintComponent(Graphics g) {
        // 画面をいったんクリア
        State state = model.getState();
        state.paintComponent(g);
        
    }

    /**
     * 画面を黒色でクリア
     * @param g  描画用のグラフィックスオブジェクト
     */
    public void clear(Graphics g) {
        size = getSize();
	    g.drawImage(imageBack, 0, 0, this);
    }

    public void playBombSound() {
        Airplane ap = model.getAirplane();
        Attack atk = ap.getAttack();
        if(!atk.isExist()) {
	        sound.stop(); // まず音を停めてから
	        sound.play(); // 再生する
        }
    }

}
