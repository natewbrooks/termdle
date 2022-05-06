public class Main {
	public static void main (String[] args) {
		DataParser answers = new DataParser("answers.txt");
		DataParser acceptedWords = new DataParser("accepted words.txt");

		new Termdle(answers.GetList(), acceptedWords.GetList());
	}
}

