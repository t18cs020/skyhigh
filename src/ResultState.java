import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ResultState implements State {

	private Model model;
	
	public ResultState(Model model) {
		super();
		this.model = model;
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
			
		case "ENTER":
			//クリアしていないとき,プレイヤーのライフを全回復してゲームに復帰
			if(!model.isCleared()) {
				model.getAirplane().reset();
				return model.getOldState();
			}
			break;
			
		default :
			break;
		}
		return this;
	}

	@Override
	public State processMousePressed() {
		return this;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(101, 101, 598, 398);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		g.setColor(Color.BLACK);
		g.drawRect(100, 100, 600, 400);

		if(model.isCleared()) {
			g.drawString("ゲームクリア！" ,200 , 150);
		}
		else {
			g.drawString("ゲームオーバー" ,200 , 150);
			g.drawString("Enterキーでゲームを再開" , 200, 400);
		}
		g.drawString("Escキーでタイトルに戻る" , 200, 450);
	}

}
