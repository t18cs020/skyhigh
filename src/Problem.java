import java.io.*;

public class Problem {

	private String[] exam;
	
	public Problem() {
		exam = new String[30];
	}
	
	public String[] makeProblem() {

		File file = new File("problems.txt");
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			String str;
			int i = 0;
			while((str = br.readLine()) != null) {
				exam[i] = str;
				i++;
			}
			
		}catch(IOException e){
			return exam;
		}
		
		return exam;
	}
}