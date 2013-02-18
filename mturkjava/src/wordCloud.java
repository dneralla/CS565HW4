import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class wordCloud{
	public static void main(String[] args) throws IOException{
	readPurified();
	}

	
public static void readPurified() throws IOException{
	
	File purifiedFile=new File("keywordsPurified.txt");
	
	BufferedReader br = MyIO.createReader(purifiedFile);
	String words="";
	try {
		String word = br.readLine();
		while (word != null) {
			words=words+" "+word;
			word = br.readLine();
		}
		sendPostRequest("http://www.wordle.net/advanced", words);
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	
}
	
public static void sendPostRequest(String url, String words) {

			StringBuffer sb = null;

		    try {

		        String data = URLEncoder.encode("text", "UTF-8") + "="
		        		+ URLEncoder.encode(words, "UTF-8");
		                //+ URLEncoder.encode("hello how are you hello hello how are hi abc hkk", "UTF-8");
		        
		        FileOutputStream fos = new FileOutputStream("wcl.html", true);
		        StringBuffer strContent;

		        URL requestUrl = new URL(url);
		        HttpURLConnection conn = (HttpURLConnection) requestUrl
		                .openConnection();
		        conn.setDoOutput(true);
		        conn.setRequestMethod("POST");

		        OutputStreamWriter osw = new OutputStreamWriter(
		                conn.getOutputStream());
		        osw.write(data);
		        osw.flush();

		        BufferedReader br = new BufferedReader(new InputStreamReader(
		                conn.getInputStream()));

		        String in = "";
		        sb = new StringBuffer();
		        while ((in = br.readLine()) != null) {
		            sb.append(in + "\n");
		        }
		        
		        strContent=sb;
		        fos.write(strContent.toString().getBytes());
		        fos.close();
		        
		        osw.close();
		        br.close();
		    } catch (UnsupportedEncodingException e) {
		        e.printStackTrace();
		    } catch (MalformedURLException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return; // sb.toString();
		}
	}

