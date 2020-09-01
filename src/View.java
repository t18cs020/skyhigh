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
    private Image imageAp, imageDAp, imageAtk, imageWl, imageBoss, imageBack;
    private Image imageDBoss, imageBAtk, imageBack2, imageBack3, imageBoss2, imageBoss3, imageBossComing;
    private Image bossComing, imageDBoss2 ,imageDBoss3;
    private AudioClip sound;
    private Dimension size;
    public View(Model model) {
        this.model = model;
        
        // 画像を読み込む．画像ファイルは src においておくと bin に自動コピーされる
        imageAp = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ap.png"));
        imageDAp = Toolkit.getDefaultToolkit().getImage(getClass().getResource("dap.png"));
        imageAtk = Toolkit.getDefaultToolkit().getImage(getClass().getResource("atk.png"));
        imageBAtk = Toolkit.getDefaultToolkit().getImage(getClass().getResource("rock.png"));
        imageWl = Toolkit.getDefaultToolkit().getImage(getClass().getResource("wall.jpg"));
        imageBoss = Toolkit.getDefaultToolkit().getImage(getClass().getResource("boss.png"));
        imageBoss2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("boss2.png"));
        imageBoss3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("boss2.png"));
        imageDBoss = Toolkit.getDefaultToolkit().getImage(getClass().getResource("damaged_boss.png"));
        imageDBoss2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("damaged_boss2.png"));
        imageDBoss3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("damaged_boss2.png"));
        imageBack = Toolkit.getDefaultToolkit().getImage(getClass().getResource("sky.jpg"));
        imageBack2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("sky2.jpg"));
        imageBack3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("sky3.jpg"));
        bossComing = Toolkit.getDefaultToolkit().getImage(getClass().getResource("zoom.jpg"));
        
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

    public void drawPlayer(Graphics g, Airplane ap) {
        if(ap.isApUndamegedTime()) {
        	g.drawImage(imageDAp, ap.getApx(), ap.getApy(), this);
        }
        else {
        	g.drawImage(imageAp, ap.getApx(), ap.getApy(), this);
        }
        //プレイヤーの攻撃
        Attack atk = ap.getAttack();
        if(atk.isExist()) {
        	g.drawImage(imageAtk, atk.getAtx(), atk.getAty(), this);
        }
    }
    
    public void drawGame(Graphics g,int level, Boss boss) {
	    switch (level) {
	    case 0:
	    	g.drawImage(imageBack, 0, 0, this);
	    	//ボス,攻撃
	    	if(boss.isBossExist()) {
	    		if(boss.isUndamegedTime()) {//ボスの無敵時間
	    			g.drawImage(imageDBoss, boss.getBx(), boss.getBy(), this);
	    		}
	    		else {
	    			g.drawImage(imageBoss, boss.getBx(), boss.getBy(), this);
	    		}
	    		g.drawString("LIFE: " + boss.getLife() , 400, 20);
	    	}
	    	break;
	    case 1:
	    	g.drawImage(imageBack2, 0, 0, this);
	    	//ボス,攻撃
	    	if(boss.isBossExist()) {
	    		if(boss.isUndamegedTime()) {//ボスの無敵時間
	    			g.drawImage(imageDBoss2, boss.getBx(), boss.getBy(), this);
	    		}
	    		else {
	    			g.drawImage(imageBoss2, boss.getBx(), boss.getBy(), this);
	    		}
	    		g.drawString("LIFE: " + boss.getLife() , 400, 20);
	    	}
	    	break;
	    case 2:
	    	g.drawImage(imageBack3, 0, 0, this);
	    	//ボス,攻撃
	    	if(boss.isBossExist()) {
	    		if(boss.isUndamegedTime()) {//ボスの無敵時間
	    			g.drawImage(imageDBoss3, boss.getBx(), boss.getBy(), this);
	    		}
	    		else {
	    			g.drawImage(imageBoss3, boss.getBx(), boss.getBy(), this);
	    		}
	    		g.drawString("LIFE: " + boss.getLife() , 400, 20);
	    	}
	    	break;
	    }
	    Attack bossatk = boss.getAttack();
		if(bossatk.isExist()) {
			g.drawImage(imageBAtk, bossatk.getAtx(), bossatk.getAty(), this);
		}
    }
    
    public void drawWall(Graphics g, LinkedList <Wall> wall) {
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
    public Image getImageBack() {
    	return imageBack;
    }
    public Image getImageAp() {
    	return imageAp;
    }
    public Image getBossComing() {
    	return bossComing;
    }
}
