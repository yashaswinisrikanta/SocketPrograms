package client;

import java.net.*;
import java.io.*;


public class UpperCaseClient {

  public static void main(String[] args) {

    int port = 80;
    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
    String accStr=null;  
try{
    System.out.println("Enter the string to be converted to upper case");
    accStr = br1.readLine();
}
catch(Exception e){
	
	e.printStackTrace();
}
    for (int i = 0; i < args.length; i++) {
      try {
         URL u = new URL(args[i]);
         if (u.getPort() != -1) port = u.getPort();
         if (!(u.getProtocol().equalsIgnoreCase("http"))) {
            System.err.println("Sorry. I only understand http.");
           continue;
         }
         Socket s = new Socket(u.getHost(), port);
         OutputStream theOutput = s.getOutputStream();
         PrintWriter pw = new PrintWriter(theOutput, false);
         pw.print("GET " + u.getFile()+"/?message="+accStr + " HTTP/1.0\r\n"  );
         pw.print("Accept: text/plain, text/html, text/*\r\n");
         pw.print("\r\n");
         pw.flush();
         InputStream in = s.getInputStream();
         InputStreamReader isr = new InputStreamReader(in);
         BufferedReader br = new BufferedReader(isr);
         int c;
         while ((c = br.read()) != -1) {
           System.out.print((char) c);
         }
         
        
      }
      
 catch (MalformedURLException ex) {
        System.err.println(args[i] + " is not a valid URL");
      }
      catch (IOException ex) {
        System.err.println(ex);
      }

    }

  }

}