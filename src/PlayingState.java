import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

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
       model.damaged_ap();
    	model.damaged_boss();
    	
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
    	if( level < 3 && model.getWallCount() >= Model.QUOTA[level]  && (!boss.isBossExist())){
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
        Airplane ap = model.getAirplane();
        LinkedList <Wall> wall = model.getWall();
        Boss boss = model.getBoss();
        Attack bossatk = boss.getAttack();//ボスの攻撃

	    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
	    g.setColor(Color.WHITE);
	    
	    //背景
	    switch (level) {
	    case 0:
	    	g.drawImage(view.imageBack, 0, 0, view);
	    	//ボス,攻撃
	    	if(boss.isBossExist()) {
	    		if(boss.isUndameged_time()) {//ボスの無敵時間
	    			g.drawImage(view.imageDBoss, boss.getBx(), boss.getBy(), view);
	    		}
	    		else {
	    			g.drawImage(view.imageBoss, boss.getBx(), boss.getBy(), view);
	    		}
	    		g.drawString("LIFE: " + boss.getLife() , 400, 20);
	    	}
	    	break;
	    case 1:
	    	g.drawImage(view.imageBack2, 0, 0, view);
	    	//ボス,攻撃
	    	if(boss.isBossExist()) {
	    		if(boss.isUndameged_time()) {//ボスの無敵時間
	    			g.drawImage(view.imageDBoss2, boss.getBx(), boss.getBy(), view);
	    		}
	    		else {
	    			g.drawImage(view.imageBoss2, boss.getBx(), boss.getBy(), view);
	    		}
	    		g.drawString("LIFE: " + boss.getLife() , 400, 20);
	    	}
	    	break;
	    case 2:
	    	g.drawImage(view.imageBack3, 0, 0, view);
	    	//ボス,攻撃
	    	if(boss.isBossExist()) {
	    		if(boss.isUndameged_time()) {//ボスの無敵時間
	    			g.drawImage(view.imageDBoss3, boss.getBx(), boss.getBy(), view);
	    		}
	    		else {
	    			g.drawImage(view.imageBoss3, boss.getBx(), boss.getBy(), view);
	    		}
	    		g.drawString("LIFE: " + boss.getLife() , 400, 20);
	    	}
	    	break;
	    }
	    //プレイヤー
	    view.drawPlayer(g, ap);
		if(bossatk.isExist()) {
			g.drawImage(view.imageBAtk, bossatk.getAtx(), bossatk.getAty(), view);
		}
        //壁
		for(int i = 0; i < wall.size() ; i++) {
			Wall w = wall.get(i);
			if(w.getExist()) {
				g.drawImage(view.imageWl, w.getWx(), w.getWy(), view);
			}
		}

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

