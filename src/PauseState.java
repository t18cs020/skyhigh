import java.awt.Graphics;

public class PauseState implements State {
	private Model model;
	private View view;
	private int cursor;
	
	public PauseState(Model model) {
		super();
		this.model = model;
		view = model.getView();
		cursor = 0;
	}
	
	@Override
	public State processTimeElapsed(int msec) {
		// TODO 自動生成されたメソッド・スタブ
		return this;
	}

	@Override
	public State processKeyTyped(String typed) {
		switch(typed) {
		case "ESC":
			return model.getOldState();			
		case "ENTER" :
			switch (cursor) {
			case 0:
				return model.getOldState();
			case 1:
				return new TitleState(model);
			default :
				break;
			}
			break;
			
		case "UP" :
			if(cursor > 0) {
				cursor--;
			}

			break;
			
		case "DOWN" :
			if(cursor < 1) {
				cursor++;
			}
			break;	

		default :
			break;
		}
		return this;	
	}

	@Override
	public State processMousePressed(int x, int y) {
		if (x >= 200 && x <= 440 && y >= 170 && y <= 200) {
			return model.getOldState();
		}
		else if (x >= 200 && x <= 440 && y >= 370 && y <= 400) {
			return new TitleState(model);
		}
		return this;
	}

	@Override
	public void paintComponent(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		view.drawPause(g, cursor);
	}

}
