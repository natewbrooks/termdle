import java.util.*;

public class Main {	
	public static void main (String[] args) {
		DataParser parser = new DataParser("answers.txt");
		Termdle termdle = new Termdle(parser.GetAnswers());
	}
}

