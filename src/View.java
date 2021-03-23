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
    private Image imageRock;
    private Image imageBack2;
    private Image imageBack3;
    private Image imageBoss2;
    private Image imageBoss3;
    private Image bossComing;
    private Image imageDBoss2;
    private Image imageDBoss3;
    private Image imageGirl;
    private AudioClip bgm;
    private AudioClip bgm75;
    private AudioClip bgm50;
    private AudioClip bgm25;
    private AudioClip bomb;
    private AudioClip bomb75;
    private AudioClip bomb50;
    private AudioClip bomb25;
    
    public View(Model model) {
        this.model = model;
        
        // 画像を読み込む．画像ファイルは src においておくと bin に自動コピーされる
        imageAp = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ap.png"));
        imageDAp = Toolkit.getDefaultToolkit().getImage(getClass().getResource("dap.png"));
        imageAtk = Toolkit.getDefaultToolkit().getImage(getClass().getResource("atk.png"));
        imageBAtk = Toolkit.getDefaultToolkit().getImage(getClass().getResource("bossatk.png"));
        imageRock = Toolkit.getDefaultToolkit().getImage(getClass().getResource("rock.png"));
        imageWl = Toolkit.getDefaultToolkit().getImage(getClass().getResource("wall.jpg"));
        imageBoss = Toolkit.getDefaultToolkit().getImage(getClass().getResource("boss.png"));
        imageBoss2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("boss2.png"));
        imageBoss3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("boss3.png"));
        imageDBoss = Toolkit.getDefaultToolkit().getImage(getClass().getResource("damaged_boss.png"));
        imageDBoss2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("damaged_boss2.png"));
        imageDBoss3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("damaged_boss3.png"));
        imageBack = Toolkit.getDefaultToolkit().getImage(getClass().getResource("back.jpg"));
        imageBack2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("back2.jpg"));
        imageBack3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("back3.jpg"));
        imageGirl = Toolkit.getDefaultToolkit().getImage(getClass().getResource("girl.png"));
        bossComing = Toolkit.getDefaultToolkit().getImage(getClass().getResource("bossComing.jpg"));
        
        // サウンドを読み込む
        bomb = Applet.newAudioClip(getClass().getResource("bomb.wav"));
        bomb75 = Applet.newAudioClip(getClass().getResource("bomb75.wav"));
        bomb50 = Applet.newAudioClip(getClass().getResource("bomb50.wav"));
        bomb25 = Applet.newAudioClip(getClass().getResource("bomb25.wav"));
        bgm = Applet.newAudioClip(getClass().getResource("bgm.wav"));
        bgm75 = Applet.newAudioClip(getClass().getResource("bgm75.wav"));
        bgm50 = Applet.newAudioClip(getClass().getResource("bgm50.wav"));
        bgm25 = Applet.newAudioClip(getClass().getResource("bgm25.wav"));

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
    //ゲーム画面の描画
    public void drawGame(Graphics g,int level, Boss boss) {
    	Airplane ap = model.getAirplane();
    	Image ib;
    	Image idb;
    	Image back;
	    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
	    g.setColor(Color.WHITE);
        // 画面をいったんクリア
        clear(g);
        List <Wall> wall = model.getWall();
        //背景、ボスの切り替え
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
        //壁
		drawWall(g, wall);
		//障害物
		drawObstacle(g, model.getObstacle());
    	//ボス
    	if(boss.isBossExist()) {
    		if(boss.isUndamegedTime()) {//ボスの無敵時間
    			g.drawImage(idb, boss.getBx(), boss.getBy(), this);
    		}
    		else {
    			g.drawImage(ib, boss.getBx(), boss.getBy(), this);
    		}
    		g.drawString("LIFE: " , 400, 20);
    		for(int i = 0; i <  boss.getLife(); i++) {
    			g.setColor(Color.BLUE);
    			g.drawString("♥" , 460 + (20*i), 20);
    		}
    	    g.setColor(Color.WHITE);
    	}
    	//ボスの攻撃の描写
    	for(int i = 0; i < Boss.ATTACKS; i++)
    		drawBossAtk(g, boss.getAttack().get(i));
	    
	    //プレイヤー
	    drawPlayer(g, ap);
		
		g.drawString("LIFE: " , 10, 20);
		for(int i = 0; i < ap.getLife(); i++) {
			g.setColor(Color.RED);
			g.drawString("♥" , 70 + (20*i), 20);
		}
	    g.setColor(Color.WHITE);
	    g.drawString("SCORE: " + model.getScore() , 10, 40);
	    g.drawString("wall: " + model.getWallCount() , 10, 60);
	    
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
    public void drawBossAtk(Graphics g, Attack bossAtk) {
    	g.drawImage(imageBAtk, bossAtk.getAtx(), bossAtk.getAty(), this);
    }
    //障害物の描画
    public void drawObstacle(Graphics g, Attack obstacle) {
		if(obstacle.isExist()) {
			g.drawImage(imageRock, obstacle.getAtx(), obstacle.getAty(), this);
		}
    }
    /**
     * 画面を黒色でクリア
     * @param g  描画用のグラフィックスオブジェクト
     */
    public void clear(Graphics g) {
	    g.drawImage(imageBack, 0, 0, this);
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

	public void drawTitle(Graphics g,int cursor, List<Wall> wall) {
		
		clear(g);
		drawWall(g, wall);
		
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 80));
		g.setColor(Color.BLACK);
		g.drawString("Sky High", 230, 150);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		if(cursor == 0)g.setColor(Color.RED);
		g.drawString("ゲームスタート", 330, 300);
		if(cursor == 0)g.setColor(Color.BLACK);
		if(cursor == 1)g.setColor(Color.RED);
		g.drawString("ヘルプ", 330, 350);
		if(cursor == 1)g.setColor(Color.BLACK);
		if(cursor == 2)g.setColor(Color.RED);
		g.drawString("ランキング", 330, 400);
		if(cursor == 2)g.setColor(Color.BLACK);
		if(cursor == 3)g.setColor(Color.RED);
		g.drawString("ゲーム終了", 330, 450);
		if(cursor == 3)g.setColor(Color.BLACK);
		g.drawString("(Enterキーで決定)", 330, 500);
		g.drawImage(imageAp, 280, 270 + 50*cursor , this);
		g.drawImage(imageGirl, 50, 200, this);
	}

	public void drawHelp(Graphics g, List<Wall> wall, int page) {
		clear(g);
		drawWall(g,wall);
		g.setColor(Color.WHITE);
		g.fillRect(91, 91, 658, 408);
		g.setColor(Color.BLACK);
		g.drawRect(90, 90, 660, 410);
		//本文
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		switch (page) {
		case 0:
			g.drawString("操作方法:", 100, 140);
			g.drawString("Wキー:上へ移動", 250, 140);
			g.drawString("Sキー:下へ移動", 250, 170);
			g.drawString("Aキー:左へ移動", 250, 200);
			g.drawString("Dキー:右へ移動", 250, 230);
			g.drawString("マウスをクリックして攻撃", 250, 260);
			g.drawString("音量調整:", 100, 320);
			g.drawString("BGM", 190, 320);
			g.drawString("SE", 190, 380);
			for(int i = 0; i < 4; i++) {
				//BGM音量調節
				if( i == model.getBgmVolume() )
					g.setColor(Color.RED);
				else
					g.setColor(Color.GRAY);
				g.fillRect(240 + 61*i, 291, 60, 40);
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
				g.setColor(Color.BLACK);
				g.drawRect(239 + 61*i, 290, 60, 40);
				//SE音量調節
				if( i == model.getBombVolume() )
					g.setColor(Color.RED);
				else
					g.setColor(Color.GRAY);
				g.fillRect(240 + 61*i, 351, 60, 40);
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
				g.setColor(Color.BLACK);
				g.drawRect(239 + 61*i, 350, 60, 40);
			}
			g.drawString("25", 250, 320);
			g.drawString("50", 310, 320);
			g.drawString("75", 370, 320);
			g.drawString("100", 425, 320);
			
			g.drawString("25", 250, 380);
			g.drawString("50", 310, 380);
			g.drawString("75", 370, 380);
			g.drawString("100", 425, 380);
			break;
		case 1:
			g.drawString("ルール:", 100, 140);
			g.drawString("一定数壁を超えたらボスが出現します", 250, 140);
			g.drawString("攻撃によって壁を壊したりボスに", 250, 170);
			g.drawString("ダメージを与えることがができます", 250, 200);
			g.drawString("ボスの体力が0になったらレベルが上昇し,", 250, 230);
			g.drawString("プレイヤーの体力が2回復します", 250, 260);
			break;
		case 2:
			g.drawString("ルール:", 100, 140);
			g.drawString("プレイヤーの体力が0になったり,", 250, 140);
			g.drawString("墜落したらゲームオーバーです", 250, 170);
			g.drawString("ゲーム中にbキーを押すとボスが来た画面に遷移します", 250, 200);
			g.drawString("(再度bキーを押すとゲームを再開します)", 250, 230);
			g.drawString("ゲーム中,またはボスが来た画面でEscを押すと", 250, 260);
			g.drawString("ポーズ画面に遷移します", 250, 290);
			break;
		case 3:
			g.drawString("クレジット", 100, 140);
			g.drawString("画像		：", 100, 170);
			g.drawString("P Z L", 250, 170);
			g.drawString("Twitter：@PZL96657890", 250, 200);
			g.drawString("音声		：", 100, 230);
			g.drawString("魔王魂", 250, 230);
			break;
		}
		g.drawString("Enterキーでタイトルに戻る", 100, 450);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		g.drawString((page + 1 )+"/4", 390, 480);
	}

	public void drawRanking(Graphics g, List<Integer> ranking, List<Wall> wall) {
		clear(g);
	    //本文
		drawWall(g,wall);
		g.setColor(Color.WHITE);
		g.fillRect(91, 91, 658, 408);
		g.setColor(Color.BLACK);
		g.drawRect(90, 90, 660, 410);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		g.drawString("歴代スコアベスト3" ,200 , 150);
		g.drawString("1位 : " + ranking.get(0) ,200 , 250);
		g.drawString("2位 : " + ranking.get(1) ,200 , 310);
		g.drawString("3位 : " + ranking.get(2) ,200 , 370);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		g.drawString("Enterキーでタイトルに戻る", 200, 450);
	}
	//リザルト画面の描画
	public void drawResult(Graphics g, List<Integer> ranking) {
		g.setColor(Color.WHITE);
		g.fillRect(101, 101, 648, 398);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		g.setColor(Color.BLACK);
		g.drawRect(100, 100, 650, 400);

		if(model.isCleared()) {
			g.drawString("ゲームクリア！" ,150 , 130);
			g.drawString("あなたのスコア : " + model.getScore() ,150 , 160);
			g.drawString("1位 : " + ranking.get(0) ,150 , 250);
			g.drawString("2位 : " + ranking.get(1) ,150 , 300);
			g.drawString("3位 : " + ranking.get(2) ,150 , 350);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
			if(model.isContinued()) {
				g.drawString("コンティニューしたのでスコアは反映されません" ,150 , 400);
			}
		}
		else {
			g.drawString("Enterキーでゲームを再開" , 150, 300);
			g.drawString("(スコアは反映されません)" ,150 , 350);
		}
		g.drawString("Escキーでタイトルに戻る" , 150, 450);
	}
	
	public void drawPause(Graphics g, int cursor) {
		g.setColor(Color.WHITE);
		g.fillRect(101, 101, 648, 398);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		g.setColor(Color.BLACK);
		g.drawRect(100, 100, 650, 400);
		if(cursor == 0)
			g.setColor(Color.RED);
		g.drawString("ゲームを再開する" ,200 , 200);
		if(cursor == 0)
			g.setColor(Color.BLACK);
		
		if(cursor == 1)
			g.setColor(Color.RED);
		g.drawString("ゲームを終了する" ,200 , 400);
		if(cursor == 1)
			g.setColor(Color.BLACK);

		g.drawImage(imageAp, 150, 170 + 200*cursor , this);
	}
	public void startBomb(int volume) {
		stopBomb();
		switch (volume) {
		case 0:
			bomb25.play();
			break;
		case 1:
			bomb50.play();
			break;
		case 2:
			bomb75.play();
			break;
		case 3:
			bomb.play();
			break;
		}
	}
	//bgmをストップする
	public void stopBomb() {
		bomb25.stop();
		bomb50.stop();
		bomb75.stop();
		bomb.stop();
	}
	//BGMを再生する
	public void startBgm(int volume) {
		stopBgm();
		switch (volume) {
		case 0:
			bgm25.loop();
			break;
		case 1:
			bgm50.loop();
			break;
		case 2:
			bgm75.loop();
			break;
		case 3:
			bgm.loop();
			break;
		}
	}
	//bgmをストップする
	public void stopBgm() {
		bgm25.stop();
		bgm50.stop();
		bgm75.stop();
		bgm.stop();
	}
	
}
