import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PreProcessor {
	
	private String text;
	private HashSet<String> stopwords ;
	private List<String> terms ;
	private boolean isStop ;
	private HashSet<String> impWords ;
	
	public PreProcessor(String text, boolean isStop){
		this.text = text.toLowerCase();
		terms = new ArrayList<String>();
		stopwords = StopWords.getStopWordsFromFile("SmartStopList.txt");
		impWords = StopWords.getImpWordsFromCCS();
	}
	
	public List<String> getProcessedText(){
		removePunctuation();
		if(isStop)
			removeStopwordFromFile();
		else
			removeStopWordFromCCS();
		return terms;
	}
	
	private void removePunctuation(){
		text = text.replaceAll("\\.\\s*|\\?\\s*|\\s*,\\s*|\\s*!\\s*", " ");
	}
	
	private void removeStopwordFromFile(){
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
	
	private void removeStopWordFromCCS() {
		StringBuilder sb = new StringBuilder();
		String[] textArr = text.trim().split(" ");
		for(String token : textArr){
			if(impWords.contains(token)){
				if(stopwords.contains(token)){
					if(!sb.toString().equals(" ") && !sb.toString().equals(""))
						terms.add(sb.toString().trim());
					sb = new StringBuilder();
				}
				else
					sb.append(token + " ");
			}
			else{
				if(!sb.toString().equals(" ") && !sb.toString().equals(""))
					terms.add(sb.toString().trim());
				sb = new StringBuilder();
			}
		}
	}
}