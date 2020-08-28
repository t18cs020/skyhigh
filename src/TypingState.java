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
		
	    g.setColor(Color.WHITE);

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


