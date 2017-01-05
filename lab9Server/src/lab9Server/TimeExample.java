package lab9Server;
import java.io.*;
import java.net.*;

public class TimeExample {
	public static void main(String[] args) {
        try {
            Socket s = new Socket("time-A.timefreq.bldrdoc.gov", 13);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    s.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
                System.out.println(line);

            s.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }
//	public static void main(String[] args) {
//		try{ 
//			Socket t = new Socket("time-A.timefreq.bldrdoc.gov",13);
//			BufferedReader is = new BufferedReader(new InputStreamReader(t.getInputStream()));
//			boolean more = true;
//		
//			while (more){ 
//				String str = is.readLine();
//				if (str == null) more = false;
//				else	System.out.println(str);
//			}
//			if (!t.isClosed()) t.close();
//			
//		}catch(IOException e){ 
//			System.out.println("Error" + e); 
//		}
//	}
}