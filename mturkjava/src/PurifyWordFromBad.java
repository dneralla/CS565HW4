import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;


public class PurifyWordFromBad {

	static String keywordsAllPath = "keywordsAll.txt";
	static String keywordsOffencePath = "keywordsUnique2.txt";
	static String keywordsPurifiedPath = "keywordsPurified.txt";

	public static void main(String[] args) {
		getPurifiedWords();
	}


	public static void getPurifiedWords() {
		// TODO Auto-generated method stub
		// Build the hashMap
		System.out.println("--Start building hashMap...");
		File inFileOffence = MyIO.createFile(keywordsOffencePath, true);
		BufferedReader bufferedReaderUnique = MyIO.createReader(inFileOffence);

		HashSet<String> hashSet = new HashSet<String>();
		String line = null;
		try {
			line = bufferedReaderUnique.readLine();
			while (line != null) {
				// System.out.println(line);
				hashSet.add(line);
				line = bufferedReaderUnique.readLine();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		MyIO.close(bufferedReaderUnique);
		System.out.println("--hashMap built successfully!");
		
		// Set reader and writer
		System.out.println("--Start purifying words...");
		File inFileAll = MyIO.createFile(keywordsAllPath, true);
		File outFile = MyIO.createFile(keywordsPurifiedPath, false);
		BufferedReader bufferedReaderAll = MyIO.createReader(inFileAll);
		BufferedWriter bufferedWriter = MyIO.createWriter(outFile);		

		// Execute
		try {
			line = bufferedReaderAll.readLine();
			while (line != null) {
				// Here: Parse the line to get the three keywords
				// System.out.println(line);
				if (! hashSet.contains(line)) {	// Write keywords that are in the hashSet
					bufferedWriter.append(line + "\n");
				}
				line = bufferedReaderAll.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Close reader and writer
		MyIO.close(bufferedReaderAll);
		MyIO.close(bufferedWriter);
		System.out.println("--All words purified!");
	}

}
