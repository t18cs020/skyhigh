import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

public class PlayingState implements State {//ゲーム中のstate

	private Model model;
	private Airplane ap;
	private Boss boss;
	private View view;
	private int level;

	public PlayingState(Model model) {
		super();
		this.model = model;
		ap = model.getAirplane();
		boss = model.getBoss();
		view = model.getView();
		level = 0;
    	view.startBgm(model.getBgmVolume());
	}
	
	// タイトル状態におけるキータイプイベント処理
	public State processKeyTyped(String typed) {
		switch(typed) {
		case "b": 
			model.setOldState(this);
			return new BossState(model);
		case "ESC":
			model.setOldState(this);
			return new PauseState(model);
		default :
			break;
		}
		
		ap.move(typed);
		return this;
	}

	// タイトル状態の時間経過イベントを処理するメソッド
	public State processTimeElapsed(int msec) { 
        int time = model.getTime();
        if(time % 10 == 0) {
        	model.makeWall();
        }
        //ボスの攻撃発射
        if(boss.isBossExist() && time % 20 == 0) {
        	for(int i = 0; i < Boss.ATTACKS; i++)
        		boss.shot(boss.getAttack().get(i));
        }
        
        //障害物の生成
       makeObstacle();
       
        //無敵時間の計測
       model.damagedAp();
    	model.damagedBoss();
    	
       model.update();
       
       //自機のライフが0になったときリザルトへ
       if(ap.getLife() == 0) {
			model.setOldState(this);
			model.setContinued();
    	   return new ResultState(model);
       }
       //ボスのライフが0になったときレベルアップ
       if(boss.getLife() <= 0) {
    	   level++;
    	   model.calcBossScore();
    	   model.setLevel(level);
    	   if(level == 3) {
    		   model.setCleared(true);
    		   return new ResultState(model);
    	   }
    	   ap.heal();
    	   ap.setDeltaAttackCount(0);
    	   model.reset(level);
    	   model.setViewState(level);
       }
       
       //ボスがいないかつ一定スコアに達したとき,ボスを登場させる
    	if(model.getWallCount() >= model.getQUOTA(level)  && (!boss.isBossExist())){
    		boss.setBossExist(true);
    	}
		return this; 
	}
	
	private void makeObstacle() {
		if(!model.getObstacle().isExist()) {
			model.makeObstacle();
		}
	}

	// タイトル状態のマウスクリックイベントを処理するメソッド
	public State processMousePressed(int x, int y) {
    	 model.getAirplane().shot();
		return this; 
	}
	
	public void paintComponent(Graphics g) {
		Boss boss = model.getBoss();
        view.drawGame(g,level, boss);
	    
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
	public View getView() {
		return view;
	}
	public int getLevel() {
		return level;
	}

}

