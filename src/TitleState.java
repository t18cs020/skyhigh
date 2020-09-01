import java.awt.Color;
import java.awt.Font;
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
			
			view.clear(g);
			switch (help) {
			case 0:
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
				g.drawImage(view.getImageAp(), 280, 280 + 50*cursor , view);
				break;
				
			case 1:
				//本文
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
				g.setColor(Color.BLACK);
				g.drawString("操作方法:", 100, 50);
				g.drawString("Wキー:上へ移動", 250, 50);
				g.drawString("Sキー:下へ移動", 250, 80);
				g.drawString("Aキー:左へ移動", 250, 110);
				g.drawString("Dキー:右へ移動", 250, 140);
				g.drawString("マウスをクリックして攻撃", 250, 170);
				g.drawString("ルール:", 100, 210);
				g.drawString("一定数壁を超えたらボスが出現します", 250, 210);
				g.drawString("攻撃によって壁を壊したりボスに", 250, 250);
				g.drawString("ダメージを与えることがができます", 250, 290);
				g.drawString("ボスの体力が0になったらレベルが上昇します", 250, 330);
				g.drawString("プレイヤーの体力が0になったら", 250, 370);
				g.drawString("ゲームオーバーです", 250, 400);
				g.drawString("ゲーム中にbキーを押すとボスが来た画面に遷移します", 250, 440);
				g.drawString("(再度bキーを押すとゲームを再開します)", 250, 470);
				g.drawString("ゲーム中,ボスが来た画面でEscを押すと", 250, 510);
				g.drawString("タイトル画面に戻ります", 250, 530);
				g.drawString("Enterキーでタイトルに戻る", 100, 560);
				break;
			default :
				break;
			}
		}
}

