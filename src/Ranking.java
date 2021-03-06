import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Ranking {

	private List<Integer> score;
	
	public Ranking() {
		score = new LinkedList<>();
	}
	
	public List<Integer> read(int playerScore) {

		score.add(playerScore);
		File file = new File("score.txt");
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			String str;
			while((str = br.readLine()) != null) {
				score.add(Integer.parseInt(str));
			}
			//スコアをソートする
			Collections.sort(score);
			Collections.reverse(score);	
			score = score.subList(0,3);
		}catch(IOException e){
            return score;
		}
		return score;
	}
	
	public void write() {
		File file = new File("score.txt");
		//ランキングファイルに書き込む
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)))){
			for(int s: score) {
				out.println(s);
			}
		}catch(IOException e){
			
		}
	}
}