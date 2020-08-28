import java.util.LinkedList;

public class Model {

    private View view;
    private Controller controller;
    private State state;
    private State oldState;
    private int viewState;
    // Sample instance variables:
    private Airplane ap;
    private LinkedList <Wall> wall;
    private String typedChar;
    private Boss boss;

    private int undamaged_count;
    private int undamaged_count_boss;
    private int time;
    private int score;
    private int throughCount;//壁を通過したかどうかを確認する
    public final static int[] QUOTA = {1, 2, 2};//ボスの登場するスコアのノルマ;//ボスの登場するスコアのノルマ
    private int wallCount;
    private boolean hit;
    private boolean cleared;

	public Model() {
        view = new View(this);
        controller = new Controller(this);
        ap = new Airplane(this);
        wall = new LinkedList<Wall>();
        boss = new Boss(0);
        state = new TitleState(this);
        oldState = state;
        viewState = 0;
        typedChar = "";
        time = 0;
        score = 0;
        throughCount = 0;
        undamaged_count = 0;
        undamaged_count_boss = 0;
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
				wall.add(new Wall(i * Wall.HEIGHT , this));
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
    	if(boss.isBossExist() && hitBoss(atk, stateNumber) && atk.isExist()) {
    		hit = true;
        	boss.damagedBoss();
			atk.reach();
    	}

    	if(bossAtk.isExist())
    		hitAp(bossAtk);

    	//壁の処理
    	for(int i = 0; i < wall.size() ; i++) {
    		Wall w = wall.get(i);
    		w.updateWall();
    		//壁が範囲外に出たら消す
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
    				wallCount++;
    			}
    		}
    		//攻撃と壁の衝突
    		if(atk.isExist() && hitWall(w, atk)) {
    			hit = true;
    			wall.remove(i);
    			atk.reach();
    			i--;
    		}
    	}
    }

    public void hitAp(Attack bossAtk) {
    	if(bossAtk.getAtx() <= ap.getApx()) {
    		if(equalY(bossAtk.getAty(), ap.getApy(), Attack.SIZE /2 ,Airplane.HEIGHT)
	        		&& (!ap.isUndameged_time())
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
	        		&& (!boss.isUndameged_time())
	        		&& !atk.isThrough()) {
    			return true;
	        }
    		atk.setThrough(true);
    	}
    	return false;
	}
    
    public boolean hitWall(Wall w, Attack atk) {
		if(equalY(atk.getAty(), w.getWy(),Attack.SIZE, Wall.HEIGHT) &&ap.getApx() <= w.getWx()) {
			if(!w.hitWall(atk.getAtx()) ) {
				return true;
			}
		}
		return false;
    }

	private boolean equalY(int y, int wy ,int distance, int height) {
    	if((y + (distance/2) >= wy && y + (distance/2) < wy + height)
    			|| (y >= wy && y < wy + height)
    			|| (y + distance >= wy && y + distance < wy + height))
    		return true;
		return false;
	}

	public void damaged_ap() {
    	if(ap.isUndameged_time()) {
    		undamaged_count++;
    		if(undamaged_count > 20) {
    			undamaged_count = 0;
    			ap.setUndameged_time(false);
    		}
    	}
	}
	
	public void damaged_boss() {
    	if(boss.isUndameged_time()) {
    		undamaged_count_boss++;
    		if(undamaged_count_boss > 20) {
    			undamaged_count_boss = 0;
    			boss.setUndameged_time(false);
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
    
    public LinkedList<Wall> getWall() {
		return wall;
	}

	public Boss getBoss() {
		return boss;
	}
	
	public State getState() { return state; }
	
    public int getUndamaged_count() {
		return undamaged_count;
	}

	public void setUndamaged_count(int undamaged_count) {
		this.undamaged_count = undamaged_count;
	}

	public int getUndamaged_count_boss() {
		return undamaged_count_boss;
	}

	public void setUndamaged_count_boss(int undamaged_count_boss) {
		this.undamaged_count_boss = undamaged_count_boss;
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
        ap = new Airplane(this);
        wall = new LinkedList<Wall>();
        boss = new Boss(0);
        typedChar = "";
        time = 0;
        score = 0;
        throughCount = 0;
        undamaged_count = 0;
        undamaged_count_boss = 0;
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
}
