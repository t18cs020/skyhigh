import java.awt.Graphics;

public class TitleState implements State{
	private Model model;
	private View view;
	private int cursor;
	private int help;
	
	public TitleState(Model model) {
		super();
		this.model = model;
		view = model.getView();
		cursor = 0;
		help = 0;
	}
	
	public State processKeyTyped(String typed) {
		
		switch (typed) {
		case "ENTER" :
			switch (cursor) {
			case 0:
				model.newGame();
				return new PlayingState(model);
			case 1:
				help = (help+1) % 2;
				break;
			case 2:
				help = (help+1) % 2;
				break;
			case 3:
				System.exit(0);
			default :
				break;
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

		default :
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

			switch (help) {
			case 1:
				switch (cursor) {
				case 1:
					view.drawHelp(g);
					break;
				case 2:
					view.drawRanking(g);
					break;
				default :
					break;
				}
				break;
			default :
				view.drawTitle(g, cursor, help);
				break;
			}
		}
}

