package multipleChatRoom;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
            System.err.println(
                "Usage: java SocketClient <host name> <port number>");
            System.exit(1);
        }
 
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
 
        try {
            Socket clientSocket = new Socket(hostName, portNumber);
            
            SocketClient client = new SocketClient();
            
            //start server data reader thread
            client.new ServerDataReader(clientSocket).start();
            
            //start client data sender thread
            client.new ClinetDataSender(clientSocket).start();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }
	
	class ServerDataReader extends Thread{
		BufferedReader in = null;
		Socket clientSocket = null;
		public ServerDataReader(Socket clientSocket){
			this.clientSocket = clientSocket;
		}
		
		public void run(){
			try{
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String serverResponse;
				while ((serverResponse = in.readLine()) != null){
					System.out.println(serverResponse);
	                if(serverResponse.equalsIgnoreCase("Client connection closed")){
	                	System.out.println("Breaking the client loop and making it free to exit.");
	                	closeConnection();
	                	break;
	                }	
				}
				System.out.println("Terminating client process.");
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				closeConnection();
			}
		}
		
		private void closeConnection(){
			try{
				in.close();
			}catch(Exception ex){
				
			}
		}
	}
	
	class ClinetDataSender extends Thread{
		Socket clientSocket= null;
		PrintWriter out = null;
		BufferedReader stdIn = null;
		
		public ClinetDataSender(Socket clientSocket){
			this.clientSocket = clientSocket;
		}
		
		public void run(){
			try{
				out = new PrintWriter(clientSocket.getOutputStream(), true);            
	            stdIn = new BufferedReader(new InputStreamReader(System.in));
	                       
	            String clientInput;
	            while ((clientInput = stdIn.readLine()) != null) {
	                out.println(clientInput);                
	            }
			} catch(Exception ex) {
				ex.printStackTrace();
			} finally {
				closeConnection();
			}
			
		}
		
		private void closeConnection(){
			try{
				out.close();
				stdIn.close();
			}catch(Exception ex){
				
			}
		}
		
	}
}
