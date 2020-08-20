import java.awt.Graphics;

public interface State {
	
	// 状態に応じて時間経過イベントを処理するメソッド
	public State processTimeElapsed(int msec);
	// 状態に応じてキータイプイベントを処理するメソッド
	public State processKeyTyped(String typed);
	// 状態に応じてマウスクリックイベントを処理するメソッド
	public State processMousePressed();
	// 状態に応じて画面を描画するメソッド
	public void paintComponent(Graphics g);
	
	
}
