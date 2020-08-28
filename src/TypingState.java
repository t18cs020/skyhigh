import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedList;

public class TypingState implements State {

	private Model model;
	private View view;
	private String s;
	private String answer;
	private int count;
	private boolean isTyped;
	private String[] data = {"apple", "gomi", "kasu", "unko", "shine"};
	public final String BS = "\b";
	
	public TypingState(Model model) {
		super();
		this.model = model;
		view = model.getView();
		s = "";
		RandNumGenerator r = RandNumGenerator.getInstance();
		answer = data[r.nextInt(5)];
		this.isTyped = false;
		this.count = 0;
	}
	
	public State processTimeElapsed(int msec) {
		return this;
	}

	@Override
	public State processKeyTyped(String typed) {
		if(update(typed)) {
			model.setHit(false);
			return model.getOldState();
		}
		return this;
	}

	@Override
	public State processMousePressed() {

		return this;
	}

	@Override
	public void paintComponent(Graphics g) {
        view.clear(g);
        Airplane ap = model.getAirplane();
        LinkedList <Wall> wall = model.getWall();
        Boss boss = model.getBoss();
        Attack bossatk = boss.getAttack();//ボスの攻撃

	    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
	    g.setColor(Color.WHITE);
	
	    
	    Image imageback = Toolkit.getDefaultToolkit().getImage(getClass().getResource("robot.png"));
	    Image imageboss = Toolkit.getDefaultToolkit().getImage(getClass().getResource("robot.png"));
	    Image imagedboss = Toolkit.getDefaultToolkit().getImage(getClass().getResource("robot.png"));
	    //レベルによって画像を変える
	    switch (model.getViewState()) {
	    case 0:
	    	imageback = view.imageBack;
	    	imageboss = view.imageBoss;
	    	imagedboss = view.imageDBoss;
	    	break;
	    case 1:
	    	imageback = view.imageBack2;
	    	imageboss = view.imageBoss2;
	    	imagedboss = view.imageDBoss2;
	    	break;	    
	    case 2:
	    	imageback = view.imageBack3;
	    	imageboss = view.imageBoss3;
	    	imagedboss = view.imageDBoss3;
	    	break;
	    }
	    
	    //背景
	    g.drawImage(imageback, 0, 0, view);
	    //プレイヤー
	    view.drawPlayer(g, ap);
		//ボス,攻撃
		if(boss.isBossExist()) {
	        if(boss.isUndameged_time()) {//ボスの無敵時間
	        	g.drawImage(imagedboss, boss.getBx(), boss.getBy(), view);
	        }
	        else {
	        	g.drawImage(imageboss, boss.getBx(), boss.getBy(), view);
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
	    g.drawString("wall: " + model.getWallCount() , 10, 60);
	    
	    g.fillRect(100, 100, 600, 60);
	    g.fillRect(100, 300, 600, 60);
	    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
	    g.setColor(Color.BLACK);
	    g.drawString(answer , 110, 150);
	    g.drawString(s , 110, 350);
	    
	}

	public boolean update(String event) {

		if(event.equals(BS) && !s.isEmpty())
			s = s.substring(0, getLength()-1);
		else if(event.equals(BS))
			;
		else
			s += event;
		
		if(s.equals(answer)) {
			return true;
		}
		return false;
	}
	
	public int getLength() {
		return getS().length();
	}

	public boolean getIsTyped() {
		return isTyped;
	}

	public String getS() {
		return s;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int i) {
		count = i;
	}

}

