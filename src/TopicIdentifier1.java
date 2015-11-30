import in.codehub.document.Document;
import in.codehub.paperparser.Paper;
import in.codehub.paperparser.PaperParser;
import in.codehub.pdfreader.PdfReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class TopicIdentifier1 {
	String path ; 
	boolean file_folder ;
	
	
	public TopicIdentifier1(String file_path, boolean file_folder) {
		this.path = file_path ; 
		this.file_folder = file_folder ;
	}
	
	public HashMap<String, Double> getTopic() throws IOException{
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

	private HashMap<String, Double> getTopicFromString(String paper_abstract) {
		PreProcessor preProcessor = new PreProcessor(paper_abstract, false);
		List<String> abstract_terms = preProcessor.getProcessedText();
		HashMap<String, Integer> freq_map = getFreq(abstract_terms);
		HashMap<String, Integer> dist_edge_map = getDistEdges(abstract_terms);
		HashMap<String, Double> word_score_map = new HashMap<String, Double>();
		for(String terms : abstract_terms){
			double val = 0;
			for(String token : terms.split(" ")){
				//this is deg/freq
				val = val + (double)(freq_map.get(token) + dist_edge_map.get(token))/(freq_map.get(token));
			}
			word_score_map.put(terms, val);
		}
		return word_score_map;
	}
	
	private HashMap<String, Integer> getFreq(List<String> terms){
		HashMap<String, Integer> loc_freq_map = new HashMap<String, Integer>();
		for(String term : terms){
			for(String token : term.split(" ")){
				if(loc_freq_map.containsKey(token)){
					int old_val = loc_freq_map.get(token);
					loc_freq_map.put(token, old_val+1);
				}
				else{
					loc_freq_map.put(token , 1);
				}
			}
		}
		
		return loc_freq_map;
	}

	private HashMap<String, Integer> getDistEdges(List<String> terms){
		HashMap<String, Integer> loc_dist_edge_map = new HashMap<String, Integer>();
		for(String term : terms){
			//update all words in list to old_val + list.size()-1
			for(String token : term.split(" ")){
				if(loc_dist_edge_map.containsKey(token)){
					int old_val = loc_dist_edge_map.get(token);
					loc_dist_edge_map.put(token, old_val+(term.split(" ").length -1));
				}
				else{
					loc_dist_edge_map.put(token, term.split(" ").length -1);
				}
			}
		}
		return loc_dist_edge_map;
	}
}
