import java.awt.Graphics;
import java.util.List;

public class TitleState implements State{
	private Model model;
	private View view;
	private int cursor;
	private int help;
	private List <Integer> ranking;
	
	public TitleState(Model model) {
		super();
		this.model = model;
		view = model.getView();
		cursor = 0;
		help = 0;
		Ranking r = new Ranking();
		ranking = r.read(0);
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
				break;
			default :
				break;
			}
			break;
			
		case "UP" :
			if(cursor > 0 && help == 0)
				cursor--;
			break;
			
		case "DOWN" :
			if(cursor < 3 && help == 0)
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

			if (help == 1) {
			//画面遷移
				switch (cursor) {
				//ヘルプ画面へ
				case 1:
					view.drawHelp(g);
					break;
				//ランキング画面へ
				case 2:
					view.drawRanking(g, ranking);
					break;
				default :
					break;
				}
			}
			else {
				view.drawTitle(g, cursor);
			}
		}
}

