import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

public class PlayingState implements State {

	private Model model;
	private Airplane ap;
	private Boss boss;
	private int time;
	private View view;
	private String typedChar;
	private int level;
	
	public PlayingState(Model model) {
		super();
		this.model = model;
		ap = model.getAirplane();
		boss = model.getBoss();
		time = model.getTime();
		view = model.getView();
		typedChar = "";
		level = 0;
	}
	
	// タイトル状態におけるキータイプイベント処理
	public State processKeyTyped(String typed) {
		typedChar = typed;
		model.setTypedChar(typedChar);
		
		switch(typedChar) {
		case "b": 
			model.setOldState(this);
			return new BossState(model);
		case "ESC": 
			return new TitleState(model);
		default :
			break;
		}
		
		ap.move(typedChar);
		return this;
	}

	// タイトル状態の時間経過イベントを処理するメソッド
	public State processTimeElapsed(int msec) { 
        model.setTime(++time);
        if(time % 10 == 0) {
        	model.makeWall();
        }
    	
        if(boss.isBossExist()) {
        	boss.shot();
        }
        
        //無敵時間の計測
       model.damagedAp();
    	model.damagedBoss();
    	
       model.update(level);
       if(model.isHit()) {
			model.setOldState(this);
    	   return new TypingState(model);
       }
       //自機のライフが0になったときリザルトへ
       if(ap.getLife() == 0) {
			model.setOldState(this);
    	   return new ResultState(model);
       }
       //ボスのライフが0になったときレベルアップ
       if(boss.getLife() == 0) {
    	   model.reset(level++);
    	   model.setViewState(level);
    	   if(level == 3) {
    		   model.setCleared(true);
    		   return new ResultState(model);
    	   }
       }
       
       //ボスがいないかつ一定スコアに達したとき,ボスを登場させる
    	if(model.getWallCount() >= model.getQUOTA(level)  && (!boss.isBossExist())){
    		boss.setBossExist(true);
    	}
		return this; 
	}
	
	// タイトル状態のマウスクリックイベントを処理するメソッド
	public State processMousePressed() {
        model.getView().playBombSound();
    	 model.getAirplane().shot();
		return this; 
	}
	
	public void paintComponent(Graphics g) {
        // 画面をいったんクリア
        view.clear(g);
        List <Wall> wall = model.getWall();

	    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
	    g.setColor(Color.WHITE);
	    
		//ゲーム画面の描画
		view.drawGame(g, level, boss);
	    //プレイヤー
	    view.drawPlayer(g, ap);
		
        //壁
		view.drawWall(g, wall);

	    g.drawString("LIFE: " + ap.getLife() , 10, 20);
	    g.drawString("SCORE: " + model.getScore() , 10, 40);
	    g.drawString("wall: " + model.getWallCount() , 10, 60);
	    
	}
	public Model getModel() {
		return model;
	}

	public Airplane getAp() {
		return ap;
	}
	public Boss getBoss() {
		return boss;
	}
	public int getTime() {
		return time;
	}
	public View getView() {
		return view;
	}
	public String getTypedChar() {
		return typedChar;
	}
	public int getLevel() {
		return level;
	}
}

