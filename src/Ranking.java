import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Ranking {

	private List<Integer> ranking;
	PrintWriter out;
	
	public Ranking() {
		ranking = new LinkedList<Integer>();
	}
	
	public List<Integer> addRanking(int PlayerScore) {

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
			//ランキングファイルに書き込む
			out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			for(int s: ranking) {
				out.println(s);
			}
			br.close();
			
		}catch(FileNotFoundException e){
            return ranking;
        }catch(IOException e){
            return ranking;
		}finally {
			out.close();
		}
		
		return ranking;
	}
}