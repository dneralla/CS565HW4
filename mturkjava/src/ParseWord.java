import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;



public class ParseWord {
	
	String inFilePath = "output.txt";
	String keywordsAllPath = "keywordsAll.txt";
	String keywordsUniquePath = "keywordsUnique.txt";
	
	
   public void setFilePaths(String fileName,String keyAll ,String keyUnique)
   {
	   this.inFilePath = fileName ;
	   this.keywordsAllPath=keyAll;
	   this.keywordsUniquePath=keyUnique;
   }
	
	
	public static void main(String[] args) {
		ParseWord parseWord = new ParseWord();
		parseWord.setFilePaths("", "", "");
		parseWord.getAllWords();
		parseWord.getUniqueWords();
		
	}


	public  void getAllWords() {
		// TODO Auto-generated method stub
		// Set reader and writer
		System.out.println("--Start getting keywords...");
		File inFile = MyIO.createFile(inFilePath, true);
		File outFile = MyIO.createFile(keywordsAllPath, false);
		BufferedReader bufferedReader = MyIO.createReader(inFile);
		BufferedWriter bufferedWriter = MyIO.createWriter(outFile);	
		String line = null;

		// Execute
		try {
			line = bufferedReader.readLine();
			while (line != null) {
				// Here: Parse the line to get the three keywords
				// System.out.println(line);
				String[] keywords = getKeywords(line);
				if (keywords != null) {
					for(String keyword: keywords) {
						// System.out.println(keyword);
						/** If we want to keep Capital letters, uncomment this sentence and comment the next one */
						// writeKeywords(keyword, bufferedWriter);
						writeKeywords(keyword.toLowerCase(), bufferedWriter);
					}
				}
				line = bufferedReader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Close reader and writer
		MyIO.close(bufferedReader);
		MyIO.close(bufferedWriter);
		System.out.println("--Keywords got.");
	}

	private static void writeKeywords(String keyword,
			BufferedWriter bufferedWriter) throws IOException {
		// TODO Auto-generated method stub
		bufferedWriter.append(keyword + "\n");
	}
	
	private static String[] getKeywords(String line) {
		// TODO Auto-generated method stub
		String[] blocks = line.split("\t");
		if (blocks.length >= 35) {
			return new String[] {blocks[30], blocks[32], blocks[34]};
		}
		return null;
	}
	
	
	public  void getUniqueWords() {
		System.out.println("--Start extracting unique keywords...");
		// Set reader and writer
		File inFile = MyIO.createFile(keywordsAllPath, true);		
		File outFile = MyIO.createFile(keywordsUniquePath, false);
		BufferedReader bufferedReader = MyIO.createReader(inFile);
		BufferedWriter bufferedWriter = MyIO.createWriter(outFile);
		
		// Read inFile and build hashMap
		// User HashMap instead of HashSet in case we want to use counter for each unique word
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		try {
			String keyword = bufferedReader.readLine();
			while (keyword != null) {
				addKeywordToHashMap(keyword, hashMap);
				keyword = bufferedReader.readLine();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Iterate hashMap and write outFile
		Iterator<Entry<String, Integer>> iterator = hashMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Integer> entry = iterator.next();
			// System.out.println(entry.getKey() + entry.getValue());
			try {
				bufferedWriter.append(entry.getKey() + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Close reader and writer
		MyIO.close(bufferedReader);
		MyIO.close(bufferedWriter);
		System.out.println("--Unique keywords extracted.");
	}

	private static void addKeywordToHashMap(String keyword,
			HashMap<String, Integer> hashMap) {
		// TODO Auto-generated method stub
		if (! hashMap.containsKey(keyword)) {	// First time we see the keyword
			hashMap.put(keyword, 1);
		} else {								// Add value by 1
			hashMap.put(keyword, hashMap.get(keyword)+1);
		}
	}
	
}
