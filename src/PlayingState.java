import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

public class PlayingState implements State{
	
	private Model model;
	private Airplane ap;
	private int undamaged_count;
	private Boss boss;
	private int undamaged_count_boss;
	private int time;
	private View view;
	private String typedChar;
	
	public PlayingState(Model model) {
		super();
		this.model = model;
		ap = model.getAirplane();
		boss = model.getBoss();
        undamaged_count = model.getUndamaged_count();
        undamaged_count_boss = model.getUndamaged_count_boss();
        time = model.getTime();
        view = model.getView();
        typedChar = "";
	}
	
	// タイトル状態におけるキータイプイベント処理
	public State processKeyTyped(String typed) {
		typedChar = typed;
        model.setTypedChar(typedChar);
		if (typedChar.equals(" ")) {
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
    	if(ap.isUndameged_time()) {
    		undamaged_count++;
    		if(undamaged_count > 20) {
    			undamaged_count = 0;
    			ap.setUndameged_time(false);
    		}
    	}
    	
    	if(boss.isUndameged_time()) {
    		undamaged_count_boss++;
    		if(undamaged_count_boss > 20) {
    			undamaged_count_boss = 0;
    			boss.setUndameged_time(false);
    		}
    	}
    	
       model.update();
       //ボスがいないかつ一定スコアに達したとき,ボスを登場させる
    	if(model.getScore() >= Model.QUOTA  && (!boss.isBossExist())){
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
        Attack atk = ap.getAttack();//プレイヤーの攻撃
        LinkedList <Wall> wall = model.getWall();
        Boss boss = model.getBoss();
        Attack bossatk = boss.getAttack();//ボスの攻撃

	    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
	    g.setColor(Color.WHITE);
	    
	    //背景
	    g.drawImage(view.imageBack, 0, 0, view);
	    //プレイヤーアイコン
        if(ap.isUndameged_time()) {
        	g.drawImage(view.imageDAp, ap.getApx(), ap.getApy(), view);
        }
        else {
        	g.drawImage(view.imageAp, ap.getApx(), ap.getApy(), view);
        }
        //プレイヤーの攻撃
        if(atk.isExist()) {
        	g.drawImage(view.imageAtk, atk.getAtx(), atk.getAty(), view);
        }
		//ボス,攻撃
		if(boss.isBossExist()) {
	        g.drawImage(view.imageBoss, boss.getBx(), boss.getBy(), view);
	        if(boss.isUndameged_time()) {//ボスの無敵時間
	        	g.drawImage(view.imageDBoss, boss.getBx(), boss.getBy(), view);
	        }
	        else {
	        	g.drawImage(view.imageBoss, boss.getBx(), boss.getBy(), view);
	        }
	        if(bossatk.isExist()) {
	        	g.drawImage(view.imageBAtk, bossatk.getAtx(), bossatk.getAty(), view);
	        }
	        g.drawString("LIFE: " + boss.getLife() , 400, 20);
		}
        //壁
		for(int i = 0; i < wall.size() ; i++) {
			Wall w = wall.get(i);
			if(w.getExist()) {
				g.drawImage(view.imageWl, w.getWx(), w.getWy(), view);
			}
		}
		
	    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
	    g.setColor(Color.WHITE);
	    g.drawString("LIFE: " + ap.getLife() , 10, 20);
	    g.drawString("SCORE: " + model.getScore() , 10, 40);

	}
	public Model getModel() {
		return model;
	}
	public int getUndamaged_count() {
		return undamaged_count;
	}
	public int getUndamaged_count_boss() {
		return undamaged_count_boss;
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
}
