import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TitleState implements State{
	private Model model;
	private View view;
	private int cursor;
	
	public TitleState(Model model) {
		super();
		this.model = model;
		view = model.getView();
		cursor = 0;
	}
	
	public State processKeyTyped(String typed) {
		
		switch (typed) {
		case "ENTER" :
			switch (cursor) {
			case 0:
				model.newGame();
				return new PlayingState(model);
			case 1:
				return new HelpState(model);
			case 2:
				return new RankingState(model);
			case 3:
				System.exit(0);
			}
			break;
			
		case "UP" :
			if(cursor > 0)
				cursor--;
			break;
			
		case "DOWN" :
			if(cursor < 3)
				cursor++;
			break;

		}
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
		    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		    g.setColor(Color.BLACK);
		    g.drawString("Sky High", 275, 150);
		    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		    g.drawString("ゲームスタート", 330, 300);
		    g.drawString("ヘルプ", 330, 350);
		    g.drawString("ランキング", 330, 400);
		    g.drawString("ゲーム終了", 330, 450);
		    g.drawString("(Enterキーで決定)", 330, 500);
		    g.drawImage(view.imageAp, 280, 280 + 50*cursor , view);
		    
		}
}

