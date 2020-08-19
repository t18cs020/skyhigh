import java.util.LinkedList;

public class Model {

    private View view;
    private Controller controller;
    private State state;
    // Sample instance variables:
    private Airplane ap;
    private LinkedList <Wall> wall;
    
    private Boss boss;
    
    private int score;
	private int throughCount;//壁を通過したかどうかを確認する
    public final static int QUOTA = 1000;//ボスの登場するスコアのノルマ

	public Model() {
        view = new View(this);
        controller = new Controller(this);
        ap = new Airplane(this);
        wall = new LinkedList<Wall>();
        boss = new Boss(this, 0);
        state = new TitleState(this);
    }

    public synchronized void processTimeElapsed(int msec) {
		state = state.processTimeElapsed(msec);
        view.repaint();
    }

	public synchronized void processKeyTyped(String typed) {
		state = state.processKeyTyped(typed);
        view.repaint();
    }

    public synchronized void processMousePressed() {
    	state = state.processMousePressed();
        view.repaint();
    }

    public void start() {
        controller.start();
    }
    
    public void makeWall() {
    	if(wall.isEmpty()) {
			for(int i = 0; i * Wall.HEIGHT < Game.WIN_HEIGHT ; i++) {
				wall.add(new Wall(i * Wall.HEIGHT , this));
			}
			throughCount = 0;
    	}
	}
    
	public void update() {
        ap.update();
        boss.update();
        Attack atk = ap.getAttack();
        Attack bossAtk = boss.getAttack();
        //ボスに攻撃が当たったかどうかを判別する
        if(boss.isBossExist())
        	hitBoss(atk);
        
        if(bossAtk.isExist())
        	hitAp(bossAtk);
        
        //壁の処理
        for(int i = 0; i < wall.size() ; i++) {
        	Wall w = wall.get(i);
        	w.updateWall();
        	if(!w.getExist()) {
				wall.remove(i);
				i--;
				continue;
        	}
        	//プレイヤーアイコンと壁の衝突
        	if(ap.getApx() + (Attack.SIZE/2) >= w.getWx()){
	        	if((!ap.isUndameged_time()) && equalY(ap.getApy(), w.getWy(),Airplane.HEIGHT, Wall.HEIGHT) 
	        			&& !w.isThrough()) {
	        		ap.damagedAirplane();
	        	}
	        	w.setThrough(true);
	        	throughCount++;
	        	if(throughCount == wall.size()) {
	        		score += 1000;
	        	}
        	}
        	
        	//攻撃と壁の衝突
			if(atk.isExist()) {
				i = hitWall(w, atk, i);
			}
        }
	}
	
    private void hitAp(Attack bossAtk) {
    	if(bossAtk.getAtx() <= ap.getApx()) {
    		if(equalY(bossAtk.getAty(), ap.getApy(), Attack.SIZE ,Airplane.HEIGHT) 
	        		&& (!ap.isUndameged_time())
	        		&& !bossAtk.isThrough()) {//x,y座標の関係,無敵時間,弾が過ぎてるかどうか
	        	ap.damagedAirplane();
				bossAtk.reach();
	        }
    		bossAtk.setThrough(true);
    	}
	}
    
    private void hitBoss(Attack atk) {
    	if(atk.getAtx() >= boss.getBx()) {
    		if(equalY(atk.getAty(), boss.getBy(), Attack.SIZE ,Boss.BOSS1_SIZE) 
	        		&& (!boss.isUndameged_time())
	        		&& !atk.isThrough()) {
	        	boss.damagedBoss();
				atk.reach();
	        }
    		atk.setThrough(true);
    	}
	}
    
    private int hitWall(Wall w, Attack atk, int i) {
		if(equalY(atk.getAty(), w.getWy(),Attack.SIZE, Wall.HEIGHT) &&ap.getApx() <= w.getWx()) {
			if(!w.hitWall(atk.getAtx()) ) {
				wall.remove(i);
				atk.reach();
				i--;
			}
		}
		return i;
    }

	private boolean equalY(int y, int wy ,int distance, int height) {
    	if((y + (distance/2) >= wy && y + (distance/2) < wy + height)
    			|| (y >= wy && y < wy + height)
    			|| (y + distance >= wy && y + distance < wy + height))
    		return true;
		return false;
	}

	public View getView() {
        return view;
    }

    public Controller getController() {
        return controller;
    }
    
    public int getScore() {
    	return score;
    }
    
    public Airplane getAirplane() {
    	return ap;
    }
    
    public LinkedList<Wall> getWall() {
		return wall;
	}

	public Boss getBoss() {
		return boss;
	}
	
	public State getState() { return state; }

}
