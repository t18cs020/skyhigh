import java.util.LinkedList;

public class Model {

    private View view;
    private Controller controller;
    // Sample instance variables:
    private int time;
    private String typedChar = "";
    
    private Airplane ap;
    private LinkedList <Wall> wall;
    
    private Boss boss;
    
    private int undamaged_count;
	private int undamaged_count_boss;
    private int score;
	private int throughCount;//壁を通過したかどうかを確認する
    private final static int QUOTA = 1000;//ボスの登場するスコアのノルマ

	public Model() {
		time = 0;
        view = new View(this);
        controller = new Controller(this);
        ap = new Airplane(this);
        wall = new LinkedList<Wall>();
        boss = new Boss(this);
        undamaged_count = 0;
        undamaged_count_boss = 0;
        score = 0;
    }

    public synchronized void processTimeElapsed(int msec) {
        time++;
        if(time % 10 == 0) {
        	makeWall();
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
    	
       update();
       //ボスがいないかつ一定スコアに達したとき,ボスを登場させる
    	if(score >= QUOTA  && (!boss.isBossExist())){
    		boss.setBossExist(true);
    	}
    	
        view.repaint();
    }

	public synchronized void processKeyTyped(String typed) {
        typedChar = typed;
        ap.move(typedChar);
        view.repaint();
    }

    public synchronized void processMousePressed() {
        view.playBombSound();
    	 ap.shot();
        view.repaint();
    }

    public void start() {
        controller.start();
    }
    
    private void makeWall() {
    	if(wall.isEmpty()) {
			for(int i = 0; i * Wall.HEIGHT < Game.WIN_HEIGHT ; i++) {
				wall.add(new Wall(i * Wall.HEIGHT , this));
			}
			throughCount = 0;
    	}
	}
    
	private void update() {
        ap.update();
        boss.update();
        Attack atk = ap.getAttack();
        int aty = 0;
        if(atk.isExist()) {
			aty = atk.getAty();
        }
        
        //ボスに攻撃が当たったかどうかを判別する
        if(boss.isBossExist())
        	hit(atk);
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
	        	if((!ap.isUndameged_time()) && equalY(ap.getApy(), w.getWy(),Attack.SIZE) && !w.isThrough()) {
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
				if(equalY(aty, w.getWy(),Attack.SIZE) &&ap.getApx() <= w.getWx()) {
					if(!w.hitWall(atk.getAtx()) ) {
						wall.remove(i);
						atk.reachWall();
						i--;
					}
				}
			}
        }
	}
	
    private void hit(Attack atk) {
        if(equalY(ap.getAttack().getAty(), boss.getBy(), Boss.BOSS_SIZE) 
        		&& atk.getAtx() >= boss.getBx() && (!boss.isUndameged_time())) {
        	boss.damagedBoss();
        }
	}

	private boolean equalY(int y, int wy ,int distance) {
    	if((y + (distance/2) >= wy && y + (distance/2) < wy + Wall.HEIGHT)
    			|| (y >= wy && y < wy + Wall.HEIGHT)
    			|| (y + distance >= wy && y + distance < wy + Wall.HEIGHT))
    		return true;
		return false;
	}

	public View getView() {
        return view;
    }

    public Controller getController() {
        return controller;
    }

    public int getTime() {
        return time;
    }
    
    public int getScore() {
    	return score;
    }

    public String getTypedChar() {
        return typedChar;
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

}
