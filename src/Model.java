import java.util.LinkedList;
import java.util.List;

public class Model {

    private View view;//画面の描画
    private Controller controller;
    private State state;//現在のstate
    private State oldState;//過去のstate
    private int viewState;//表示しているstateを管理する数字
    // Sample instance variables:
    private Airplane ap;//自機
    private List<Wall> wall;//障害物（壁）
    private String typedChar;//入力した文字
    private Boss boss;//ボス
    private Attack obstacle;//障害物（隕石）
    private static final int OBSTACLE_SPEED = -7;
    
    private static final int COOLTIME = 60;//無敵時間   
    private int undamagedCount;//無敵時間のカウント
    private int undamagedCountBoss;//ボスの無敵時間のカウント
    private int time;
    private int deltaTime;
    private int score;//プレイヤーのスコア
    private int throughCount;//壁を通過したかどうかを確認する
    private static final int[] QUOTA = {3,5,7};//ボスの登場するスコアのノルマ
    private int wallCount;
    private boolean hit;
    private boolean cleared;
    private boolean continued;

    private int level;
	private static final int BONUS_SCORE = 500;
    private int bonus;
    private int bgmVolume;//bgmの音量
    private int bombVolume;//seの音量
    
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
        bonus = 0;
        level = 0;
        setViewState(0);
        bgmVolume = 3;
        bombVolume = 3;
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

    public synchronized void processMousePressed(int x, int y) {
    	state = state.processMousePressed(x,y);
        view.repaint();
    }

    public void start() {
        controller.start();
    }
    //壁を生成する
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
    //障害物を生成する
    public void makeObstacle() {
    	if(!obstacle.isExist() && level != 0) {//障害物が存在しないかつ,レベルが1でない時
			RandNumGenerator r = RandNumGenerator.getInstance();
			obstacle.shotAttack(Game.WIN_WIDTH, r.nextInt(Game.WIN_HEIGHT));
    	}
	}
    //ゲーム内容の更新をする
    public void update() {
    	ap.update();
    	boss.update(level);
    	obstacle.updateAttack(OBSTACLE_SPEED);
    	obstacle.isOutOfScreen();
    	
    	Attack atk = ap.getAttack();
    	LinkedList <Attack> bossAtk = boss.getAttack();
    	//ボスに攻撃が当たったかどうかを判別する
    	hitAttack(atk);

    	for(int i = 0; i < Boss.ATTACKS ; i++) {
    		hitAp(bossAtk.get(i));
    	}
    	hitAp(obstacle);
    	hitObstacle(atk, obstacle);
    	
    	//壁の処理
    	for(int i = wall.size() -1; i >= 0 ; i--) {
    		Wall w = wall.get(i);
    		w.updateWall(level);
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
    			calcScore();
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
	private void hitAttack(Attack atk) {
    	if(boss.isBossExist() && hitBoss(atk, level) && atk.isExist()) {
    		hit = true;
    		boss.damagedBoss();
    		atk.reach();
    		calcScore();
    	}
    }
	//自機とボスの攻撃の衝突判定
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
    		atk.reach();
    		atk2.reach();
    		calcScore();
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
	//自機の無敵時間の計算
	public void damagedAp() {
    	if(ap.isApUndamegedTime()) {
    		undamagedCount++;
    		if(undamagedCount > COOLTIME) {
    			undamagedCount = 0;
    			ap.setApUndamegedTime(false);
    		}
    	}
	}
	//敵の無敵時間の計算
	public void damagedBoss() {
    	if(boss.isUndamegedTime()) {
    		undamagedCountBoss++;
    		if(undamagedCountBoss > COOLTIME) {
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
	/*もう一度ゲームを始めた時にリセットする*/
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
        bonus = 0;
        level = 0;
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
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getBgmVolume() {
		return bgmVolume;
	}

	public void setBgmVolume(int bgmVolume) {
		this.bgmVolume = bgmVolume;
	}
	
	public int getBombVolume() {
		return bombVolume;
	}

	public void setBombVolume(int bombVolume) {
		this.bombVolume = bombVolume;
	}
}
