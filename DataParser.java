import java.io.*;
import java.util.*;

public class DataParser {
	private File parseFile;
	private ArrayList<String> list = new ArrayList<String>();
	
	public DataParser(String fileName) {
		// trys to get the file and catches the error if the file doesn't exist
		try {
			this.parseFile = new File(fileName);
			Scanner scan = new Scanner(this.parseFile);
			while(scan.hasNextLine()) {
				String data = scan.nextLine();
				list.add(data);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file " + fileName + " doesn't exist!");
		}
	}

	public ArrayList<String> GetList() {
		return list;
	}
}

