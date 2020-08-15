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
    private Image imageAp;
    private Image imageAtk;
    private Image imageWl;
    private AudioClip sound;
    private Dimension size;

    public View(Model model) {
        this.model = model;
        
        // 画像を読み込む．画像ファイルは src においておくと bin に自動コピーされる
        imageAp = Toolkit.getDefaultToolkit().getImage(getClass().getResource("U1L.png"));
        imageAtk = Toolkit.getDefaultToolkit().getImage(getClass().getResource("mikiko.jpg"));
        imageWl = Toolkit.getDefaultToolkit().getImage(getClass().getResource("nom.png"));
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
        clear(g);
        Airplane ap = model.getAirplane();
        Attack atk = ap.getAttack();
        LinkedList <Wall> wall = model.getWall();
        /*
        // 描画する
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        g.setColor(Color.WHITE);
                g.drawString("wall: " + wall.size() , 100, 200);
        g.drawString("Time: " + model.getTime(), 100, 200);
        g.drawString("Key Typed: " + model.getTypedChar(), 100, 250);
        g.drawString("Mouse Pressed: " + model.getMX() + "," + model.getMY(), 100, 300);
*/
        // 画像の表示例
        
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        g.setColor(Color.WHITE);
                g.drawString("wall: " + wall.size() , 100, 200);
                
        g.drawImage(imageAp, ap.getApx(), ap.getApy(), this);
        if(atk.isExist()) {
        	g.drawImage(imageAtk, atk.getAtx(), atk.getAty(), this);
        }
        
		for(int i = 0; i < wall.size() ; i++) {
			Wall w = wall.get(i);
			if(w.getExist()) {
				g.drawImage(imageWl, w.getWx(), w.getWy(), this);
			}
		}
    }

    /**
     * 画面を黒色でクリア
     * @param g  描画用のグラフィックスオブジェクト
     */
    public void clear(Graphics g) {
        size = getSize();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, size.width, size.height);
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
