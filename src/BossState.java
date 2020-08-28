import java.awt.Graphics;

public class BossState implements State {

	private Model model;
	private View view;
	
	public BossState(Model model) {
		super();
		this.model = model;
		view = model.getView();
	}
	
	@Override
	public State processTimeElapsed(int msec) {
		return this;
	}

	@Override
	public State processKeyTyped(String typed) {
		switch(typed) {
		case "ESC":
			return new TitleState(model);
			
		case "b":
			return model.getOldState();
		}
		return this;
	}

	@Override
	public State processMousePressed() {
		// TODO 自動生成されたメソッド・スタブ
		return this;
	}

	@Override
	public void paintComponent(Graphics g) {
        view.clear(g);
		g.drawImage(view.getBossComing(), 0 , 0, view);
	}

}
