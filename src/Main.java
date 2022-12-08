import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            String input = reader.readLine();
            Analyzer analyzer = new Analyzer(input);
            analyzer.analysisResult();
            for(int i = 0; i < analyzer.allLexemes.size(); i++){
                System.out.println(analyzer.allLexemes.get(i).toString());
            }
        }
    }
}