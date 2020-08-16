import java.util.LinkedList;

public class Model {

    private View view;
    private Controller controller;
    // Sample instance variables:
    private int time;
    private String typedChar = "";
    
    private Airplane ap;
    private LinkedList <Wall> wall;

	public Model() {
		time = 0;
        view = new View(this);
        controller = new Controller(this);
        ap = new Airplane(this);
        wall = new LinkedList<Wall>();
    }

    public synchronized void processTimeElapsed(int msec) {
        time++;
        if(time % 10 == 0)
        	makeWall();
        update();
        view.repaint();
    }

	public synchronized void processKeyTyped(String typed) {
        typedChar = typed;
        ap.move(typedChar);
        view.repaint();
        int a = 2;
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
    	}
	}
    
	private void update() {
        ap.update();
        Attack atk = ap.getAttack();
        int aty = 0;
        if(atk.isExist()) {
			aty = atk.getAty();
        }
        for(int i = 0; i < wall.size() ; i++) {
        	Wall w = wall.get(i);
        	w.updateWall();
        	if(!w.getExist()) {
				wall.remove(i);
				i--;
        	}
        	
			if(atk.isExist()) {
				int j = 0;
				while(j < Attack.SIZE) {
					if(aty + j < w.getWy() || aty + j >= w.getWy() + Wall.HEIGHT) {//攻撃と壁のブロックのy座標が同じとき
						//何もしない
					}
					else {
						if(!w.hitWall(atk.getAtx()) ) {
							wall.remove(i);
							atk.reachWall();
							i--;
							break;
						}
					}
					j++;
				}
			}
        }
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

    public String getTypedChar() {
        return typedChar;
    }
    
    public Airplane getAirplane() {
    	return ap;
    }
    
    public LinkedList<Wall> getWall() {
		return wall;
	}

}
