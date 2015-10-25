import java.io.IOException;

public class Main {
	
	public static void main(String[] args) {
		TopicIdentifier topicIdentifier = new TopicIdentifier("test6.pdf", false);
		try {
			System.out.println(topicIdentifier.getTopic());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
