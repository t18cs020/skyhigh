import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HelpState implements State {
	private Model model;
	private View view;
	
	public HelpState(Model model) {
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
		if(typed.equals("ESC"))
			return new TitleState(model);
		return this;
	}

	@Override
	public State processMousePressed() {
		return this;
	}

	@Override
	public void paintComponent(Graphics g) {
	    //背景
	    g.drawImage(view.imageBack, 0, 0, view);
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
	    g.drawString("Escキーでタイトルに戻る", 100, 560);
	}

}
