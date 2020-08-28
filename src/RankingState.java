import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class RankingState implements State {
	private Model model;
	private View view;
	
	public RankingState(Model model) {
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
		if(typed == "ESC")
			return new TitleState(model);
		return this;
	}

	@Override
	public State processMousePressed() {
		return this;
	}

	@Override
	public void paintComponent(Graphics g) {
		view.clear(g);
	    //本文
	    g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
	    g.setColor(Color.WHITE);
	    g.drawString("まだできてないです", 200, 100);
	    g.drawString("Escキーでタイトルに戻る", 200, 550);

	}

}
