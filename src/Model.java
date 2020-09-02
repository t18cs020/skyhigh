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
    private Attack obstacle;//障害物
    private static final int OBSTACLE_SPEED = -20;

    private int undamagedCount;//無敵時間のカウント
    private int undamagedCountBoss;//ボスの無敵時間のカウント
    private int time;
    private int deltaTime;
    private int score;//プレイヤーのスコア
    private int throughCount;//壁を通過したかどうかを確認する
    private static final int[] QUOTA = {1,2,3};//{5, 7, 9};//ボスの登場するスコアのノルマ
    private int wallCount;
    private boolean hit;
    private boolean cleared;
    private boolean continued;
    private boolean typingObstacle;
    private boolean typingBossAtk;
    private boolean typingBoss;

	private static final int BONUS_SCORE = 500;
    private int bonus;

	public Model() {
        view = new View(this);
        controller = new Controller(this);
        ap = new Airplane(this);
        wall = new LinkedList<>();
        boss = new Boss(0);
        state = new TitleState(this);
        oldState = state;
        obstacle = new Attack();
        viewState = 0;
        typedChar = "";
        time = 0;
        deltaTime = 0;
        score = 0;
        throughCount = 0;
        undamagedCount = 0;
        undamagedCountBoss = 0;
        wallCount = 0;
        hit = false;
        cleared = false;
        continued = false;
        typingObstacle = false;
        typingBossAtk = false;
        typingBoss = false;
        bonus = 0;
        setViewState(0);
    }

	public synchronized void processTimeElapsed(int msec) {
		time++;
		state = state.processTimeElapsed(msec);
        view.repaint();
    }

	public synchronized void processKeyTyped(String typed) {
		typedChar = typed;
		state = state.processKeyTyped(typedChar);
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
    
    public void makeObstacle() {
    	if(!obstacle.isExist()) {
			RandNumGenerator r = RandNumGenerator.getInstance();
			obstacle.shotAttack(Game.WIN_WIDTH, r.nextInt(Game.WIN_HEIGHT));
    	}
	}
    
    public void update(int stateNumber) {
    	ap.update();
    	boss.update();
    	obstacle.updateAttack(OBSTACLE_SPEED);
    	obstacle.isOutOfScreen();
    	
    	Attack atk = ap.getAttack();
    	Attack bossAtk = boss.getAttack();
    	//ボスに攻撃が当たったかどうかを判別する
    	hitAttack(atk, stateNumber);
    	
    	hitAp(bossAtk);
    	hitBossAtk(atk, bossAtk);
    	hitAp(obstacle);
    	hitObstacle(atk, obstacle);
    	
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
    			if(isHitApWall(w)) {
    				ap.damagedAirplane();
    			}
    			w.setThrough(true);
    			throughCount++;
    			if(throughCount == wall.size()) {
    				score = score + 1000 + BONUS_SCORE * bonus;
    				bonus = 0;
    				wallCount++;
    			}
    		}
    		//攻撃と壁の衝突
    		if(atk.isExist() && hitWall(w, atk)) {
    			hit = true;
    			wall.remove(i);
    			atk.reach();
    			bonus++;
    		}
    	}
    }
    //時間の差を求める
    public int elapseTime() {
    	return time - deltaTime;
    }
    
    //自機と壁の衝突判定
	private boolean isHitApWall(Wall w) {
		return (!ap.isApUndamegedTime()) && equalY(ap.getApy(), w.getWy(),Airplane.HEIGHT, Wall.HEIGHT) 
				&& !w.isThrough();
	}
	//攻撃とボスの衝突判定
	private void hitAttack(Attack atk, int stateNumber) {
    	if(boss.isBossExist() && hitBoss(atk, stateNumber) && atk.isExist()) {
    		hit = true;
    		boss.damagedBoss();
    		typingBoss = true;
    		atk.reach();
    	}
    }

	public void hitAp(Attack bossAtk) {
    	if(bossAtk.isExist() && bossAtk.getAtx() <= ap.getApx()) {
    		if(equalY(bossAtk.getAty(), ap.getApy(), Attack.SIZE /2 ,Airplane.HEIGHT)
	        		&& !ap.isApUndamegedTime()
	        		&& !bossAtk.isThrough()) {//x,y座標の関係,無敵時間,弾が過ぎてるかどうか
	        	ap.damagedAirplane();
				bossAtk.reach();
	        }
    		bossAtk.setThrough(true);
    	}
	}
    //ボスに攻撃が当たったかどうか
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
    //障害物に攻撃が当たったかどうか
    private void hitObstacle(Attack atk, Attack atk2) {
    	if(atk.getAtx() >= atk2.getAtx() && equalY(atk.getAty(), atk2.getAty(), Attack.SIZE , Attack.SIZE)
    				&& atk.isExist() && !atk2.isThrough()){
    		hit = true;
    		typingObstacle = true;
    		atk.reach();
    	}
	}
    //障害物に攻撃が当たったかどうか
    private void hitBossAtk(Attack atk, Attack atk2) {
    	if(atk.getAtx() >= atk2.getAtx() && equalY(atk.getAty(), atk2.getAty(), Attack.SIZE , Attack.SIZE)
    				&& atk.isExist() && !atk2.isThrough()){
    		hit = true;
    		typingBossAtk = true;
    		atk.reach();
    	}
	}
    //自分の攻撃が壁に当たったかどうか
    public boolean hitWall(Wall w, Attack atk) {
		return (equalY(atk.getAty(), w.getWy(),Attack.SIZE, Wall.HEIGHT) 
				&&ap.getApx() <= w.getWx() && !w.hitWall(atk.getAtx()) ) ;
    }

    public void calcBossScore() {//スコア計算
    	//攻撃15回以内にボスを倒したらボーナス
    	score += BONUS_SCORE * max(15 - ap.getDeltaAttackCount(),0);
 	}
    
    public void calcScore() {//スコア計算
    	score += BONUS_SCORE;
 	}
//どっちが大きいか判別する
	private int max(int a, int b) {
    	if(a>b) return a;
		return b;
	}

	//y座標が一致してるかどうか
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
        wall.clear();
        boss = new Boss(0);
        obstacle = new Attack();
        typedChar = "";
        time = 0;
        deltaTime = 0;
        score = 0;
        throughCount = 0;
        undamagedCount = 0;
        undamagedCountBoss = 0;
        wallCount = 0;
        viewState = 0;
        setHit(false);
        setCleared(false);
        continued = false;
        setTypingObstacle(false);
        setTypingBossAtk(false);
        setTypingBoss(false);
        bonus = 0;
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
	
	public Attack getObstacle() {
		return obstacle;
	}

	public boolean isContinued() {
		return continued;
	}

	public void setContinued() {
		this.continued = true;
	}
	
	public int getDeltaTime() {
		return deltaTime;
	}

	public void setDeltaTime(int deltaTime) {
		this.deltaTime = deltaTime;
	}

    public boolean isTypingObstacle() {
		return typingObstacle;
	}

	public void setTypingObstacle(boolean typingObstacle) {
		this.typingObstacle = typingObstacle;
	}
	
	public boolean isTypingBossAtk() {
		return typingBossAtk;
	}

	public void setTypingBossAtk(boolean typingBossAtk) {
		this.typingBossAtk = typingBossAtk;
	}
	
	public boolean isTypingBoss() {
		return typingBoss;
	}

	public void setTypingBoss(boolean typingBoss) {
		this.typingBoss = typingBoss;
	}
}
