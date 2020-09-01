import java.awt.Graphics;

public class TypingState implements State {

	private Model model;
	private String s;
	private String answer;
	private int count;
	private boolean isTyped;
	private String[] data = {"apple", "gomi", "kasu", "unko", "shine"};
	public static final String BS = "\b";
	
	public TypingState(Model model) {
		super();
		this.model = model;
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
		model.getView().drawTyping(g, answer, s);
	}

	public boolean update(String event) {

		if(event.equals(BS) && !s.isEmpty())
			s = s.substring(0, getLength()-1);
		else if(!event.equals(BS))
			s += event;
		
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


