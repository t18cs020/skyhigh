import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Ranking {

	private List<Integer> ranking;
	
	public Ranking() {
		ranking = new LinkedList<Integer>();
	}
	
	public List<Integer> read(int PlayerScore) {

		ranking.add(PlayerScore);
		File file = new File("score.txt");
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			String str;
			while((str = br.readLine()) != null) {
				ranking.add(Integer.parseInt(str));
			}
			//スコアをソートする
			Collections.sort(ranking);
			Collections.reverse(ranking);	
			ranking = ranking.subList(0,3);
			br.close();
		}catch(FileNotFoundException e){
            return ranking;
        }catch(IOException e){
            return ranking;
		}
		return ranking;
	}
	
	public void write() {
		File file = new File("score.txt");
		//ランキングファイルに書き込む
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)))){
			for(int s: ranking) {
				out.println(s);
			}
			out.close();
		}catch(FileNotFoundException e){
			return;
		}catch(IOException e){
			return;
		}
	}
}