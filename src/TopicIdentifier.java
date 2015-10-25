import java.io.File;
import java.io.IOException;
import java.util.List;

import in.codehub.document.Document;
import in.codehub.paperparser.Paper;
import in.codehub.paperparser.PaperParser;
import in.codehub.pdfreader.PdfReader;

public class TopicIdentifier {
	
	String path ; 
	boolean file_folder ;
	
	public TopicIdentifier(String file_path, boolean file_folder) {
		this.path = file_path ; 
		this.file_folder = file_folder ;
	}
	
	public String getTopic() throws IOException{
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
			return sb.toString();
		}
	}

	private String getTopicFromString(String paper_abstract) {
		PreProcessor preProcessor = new PreProcessor(paper_abstract);
		List<String> abstract_terms = preProcessor.getProcessedText();
		System.out.println(abstract_terms);
		
		return null;
	}
}
