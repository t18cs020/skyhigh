import java.awt.Graphics;
import java.util.List;

public class TitleState implements State{//タイトル画面を表示中のstate
	private Model model;
	private View view;
	private int cursor;//カーソル
	private int page;//ページ数
	private int help;//ヘルプを表示しているか
	private List <Integer> ranking;//ランキングのリスト
	private List <Wall> wall;//背景に表示している壁

	public TitleState(Model model) {
		super();
		this.model = model;
		view = model.getView();
		cursor = 0;
		page = 0;
		help = 0;
		Ranking r = new Ranking();
		ranking = r.read(0);
		model.makeWall();
		wall = model.getWall();
		view.stopBgm();
	}
	/*入力された時の処理*/
	public State processKeyTyped(String typed) {

		switch (typed) {
		case "ENTER" ://ENTER（決定）を押された時の処理
			switch (cursor) {
			case 0://ゲーム開始
				model.newGame();
				return new PlayingState(model);
			case 1://ヘルプ画面へ
				help = (help+1) % 2;
				break;
			case 2://ランキング画面へ
				help = (help+1) % 2;
				break;
			case 3://終了する
				System.exit(0);
				break;
			default :
				break;
			}
			break;

		case "UP" :
			if(cursor > 0 && help == 0) {
				cursor--;
			}

			break;

		case "DOWN" :
			if(cursor < 3 && help == 0) {
				cursor++;
			}
			break;	

		case "LEFT" :
			if(cursor == 1 && help == 1 && page > 0) {
				page--;
			}
			break;
			
		case "RIGHT" :
			if(cursor == 1 && help == 1 && page < 3) {
				page++;
			}
			break;
			
		default :
			break;
		}
		return this;	
	}
	// タイトル状態の時間経過イベントを処理するメソッド
	public State processTimeElapsed(int msec) { 
		for(int i = 0; i < wall.size(); i++) {
			wall.get(i).updateWall(0);
			if(!wall.get(i).getExist()) {
				wall.remove(i);
			}
		}
		if(wall.isEmpty()) {
			model.makeWall();
		}
		return this; 
	}

	// タイトル状態のマウスクリックイベントを処理するメソッド
	public State processMousePressed(int x, int y) {
		if(help == 0) {
			if (x >= 330 && x <= 505 && y >= 275 && y <= 325) {
				model.newGame();
				return new PlayingState(model);
			}
			else if (x >= 330 && x <= 405 && y >= 325 && y <= 375) {
				cursor = 1;
				help = 1;
			}
			else if (x >= 330 && x <= 455 && y >= 375 && y <= 425) {
				cursor = 2;
				help = 1;
			}
			else if (x >= 330 && x <= 455 && y >= 425 && y <= 475)
				System.exit(0);
		}
		else if(help == 1 && cursor == 1) {
			if (x >= 240 && x <= 300 && y >= 291 && y <= 330) {
				model.setBgmVolume(0);
			}
			else if (x >= 301 && x <= 361 && y >= 291 && y <= 330) {
				model.setBgmVolume(1);
			}
			else if (x >= 362 && x <= 422 && y >= 291 && y <= 330) {
				model.setBgmVolume(2);
			}
			else if (x >= 423 && x <= 483 && y >= 291 && y <= 330) {
				model.setBgmVolume(3);
			}
			else if (x >= 240 && x <= 300 && y >= 351 && y <= 390) {
				model.setBombVolume(0);
			}
			else if (x >= 301 && x <= 361 && y >= 351 && y <= 390) {
				model.setBombVolume(1);
			}
			else if (x >= 362 && x <= 422 && y >= 351 && y <= 390) {
				model.setBombVolume(2);
			}
			else if (x >= 423 && x <= 483 && y >= 351 && y <= 390)
				model.setBombVolume(3);
			else
				help = 0;
		}
		else
			help = 0;
		return this;
	}

	public void paintComponent(Graphics g) {
		g.drawImage(view.getImageBack(), 0, 0, view);
		view.drawWall(g, wall);
		if (help == 1) {
			//画面遷移
			switch (cursor) {
			//ヘルプ画面へ
			case 1:
				view.drawHelp(g, wall, page);
				break;
			//ランキング画面へ
			case 2:
				view.drawRanking(g, ranking, wall);
				break;
			default :
				break;
			}
		}
		else {
			view.drawTitle(g, cursor, wall);
		}
	}
}

