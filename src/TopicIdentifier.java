import in.codehub.document.Document;
import in.codehub.paperparser.Paper;
import in.codehub.paperparser.PaperParser;
import in.codehub.pdfreader.PdfReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TopicIdentifier {
	
	String path ; 
	boolean file_folder ;
	HashMap<String, HashSet<String>> concepts ;
	XMLParser1 parser ;
	
	public TopicIdentifier(String file_path, boolean file_folder) {
		this.path = file_path ; 
		this.file_folder = file_folder ;
		parser = new XMLParser1("acmccs.xml");
		concepts = parser.parseDocument();
	}
	
	public HashMap<String, Integer> getTopic() throws IOException{
		//Given path is a file
		if(!file_folder){
			Document document = PdfReader.getInstance().read(path);
			Paper paper = PaperParser.getInstance().parse(document);
			String paper_abstract = paper.getAbstract();
			return getTopicFromString(paper_abstract);
		}
		//Given file path is a folder
		else{
			File paper_folder = new File(path);
			File[] papers = paper_folder.listFiles();
			StringBuilder sb = new StringBuilder();
			for(File paper: papers){
				Document document = PdfReader.getInstance().read(paper.getAbsolutePath());
				Paper p = PaperParser.getInstance().parse(document);
				String paper_abstract = p.getAbstract();
				sb.append(getTopicFromString(paper_abstract));
			}
			return null;
		}
	}

	private HashMap<String, Integer> getTopicFromString(String paper_abstract) {
		PreProcessor preProcessor = new PreProcessor(paper_abstract);
		List<String> abstract_terms = preProcessor.getProcessedText();
		System.out.println(abstract_terms);
		HashMap<String, Integer> weights = new HashMap<String, Integer>();
		int maxWeight = 1 ; 
		for(String abstract_term: abstract_terms){
			String[] tokens = abstract_term.split(" ");
			for(String token: tokens){
				for(String concept_name: concepts.keySet()){
					HashSet<String> labels = concepts.get(concept_name);
					if(labels.contains(token)){
						if(weights.containsKey(concept_name)){
							int weight = weights.get(concept_name);
							weights.put(concept_name, ++weight);
							if(weight > maxWeight){
								maxWeight = weight ;
							}
						}
						else{
							weights.put(concept_name, 1);
						}
					}
				}
			}
		}
		System.out.println(maxWeight);
		for(String topics: weights.keySet()){
			if(weights.get(topics) == maxWeight || weights.get(topics) == maxWeight - 1)
				System.out.println(topics + " " + weights.get(topics));
		}
		return weights;
	}

	/*private List<String> stemAbstractTerms(List<String> abstract_terms) {
		PorterStemmer stemmer = new PorterStemmer();
		List<String> stemmed_terms = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for(String term: abstract_terms){
			String[] single_words = term.split(" ");
			for(int i = 0 ; i < single_words.length ; ++i){
				String word = single_words[i];
				stemmer.add(word.toCharArray(), word.length());
				stemmer.stem();
				sb.append(stemmer.toString() + " ");
			}
			stemmed_terms.add(sb.toString().trim());
			sb = new StringBuilder();
		}
		return stemmed_terms;
	}*/
}
