import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TitleState implements State{
	private Model model;
	private View view;
	
	public TitleState(Model model) {
		super();
		this.model = model;
		view = model.getView();
	}
	
	public State processKeyTyped(String typed) {
		if (typed.equals(" ")) 
			return new PlayingState(model);
		return this;	
	}
		// タイトル状態の時間経過イベントを処理するメソッド
		public State processTimeElapsed(int msec) { return this; }
		// タイトル状態のマウスクリックイベントを処理するメソッド
		public State processMousePressed() { return this; }
		// タイトル状態を描画するメソッド
		public void paintComponent(Graphics g) {
		    //背景
		    g.drawImage(view.imageBack, 0, 0, view);
		    //タイトル
		    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		    g.setColor(Color.BLACK);
			g.drawString("TITLE", 300, 100);
			g.drawString("spaceキーでスタート", 200, 200);
		}
}

