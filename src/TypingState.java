import java.awt.Graphics;

public class TypingState implements State {

	private Model model;
	private String s;
	private String answer;
	private int count;
	private boolean isTyped;
	private String[] data;
	public static final String BS = "\b";
	
	public TypingState(Model model) {
		super();
		this.model = model;
		s = "";
		Problem problem = new Problem();
		data = problem.makeProblem();
		RandNumGenerator r = RandNumGenerator.getInstance();
		answer = data[r.nextInt(data.length)];
		this.isTyped = false;
		this.count = 0;
	}
	
	public State processTimeElapsed(int msec) {
		
		if(model.elapseTime() > 30) {
			if(model.isTypingObstacle()) {
				model.setTypingObstacle(false);
				model.setHit(false);
				return model.getOldState();
			}
			if(model.isTypingBossAtk()) {
				model.setTypingBossAtk(false);
				model.setHit(false);
				return model.getOldState();
			}
		}
		return this;
	}

	@Override
	public State processKeyTyped(String typed) {
		if(update(typed)) {//タイピングが完了したら
			model.setHit(false);
			model.calcScore();
			if(model.isTypingObstacle()) {
				model.getObstacle().reach();
				model.setTypingObstacle(false);
			}
			if(model.isTypingBossAtk()) {
				model.getBoss().getAttack().reach();
				model.setTypingBossAtk(false);
			}
			if(model.isTypingBoss()) {
				if(model.elapseTime() <= 30) {
					model.getBoss().damagedBoss();
				}
				model.setTypingBoss(false);
			}
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
		model.getView().drawTyping(g, answer, s);
	}

	public boolean update(String event) {

		switch (event) {
        case "ENTER":
            break;
        case "UP":
            break;
        case "DOWN":
            break;
        case "LEFT":
            break;
        case "RIGHT":
            break;
        case "ESC":
            break;
        default:
        	if(event.equals(BS) && !s.isEmpty())
        		s = s.substring(0, getLength()-1);
        	else if(!event.equals(BS))
        		s += event;
        	break;
		}
		return s.equals(answer);
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


