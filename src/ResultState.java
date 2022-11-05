import java.awt.Graphics;
import java.util.List;

public class ResultState implements State {//リザルトを表示する画面のstate

	private Model model;
	private List <Integer> ranking;

	public ResultState(Model model){
		super();
		this.model = model;
		Ranking r = new Ranking();
		if(model.isContinued()) {
			ranking = r.read(0);
		}
		else {
			ranking = r.read(model.getScore());
		}
		r.write();
	}

	@Override
	public State processTimeElapsed(int msec) {
		return this;
	}

	@Override
	public State processKeyTyped(String typed) {
		switch(typed) {
		case "ESC":
			model.getView().stopBgm();
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
	public State processMousePressed(int x, int y) {
		return this;
	}

	@Override
	public void paintComponent(Graphics g) {
		model.getView().drawResult(g, ranking);
	}
}
