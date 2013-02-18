import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class MyIO {
	static File createFile(String path, boolean isInFile) {
		File file = new File(path);
		if (! file.exists()) {
			if (isInFile) {
				System.out.println("The source file, " + path + ", does not exist in the filefolder.");
			} else {
				System.out.println("The target file, " + path + " does not exist in the filefolder, let us create one.");				
			}
		}
		return file;
	}
	
	
	static BufferedWriter createWriter(File file) {
		// TODO Auto-generated method stub
		try {
			return new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	static BufferedReader createReader(File file) {
		// TODO Auto-generated method stub
		try {
			return new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	static void close(BufferedWriter bufferedWriter) {
		// TODO Auto-generated method stub
		try {
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	static void close(BufferedReader bufferedReader) {
		// TODO Auto-generated method stub
		try {
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
