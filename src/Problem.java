import java.io.*;

public class Problem {

	private String[] problem;
	
	public Problem() {
		problem = new String[14];
	}
	
	public String[] makeProblem() {

		File file = new File("problems.txt");
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			String str;
			int i = 0;
			while((str = br.readLine()) != null) {
				problem[i] = str;
				i++;
			}
			
		}catch(FileNotFoundException e){
            return problem;
        }catch(IOException e){
            return problem;
		}
		
		return problem;
	}
}