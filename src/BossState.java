import java.awt.Graphics;

public class BossState implements State {//"ボスが来た"画面を表示するstate

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
		default :
			return this;
		}
	}

	@Override
	public State processMousePressed(int x, int y) {
		return this;
	}

	@Override
	public void paintComponent(Graphics g) {
        view.clear(g);
		g.drawImage(view.getBossComing(), 0 , 0, view);
	}

}
