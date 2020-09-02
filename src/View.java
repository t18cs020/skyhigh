import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View extends JPanel {

    private Model model;
    // Sample instance variables:
    private Image imageAp;
    private Image imageDAp;
    private Image imageAtk;
    private Image imageWl;
    private Image imageBoss;
    private Image imageBack;
    private Image imageDBoss;
    private Image imageBAtk;
    private Image imageBack2;
    private Image imageBack3;
    private Image imageBoss2;
    private Image imageBoss3;
    private Image bossComing;
    private Image imageDBoss2;
    private Image imageDBoss3;
    private AudioClip sound;
    
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
        imageBoss3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("boss3.png"));
        imageDBoss = Toolkit.getDefaultToolkit().getImage(getClass().getResource("damaged_boss.png"));
        imageDBoss2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("damaged_boss2.png"));
        imageDBoss3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("damaged_boss3.png"));
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
    	Image ib;
    	Image idb;
    	Image back;
	    switch (level) {
	    case 1:
	    	//レベル2のとき
	    	ib = imageBoss2;
	    	idb = imageDBoss2;
	    	back = imageBack2;
	    	break;
	    case 2:
	    	//レベル3のとき
	    	ib = imageBoss3;
	    	idb = imageDBoss3;
	    	back = imageBack3;
	    	break;
	    default:
	    	//レベル1のとき
	    	ib = imageBoss;
	    	idb = imageDBoss;
	    	back = imageBack;
	    	break;
	    }
    	g.drawImage(ib, 0, 0, this);
    	//背景
    	g.drawImage(back, 0, 0, this);
    	//ボス
    	if(boss.isBossExist()) {
    		if(boss.isUndamegedTime()) {//ボスの無敵時間
    			g.drawImage(idb, boss.getBx(), boss.getBy(), this);
    		}
    		else {
    			g.drawImage(ib, boss.getBx(), boss.getBy(), this);
    		}
    		g.drawString("LIFE: " + boss.getLife() , 400, 20);
    	}
    	//ボスの攻撃の描写
	    drawObstacle(g, boss.getAttack());
    }
    //壁の描画
    public void drawWall(Graphics g, List<Wall> wall) {
		for(int i = 0; i < wall.size() ; i++) {
			Wall w = wall.get(i);
			if(w.getExist()) {
				g.drawImage(imageWl, w.getWx(), w.getWy(), this);
			}
		}
    }
    
    //障害物の描画
    public void drawObstacle(Graphics g, Attack obstacle) {
		if(obstacle.isExist()) {
			g.drawImage(imageBAtk, obstacle.getAtx(), obstacle.getAty(), this);
		}
    }
    /**
     * 画面を黒色でクリア
     * @param g  描画用のグラフィックスオブジェクト
     */
    public void clear(Graphics g) {
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

	public void drawTyping(Graphics g, String answer, String s) {

		int delta = model.elapseTime();

		g.setColor(Color.WHITE);
		g.fillRect(100, 100, 600, 60);
		g.fillRect(100, 300, 600, 60);
		//タイピング部分の描画
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		g.setColor(Color.BLACK);
		g.drawString(answer , 110, 150);
		g.drawString(s , 110, 350);
		//時間の描画
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		g.drawString("time : ", 110, 50);
		g.setColor(Color.WHITE);
		if(delta < 30) { //30カウント未満は白く表示
			g.fillRect(240 + delta * 10, 22, 10, 40);
		}
		else if(delta < 60) { //30カウント経過したら赤く表示
			g.setColor(Color.RED);
			g.fillRect(240 + delta * 10, 22, 10, 40);
		}
	}

	public void drawTitle(Graphics g,int cursor) {
		
		clear(g);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 80));
		g.setColor(Color.BLACK);
		g.drawString("Sky High", 230, 150);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		g.drawString("ゲームスタート", 330, 300);
		g.drawString("ヘルプ", 330, 350);
		g.drawString("ランキング", 330, 400);
		g.drawString("ゲーム終了", 330, 450);
		g.drawString("(Enterキーで決定)", 330, 500);
		g.drawImage(imageAp, 280, 280 + 50*cursor , this);

	}

	public void drawHelp(Graphics g) {
		clear(g);
		//本文
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.setColor(Color.BLACK);
		g.drawString("操作方法:", 100, 50);
		g.drawString("Wキー:上へ移動", 250, 50);
		g.drawString("Sキー:下へ移動", 250, 80);
		g.drawString("Aキー:左へ移動", 250, 110);
		g.drawString("Dキー:右へ移動", 250, 140);
		g.drawString("マウスをクリックして攻撃", 250, 170);
		g.drawString("ルール:", 100, 210);
		g.drawString("一定数壁を超えたらボスが出現します", 250, 210);
		g.drawString("攻撃によって壁を壊したりボスに", 250, 250);
		g.drawString("ダメージを与えることがができます", 250, 290);
		g.drawString("ボスの体力が0になったらレベルが上昇します", 250, 330);
		g.drawString("プレイヤーの体力が0になったり,", 250, 370);
		g.drawString("墜落したらゲームオーバーです", 250, 400);
		g.drawString("ゲーム中にbキーを押すとボスが来た画面に遷移します", 250, 440);
		g.drawString("(再度bキーを押すとゲームを再開します)", 250, 470);
		g.drawString("ゲーム中,またはボスが来た画面でEscを押すと", 250, 500);
		g.drawString("タイトル画面に戻ります", 250, 530);
		g.drawString("Enterキーでタイトルに戻る", 100, 560);
	}

	public void drawRanking(Graphics g, List<Integer> ranking) {
		clear(g);
	    //本文
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		g.setColor(Color.BLACK);
		g.drawString("歴代スコアベスト3" ,200 , 100);
		g.drawString("1位 : " + ranking.get(0) ,200 , 250);
		g.drawString("2位 : " + ranking.get(1) ,200 , 310);
		g.drawString("3位 : " + ranking.get(2) ,200 , 370);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
		g.drawString("Enterキーでタイトルに戻る", 200, 550);
	}
	//リザルト画面の描画
	public void drawResult(Graphics g, List<Integer> ranking) {
		g.setColor(Color.WHITE);
		g.fillRect(101, 101, 598, 398);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		g.setColor(Color.BLACK);
		g.drawRect(100, 100, 600, 400);

		if(model.isCleared()) {
			g.drawString("ゲームクリア！" ,200 , 150);
			g.drawString("あなたのスコア : " + model.getScore() ,200 , 200);
			g.drawString("1位 : " + ranking.get(0) ,200 , 250);
			g.drawString("2位 : " + ranking.get(1) ,200 , 300);
			g.drawString("3位 : " + ranking.get(2) ,200 , 350);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
			if(model.isContinued()) {
				g.drawString("コンティニューしたのでスコアは反映されません" ,200 , 400);
			}
		}
		else {
			g.drawString("ゲームオーバー" ,200 , 150);
			g.drawString("Enterキーでゲームを再開" , 200, 400);
		}
		g.drawString("Escキーでタイトルに戻る" , 200, 450);
	
	}
	
}
