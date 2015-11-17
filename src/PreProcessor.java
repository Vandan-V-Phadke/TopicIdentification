import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
public class PreProcessor {
	
	private String text;
	HashSet<String> stopwords ;
	List<String> terms ; 
	
	public PreProcessor(String text){
		this.text = text.toLowerCase() ;
		stopwords = StopWords.getStopWordsFromFile("SmartStopList.txt");
		terms = new ArrayList<String>();
	}
	
	public List<String> getProcessedText(){
		removePunctuation();
		removeStopword();
		return terms;
	}
	
	private void removePunctuation(){
		text = text.replaceAll("\\.\\s*|\\?\\s*|\\s*,\\s*|\\s*!\\s*", " ");
	}
	
	private void removeStopword(){
		StringBuilder sb = new StringBuilder();
		String[] textArr = text.trim().split(" ");
		for(String token : textArr){
			if(stopwords.contains(token)){
				if(!sb.toString().equals(" ") && !sb.toString().equals(""))
					terms.add(sb.toString().trim());
				sb = new StringBuilder();
			}
			else{
				sb.append(token + " ");
			}
		}
	}
}