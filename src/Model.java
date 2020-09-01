import java.util.LinkedList;
import java.util.List;

public class Model {

    private View view;
    private Controller controller;
    private State state;
    private State oldState;
    private int viewState;
    // Sample instance variables:
    private Airplane ap;
    private List<Wall> wall;
    private String typedChar;
    private Boss boss;

    private int undamagedCount;
    private int undamagedCountBoss;
    private int time;
    private int score;
    private int throughCount;//壁を通過したかどうかを確認する
    private static final int[] QUOTA = {1, 2, 2};//ボスの登場するスコアのノルマ;//ボスの登場するスコアのノルマ
    private int wallCount;
    private boolean hit;
    private boolean cleared;

	public Model() {
        view = new View(this);
        controller = new Controller(this);
        ap = new Airplane();
        wall = new LinkedList<>();
        boss = new Boss(0);
        state = new TitleState(this);
        oldState = state;
        viewState = 0;
        typedChar = "";
        time = 0;
        score = 0;
        throughCount = 0;
        undamagedCount = 0;
        undamagedCountBoss = 0;
        wallCount = 0;
        hit = false;
        cleared = false;
        setViewState(0);
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
    		int i = 0;
			for(; i * Wall.HEIGHT < Game.WIN_HEIGHT ; i++) {
				wall.add(new Wall(i * Wall.HEIGHT));
			}
			RandNumGenerator r = RandNumGenerator.getInstance();
			int j = r.nextInt(i-1);
			for(int k = 0; k < 2; k++) {//壁にブロック2個分の穴を空ける
				wall.remove(j);
			}
			throughCount = 0;
    	}
	}
    
    public void update(int stateNumber) {
    	ap.update();
    	boss.update();
    	Attack atk = ap.getAttack();
    	Attack bossAtk = boss.getAttack();
    	//ボスに攻撃が当たったかどうかを判別する
    	hitAttack(atk, stateNumber);
    	
    	hitAp(bossAtk);

    	//壁の処理
    	for(int i = wall.size() -1; i >= 0 ; i--) {
    		Wall w = wall.get(i);
    		w.updateWall();
    		//壁が範囲外に出たら消す
    		if(!w.getExist()) {
    			wall.remove(i);
    			continue;
    		}
    		//プレイヤーアイコンと壁の衝突
    		if(ap.getApx() + (Attack.SIZE/2) >= w.getWx()){
    			if((!ap.isApUndamegedTime()) && equalY(ap.getApy(), w.getWy(),Airplane.HEIGHT, Wall.HEIGHT) 
    					&& !w.isThrough()) {
    				ap.damagedAirplane();
    			}
    			w.setThrough(true);
    			throughCount++;
    			if(throughCount == wall.size()) {
    				score += 1000;
    				wallCount++;
    			}
    		}
    		//攻撃と壁の衝突
    		if(atk.isExist() && hitWall(w, atk)) {
    			hit = true;
    			wall.remove(i);
    			atk.reach();
    		}
    	}
    }

   private void hitAttack(Attack atk, int stateNumber) {
    	if(boss.isBossExist() && hitBoss(atk, stateNumber) && atk.isExist()) {
    		hit = true;
        	boss.damagedBoss();
			atk.reach();
    	}
	}

	public void hitAp(Attack bossAtk) {
    	if(bossAtk.isExist() && bossAtk.getAtx() <= ap.getApx()) {
    		if(equalY(bossAtk.getAty(), ap.getApy(), Attack.SIZE /2 ,Airplane.HEIGHT)
	        		&& (!ap.isApUndamegedTime())
	        		&& !bossAtk.isThrough()) {//x,y座標の関係,無敵時間,弾が過ぎてるかどうか
	        	ap.damagedAirplane();
				bossAtk.reach();
	        }
    		bossAtk.setThrough(true);
    	}
	}
    
    private boolean hitBoss(Attack atk, int stateNumber) {
    	if(atk.getAtx() >= boss.getBx()) {
    		if(equalY(atk.getAty(), boss.getBy(), Attack.SIZE ,Boss.BOSS_SIZE[stateNumber]) 
	        		&& (!boss.isUndamegedTime())
	        		&& !atk.isThrough()) {
    			return true;
	        }
    		atk.setThrough(true);
    	}
    	return false;
	}
    
    public boolean hitWall(Wall w, Attack atk) {
		return (equalY(atk.getAty(), w.getWy(),Attack.SIZE, Wall.HEIGHT) 
				&&ap.getApx() <= w.getWx() && !w.hitWall(atk.getAtx()) ) ;
    }

	private boolean equalY(int y, int wy ,int distance, int height) {
    	return ((y + (distance/2) >= wy && y + (distance/2) < wy + height)
    			|| (y >= wy && y < wy + height)
    			|| (y + distance >= wy && y + distance < wy + height));
	}

	public void damagedAp() {
    	if(ap.isApUndamegedTime()) {
    		undamagedCount++;
    		if(undamagedCount > 20) {
    			undamagedCount = 0;
    			ap.setApUndamegedTime(false);
    		}
    	}
	}
	
	public void damagedBoss() {
    	if(boss.isUndamegedTime()) {
    		undamagedCountBoss++;
    		if(undamagedCountBoss > 20) {
    			undamagedCountBoss = 0;
    			boss.setUndamegedTime(false);
    		}
    	}
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
    
    public List<Wall> getWall() {
		return wall;
	}

	public Boss getBoss() {
		return boss;
	}
	
	public State getState() { return state; }
	
    public int getUndamagedCount() {
		return undamagedCount;
	}

	public void setUndamagedCount(int undamagedCount) {
		this.undamagedCount = undamagedCount;
	}

	public int getUndamagedCountBoss() {
		return undamagedCountBoss;
	}

	public void setUndamagedCountBoss(int undamagedCountBoss) {
		this.undamagedCountBoss = undamagedCountBoss;
	}

	public int getTime() {
		return time;
	}

	public String getTypedChar() {
		return typedChar;
	}

	public void setTime(int i) {
		time = i;
	}

	public void setTypedChar(String s) {
		typedChar = s;
	}

	public int getWallCount() {
		return wallCount;
	}
	
	public void setWallCount(int i) {
		wallCount = i;
	}

	public void reset(int i) {
		boss.reset(i);
		wallCount = 0;
	}
	
	public void newGame() {
        ap = new Airplane();
        wall.clear();
        boss = new Boss(0);
        typedChar = "";
        time = 0;
        score = 0;
        throughCount = 0;
        undamagedCount = 0;
        undamagedCountBoss = 0;
        wallCount = 0;
        viewState = 0;
        hit = false;
        cleared = false;
	}

	public void setOldState(State st) {
		oldState = st;
	}
	
	public State getOldState() {
		return oldState;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}
	public boolean isHit() {
		return hit;
	}

	public int getViewState() {
		return viewState;
	}

	public void setViewState(int viewState) {
		this.viewState = viewState;
	}

	public boolean isCleared() {
		return cleared;
	}

	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}
	
	public int getQUOTA(int i) {
		return QUOTA[i];
	}
}
