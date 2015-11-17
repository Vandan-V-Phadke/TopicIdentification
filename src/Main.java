import java.io.IOException;


/**
 * Topic Identification Algorithm
 * @author Vandan Phadke
 * Currently stemming is not done
 */

public class Main {
	
	public static void main(String[] args) {
		TopicIdentifier topicIdentifier = new TopicIdentifier("test2.pdf", false);
		try {
			/*System.out.println(*/topicIdentifier.getTopic();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
